package app.joshie.sourswelter.block;

import app.joshie.sourswelter.CommonClass;
import app.joshie.sourswelter.Constants;
//import app.joshie.sourswelter.particle.SourParticles;
import net.minecraft.client.particle.Particle;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.MushroomBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

import static app.joshie.sourswelter.block.CommonBlocks.SOURSHROOM_BLOCK;


public class SwelterBlocks {

    public static Block register(Block pBlock, String path) {
        return Registry.register(BuiltInRegistries.BLOCK, ResourceLocation.fromNamespaceAndPath("sourswelter", path), pBlock);
    }

    public static BlockItem registerBlockItem(BlockItem pBlock, String path) {
        return Registry.register(BuiltInRegistries.ITEM, ResourceLocation.fromNamespaceAndPath("sourswelter", path), pBlock);
    }

    public static final Block SOUR_MUSHROOM = register(
            CommonBlocks.SOUR_MUSHROOM, "sour_mushroom"
    );

    public static final BlockItem SOUR_MUSHROOM_ITEM = registerBlockItem(
            CommonBlocks.SOUR_MUSHROOM_ITEM, "sour_mushroom"
    );

    public static final Block SOURSPREAD = register(
            new SourspreadBlock(
                    BlockBehaviour.Properties.of()
                            .mapColor(MapColor.COLOR_GREEN)
                            .randomTicks()
                            .strength(0.6F)
                            .sound(SoundType.GRASS)
            ) {
                @Override
                public void animateTick(BlockState p_221789_, Level p_221790_, BlockPos p_221791_, RandomSource p_221792_) {
                    super.animateTick(p_221789_, p_221790_, p_221791_, p_221792_);
                    if (p_221792_.nextInt(10) == 0) {
                        p_221790_.addParticle(ParticleTypes.MYCELIUM, (double)p_221791_.getX() + p_221792_.nextDouble(), (double)p_221791_.getY() + 1.1, (double)p_221791_.getZ() + p_221792_.nextDouble(), 0.0, 0.0, 0.0);
                    }

                }
            }, "sourspread"

    );

    public static final BlockItem SOURSPREAD_ITEM = registerBlockItem(
            new BlockItem(
                    SOURSPREAD,
                    new Item.Properties()
            ), "sourspread"
    );


    public static final BlockItem SOURSHROOM_BLOCK_ITEM = registerBlockItem(
            CommonBlocks.SOURSHROOM_BLOCK_ITEM,
            "sourshroom_block"
    );

    public static final Block SOURSHROOM_BLOCK = register(
            CommonBlocks.SOURSHROOM_BLOCK, "sourshroom_block"
    );

    public static final BlockItem SOURSHROOM_SPORE_BLOCK_ITEM = registerBlockItem(
            CommonBlocks.SOURSHROOM_SPORE_BLOCK_ITEM,
            "sourshroom_spore_block"
    );

    public static final Block SOURSHROOM_SPORE_BLOCK = register(
            CommonBlocks.SOURSHROOM_SPORE_BLOCK, "sourshroom_spore_block"
    );

    public static final Block SOURSPROUT = register(
            CommonBlocks.SOURSPROUT, "soursprout"
    );

    public static final BlockItem SOURSPROUT_ITEM = registerBlockItem(
            CommonBlocks.SOURSPROUT_ITEM,
            "soursprout"
    );

    public static final Block SOURSPROUT_PLANT = register(
            CommonBlocks.SOURSPROUT_PLANT, "soursprout_plant"
    );

    public static void registerModBlocks() {
        Constants.LOG.info("[Sour Swelter] Registered blocks");
    }

}
