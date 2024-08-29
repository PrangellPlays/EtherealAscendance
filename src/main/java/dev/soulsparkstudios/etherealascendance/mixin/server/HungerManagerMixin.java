package dev.soulsparkstudios.etherealascendance.mixin.server;

import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HungerManager.class)
public abstract class HungerManagerMixin {
    @Unique
    private PlayerEntity cachedPlayer = null;

    @Shadow
    public abstract void setFoodLevel(int foodLevel);

    @Shadow
    public abstract void setSaturationLevel(float saturationLevel);

    @Shadow
    public abstract void setExhaustion(float exhaustion);

    @Inject(method = "update", at = @At("HEAD"))
    private void etherealascendance$forceFullHunger(PlayerEntity player, CallbackInfo ci) {
        cachedPlayer = player;
        setFoodLevel(20);
        setSaturationLevel(0);
        setExhaustion(0);
    }
}
