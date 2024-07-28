package dev.soulsparkstudios.etherealascendance.mixin.client;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.soulsparkstudios.etherealascendance.registry.EtherealAscendanceFluids;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.CameraSubmersionType;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.fluid.FluidState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(net.minecraft.client.render.BackgroundRenderer.class)
public abstract class BackgroundRendererMixin {

    @Shadow
    private static float red;

    @Shadow
    private static float green;

    @Shadow
    private static float blue;

    @ModifyArgs(method = "render", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;clearColor(FFFF)V", remap = false))
    private static void etherealascendance$render(Args args, Camera camera, float partialTicks, ClientWorld level, int renderDistanceChunks, float bossColorModifier) {
        FluidState state = level.getFluidState(camera.getBlockPos());
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if (EtherealAscendanceFluids.isStellaryx(state) && camera.getSubmersionType() == CameraSubmersionType.WATER) {
            red = (float) 36 / 255;
            green = (float) 36 / 255;
            blue = (float) 41 / 255;
        }
    }
    @Inject(method = "applyFog", at = @At("HEAD"), cancellable = true)
    private static void etherealascendance$applyFog(Camera camera, BackgroundRenderer.FogType fogType, float viewDistance, boolean thickFog, float tickDelta, CallbackInfo ci) {
        assert MinecraftClient.getInstance().world != null;
        FluidState state = MinecraftClient.getInstance().world.getFluidState(camera.getBlockPos());
        if (EtherealAscendanceFluids.isStellaryx(state) && camera.getSubmersionType() == CameraSubmersionType.WATER) {
            RenderSystem.setShaderFogStart(-5);
            RenderSystem.setShaderFogEnd(10);
            ci.cancel();
        }
    }
}
