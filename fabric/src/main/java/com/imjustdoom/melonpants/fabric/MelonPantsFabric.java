package com.imjustdoom.melonpants.fabric;

import com.imjustdoom.melonpants.MelonPants;
import net.fabricmc.api.ModInitializer;

public class MelonPantsFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        MelonPants.init();
    }
}
