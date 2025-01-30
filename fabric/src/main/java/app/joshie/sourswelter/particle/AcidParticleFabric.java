package app.joshie.sourswelter.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import org.jetbrains.annotations.Nullable;


public class AcidParticleFabric extends AcidParticle {
    public AcidParticleFabric(ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed, SpriteSet pSprites) {
        super(pLevel, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed, pSprites);
    }
    public static class Factory implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprite;
        public Factory(SpriteSet sprite) {
            this.sprite = sprite;
        }
        @Nullable
        @Override
        public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double dx, double dy, double dz) {
            AcidParticle particle = new AcidParticle(level, x, y, z, dx, dy, dz, this.sprite);
            particle.pickSprite(this.sprite);
            return particle;
        }
    }
}
