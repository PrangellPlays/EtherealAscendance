package dev.soulsparkstudios.etherealascendance.mixin.client;

import dev.soulsparkstudios.etherealascendance.client.render.world.WorldRendererDuck;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.math.MatrixStack;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin implements WorldRendererDuck {
    public Runnable etherealascendance$fogCallback = null;

    @Inject(method = "renderSky(Lnet/minecraft/client/util/math/MatrixStack;Lorg/joml/Matrix4f;FLnet/minecraft/client/render/Camera;ZLjava/lang/Runnable;)V", at = @At("HEAD"))
    private void etherealascendance$cacheFogCallbackBecauseFabricApiDoesNot(MatrixStack matrices, Matrix4f projectionMatrix, float tickDelta, Camera camera, boolean thickFog, Runnable fogCallback, CallbackInfo ci) {
        etherealascendance$fogCallback = fogCallback;
    }

    @Override
    public Runnable etherealascendance$consumeFogCallback() {
        Runnable callback = etherealascendance$fogCallback == null ? () -> {} : etherealascendance$fogCallback;
        this.etherealascendance$fogCallback = null;

        return callback;
    }
}