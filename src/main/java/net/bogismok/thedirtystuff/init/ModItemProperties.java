package net.bogismok.thedirtystuff.init;

import net.bogismok.thedirtystuff.TheDirtyStuff;
import net.bogismok.thedirtystuff.item.ModItems;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;

public class ModItemProperties {
    public static void addCustomItemProperties() {
        ItemProperties.register(ModItems.CIGARETTE.get(), ResourceLocation.fromNamespaceAndPath(TheDirtyStuff.MOD_ID, "lit"), (itemStack, clientLevel, livingEntity, i) -> {
            if (itemStack.get(ModDataComponentTypes.LIT.get()) != null && itemStack.get(ModDataComponentTypes.LIT.get())) {
                return 1;
            }
            return 0;
        });

        ItemProperties.register(ModItems.CIGAR.get(), ResourceLocation.fromNamespaceAndPath(TheDirtyStuff.MOD_ID, "lit"), (itemStack, clientLevel, livingEntity, i) -> {
            if (itemStack.get(ModDataComponentTypes.LIT.get()) != null && itemStack.get(ModDataComponentTypes.LIT.get())) {
                return 1;
            }
            return 0;
        });
    }
}