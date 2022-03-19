package net.blumbo.armortweaks.mixin;

import net.minecraft.world.effect.AttackDamageMobEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AttackDamageMobEffect.class)
public class AttackDamageMobEffectMixin {

    @Shadow @Final protected double multiplier;

    // Buff strength and weakness effect
    @Inject(at = @At("HEAD"), method = ("getAttributeModifierValue"), cancellable = true)
    protected void getAttributeModifierValue(int i, AttributeModifier attributeModifier, CallbackInfoReturnable<Double> cir) {

        cir.setReturnValue(this.multiplier * 1.5 * (double)(i + 1));

    }

}
