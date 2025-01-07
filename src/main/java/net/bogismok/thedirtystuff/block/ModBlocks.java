package net.bogismok.thedirtystuff.block;

import net.bogismok.thedirtystuff.TheDirtyStuff;
import net.bogismok.thedirtystuff.block.custom.DriedTobaccoLeavesPalletBlock;
import net.bogismok.thedirtystuff.block.custom.DryingRackBlock;
import net.bogismok.thedirtystuff.block.custom.TobaccoBlock;
import net.bogismok.thedirtystuff.block.custom.TobaccoLeavesPalletBlock;
import net.bogismok.thedirtystuff.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, TheDirtyStuff.MOD_ID);

    //blocks
    public static final RegistryObject<Block> DRYING_RACK = registerBlock("drying_rack",
            () -> new DryingRackBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).noOcclusion().strength(1.0F, 1.0F).randomTicks()));

    //item blocks
    public static final RegistryObject<Block> TOBACCO = BLOCKS.register("tobacco",
            () -> new TobaccoBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WHEAT)));
    public static final RegistryObject<Block> TOBACCO_LEAVES_PALLET = BLOCKS.register("tobacco_leaves_pallet",
            () -> new TobaccoLeavesPalletBlock(BlockBehaviour.Properties.of().noOcclusion()));
    public static final RegistryObject<Block> DRIED_TOBACCO_LEAVES_PALLET = BLOCKS.register("dried_tobacco_leaves_pallet",
            () -> new DriedTobaccoLeavesPalletBlock(BlockBehaviour.Properties.of().noOcclusion()));

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}