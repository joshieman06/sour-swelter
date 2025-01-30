package app.joshie.sourswelter.particle;

import app.joshie.sourswelter.Constants;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class AcidParticle extends TextureSheetParticle {
    private static final double EFFECT_RADIUS = 1;
    private final SpriteSet spriteSet;

    // Constructor
    public AcidParticle(ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed, SpriteSet pSprites) {
        super(pLevel, pX, pY, pZ, 0.0, 0.0, 0.0);
        this.spriteSet = pSprites;
        this.friction = 0.96F;
        this.speedUpWhenYMotionIsBlocked = true;
        this.xd *= 0.0;
        this.yd *= 0.0;
        this.zd *= 0.0;
        this.xd += pXSpeed;
        this.yd += pYSpeed;
        this.zd += pZSpeed;
        this.hasPhysics = true;
        this.gravity = 0;
        this.setSpriteFromAge(pSprites);

        // Set initial size
        this.quadSize = 1f;

        // Set lifetime to 3 seconds (60 ticks)
        this.lifetime = 60 + random.nextInt(10);
    }

    // Override tick method to handle size change
    @Override
    public void tick() {
        if (20 >= (this.lifetime - age)) {
            this.alpha = (float) (((lifetime - age) / 20.0) - 0.05);
        }
        super.tick();
        // Check if we are in the first second
        this.setSpriteFromAge(spriteSet);
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

}