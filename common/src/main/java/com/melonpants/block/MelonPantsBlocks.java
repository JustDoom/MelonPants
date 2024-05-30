package com.melonpants.block;

import com.melonpants.MelonPants;
import com.melonpants.item.MelonPantsItems;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CarvedPumpkinBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

import java.util.function.Supplier;

public class MelonPantsBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(MelonPants.MOD_ID, Registries.BLOCK);

    // Blocks
    public static final RegistrySupplier<Block> CARVED_MELON = registerBlock("carved_melon", () ->
            new CarvedPumpkinBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_LIGHT_GREEN)
                    .strength(1.0f)
                    .sound(SoundType.WOOD).lightLevel((blockStatex) -> 15)
                    .pushReaction(PushReaction.DESTROY)),
            MelonPants.MELONPANTS_TAB);

    public static final RegistrySupplier<Block> HAPPY_CARVED_MELON = registerBlock("happy_carved_melon", () ->
                    new CarvedPumpkinBlock(BlockBehaviour.Properties.of()
                            .mapColor(MapColor.COLOR_LIGHT_GREEN)
                            .strength(1.0f)
                            .sound(SoundType.WOOD).lightLevel((blockStatex) -> 15)
                            .pushReaction(PushReaction.DESTROY)),
            MelonPants.MELONPANTS_TAB);

    private static <T extends Block> RegistrySupplier<T> registerBlock(String name, Supplier<T> block, RegistrySupplier<CreativeModeTab> tab) {
        RegistrySupplier<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, tab);
        return toReturn;
    }

    private static <T extends Block> RegistrySupplier<T> registerOnlyBlock(String name, Supplier<T> block) {
        return BLOCKS.register(name, block);
    }

    private static <T extends Block> RegistrySupplier<Item> registerBlockItem(String name, RegistrySupplier<T> block, RegistrySupplier<CreativeModeTab> tab) {
        return MelonPantsItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().arch$tab(tab)));
    }

    public static void init() {
        BLOCKS.register();
    }
}
