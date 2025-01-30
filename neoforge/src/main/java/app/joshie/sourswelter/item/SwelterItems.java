package app.joshie.sourswelter.item;

import app.joshie.sourswelter.Constants;
import app.joshie.sourswelter.block.CommonBlocks;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class SwelterItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, Constants.MOD_ID);

    public static final DeferredHolder<Item, Item> SOURSPRAYER = ITEMS.register("soursprayer", () -> CommonItems.SOUR_SPRAY_ITEM);


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
