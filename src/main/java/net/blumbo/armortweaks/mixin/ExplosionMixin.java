package net.blumbo.armortweaks.mixin;

import net.minecraft.world.damagesource.BadRespawnPointDamage;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.boss.enderdragon.EndCrystal;
import net.minecraft.world.level.Explosion;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(Explosion.class)
public class ExplosionMixin {

    @Shadow @Final @Nullable private Entity source;

    @Shadow @Final private DamageSource damageSource;

    // Nerf crystal and bed/anchor damage
    @ModifyArg(method = "explode", index = 1, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z"))
    protected float explode(float damage) {
        if (source instanceof EndCrystal || damageSource instanceof BadRespawnPointDamage) damage *= 0.5625F;
        return damage;
    }

}
