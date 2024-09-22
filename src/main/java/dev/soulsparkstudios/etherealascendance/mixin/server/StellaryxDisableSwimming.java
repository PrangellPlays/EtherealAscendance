package dev.soulsparkstudios.etherealascendance.mixin.server;

import dev.soulsparkstudios.etherealascendance.init.EtherealAscendanceBlocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({PlayerEntity.class})
public abstract class StellaryxDisableSwimming extends LivingEntity {
    @Shadow public abstract boolean isPlayer();

    public StellaryxDisableSwimming(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(
            method = {"updateSwimming"},
            at = {@At("HEAD")},
            cancellable = true
    )

    public void etherealascendance$disableSwimmingInStellaryx(CallbackInfo ci) {
        LivingEntity entity = (LivingEntity) (Object) this;
        if (entity instanceof PlayerEntity player) {
            if (getWorld().getBlockState(player.getBlockPos()).isOf(EtherealAscendanceBlocks.STELLARYX)) {
                this.setSwimming(false);
                ci.cancel();
            }
        }
    }
}
