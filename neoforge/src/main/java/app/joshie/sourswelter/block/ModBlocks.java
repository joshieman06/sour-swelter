package app.joshie.sourswelter.block;


import app.joshie.sourswelter.Constants;
import app.joshie.sourswelter.SourSwelter;
//import app.joshie.sourswelter.particle.SourParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;


public class ModBlocks {

    // Create DeferredRegister for Blocks and Items
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Registries.BLOCK, Constants.MOD_ID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, Constants.MOD_ID);

    // Register the block using the common block
    public static final DeferredHolder<Block, Block> SOUR_MUSHROOM = BLOCKS.register("sour_mushroom", () -> CommonBlocks.SOUR_MUSHROOM);

    // Register the BlockItem for the block
    public static final DeferredHolder<Item, BlockItem> SOUR_MUSHROOM_ITEM = ITEMS.register("sour_mushroom", () -> CommonBlocks.SOUR_MUSHROOM_ITEM);

    public static final DeferredHolder<Block, Block> SOURSPREAD = BLOCKS.register("sourspread", () -> new SourspreadBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PURPLE).randomTicks().strength(0.6F).sound(SoundType.GRASS))
    {
        @Override
        public void animateTick(BlockState p_221789_, Level p_221790_, BlockPos p_221791_, RandomSource
        p_221792_) {
        super.animateTick(p_221789_, p_221790_, p_221791_, p_221792_);
        if (p_221792_.nextInt(10) == 0) {
            p_221790_.addParticle(ParticleTypes.MYCELIUM, (double)p_221791_.getX() + p_221792_.nextDouble(), (double)p_221791_.getY() + 1.1, (double)p_221791_.getZ() + p_221792_.nextDouble(), 0.0, 0.0, 0.0);
        }

    }
    }
    );
    public static final DeferredHolder<Item, BlockItem> SOURSPREAD_ITEM = ITEMS.register("sourspread", () -> new BlockItem(SOURSPREAD.get(), new Item.Properties()));


    public static final DeferredHolder<Block, Block> SOURSHROOM_BLOCK = BLOCKS.register("sourshroom_block", () -> CommonBlocks.SOURSHROOM_BLOCK);

    public static final DeferredHolder<Item, BlockItem> SOURSHROOM_BLOCK_ITEM = ITEMS.register("sourshroom_block", () -> CommonBlocks.SOURSHROOM_BLOCK_ITEM);


    public static final DeferredHolder<Block, Block> SOURSHROOM_SPORE_BLOCK = BLOCKS.register("sourshroom_spore_block", () -> CommonBlocks.SOURSHROOM_SPORE_BLOCK);

    public static final DeferredHolder<Item, BlockItem> SOURSHROOM_SPORE_BLOCK_ITEM = ITEMS.register("sourshroom_spore_block", () -> CommonBlocks.SOURSHROOM_SPORE_BLOCK_ITEM);

    public static final DeferredHolder<Block, Block> SOURSPROUT_BLOCK = BLOCKS.register("soursprout", () -> CommonBlocks.SOURSPROUT);

    public static final DeferredHolder<Item, BlockItem> SOURSPROUT_BLOCK_ITEM = ITEMS.register("soursprout", () -> CommonBlocks.SOURSPROUT_ITEM);

    public static final DeferredHolder<Block, Block> SOURSPROUT_PLANT_BLOCK = BLOCKS.register("soursprout_plant", () -> CommonBlocks.SOURSPROUT_PLANT);



    // Register method to call in the main mod class
    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
        ITEMS.register(eventBus);
    }
}
