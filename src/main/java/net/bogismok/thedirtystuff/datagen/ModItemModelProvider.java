package net.bogismok.thedirtystuff.datagen;

import net.bogismok.thedirtystuff.TheDirtyStuff;
import net.bogismok.thedirtystuff.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, TheDirtyStuff.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        basicItem(ModItems.TOBACCO_SEEDS.get());
        basicItem(ModItems.TOBACCO_LEAVES.get());
        basicItem(ModItems.DRIED_TOBACCO_LEAVES.get());
    }
}
