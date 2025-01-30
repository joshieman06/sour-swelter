package app.joshie.sourswelter.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.NetherVines;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class SoursproutBlock extends GrowingPlantHeadBlock {
    public static final int MAX_HEIGHT = 4;
    public static final MapCodec<net.minecraft.world.level.block.TwistingVinesBlock> CODEC = simpleCodec(net.minecraft.world.level.block.TwistingVinesBlock::new);
    public static final VoxelShape SHAPE = Block.box(2.0, 0.0, 2.0, 14.0, 14.0, 14.0);

    public MapCodec<net.minecraft.world.level.block.TwistingVinesBlock> codec() {
        return CODEC;
    }

    public SoursproutBlock(BlockBehaviour.Properties p_154864_) {
        super(p_154864_, Direction.UP, SHAPE, false, 0.1);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        // Check the current height of the plant
        int height = getHeight(level, pos);

        // Only allow growth if the height is less than MAX_HEIGHT
        if (height < MAX_HEIGHT) {
            super.randomTick(state, level, pos, random);
        }
    }

    // Method to get the height of the plant based on the block position
    private int getHeight(ServerLevel level, BlockPos pos) {
        int height = 0;
        BlockPos currentPos = pos;

        while (level.getBlockState(currentPos).is(this) ||level.getBlockState(currentPos).is(this.getBodyBlock()) ) {
            height++;
            currentPos = currentPos.below(); // Move down to check previous segments
        }

        return height;
    }

    protected int getBlocksToGrowWhenBonemealed(RandomSource p_222649_) {
        return 1;
    }

    protected Block getBodyBlock() {
        return CommonBlocks.SOURSPROUT_PLANT;
    }

    protected boolean canGrowInto(BlockState p_154869_) {
        return NetherVines.isValidGrowthState(p_154869_);
    }
}
