package com.melonpants.item;

import com.melonpants.MelonPants;
import com.melonpants.item.custom.ModArmorMaterials;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;

public class MelonPantsItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(MelonPants.MOD_ID, Registries.ITEM);

    public static final RegistrySupplier<Item> MELON_HELMET = ITEMS.register("melon_helmet", () -> new ArmorItem(ModArmorMaterials.MELON,
            ArmorItem.Type.HELMET, new Item.Properties().arch$tab(MelonPants.MELONPANTS_TAB)));

    public static final RegistrySupplier<Item> MELON_CHESTPLATE = ITEMS.register("melon_chestplate", () -> new ArmorItem(ModArmorMaterials.MELON,
            ArmorItem.Type.CHESTPLATE, new Item.Properties().arch$tab(MelonPants.MELONPANTS_TAB)));

    public static final RegistrySupplier<Item> MELON_PANTS = ITEMS.register("melon_pants", () -> new ArmorItem(ModArmorMaterials.MELON,
            ArmorItem.Type.LEGGINGS, new Item.Properties().arch$tab(MelonPants.MELONPANTS_TAB)));

    public static final RegistrySupplier<Item> MELON_BOOTS = ITEMS.register("melon_boots", () -> new ArmorItem(ModArmorMaterials.MELON,
            ArmorItem.Type.BOOTS, new Item.Properties().arch$tab(MelonPants.MELONPANTS_TAB)));

//    public static final RegistrySupplier<Item> MELON_SHIELD = ITEMS.register("melon_shield", () -> new CustomShieldItem(new Item.Properties().durability(100).arch$tab(MelonPants.MELONPANTS_TAB)));

    public static void init() {
        ITEMS.register();
    }
}
