package app.joshie.sourswelter.entity;

import app.joshie.sourswelter.Constants;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(Registries.ENTITY_TYPE, Constants.MOD_ID);



    public static final DeferredHolder<EntityType<?>, EntityType<Entity>> ACID_ENTITY = ENTITIES.register("acid_entity", () -> EntityType.Builder.of(AcidEntity::new, MobCategory.MISC) // Entity supplier and category
            .sized(0.6F, 1.95F) // Size (width, height)
            .build(ResourceLocation.fromNamespaceAndPath("sourswelter", "acid_entity").toString()));

    public static final DeferredHolder<EntityType<?>, EntityType<SourscourgeEntity>> SOURSCOURGE = ENTITIES.register("sourscourge", () -> EntityType.Builder.of(SourscourgeEntity::new, MobCategory.MONSTER)
            .sized(0.6F, 1.95F) // Size (width, height)
            .build(ResourceLocation.fromNamespaceAndPath("sourswelter", "sourscourge").toString()));

    public static void register(IEventBus eventBus) {
        ENTITIES.register(eventBus);
    }
}
