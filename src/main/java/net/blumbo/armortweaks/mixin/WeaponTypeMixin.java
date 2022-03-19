package net.blumbo.armortweaks.mixin;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.WeaponType;
import net.minecraft.world.level.Level;
import net.minecraft.world.scores.Objective;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WeaponType.class)
public abstract class WeaponTypeMixin{

    @Shadow @Final public static WeaponType SWORD;

    @Shadow @Final public static WeaponType AXE;

    @Shadow @Final public static WeaponType PICKAXE;

    @Shadow @Final public static WeaponType TRIDENT;

    // Buff weapon damage
    @Inject(method = "getDamage", at = @At("HEAD"), cancellable = true)
    public void getDamage(Tier tier, CallbackInfoReturnable<Float> cir) {

        if (tier == Tiers.WOOD || tier == Tiers.GOLD) return;

        if (this.equals(SWORD)) cir.setReturnValue(tier.getAttackDamageBonus() + 3.0F);
        else if (this.equals(AXE)) cir.setReturnValue(tier.getAttackDamageBonus() + 4.0F);
        else if (this.equals(PICKAXE)) cir.setReturnValue(tier.getAttackDamageBonus() + 2.0F);
        else if (this.equals(TRIDENT)) cir.setReturnValue(6.0F);

    }

}
