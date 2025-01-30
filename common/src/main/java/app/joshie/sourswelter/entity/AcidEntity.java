package app.joshie.sourswelter.entity;

import app.joshie.sourswelter.damagetypes.SourDamageTypes;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.damagesource.DamageSource;

import java.util.ArrayList;

public class AcidEntity extends Entity {
    private Vec3 velocity;
    private int lifetime; // Total lifetime in ticks
    private int age;      // Current age in ticks
    private ArrayList<LivingEntity> harmed;
    private Entity owner;

    public AcidEntity(EntityType<? extends Entity> entityType, Level level) {
        super(entityType, level);
        this.noPhysics = true; // Allows collision checks
        this.lifetime = 60; // Random lifetime between 60-70 ticks
        this.age = 0;
        this.velocity = Vec3.ZERO; // Default velocity
    }

    // Constructor for initializing with position and velocity
    public AcidEntity(EntityType<? extends Entity> entityType, Level level, Vec3 position, Vec3 velocity, Entity owner) {
        this(entityType, level);
        this.setPos(position.x, position.y, position.z);
        this.velocity = velocity;
        this.owner = owner;
    }



    public static AcidEntity create(EntityType<? extends Entity> type, Level level, Vec3 position, Vec3 velocity, Entity owner) {
        AcidEntity entity = new AcidEntity(type, level);
        entity.setPos(position.x, position.y, position.z);
        entity.velocity = velocity;
        entity.owner = owner;
        return entity;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {

    }

    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide()) return; // Only handle logic on the server

        if (age >= lifetime) {
            this.remove(RemovalReason.DISCARDED); // Remove entity after its lifetime
            return;
        }

        // Update position based on velocity
        Vec3 newPosition = this.position().add(velocity);
        this.setPos(newPosition.x, newPosition.y, newPosition.z);

        // Apply deceleration
        double decelerationFactor = 0.96; // Adjust this for desired deceleration
        this.velocity = this.velocity.scale(decelerationFactor);

        // Handle collision with blocks
        if (this.level().getBlockState(this.blockPosition()).isSolidRender(this.level(), this.blockPosition())) {
            this.remove(RemovalReason.DISCARDED); // Stop the entity if it hits a block
            return;
        }

        // Apply effects to nearby entities
        applyEffects();

        // Increment age
        this.age++;
    }

    @Override
    public boolean isInvisible() {
        return true; // Always invisible
    }

    @Override
    public boolean isPushable() {
        return false; // Not pushable
    }

    @Override
    public boolean isPickable() {
        return false; // Not pickable
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compoundTag) {

    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compoundTag) {

    }

    private void applyEffects() {
        AABB boundingBox = new AABB(this.position().subtract(1, 1, 1), this.position().add(1, 1, 1)); // 1-block radius
        this.level().getEntitiesOfClass(LivingEntity.class, boundingBox).forEach(entity -> {
            // Apply effects to entities
            if ((entity != this.owner || age > 10) && !(entity instanceof SourscourgeEntity)) {
                Holder<DamageType> type = level().registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(SourDamageTypes.ACID_SPRAY);
                entity.hurt(new DamageSource(type, this.owner) {
                    @Override
                    public Component getLocalizedDeathMessage(LivingEntity pLivingEntity) {
                        String s = "death.attack." + this.type().msgId();
                        if (owner != entity) {
                            if (owner instanceof Player livingOwner) {

                                String s1 = s + ".player";
                                return livingOwner.getKillCredit() != null ? net.minecraft.network.chat.Component.translatable(s1, new Object[]{entity.getDisplayName(), owner.getDisplayName()}) : net.minecraft.network.chat.Component.translatable(s, new Object[]{owner.getDisplayName()});
                            } else {
                                return net.minecraft.network.chat.Component.translatable(s, new Object[]{entity.getDisplayName()});
                            }
                        } else {
                            if (owner == entity) {
                                String s1 = s + ".self";
                                return net.minecraft.network.chat.Component.translatable(s1, new Object[]{owner.getDisplayName()});
                            } else {
                                return net.minecraft.network.chat.Component.translatable(s, new Object[]{owner.getDisplayName()});
                            }
                        }
                    }
                }, 2); // Example: Apply damage
                entity.addEffect(new MobEffectInstance(MobEffects.POISON, 100, 0), owner);
            }
        });
    }
}
