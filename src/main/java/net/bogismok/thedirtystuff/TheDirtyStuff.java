package net.bogismok.thedirtystuff;

import com.mojang.logging.LogUtils;
import net.bogismok.thedirtystuff.block.ModBlocks;
import net.bogismok.thedirtystuff.block.entity.ModBlockEntities;
import net.bogismok.thedirtystuff.init.ModDataComponentTypes;
import net.bogismok.thedirtystuff.init.ModCreativeTabs;
import net.bogismok.thedirtystuff.init.ModItemProperties;
import net.bogismok.thedirtystuff.item.ModItems;
import net.bogismok.thedirtystuff.recipe.ModRecipes;
import net.bogismok.thedirtystuff.screen.custom.DryingRackScreen;
import net.bogismok.thedirtystuff.screen.ModMenuTypes;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import org.slf4j.Logger;

@Mod(TheDirtyStuff.MOD_ID)
public class TheDirtyStuff {
    public static final String MOD_ID = "thedirtystuff";

    public static final Logger LOGGER = LogUtils.getLogger();

    public TheDirtyStuff(FMLJavaModLoadingContext context) {

        IEventBus modEventBus = context.getModEventBus();
        modEventBus.addListener(this::commonSetup);
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        ModCreativeTabs.register(modEventBus);

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);

        ModBlockEntities.register(modEventBus);
        ModMenuTypes.register(modEventBus);

        ModDataComponentTypes.register(modEventBus);

        ModRecipes.register(modEventBus);

        // Register our mod's ForgeConfigSpec so that Forge can create and load the config file for us
        context.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            ComposterBlock.COMPOSTABLES.put(ModItems.TOBACCO_LEAVES.get(), 0.65f);
            ComposterBlock.COMPOSTABLES.put(ModItems.TOBACCO_SEEDS.get(), 0.3f);
        });
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            MenuScreens.register(ModMenuTypes.DRYING_RACK_MENU.get(), DryingRackScreen::new);
            ModItemProperties.addCustomItemProperties();
        }
    }

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public class LootTableModifier {
        @SubscribeEvent
        public static void onLootTableLoad(LootTableLoadEvent event) {
            if (event.getName().equals(ResourceLocation.fromNamespaceAndPath("minecraft","chests/village/village_plains_house"))) {
                LootPool pool = LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.TOBACCO_SEEDS.get()))
                        .build();

                event.getTable().addPool(pool);
            }
        }
    }
}
