package com.melonpants.forge;

import dev.architectury.platform.forge.EventBuses;
import com.melonpants.MelonPants;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(MelonPants.MOD_ID)
public class MelonPantsForge {
    public MelonPantsForge() {
        // Submit our event bus to let architectury register our content on the right time
        EventBuses.registerModEventBus(MelonPants.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        MelonPants.init();
    }
}
