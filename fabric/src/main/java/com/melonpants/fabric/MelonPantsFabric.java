package com.melonpants.fabric;

import com.melonpants.MelonPants;
import net.fabricmc.api.ModInitializer;

public class MelonPantsFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        MelonPants.init();
    }
}
