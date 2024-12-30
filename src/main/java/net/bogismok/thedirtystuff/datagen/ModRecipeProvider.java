package net.bogismok.thedirtystuff.datagen;

import net.bogismok.thedirtystuff.block.ModBlocks;
import net.bogismok.thedirtystuff.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pRegistries) {
        super(pOutput, pRegistries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.DRYING_RACK.get())
                .pattern("###")
                .pattern("###")
                .pattern("X X")
                .define('#', Tags.Items.RODS_WOODEN)
                .define('X', ItemTags.PLANKS)
                .unlockedBy("got_wood", has(ItemTags.PLANKS)).save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.CIGARETTE.get(), 1)
                .requires(ModItems.DRIED_TOBACCO_LEAVES.get())
                .requires(Items.PAPER)
                .unlockedBy("has_dried_tobacco", has(ModItems.DRIED_TOBACCO_LEAVES.get())).save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.CIGAR.get(), 1)
                .requires(ModItems.DRIED_TOBACCO_LEAVES.get())
                .requires(ModItems.DRIED_TOBACCO_LEAVES.get())
                .requires(ModItems.DRIED_TOBACCO_LEAVES.get())
                .requires(Items.PAPER)
                .unlockedBy("has_dried_tobacco", has(ModItems.DRIED_TOBACCO_LEAVES.get())).save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.TOBACCO_SEEDS.get(), 1)
                .requires(ModItems.TOBACCO_LEAVES.get())
                .unlockedBy("has_tobacco", has(ModItems.TOBACCO_LEAVES.get())).save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.TOBACCO_LEAVES_PACKAGE.get(), 1)
                .requires(ModItems.TOBACCO_LEAVES.get())
                .requires(ModItems.TOBACCO_LEAVES.get())
                .requires(ModItems.TOBACCO_LEAVES.get())
                .requires(ModItems.TOBACCO_LEAVES.get())
                .requires(ModItems.TOBACCO_LEAVES.get())
                .requires(ModItems.TOBACCO_LEAVES.get())
                .requires(ModItems.TOBACCO_LEAVES.get())
                .requires(ModItems.TOBACCO_LEAVES.get())
                .requires(Items.PAPER)
                .unlockedBy("has_tobacco", has(ModItems.TOBACCO_LEAVES.get())).save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.DRIED_TOBACCO_LEAVES_PACKAGE.get(), 1)
                .requires(ModItems.DRIED_TOBACCO_LEAVES.get())
                .requires(ModItems.DRIED_TOBACCO_LEAVES.get())
                .requires(ModItems.DRIED_TOBACCO_LEAVES.get())
                .requires(ModItems.DRIED_TOBACCO_LEAVES.get())
                .requires(ModItems.DRIED_TOBACCO_LEAVES.get())
                .requires(ModItems.DRIED_TOBACCO_LEAVES.get())
                .requires(ModItems.DRIED_TOBACCO_LEAVES.get())
                .requires(ModItems.DRIED_TOBACCO_LEAVES.get())
                .requires(Items.PAPER)
                .unlockedBy("has_dried_tobacco", has(ModItems.DRIED_TOBACCO_LEAVES.get())).save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.TOBACCO_LEAVES.get(), 8)
                .requires(ModItems.TOBACCO_LEAVES_PACKAGE.get())
                .unlockedBy("has_tobacco_package", has(ModItems.TOBACCO_LEAVES_PACKAGE.get())).save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.DRIED_TOBACCO_LEAVES.get(), 8)
                .requires(ModItems.DRIED_TOBACCO_LEAVES_PACKAGE.get())
                .unlockedBy("has_dried_tobacco_package", has(ModItems.DRIED_TOBACCO_LEAVES_PACKAGE.get())).save(recipeOutput);
    }
}
