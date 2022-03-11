package net.blumbo.armortweaks.mixin;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.world.entity.player.Inventory;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity {

    protected PlayerMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Shadow public abstract Iterable<ItemStack> getHandSlots();

    // Check for if attacked is in water
    boolean inWater = false;
    @Inject(at = @At("HEAD"), method = "attack")
    protected void attack(Entity entity, CallbackInfo ci) {
        inWater = entity.isInWater();
    }

    // Modify weapon and strength effect damage
    @Redirect(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;getAttributeValue(Lnet/minecraft/world/entity/ai/attributes/Attribute;)D"))
    protected double setAttackDamage(Player instance, Attribute attribute) {

        double damage = this.getAttributes().getValue(attribute);

        ItemStack itemStack = getMainHandItem();
        if (itemStack == null) return damage;

        Item item = itemStack.getItem();

        // Buff swords and tools
        if (item instanceof SwordItem || item instanceof AxeItem ||
                item instanceof PickaxeItem || item instanceof ShovelItem) {
            String type = item.toString().split("_")[0];
            if (!type.equals("wooden") && !type.equals("golden")) damage += 1;
        }

        // Buff sharpness enchantment
        int sharpness = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SHARPNESS, itemStack);
        if (sharpness > 0) {
            damage += -0.5 + sharpness * 0.5;
        }

        // Buff trident damage, nerf impaling
        if (item instanceof TridentItem) {
            damage += 1;
            int impaling = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.IMPALING, itemStack);
            if (inWater && impaling > 0) {
                damage -= impaling;
            }
        }

        // Buff strength effect
        if (hasEffect(MobEffect.byId(5))) {
            int strength = 1 + getEffect(MobEffect.byId(5)).getAmplifier();
            damage /= 1 + strength * 0.2F;
            damage *= 1 + strength * 0.3F;
        }

        return damage;

    }


}
