package net.bogismok.thedirtystuff.item;

import net.bogismok.thedirtystuff.TheDirtyStuff;
import net.bogismok.thedirtystuff.component.ModDataComponentTypes;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;

public class ModItemProperties {
    public static void addCustomItemProperties() {
        ItemProperties.register(ModItems.CIGARETTE.get(), ResourceLocation.fromNamespaceAndPath(TheDirtyStuff.MOD_ID, "amount"), (itemStack, clientLevel, livingEntity, i) -> {
            if (itemStack.get(ModDataComponentTypes.AMOUNT.get()) != null) {
                return itemStack.get(ModDataComponentTypes.AMOUNT.get()) * 0.05F;
            }
            return 0;
        });
    }
}
