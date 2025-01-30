package app.joshie.sourswelter.feature;

import app.joshie.sourswelter.Constants;
import app.joshie.sourswelter.block.CommonBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModFeatures {
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(Registries.FEATURE, Constants.MOD_ID);

    public static final DeferredHolder SOURSHROOM = FEATURES.register("sourshroom", () -> CommonFeatures.SOURSHROOM);

    public static void register(IEventBus event) {
        FEATURES.register(event);
    }
}
