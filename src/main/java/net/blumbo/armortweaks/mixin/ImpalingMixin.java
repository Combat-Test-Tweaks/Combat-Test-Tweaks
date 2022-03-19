package net.blumbo.armortweaks.mixin;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.item.enchantment.TridentImpalerEnchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TridentImpalerEnchantment.class)
public class ImpalingMixin {

    // Nerf impaling enchantment
    @Inject(at = @At("HEAD"), method = "getDamageBonus", cancellable = true)
    protected void getDamageBonus(int i, LivingEntity livingEntity, CallbackInfoReturnable<Float> cir) {

        if (livingEntity == null || livingEntity.getMobType() != MobType.WATER && !livingEntity.isInWaterOrRain()) {
            cir.setReturnValue(0.0F);
        } else {
            cir.setReturnValue((float)i * 1.5F);
        }

    }

}
