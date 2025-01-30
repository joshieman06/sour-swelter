package app.joshie.sourswelter.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HugeMushroomBlock;
import net.minecraft.world.level.block.MushroomBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

public class CommonBlocks {
    public static final ResourceKey<ConfiguredFeature<?, ?>> SOURSHROOM_FEATURE = ResourceKey.create(
            Registries.CONFIGURED_FEATURE,
            ResourceLocation.fromNamespaceAndPath("sourswelter", "sourshroom")
    );

    // Define the block
    public static final Block SOUR_MUSHROOM = new MushroomBlock(
            SOURSHROOM_FEATURE,
            BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_LIGHT_GREEN)
                    .noCollission()
                    .randomTicks()
                    .instabreak()
                    .hasPostProcess((blockState, blockGetter, blockPos) -> true)
                    .sound(SoundType.GRASS)
                    .lightLevel(p_50892_ -> 1)
                    .pushReaction(PushReaction.DESTROY)
    );



    // Define the BlockItem
    public static final BlockItem SOUR_MUSHROOM_ITEM = new BlockItem(SOUR_MUSHROOM, new Item.Properties());

    public static final Block SOURSHROOM_BLOCK = new HugeMushroomBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GREEN).instrument(NoteBlockInstrument.BASS).strength(0.2F).sound(SoundType.SLIME_BLOCK).ignitedByLava());
    public static final BlockItem SOURSHROOM_BLOCK_ITEM = new BlockItem(SOURSHROOM_BLOCK, new Item.Properties());

    public static final Block SOURSHROOM_SPORE_BLOCK = new HugeMushroomBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GREEN).instrument(NoteBlockInstrument.BASS).strength(0.2F).sound(SoundType.SLIME_BLOCK).ignitedByLava().lightLevel((p_50856_) -> {
        return 8;
    })) {
        public void animateTick(BlockState p_222503_, Level p_222504_, BlockPos p_222505_, RandomSource p_222506_) {
            int i = p_222505_.getX();
            int j = p_222505_.getY();
            int k = p_222505_.getZ();
            BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

            for(int l = 0; l < 30; ++l) {
                blockpos$mutableblockpos.set(i + Mth.nextInt(p_222506_, -10, 10), j - Mth.nextInt(p_222506_, -10, 10), k + Mth.nextInt(p_222506_, -10, 10));
                BlockState blockstate = p_222504_.getBlockState(blockpos$mutableblockpos);
                if (!blockstate.isCollisionShapeFullBlock(p_222504_, blockpos$mutableblockpos)) {
                    p_222504_.addParticle(ParticleTypes.SPORE_BLOSSOM_AIR, (double)blockpos$mutableblockpos.getX() + p_222506_.nextDouble(), (double)blockpos$mutableblockpos.getY() + p_222506_.nextDouble(), (double)blockpos$mutableblockpos.getZ() + p_222506_.nextDouble(), 0.0, 0.0, 0.0);
                }
            }

        }
    };
    public static final BlockItem SOURSHROOM_SPORE_BLOCK_ITEM = new BlockItem(SOURSHROOM_SPORE_BLOCK, new Item.Properties());

    public static final Block SOURSPROUT = new SoursproutBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GREEN).randomTicks().noCollission().instabreak().sound(SoundType.WEEPING_VINES).pushReaction(PushReaction.DESTROY));
    public static final Block SOURSPROUT_PLANT = new SoursproutPlantBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GREEN).noCollission().instabreak().sound(SoundType.WEEPING_VINES).pushReaction(PushReaction.DESTROY));

    public static final BlockItem SOURSPROUT_ITEM = new BlockItem(SOURSPROUT, new Item.Properties());




}
