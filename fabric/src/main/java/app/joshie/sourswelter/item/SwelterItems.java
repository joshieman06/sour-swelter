package app.joshie.sourswelter.item;

import app.joshie.sourswelter.Constants;
import app.joshie.sourswelter.block.CommonBlocks;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class SwelterItems {
    public static Item registerItem(Item pBlock, String path) {
        return Registry.register(BuiltInRegistries.ITEM, ResourceLocation.fromNamespaceAndPath("sourswelter", path), pBlock);
    }

    public static final Item SOUR_SPRAY = registerItem(
            CommonItems.SOUR_SPRAY_ITEM, "sour_spray"
    );

    public static void registerModItems() {
        Constants.LOG.info("[Sour Swelter] Registered items");
    }
}
