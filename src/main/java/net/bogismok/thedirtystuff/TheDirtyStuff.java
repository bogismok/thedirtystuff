package net.bogismok.thedirtystuff;

import com.mojang.logging.LogUtils;
import net.bogismok.thedirtystuff.block.ModBlocks;
import net.bogismok.thedirtystuff.block.entity.ModBlockEntities;
import net.bogismok.thedirtystuff.item.ModCreativeTabs;
import net.bogismok.thedirtystuff.item.ModItems;
import net.bogismok.thedirtystuff.recipe.ModRecipes;
import net.bogismok.thedirtystuff.screen.custom.DryingRackScreen;
import net.bogismok.thedirtystuff.screen.ModMenuTypes;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
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

        ModCreativeTabs.register(modEventBus);

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);

        ModBlockEntities.register(modEventBus);
        ModMenuTypes.register(modEventBus);

        ModRecipes.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {

    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            MenuScreens.register(ModMenuTypes.DRYING_RACK_MENU.get(), DryingRackScreen::new);
        }
    }
}
