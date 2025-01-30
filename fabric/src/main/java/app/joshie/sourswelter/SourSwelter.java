package app.joshie.sourswelter;

import app.joshie.sourswelter.block.SwelterBlocks;
import app.joshie.sourswelter.entity.SwelterEntities;
import app.joshie.sourswelter.feature.Sourshroom;
import app.joshie.sourswelter.particle.AcidParticle;
import app.joshie.sourswelter.particle.AcidParticleFabric;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.NoopRenderer;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.configurations.HugeMushroomFeatureConfiguration;

import static app.joshie.sourswelter.block.SwelterBlocks.registerModBlocks;
import static app.joshie.sourswelter.item.SwelterItems.registerModItems;
import static app.joshie.sourswelter.particle.ParticleTypes.ACID_PARTICLE;


public class SourSwelter implements ModInitializer {
    
    @Override
    public void onInitialize() {
        
        // This method is invoked by the Fabric mod loader when it is ready
        // to load your mod. You can access Fabric and Common code in this
        // project.

        // Use Fabric to bootstrap the Common mod.
        BlockRenderLayerMap.INSTANCE.putBlock(SwelterBlocks.SOUR_MUSHROOM, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(SwelterBlocks.SOURSPROUT, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(SwelterBlocks.SOURSPROUT_PLANT, RenderType.cutout());
        Constants.LOG.info("Hello Fabric world!");
        CommonClass.init();
        registerModBlocks();
        registerModItems();
        Registry.register(BuiltInRegistries.FEATURE, ResourceLocation.fromNamespaceAndPath("sourswelter", "sourshroom"), new Sourshroom(HugeMushroomFeatureConfiguration.CODEC));
        ParticleFactoryRegistry.getInstance().register(ACID_PARTICLE, AcidParticleFabric.Factory::new);
        EntityRendererRegistry.register(SwelterEntities.ACID_ENTITY, NoopRenderer::new);
    }
}
