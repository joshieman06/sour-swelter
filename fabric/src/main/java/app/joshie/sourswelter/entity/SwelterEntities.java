package app.joshie.sourswelter.entity;

import app.joshie.sourswelter.item.CommonItems;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.Item;


public class SwelterEntities {
    public static <T extends Entity> EntityType<T> registerEntity(EntityType<T> entityType, String path) {
        return Registry.register(
                BuiltInRegistries.ENTITY_TYPE,
                ResourceLocation.fromNamespaceAndPath("sourswelter", path),
                entityType
        );
    }

    public static final EntityType<AcidEntity> ACID_ENTITY = registerEntity(
            EntityType.Builder.<AcidEntity>of(AcidEntity::new, MobCategory.MISC)
                    .sized(0.6F, 1.95F)
                    .build(ResourceLocation.fromNamespaceAndPath("sourswelter", "acid_entity").toString()), // Explicitly define the type
            "acid_entity"
    );
}
