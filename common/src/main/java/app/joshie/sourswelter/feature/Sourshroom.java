package app.joshie.sourswelter.feature;

import app.joshie.sourswelter.block.CommonBlocks;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelWriter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.AbstractHugeMushroomFeature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.HugeMushroomFeatureConfiguration;

public class Sourshroom extends AbstractHugeMushroomFeature {
    public Sourshroom(Codec<HugeMushroomFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    protected int getTreeRadiusForHeight(int i, int i1, int i2, int i3) {
        return 0;
    }

    @Override
    protected void makeCap(LevelAccessor levelAccessor, RandomSource randomSource, BlockPos blockPos, int i, BlockPos.MutableBlockPos mutableBlockPos, HugeMushroomFeatureConfiguration hugeMushroomFeatureConfiguration) {
        // Not implemented, keep this if you need it for future use.
    }

    @Override
    public boolean place(FeaturePlaceContext<HugeMushroomFeatureConfiguration> context) {
        BlockPos basePos = context.origin();
        LevelWriter levelWriter = context.level(); // Get the LevelWriter instance
        RandomSource randomSource = context.random(); // Get the random source

        // Generate a random height for the stem
        int randomHeight = randomSource.nextInt(6) + 3; // Random height between 4 and 6
        int tiltDirection = randomSource.nextInt(3) - 1; // -1, 0, or 1 for x-axis

        // Place the bottom layer
        placeLayer(levelWriter, basePos.above(randomHeight-1), new int[][]{
                {1, 1, 1, 0, 1, 1, 1}, // ***0***
                {1, 0, 0, 1, 0, 0, 1}, // *00000*
                {1, 0, 1, 1, 1, 0, 1}, // *00000*
                {0, 1, 1, 1, 1, 1, 0}, // 0000000
                {1, 0, 1, 1, 1, 0, 1}, // *00000*
                {1, 0, 0, 1, 0, 0, 1}, // *00000*
                {1, 1, 1, 0, 1, 1, 1}  // ***0***
        }, tiltDirection, context.config(), context.random());

        // Place the middle layer
        placeLayer(levelWriter, basePos.above(randomHeight), new int[][]{
                {1, 1, 1, 1, 1, 1, 1}, // *******
                {1, 1, 0, 0, 0, 1, 1}, // **000**
                {1, 0, 0, 0, 0, 0, 1}, // *00000*
                {1, 0, 0, 0, 0, 0, 1}, // *00000*
                {1, 0, 0, 0, 0, 0, 1}, // *00000*
                {1, 1, 0, 0, 0, 1, 1}, // **000**
                {1, 1, 1, 1, 1, 1, 1}  // *******
        }, tiltDirection, context.config(), context.random());

        // Place the top layer
        placeLayer(levelWriter, basePos.above(randomHeight + 1), new int[][]{
                {1, 1, 1, 1, 1, 1, 1}, // *******
                {1, 1, 1, 1, 1, 1, 1}, // *******
                {1, 1, 1, 1, 1, 1, 1}, // *******
                {1, 1, 1, 0, 1, 1, 1}, // ***0***
                {1, 1, 1, 1, 1, 1, 1}, // *******
                {1, 1, 1, 1, 1, 1, 1}, // *******
                {1, 1, 1, 1, 1, 1, 1}  // *******
        }, tiltDirection, context.config(), context.random());

        // Place the stem with a random tilt
        placeTiltedStem(levelWriter, basePos.below(0), randomSource, randomHeight, tiltDirection); // Use the random height

        return true;
    }

    private void placeLayer(LevelWriter levelWriter, BlockPos startPos, int[][] layer, int tiltDirection, HugeMushroomFeatureConfiguration hugeMushroomFeatureConfiguration, RandomSource randomSource) {
        for (int y = 0; y < layer.length; y++) {
            for (int x = 0; x < layer[y].length; x++) {
                if (layer[y][x] == 0) { // Block
                    // Set the block using the LevelWriter
                    this.setBlock(levelWriter, startPos.offset(tiltDirection + x - 3, 0, y - 3), hugeMushroomFeatureConfiguration.capProvider.getState(randomSource, startPos.offset(tiltDirection + x - 3, 0, y - 3)));
                }
            }
        }
    }

    private void placeTiltedStem(LevelWriter levelWriter, BlockPos basePos, RandomSource randomSource, int height, int tiltDirection) {
        // Randomly determine the tilt direction
        int tiltHeight = randomSource.nextInt(height / 2) + 1; // Random height variation for tilt

        for (int y = 0; y < height; y++) {
            BlockPos position = basePos.above(y);


            // Tilt the stem based on the tilt direction
            if (y < tiltHeight) {
                this.setBlock(levelWriter, position, Blocks.MUSHROOM_STEM.defaultBlockState());
            } else {
                this.setBlock(levelWriter, position.offset(tiltDirection, 0, 0), Blocks.MUSHROOM_STEM.defaultBlockState());
            }
        }
    }
}
