package net.bogismok.thedirtystuff.init;

import net.bogismok.thedirtystuff.TheDirtyStuff;
import net.bogismok.thedirtystuff.block.ModBlocks;
import net.bogismok.thedirtystuff.item.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, TheDirtyStuff.MOD_ID);

    public static final RegistryObject<CreativeModeTab> THE_DIRTY_STUFF_TAB = CREATIVE_MODE_TABS.register("the_dirty_stuff_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.TOBACCO_LEAVES.get()))
                    .title(Component.translatable("creativetab.thedirtystuff.thedirtystuff"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModItems.TOBACCO_LEAVES.get());
                        output.accept(ModItems.DRIED_TOBACCO_LEAVES.get());
                        output.accept(ModItems.TOBACCO_LEAVES_PACKAGE.get());
                        output.accept(ModItems.DRIED_TOBACCO_LEAVES_PACKAGE.get());
                        output.accept(ModItems.TOBACCO_SEEDS.get());
                        output.accept(ModItems.CIGARETTE.get());
                        output.accept(ModItems.CIGAR.get());
                        output.accept(ModBlocks.DRYING_RACK.get());
                    }).build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
