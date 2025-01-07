package net.bogismok.thedirtystuff.item;

import net.bogismok.thedirtystuff.TheDirtyStuff;
import net.bogismok.thedirtystuff.block.ModBlocks;
import net.bogismok.thedirtystuff.item.custom.CigarItem;
import net.bogismok.thedirtystuff.item.custom.CigaretteItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, TheDirtyStuff.MOD_ID);

    //items
    public static final RegistryObject<Item> TOBACCO_LEAVES = ITEMS.register("tobacco_leaves",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> DRIED_TOBACCO_LEAVES = ITEMS.register("dried_tobacco_leaves",
            () -> new Item(new Item.Properties()));

    //custom items
    public static final RegistryObject<Item> CIGARETTE = ITEMS.register("cigarette",
            () -> new CigaretteItem(new Item.Properties().durability(12)));
    public static final RegistryObject<Item> CIGAR = ITEMS.register("cigar",
            () -> new CigarItem(new Item.Properties().durability(36)));

    //block items
    public static final RegistryObject<Item> TOBACCO_SEEDS = ITEMS.register("tobacco_seeds",
            () -> new ItemNameBlockItem(ModBlocks.TOBACCO.get(), new Item.Properties()));
    public static final RegistryObject<Item> TOBACCO_LEAVES_PACKAGE = ITEMS.register("tobacco_leaves_package",
            () -> new ItemNameBlockItem(ModBlocks.TOBACCO_LEAVES_PALLET.get(), new Item.Properties()));
    public static final RegistryObject<Item> DRIED_TOBACCO_LEAVES_PACKAGE = ITEMS.register("dried_tobacco_leaves_package",
            () -> new ItemNameBlockItem(ModBlocks.DRIED_TOBACCO_LEAVES_PALLET.get(), new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}