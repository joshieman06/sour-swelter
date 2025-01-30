package app.joshie.sourswelter.entity;

import app.joshie.sourswelter.Constants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;


import java.util.List;

public class SourscourgeEntity extends Mob {
    private static final EntityDataAccessor<Boolean> ATTACKING = SynchedEntityData.defineId(SourscourgeEntity.class, EntityDataSerializers.BOOLEAN);

    public AnimationState attackAnimationState = new AnimationState();

    private int attackAnimationTimeout;
    private int ticksUntilAttack = 30;
    private int ticksUntilNextAttack = 30;
    private boolean shouldCountTowardsNextAttack = true;

    public SourscourgeEntity(EntityType<? extends Mob> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    private void setupAnimationStates() {

        if(this.isAttacking() && this.attackAnimationTimeout<=0) {
            attackAnimationTimeout = 60;
            attackAnimationState.start(this.tickCount);
            Minecraft mc = Minecraft.getInstance();
            mc.player.sendSystemMessage(Component.literal("Started Animation"));
        } else {
            --this.attackAnimationTimeout;
        }

        if(!this.isAttacking()){
            Minecraft mc = Minecraft.getInstance();
            mc.player.sendSystemMessage(Component.literal("Stopped Animation"));
            Constants.LOG.info("Stopped Animation");
        }
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 40)
                .add(Attributes.ARMOR, 10);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide) {
            this.setupAnimationStates();
        }
        // Detect entities in the radius
        if (!this.level().isClientSide) {
            List<LivingEntity> nearbyEntities = this.level().getEntitiesOfClass(LivingEntity.class, getBoundingBox().inflate(2));

            if (!nearbyEntities.isEmpty()) {
                if (ticksUntilAttack > 0) {
                    ticksUntilAttack--;
                } else {
                    if (this.isAttacking()) {
                        // Complete the attack and start the cooldown
                        shouldCountTowardsNextAttack = false;
                        ticksUntilNextAttack = 30;
                        this.setAttacking(false);
                    } else if (!nearbyEntities.isEmpty()) {
                        // Start a new attack
                        attackEntities(nearbyEntities);
                        this.setAttacking(true);
                        ticksUntilAttack = 30; // Reset attack timer for the next attack cycle
                    }
                }

                if (ticksUntilNextAttack > 0) {
                    ticksUntilNextAttack--;
                } else {
                    shouldCountTowardsNextAttack = true;
                }
            }
        }
    }


    /**
     * Handles the attack logic for nearby entities.
     */
    private void attackEntities(List<LivingEntity> nearbyEntities) {
        for (LivingEntity entity : nearbyEntities) {
            if (entity != this) {
                this.launchSporeCone();
                return;
            }
        }
    }

    public void launchSporeCone() {
        if (!(this.level() instanceof ServerLevel serverLevel)) return;

        // Get the entity's position
        Vec3 origin = this.position().add(0, this.getEyeHeight() - 0.2, 0);

        // Parameters for the spore spread
        int particleCount = 36; // Number of particles in the half-sphere
        double sphereRadius = 3.0; // Radius of the sphere
        double speedModifier = 0.4; // Base speed of the particles
        ResourceLocation acidEntityId = ResourceLocation.fromNamespaceAndPath("sourswelter", "acid_entity");
        EntityType<?> acidEntityType = BuiltInRegistries.ENTITY_TYPE.get(acidEntityId);
        SimpleParticleType particle = BuiltInRegistries.PARTICLE_TYPE.get(ResourceLocation.fromNamespaceAndPath("sourswelter", "sourspray")) instanceof SimpleParticleType simpleParticleType
                ? simpleParticleType
                : ParticleTypes.SPORE_BLOSSOM_AIR; // Fallback to vanilla particles if not found

        // Generate and spawn particles
        for (int i = 0; i < particleCount; i++) {
            // Randomly generate a direction for the spore particle within a half-sphere (upper hemisphere)
            double theta = Math.acos(2 * random.nextDouble() - 1); // Random angle from the vertical axis (0 to PI)
            double phi = random.nextDouble() * 2 * Math.PI; // Random angle around the vertical axis (0 to 2*PI)

            // Make sure the angle only covers the upper half (y >= 0)
            if (theta > Math.PI / 2) {
                continue; // Skip particles that are in the lower hemisphere
            }

            // Spherical to Cartesian conversion
            double x = sphereRadius * Math.sin(theta) * Math.cos(phi);
            double z = sphereRadius * Math.sin(theta) * Math.sin(phi);
            double y = sphereRadius * Math.cos(theta); // This gives us the particle spread in the half-sphere

            // Create a velocity vector for the particle (direction is from the entity's position)
            Vec3 velocity = new Vec3(x, y, z).normalize().scale(speedModifier);

            // Spawn the particle at the calculated position with the velocity
            serverLevel.sendParticles(
                    particle,
                    origin.x, origin.y, origin.z, // Starting position
                    0, // Count (0 for custom velocity)
                    velocity.x, velocity.y, velocity.z, // Velocity
                    1.0 // Spread
            );

            // Spawn the acid entity (if desired, or use a different entity type for acid effects)
            serverLevel.addFreshEntity(AcidEntity.create(acidEntityType, serverLevel, origin, velocity, this));
        }

        this.playSound(SoundEvents.SPORE_BLOSSOM_BREAK, 1.0F, 1.0F); // Play sound effect
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder pBuilder) {
        super.defineSynchedData(pBuilder);
        pBuilder.define(ATTACKING, false); // Default to not attacking
    }

    public boolean isAttacking() {
        return this.entityData.get(ATTACKING);
    }

    public void setAttacking(boolean attacking) {
        this.entityData.set(ATTACKING, attacking);
    }

}
