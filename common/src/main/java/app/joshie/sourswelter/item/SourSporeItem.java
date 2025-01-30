package app.joshie.sourswelter.item;

import app.joshie.sourswelter.entity.AcidEntity;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraft.core.Registry;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SourSporeItem extends Item {
    private static final Random random = new Random();
    public SourSporeItem(Properties properties) {
        super(properties.durability(100)); // Set max durability here
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (level instanceof ServerLevel serverLevel) {
            // Reduce durability
            stack.hurtAndBreak(1, player, (hand == InteractionHand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND));

            // Spawn particles
            spawnAcidParticles(serverLevel, player);

            // Apply effect if particle hits entity
            applyAcidEffect(level, player);
        }

        return InteractionResultHolder.success(stack);
    }


        public static void spawnAcidParticles(Level level, LivingEntity player) {
        ResourceLocation acidEntityId = ResourceLocation.fromNamespaceAndPath("sourswelter", "acid_entity");
        EntityType<?> acidEntityType = BuiltInRegistries.ENTITY_TYPE.get(acidEntityId);
        if (level instanceof ServerLevel serverLevel) {
            // Get the player's look direction
            Vec3 lookDir = player.getLookAngle().normalize();

            // Set initial position a bit forward from the player's eyes
            Vec3 origin = player.position().add(0, player.getEyeHeight() - 0.2, 0).add(lookDir.scale(0.5));

            // Parameters for the spray effect
            int particleCount = 13; // 1 center particle + 6 surrounding particles + 6 triangle centers
            double speedModifier = 0.2; // Base speed of the particles
            double variationAmount = 0.3; // Amount of variation to add to each predefined position
            double outerHexagonRadius = 0.7; // Radius for the outer, tighter hexagon
            double innerHexagonRadius = 0.4; // Smaller radius for the inner hexagon (tighter)

            // Generate the predefined velocities (1 center particle and 6 surrounding particles)
            List<Vec3> predefinedVelocities = generateHexagonalVelocities(outerHexagonRadius);

            SimpleParticleType particle = BuiltInRegistries.PARTICLE_TYPE.get(ResourceLocation.fromNamespaceAndPath("sourswelter", "sourspray")) instanceof SimpleParticleType simpleParticleType
                    ? simpleParticleType
                    : null;
            // Add particles for the center and surrounding particles
            for (int i = 0; i <= 6; i++) {
                // Get the predefined velocity direction for surrounding particles
                Vec3 velocity = predefinedVelocities.get(i);

                // Add small variation to the velocity for randomness
                velocity = velocity.add(new Vec3(random.nextDouble() * variationAmount, random.nextDouble() * variationAmount, random.nextDouble() * variationAmount));

                // Normalize the velocity and scale it by the speed modifier
                velocity = velocity.normalize().scale(speedModifier);

                // Align with the player's look direction
                velocity = alignWithLookDirection(velocity, lookDir);

                // Get the particle type


                // Spawn surrounding particle with calculated position and velocity
                serverLevel.sendParticles(
                        particle,
                        origin.x, origin.y, origin.z, // Starting position
                        0, // Count (0 for custom velocity)
                        velocity.x, velocity.y, velocity.z, // Velocity
                        1.0 // Spread (not used with direct velocity)
                );
                serverLevel.addFreshEntity(AcidEntity.create(acidEntityType, serverLevel, origin, velocity, player));
            }

            // Generate and spawn the second, tighter hexagon (for the triangle centers)
            List<Vec3> innerHexagonVelocities = generateHexagonalVelocities(innerHexagonRadius);

            for (int i = 0; i <= 6; i++) {
                // Get the velocity for the inner hexagon (triangle centers)
                Vec3 velocity = innerHexagonVelocities.get(i);

                // Add small variation to the velocity for randomness
                velocity = velocity.add(new Vec3(random.nextDouble() * variationAmount, random.nextDouble() * variationAmount, random.nextDouble() * variationAmount));

                // Normalize the velocity and scale it by the speed modifier
                velocity = velocity.normalize().scale(speedModifier);

                // Align with the player's look direction
                velocity = alignWithLookDirection(velocity, lookDir);

                // Spawn inner hexagon particle (triangle center)
                serverLevel.sendParticles(
                        particle,
                        origin.x, origin.y, origin.z, // Starting position
                        0, // Count (0 for custom velocity)
                        velocity.x, velocity.y, velocity.z, // Velocity (direction)
                        1.0 // Spread (not used with direct velocity)
                );
            }
        }
    }

    // Generate predefined velocities: 1 center particle and 6 surrounding particles (hexagonal pattern)
    private static List<Vec3> generateHexagonalVelocities(double hexagonRadius) {
        List<Vec3> velocities = new ArrayList<>();

        // The center particle (no spread)
        velocities.add(new Vec3(0, 0, 1)); // Directly in the look direction

        // Surrounding 6 particles evenly spaced in a hexagon
        double angleStep = Math.toRadians(60); // 360 degrees / 6 particles

        for (int i = 0; i < 6; i++) {
            double angle = i * angleStep;

            // Calculate x, y components of the direction (circle around the center)
            double x = hexagonRadius * Math.cos(angle);
            double y = hexagonRadius * Math.sin(angle);

            // Add the velocity vector (scaled to a unit vector, centered on the player)
            velocities.add(new Vec3(x, y, 1)); // 1 along the z-axis (forward)
        }

        return velocities;
    }

    private static Vec3 alignWithLookDirection(Vec3 localDir, Vec3 lookDir) {
        // Find the orthogonal basis for the look direction
        Vec3 up = new Vec3(0, 1, 0);
        if (Math.abs(lookDir.dot(up)) > 0.99) {
            // Avoid singularities if lookDir is nearly vertical
            up = new Vec3(1, 0, 0);
        }

        Vec3 right = lookDir.cross(up).normalize();
        up = right.cross(lookDir).normalize();

        // Transform the local direction to world space
        return right.scale(localDir.x)
                .add(up.scale(localDir.y))
                .add(lookDir.scale(localDir.z));
    }

    private void applyAcidEffect(Level level, Player player) {
        // Check for entities in range and apply effects (e.g., poison)
    }
}
