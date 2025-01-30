package app.joshie.sourswelter.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GrowingPlantBodyBlock;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SoursproutPlantBlock extends GrowingPlantBodyBlock {
    public static final int MAX_HEIGHT = 4;
    public static final MapCodec<net.minecraft.world.level.block.TwistingVinesPlantBlock> CODEC = simpleCodec(net.minecraft.world.level.block.TwistingVinesPlantBlock::new);
    public static final VoxelShape SHAPE = Block.box(2.0, 0.0, 2.0, 14.0, 16.0, 14.0);
    public MapCodec<net.minecraft.world.level.block.TwistingVinesPlantBlock> codec() {
        return CODEC;
    }

    public SoursproutPlantBlock(BlockBehaviour.Properties p_154873_) {
        super(p_154873_, Direction.UP, SHAPE, false);
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

        while (level.getBlockState(currentPos).is(this)) {
            height++;
            currentPos = currentPos.below(); // Move down to check previous segments
        }

        return height;
    }

    protected GrowingPlantHeadBlock getHeadBlock() {
        return (GrowingPlantHeadBlock) CommonBlocks.SOURSPROUT;
    }
}
