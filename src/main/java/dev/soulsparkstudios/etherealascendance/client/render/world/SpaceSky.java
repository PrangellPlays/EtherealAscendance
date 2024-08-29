package dev.soulsparkstudios.etherealascendance.client.render.world;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.soulsparkstudios.etherealascendance.EtherealAscendance;
import dev.soulsparkstudios.etherealascendance.mixin.client.WorldRendererAccessor;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.DimensionRenderingRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.client.gl.VertexBuffer;
import net.minecraft.client.render.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;

public class SpaceSky implements DimensionRenderingRegistry.SkyRenderer, ClientTickEvents.StartWorldTick{
    public static final Identifier TEXTURE = EtherealAscendance.path("textures/space/space1.png");

    private int time = 0;
    public float globalSpeed = 0.01f;

    // Texture aspect ratio 5:1
    public static final float U_SCALE = 0.2f;
    public static final float V_SCALE = 1f;

    public SpaceSky() {
    }
    @Override
    public void onStartTick(ClientWorld world) {

    }

    @Override
    public void render(WorldRenderContext context) {
        final float tickDelta = context.tickDelta();
        final float time = this.time + tickDelta;

        final var matrices = context.matrixStack();
        final var buffer = Tessellator.getInstance().getBuffer();

        final var projMat = context.projectionMatrix();
        final var world = context.world();
        final var camera = context.camera();
        final var worldRender = context.worldRenderer();
        final var worldRender2 = (WorldRendererDuck) worldRender;
        final var worldRenderAcc = (WorldRendererAccessor) worldRender;

        final var fogCallback = worldRender2.etherealascendance$consumeFogCallback();

        // Sky
        final var skyBuffer = worldRenderAcc.etherealascendance$getSky();
        final var skyColor = world.getSkyColor(camera.getPos(), tickDelta);
        //BackgroundRenderer.applyFogColor();
        RenderSystem.depthMask(false);
        RenderSystem.setShaderColor((float) skyColor.x, (float) skyColor.y, (float) skyColor.z, 1);
        skyBuffer.bind();
        skyBuffer.draw(matrices.peek().getPositionMatrix(), projMat, RenderSystem.getShader());
        VertexBuffer.unbind();

        RenderSystem.setShaderColor(1, 1, 1, 1);
        RenderSystem.depthMask(true);

        // Stars
        BackgroundRenderer.clearFog();
        matrices.push();

        final var starBuffer = worldRenderAcc.etherealascendance$getStars();
        matrices.multiply(RotationAxis.NEGATIVE_X.rotation(time * globalSpeed * 0.3f));
        starBuffer.bind();
        starBuffer.draw(matrices.peek().getPositionMatrix(), context.projectionMatrix(), GameRenderer.getPositionProgram());
        VertexBuffer.unbind();

        matrices.pop();

        //Space
        matrices.push();

        matrices.translate(0, -3.2, 0);
        matrices.scale(64, 64, 64);

        RenderSystem.setShader(GameRenderer::getPositionColorTexProgram);
        RenderSystem.setShaderTexture(0, TEXTURE);

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();

        buffer.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR_TEXTURE);

        BufferRenderer.drawWithGlobalProgram(buffer.end());

        matrices.pop();

        //BackgroundRenderer.applyFogColor();
        fogCallback.run();
    }

    public static class Effects extends DimensionEffects {
        public Effects() {
            super(Float.NaN, false, SkyType.NORMAL, false, true);
        }

        @Override
        public Vec3d adjustFogColor(Vec3d color, float sunHeight) {
            return color;
        }

        @Override
        public boolean useThickFog(int camX, int camY) {
            return false;
        }
    }
}
