package net.bogismok.thedirtystuff;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static net.minecraftforge.registries.ForgeRegistries.MOB_EFFECTS;

@Mod.EventBusSubscriber(modid = TheDirtyStuff.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config
{
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    public static final ForgeConfigSpec.ConfigValue<String> CIGARETTE_EFFECT = BUILDER
            .comment("Effect of a potion added when cigarette is smoked")
            .define("cigaretteEffect", "minecraft:speed");

    private static final ForgeConfigSpec.IntValue CIGARETTE_EFFECT_LEVEL = BUILDER
            .comment("Level of a potion added when cigarette is smoked")
            .defineInRange("cigaretteEffectLevel", 1, 0, Integer.MAX_VALUE);

    private static final ForgeConfigSpec.IntValue CIGARETTE_EFFECT_DURATION = BUILDER
            .comment("Duration of a potion added when cigarette is smoked (in ticks (20 ticks is 1 second))")
            .defineInRange("cigaretteEffectDuration", 100, 0, Integer.MAX_VALUE);

    public static final ForgeConfigSpec.ConfigValue<String> CIGAR_EFFECT = BUILDER
            .comment("Effect of a potion added when cigar is smoked")
            .define("cigarEffect", "minecraft:strength");

    private static final ForgeConfigSpec.IntValue CIGAR_EFFECT_LEVEL = BUILDER
            .comment("Level of a potion added when cigar is smoked")
            .defineInRange("cigarEffectLevel", 1, 0, Integer.MAX_VALUE);

    private static final ForgeConfigSpec.IntValue CIGAR_EFFECT_DURATION = BUILDER
            .comment("Duration of a potion added when cigar is smoked (in ticks (20 ticks is 1 second))")
            .defineInRange("cigarEffectDuration", 100, 0, Integer.MAX_VALUE);

    private static final ForgeConfigSpec.ConfigValue<List<? extends String>> IGNITERS_STRING = BUILDER
            .comment("A list of items that can light a cigarette.")
            .defineListAllowEmpty("igniters", List.of("minecraft:flint_and_steel", "minecraft:fire_charge"), Config::validateItemName);

    static final ForgeConfigSpec SPEC = BUILDER.build();

    public static int cigaretteEffectLevel;
    public static int cigaretteEffectDuration;
    public static Holder<MobEffect> cigaretteEffect;

    public static int cigarEffectLevel;
    public static int cigarEffectDuration;
    public static Holder<MobEffect> cigarEffect;

    public static Set<Item> igniters;

    private static boolean validateItemName(final Object obj)
    {
        return obj instanceof final String itemName && ForgeRegistries.ITEMS.containsKey(ResourceLocation.parse(itemName));
    }

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event)
    {
        cigaretteEffect = MOB_EFFECTS.getHolder(ResourceLocation.parse(CIGARETTE_EFFECT.get())).orElse(null);
        cigaretteEffectLevel = CIGARETTE_EFFECT_LEVEL.get();
        cigaretteEffectDuration = CIGARETTE_EFFECT_DURATION.get();

        cigarEffect = MOB_EFFECTS.getHolder(ResourceLocation.parse(CIGAR_EFFECT.get())).orElse(null);
        cigarEffectLevel = CIGAR_EFFECT_LEVEL.get();
        cigarEffectDuration = CIGAR_EFFECT_DURATION.get();

        igniters = IGNITERS_STRING.get().stream().map(itemName -> ForgeRegistries.ITEMS.getValue(ResourceLocation.parse(itemName)))
                .collect(Collectors.toSet());
    }
}
