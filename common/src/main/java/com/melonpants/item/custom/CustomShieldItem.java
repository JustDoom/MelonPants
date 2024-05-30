package com.melonpants.item.custom;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CustomShieldItem extends Item implements Equipable {
    public static final int EFFECTIVE_BLOCK_DELAY = 5;
    public static final float MINIMUM_DURABILITY_DAMAGE = 3.0F;
    public static final String TAG_BASE_COLOR = "Base";

    public CustomShieldItem(Item.Properties properties) {
        super(properties);
        DispenserBlock.registerBehavior(this, ArmorItem.DISPENSE_ITEM_BEHAVIOR);
    }

    public String getDescriptionId(ItemStack itemStack) {
        if (BlockItem.getBlockEntityData(itemStack) != null) {
            String var10000 = this.getDescriptionId();
            return var10000 + "." + getColor(itemStack).getName();
        } else {
            return super.getDescriptionId(itemStack);
        }
    }

    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> list, TooltipFlag tooltipFlag) {
        BannerItem.appendHoverTextFromBannerBlockEntityTag(itemStack, list);
    }

    public UseAnim getUseAnimation(ItemStack itemStack) {
        return UseAnim.BLOCK;
    }

    public int getUseDuration(ItemStack itemStack) {
        return 72000;
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        ItemStack itemStack = player.getItemInHand(interactionHand);
        player.startUsingItem(interactionHand);
        return InteractionResultHolder.consume(itemStack);
    }

    public boolean isValidRepairItem(ItemStack itemStack, ItemStack itemStack2) {
        return itemStack2.is(Items.MELON) || super.isValidRepairItem(itemStack, itemStack2);
    }

    public static DyeColor getColor(ItemStack itemStack) {
        CompoundTag compoundTag = BlockItem.getBlockEntityData(itemStack);
        return compoundTag != null ? DyeColor.byId(compoundTag.getInt("Base")) : DyeColor.WHITE;
    }

    public EquipmentSlot getEquipmentSlot() {
        return EquipmentSlot.OFFHAND;
    }
}
