package net.bogismok.thedirtystuff.item;

import net.bogismok.thedirtystuff.TheDirtyStuff;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, TheDirtyStuff.MOD_ID);

    public static final RegistryObject<Item> CIGARETTE = ITEMS.register("cigarette",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> TOBACCO_LEAFS = ITEMS.register("tobacco_leafs",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> DRIED_TOBACCO_LEAFS = ITEMS.register("dried_tobacco_leafs",
            () -> new Item(new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}