package net.bogismok.thedirtystuff.datagen;

import net.bogismok.thedirtystuff.block.custom.TobaccoBlock;
import net.bogismok.thedirtystuff.item.ModItems;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.bogismok.thedirtystuff.block.ModBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;

public class ModBlockLootTableProvider extends BlockLootSubProvider {
    protected ModBlockLootTableProvider(HolderLookup.Provider pRegistries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), pRegistries);
    }

    @Override
    protected void generate() {
        dropSelf(ModBlocks.DRYING_RACK.get());

        LootItemCondition.Builder lootItemConditionBuilder = LootItemBlockStatePropertyCondition.hasBlockStateProperties(ModBlocks.TOBACCO.get())
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(TobaccoBlock.AGE, TobaccoBlock.MAX_AGE));
        this.add(ModBlocks.TOBACCO.get(), this.createCropDrops(ModBlocks.TOBACCO.get(),
                ModItems.TOBACCO_LEAVES.get(), ModItems.TOBACCO_SEEDS.get(), lootItemConditionBuilder));
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}