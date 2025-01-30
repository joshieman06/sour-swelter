package app.joshie.sourswelter.event;

import app.joshie.sourswelter.entity.AcidEntity;
import app.joshie.sourswelter.entity.ModEntities;
import app.joshie.sourswelter.entity.SourscourgeEntity;
import app.joshie.sourswelter.entity.model.Sourscourge;
import app.joshie.sourswelter.entity.render.SourscourgeEntityRenderer;
import app.joshie.sourswelter.particle.AcidParticleProvider;
import net.minecraft.client.renderer.entity.NoopRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;


import static app.joshie.sourswelter.entity.ModEntities.SOURSCOURGE;
import static app.joshie.sourswelter.particle.ParticleTypes.SOUR_SPRAY;

@EventBusSubscriber(bus= EventBusSubscriber.Bus.MOD, modid = "sourswelter")
public class Handler {

    @SubscribeEvent
    public static void registerParticleProviders(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(SOUR_SPRAY.get(), AcidParticleProvider::new);
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.ACID_ENTITY.get(), NoopRenderer::new);
        event.registerEntityRenderer(SOURSCOURGE.get(), SourscourgeEntityRenderer::new);
    }

    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(Sourscourge.LAYER_LOCATION, Sourscourge::createBodyLayer);
    }

    @SubscribeEvent
    public static void onEntityAttributeCreation(EntityAttributeCreationEvent event) {
        event.put(SOURSCOURGE.get(), SourscourgeEntity.createAttributes().build());
    }
}
