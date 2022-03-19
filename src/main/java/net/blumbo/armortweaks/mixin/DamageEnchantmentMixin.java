package net.blumbo.armortweaks.mixin;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.item.enchantment.DamageEnchantment;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DamageEnchantment.class)
public abstract class DamageEnchantmentMixin {

    @Shadow @Final public int type;

    // Buff sharpness enchantment
    @Inject(at = @At("HEAD"), method = "getDamageBonus", cancellable = true)
    protected void getDamageBonus(int i, LivingEntity livingEntity, CallbackInfoReturnable<Float> cir) {

        if (type == 0) {
            cir.setReturnValue((float) Math.max(0, i));
        }

    }

}
