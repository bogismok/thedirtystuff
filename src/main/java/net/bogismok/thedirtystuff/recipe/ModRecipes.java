package net.bogismok.thedirtystuff.recipe;

import net.bogismok.thedirtystuff.TheDirtyStuff;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, TheDirtyStuff.MOD_ID);
    public static final DeferredRegister<RecipeType<?>> TYPES =
            DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, TheDirtyStuff.MOD_ID);

    public static final RegistryObject<RecipeSerializer<DryingRackRecipe>> DRYING_RACK_SERIALIZER =
            SERIALIZERS.register("drying", DryingRackRecipe.Serializer::new);
    public static final RegistryObject<RecipeType<DryingRackRecipe>> DRYING_RACK_TYPE =
            TYPES.register("drying", () -> new RecipeType<DryingRackRecipe>() {
                @Override
                public String toString() {
                    return "drying";
                }
            });


    public static void register(IEventBus eventBus) {
        SERIALIZERS.register(eventBus);
        TYPES.register(eventBus);
    }
}
