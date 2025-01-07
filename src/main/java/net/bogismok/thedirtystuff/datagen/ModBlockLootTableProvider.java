package net.bogismok.thedirtystuff.datagen;

import net.bogismok.thedirtystuff.block.custom.DriedTobaccoLeavesPalletBlock;
import net.bogismok.thedirtystuff.block.custom.TobaccoBlock;
import net.bogismok.thedirtystuff.item.ModItems;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.bogismok.thedirtystuff.block.ModBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;

import static net.bogismok.thedirtystuff.block.custom.PalletBlock.LEVEL;

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

        //holy chatgpt
        this.add(ModBlocks.DRIED_TOBACCO_LEAVES_PALLET.get(), block -> {
            LootTable.Builder table = LootTable.lootTable();
            for (int level = 0; level <= 15; level++) {
                table.withPool(
                        this.applyExplosionCondition(
                                block,
                                LootPool.lootPool()
                                        .setRolls(ConstantValue.exactly(1.0F))
                                        .add(
                                                LootItem.lootTableItem(ModItems.DRIED_TOBACCO_LEAVES_PACKAGE.get())
                                                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(level + 1)))
                                                        .when(
                                                                LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                                                                        .setProperties(StatePropertiesPredicate.Builder.properties()
                                                                                .hasProperty(DriedTobaccoLeavesPalletBlock.LEVEL, level))
                                                        )
                                        )
                        )
                );
            }
            return table;
        });

        this.add(ModBlocks.TOBACCO_LEAVES_PALLET.get(), block -> {
            LootTable.Builder table = LootTable.lootTable();
            for (int level = 0; level <= 15; level++) {
                table.withPool(
                        this.applyExplosionCondition(
                                block,
                                LootPool.lootPool()
                                        .setRolls(ConstantValue.exactly(1.0F))
                                        .add(
                                                LootItem.lootTableItem(ModItems.TOBACCO_LEAVES_PACKAGE.get())
                                                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(level + 1)))
                                                        .when(
                                                                LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                                                                        .setProperties(StatePropertiesPredicate.Builder.properties()
                                                                                .hasProperty(DriedTobaccoLeavesPalletBlock.LEVEL, level))
                                                        )
                                        )
                        )
                );
            }
            return table;
        });
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}