package app.joshie.sourswelter.feature;

import app.joshie.sourswelter.entity.AcidEntity;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.levelgen.feature.Feature;

public class SwelterFeatures {
    public static Feature<?> registerEntity(Feature<?> pBlock, String path) {
        return Registry.register(BuiltInRegistries.FEATURE, ResourceLocation.fromNamespaceAndPath("sourswelter", path), pBlock);
    }

    public static final Feature<?> GIANT_SOURSHROOM = registerEntity(CommonFeatures.SOURSHROOM, "sourshroom");
}

