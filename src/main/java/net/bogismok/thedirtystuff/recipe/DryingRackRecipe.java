package net.bogismok.thedirtystuff.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public record DryingRackRecipe(Ingredient inputItem, ItemStack output) implements Recipe<DryingRackRecipeInput> {
    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> list = NonNullList.create();
        list.add(inputItem);
        return list;
    }

    @Override
    public boolean matches(DryingRackRecipeInput dryingRackRecipeInput, Level level) {
        if(level.isClientSide()) {
            return false;
        }

        return inputItem.test(dryingRackRecipeInput.getItem(0));
    }

    @Override
    public ItemStack assemble(DryingRackRecipeInput dryingRackRecipeInput, HolderLookup.Provider provider) {
        return output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int i, int i1) {
        return true;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider provider) {
        return output;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.DRYING_RACK_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.DRYING_RACK_TYPE.get();
    }

    public static class Serializer implements RecipeSerializer<DryingRackRecipe> {
        public static final MapCodec<DryingRackRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
                Ingredient.CODEC_NONEMPTY.fieldOf("ingredient").forGetter(DryingRackRecipe::inputItem),
                ItemStack.CODEC.fieldOf("result").forGetter(DryingRackRecipe::output)
        ).apply(inst, DryingRackRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, DryingRackRecipe> STREAM_CODEC =
                StreamCodec.composite(
                        Ingredient.CONTENTS_STREAM_CODEC, DryingRackRecipe::inputItem,
                        ItemStack.STREAM_CODEC, DryingRackRecipe::output,
                        DryingRackRecipe::new);


        @Override
        public MapCodec<DryingRackRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, DryingRackRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}