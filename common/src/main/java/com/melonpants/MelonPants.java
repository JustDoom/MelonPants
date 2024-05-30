package com.melonpants;

import com.melonpants.item.MelonPantsItems;
import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class MelonPants {
    public static final String MOD_ID = "melonpants";

    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(MelonPants.MOD_ID, Registries.CREATIVE_MODE_TAB);
    public static final RegistrySupplier<CreativeModeTab> MELONPANTS_TAB = TABS.register("melonpants_tab", () ->
            CreativeTabRegistry.create(Component.translatable("itemGroup." + MelonPants.MOD_ID + ".melonpants_tab"),
                    () -> new ItemStack(MelonPantsItems.MELON_PANTS.get())));
    
    public static void init() {
        TABS.register();
        MelonPantsItems.init();
    }
}
