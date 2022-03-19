package net.blumbo.armortweaks.mixin;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.boss.enderdragon.EndCrystal;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(EndCrystal.class)
public abstract class EndCrystalMixin extends Entity {

    public EndCrystalMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    // Change explosion entity to end crystal ("null" in vanilla)
    @ModifyArg(method = "hurt", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;explode(Lnet/minecraft/world/entity/Entity;DDDFLnet/minecraft/world/level/Explosion$BlockInteraction;)Lnet/minecraft/world/level/Explosion;"))
    protected Entity explode(Entity entity) {
        return this;
    }

}
