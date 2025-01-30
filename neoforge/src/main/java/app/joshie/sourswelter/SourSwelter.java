package app.joshie.sourswelter;


import app.joshie.sourswelter.block.ModBlocks;
//import app.joshie.sourswelter.particle.SourParticles;
import app.joshie.sourswelter.entity.ModEntities;
import app.joshie.sourswelter.event.Handler;
import app.joshie.sourswelter.feature.ModFeatures;
import app.joshie.sourswelter.item.SwelterItems;
import app.joshie.sourswelter.particle.ParticleTypes;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.RenderType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;

@Mod(Constants.MOD_ID)
public class SourSwelter {

    public SourSwelter(IEventBus eventBus) {

        // This method is invoked by the NeoForge mod loader when it is ready
        // to load your mod. You can access NeoForge and Common code in this
        // project.

        // Use NeoForge to bootstrap the Common mod.
        // Register blocks and items


        CommonClass.init();
        ModBlocks.register(eventBus);
        ModFeatures.register(eventBus);
        ParticleTypes.register(eventBus);
        SwelterItems.register(eventBus);
        ModEntities.register(eventBus);

//        SourParticles.registerParticles(eventBus);
    }
}