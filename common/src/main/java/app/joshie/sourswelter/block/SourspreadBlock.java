package app.joshie.sourswelter.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SpreadingSnowyDirtBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class SourspreadBlock extends SpreadingSnowyDirtBlock {
    public static final MapCodec<SourspreadBlock> CODEC = simpleCodec(SourspreadBlock::new);

    public MapCodec<SourspreadBlock> codec() {
        return CODEC;
    }


    public SourspreadBlock(BlockBehaviour.Properties p_54898_) {
        super(p_54898_);
    }


}
