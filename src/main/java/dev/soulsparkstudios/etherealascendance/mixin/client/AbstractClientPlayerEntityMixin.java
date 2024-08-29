package dev.soulsparkstudios.etherealascendance.mixin.client;

import com.mojang.authlib.GameProfile;
import dev.soulsparkstudios.etherealascendance.EtherealAscendance;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.encryption.PlayerPublicKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractClientPlayerEntity.class)
public abstract class AbstractClientPlayerEntityMixin extends PlayerEntity {
    public AbstractClientPlayerEntityMixin(World world, BlockPos blockPos, float f, GameProfile gameProfile, @Nullable PlayerPublicKey playerPublicKey) {
        super(world, blockPos, f, gameProfile);
    }
    @Inject(method = "getSkinTexture", at = @At("HEAD"), cancellable = true)
    private void getCrewSkinTexture(CallbackInfoReturnable<Identifier> cir) {
        if(EtherealAscendance.prangellplaysUUID.contains(this.getUuid())) {
            cir.setReturnValue(new Identifier("etherealascendance", "textures/entity/crew/prangellplays.png"));
        } else if (EtherealAscendance.blimptylerUUID.contains(this.getUuid())) {
            cir.setReturnValue(new Identifier("etherealascendance", "textures/entity/crew/blimp_tyler.png"));
        }
    }
    @Inject(method = "getModel", at = @At("HEAD"), cancellable = true)
    private void getCrewModel(CallbackInfoReturnable<String> cir) {
        if(EtherealAscendance.prangellplaysUUID.contains(this.getUuid())) {
            cir.setReturnValue("slim");
        } else if (EtherealAscendance.blimptylerUUID.contains(this.getUuid())) {
            cir.setReturnValue("wide");
        }
    }
}
