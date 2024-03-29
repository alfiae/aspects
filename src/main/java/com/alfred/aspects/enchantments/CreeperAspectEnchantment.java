package com.alfred.aspects.enchantments;

import com.alfred.aspects.AspectsConfig;
import com.alfred.aspects.AspectsMod;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.explosion.EntityExplosionBehavior;
import net.minecraft.world.explosion.Explosion;

public class CreeperAspectEnchantment extends Enchantment {
    public CreeperAspectEnchantment() {
        super(Enchantment.Rarity.VERY_RARE, EnchantmentTarget.VANISHABLE, new EquipmentSlot[] { EquipmentSlot.MAINHAND });
    }

    @Override
    public boolean canAccept(Enchantment other) {
        return !AspectsMod.isAspectEnchantment(other) && super.canAccept(other);
    }

    @Override
    public boolean isAcceptableItem(ItemStack stack) {
        return AspectsConfig.getInstance().isItemAllowed(stack.getItem()) && AspectsConfig.getInstance().creeperAspectEnabled && super.isAcceptableItem(stack);
    }

    @Override
    public int getMinPower(int level) {
        return 25 + 30 * (level - 1);
    }

    @Override
    public int getMaxPower(int level) {
        return super.getMinPower(level) + 50;
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    public void onTargetDamaged(LivingEntity user, Entity target, int level) {
        if (!user.getEntityWorld().isClient)
            user.getEntityWorld().createExplosion(AspectsConfig.getInstance().creeperAspectDamagesSelf ? null : user, Explosion.createDamageSource(user.getEntityWorld(), AspectsConfig.getInstance().creeperAspectDamagesSelf ? null : user), new EntityExplosionBehavior(user), target.getX(), target.getY(), target.getZ(), level * 2, false, World.ExplosionSourceType.MOB);
    }

    @Override
    public boolean isAvailableForEnchantedBookOffer() {
        return false;
    }
}
