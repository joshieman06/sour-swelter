package app.joshie.sourswelter.particle;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class ParticleTypes {
    public static <T extends ParticleType<?>> T registerParticle(T particleType, String path) {
        return Registry.register(BuiltInRegistries.PARTICLE_TYPE,
                ResourceLocation.fromNamespaceAndPath("sourswelter", path), particleType);
    }

    // Register the particle type explicitly as SimpleParticleType
    public static final SimpleParticleType ACID_PARTICLE = registerParticle(FabricParticleTypes.simple(), "sourspray");
}
