package dev.soulsparkstudios.etherealascendance.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.mojang.blaze3d.systems.RenderSystem;
import dev.soulsparkstudios.etherealascendance.EtherealAscendanceClient;
import dev.soulsparkstudios.etherealascendance.client.render.sky.SkyBox;
import dev.soulsparkstudios.etherealascendance.dimension.EtherealAscendanceDimensions;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(WorldRenderer.class)
public abstract class WorldRendererMixin {
    @Shadow private ClientWorld world;
    @Shadow private double lastCameraY;
    @Mutable @Shadow @Final private static Identifier SUN;
    @Mutable @Shadow @Final private static Identifier MOON_PHASES;

    @Shadow private Frustum frustum;

    @ModifyVariable(
            method = "renderSky(Lnet/minecraft/client/util/math/MatrixStack;Lorg/joml/Matrix4f;FLnet/minecraft/client/render/Camera;ZLjava/lang/Runnable;)V",
            at = @At(shift = At.Shift.AFTER, target = "Lnet/minecraft/client/world/ClientWorld;getSkyColor(Lnet/minecraft/util/math/Vec3d;F)Lnet/minecraft/util/math/Vec3d;", value = "INVOKE_ASSIGN"),
            index = 8
    )
    private Vec3d darkenSky(Vec3d original) {
        if (world.getRegistryKey().equals(World.OVERWORLD) || EtherealAscendanceDimensions.isEtherealAscendanceDimension(world)) {
            assert MinecraftClient.getInstance().cameraEntity != null;
            double y = MinecraftClient.getInstance().cameraEntity.getY();
            if (y > 256 && y < 512) {
                return original.multiply(1-(y - 256) / (512 - 256));
            } else if (y > 512) {
                return Vec3d.ZERO;
            }
        }
        return original;
    }
    @Inject(
            method = "renderSky(Lnet/minecraft/client/util/math/MatrixStack;Lorg/joml/Matrix4f;FLnet/minecraft/client/render/Camera;ZLjava/lang/Runnable;)V",
            at = @At(
//"TAIL"
                    value = "INVOKE",
                    target = "Lcom/mojang/blaze3d/systems/RenderSystem;setShaderTexture(ILnet/minecraft/util/Identifier;)V",
                    ordinal = 0,
                    shift = At.Shift.BEFORE
            )
    )
    private void drawSpaceSky(MatrixStack matrices, Matrix4f projectionMatrix, float tickDelta, Camera camera, boolean bl, Runnable runnable, CallbackInfo ci) {
        // lerp opacity between y=CULL_HEIGHT and y=FOG_HEIGHT
        float opacity = (float) ((camera.getPos().y - 256) / (512 - 256));
        opacity = MathHelper.clamp(opacity, 0, 1);
        if (opacity > 0) {
            if (EtherealAscendanceDimensions.isEtherealAscendanceDimension(world)) {
                SkyBox.renderSpace(matrices, opacity);
                RenderSystem.setShader(GameRenderer::getPositionTexProgram);
            }
        }
    }
    //
    @Inject(
            method = "renderSky(Lnet/minecraft/client/util/math/MatrixStack;Lorg/joml/Matrix4f;FLnet/minecraft/client/render/Camera;ZLjava/lang/Runnable;)V",
            at = {@At(
                    value = "INVOKE",
                    target = "Lcom/mojang/blaze3d/systems/RenderSystem;setShaderTexture(ILnet/minecraft/util/Identifier;)V",
                    ordinal = 1,
                    shift = At.Shift.BEFORE,
                    remap = false)
            }
    )
    private void renderOrbitalSun(MatrixStack matrices, Matrix4f projectionMatrix, float tickDelta, Camera camera, boolean bl, Runnable runnable, CallbackInfo ci) {
        if (camera.getPos().y >= 256) {
            SUN = EtherealAscendanceClient.SUN;
        }
    }
    @Inject(
            method = "renderSky(Lnet/minecraft/client/util/math/MatrixStack;Lorg/joml/Matrix4f;FLnet/minecraft/client/render/Camera;ZLjava/lang/Runnable;)V",
            at = {
                    @At(
                            value = "INVOKE",
                            target = "Lcom/mojang/blaze3d/systems/RenderSystem;setShaderTexture(ILnet/minecraft/util/Identifier;)V",
                            ordinal = 1,
                            shift = At.Shift.BEFORE,
                            remap = false)
            }
    )
    private void renderOrbitalMoon(MatrixStack matrices, Matrix4f projectionMatrix, float tickDelta, Camera camera, boolean bl, Runnable runnable, CallbackInfo ci) {
        ClientWorld world = MinecraftClient.getInstance().world;
        double y = camera.getPos().y;
//        if (y >= Config.ATMOSPHERIC_CULL_HEIGHT) {
//            MOON_PHASES = AstralClient.MOON_ORBIT;
//        } else
        if (y >= 256) {
            MOON_PHASES = EtherealAscendanceClient.getMoon(MinecraftClient.getInstance().world);
        }
    }

    @ModifyVariable(
            method = "renderSky(Lnet/minecraft/client/util/math/MatrixStack;Lorg/joml/Matrix4f;FLnet/minecraft/client/render/Camera;ZLjava/lang/Runnable;)V",
            at = @At(
                    target = "Lnet/minecraft/client/render/BackgroundRenderer;setFogBlack()V",
                    shift = At.Shift.BEFORE,
                    value = "INVOKE"
            ),
            ordinal = 1,
            index = 4
    )
    private float atmosphereGradientR(float original) {
        if (!EtherealAscendanceDimensions.hasAtmosphere(this.world, false)) {
            return 0;
        } else  if (!EtherealAscendanceDimensions.isOrbit(this.world)) {
            double cameraY = this.lastCameraY;
            if (cameraY >= 256) {
                return (1 - ((float) ((this.lastCameraY -  256) / (512 - 256)))) * original;
            }
        }
        return original;
    }
    @ModifyVariable(
            method = "renderSky(Lnet/minecraft/client/util/math/MatrixStack;Lorg/joml/Matrix4f;FLnet/minecraft/client/render/Camera;ZLjava/lang/Runnable;)V",
            at = @At(
                    target = "Lnet/minecraft/client/render/BackgroundRenderer;setFogBlack()V",
                    shift = At.Shift.BEFORE,
                    value = "INVOKE"
            ),
            ordinal = 2,
            index = 5
    )
    private float atmosphereGradientG(float original) {
        if (!EtherealAscendanceDimensions.hasAtmosphere(this.world, false)) {
            return 0;
        } else if (!EtherealAscendanceDimensions.isOrbit(this.world)) {
            double cameraY = this.lastCameraY;
            if (cameraY >= 256) {
                return (1 - ((float) ((this.lastCameraY -  256) / (512 - 256)))) * original;
            }
        }
        return original;
    }
    @ModifyVariable(
            method = "renderSky(Lnet/minecraft/client/util/math/MatrixStack;Lorg/joml/Matrix4f;FLnet/minecraft/client/render/Camera;ZLjava/lang/Runnable;)V",
            at = @At(
                    target = "Lnet/minecraft/client/render/BackgroundRenderer;setFogBlack()V",
                    shift = At.Shift.BEFORE,
                    value = "INVOKE"
            ),
            ordinal = 3,
            index = 6
    )
    private float atmosphereGradientB(float original) {
        if (!EtherealAscendanceDimensions.hasAtmosphere(this.world, false)) {
            return 0;
        } else if (!EtherealAscendanceDimensions.isOrbit(this.world)) {
            double cameraY = this.lastCameraY;
            if (cameraY >= 256) {
                return (1 - ((float) ((this.lastCameraY -  256) / (512 - 256)))) * original;
            }
        }
        return original;
    }
    @ModifyExpressionValue(
            method = "renderStars(Lnet/minecraft/client/render/BufferBuilder;)Lnet/minecraft/client/render/BufferBuilder$BuiltBuffer;",
            at = @At(value = "CONSTANT", args = {"intValue=1500"}))
    private int multiplyStars(int original) {
        return EtherealAscendanceClient.shouldMultiplyStars() ? original * EtherealAscendanceClient.multiplyStars() : original;
    }
}