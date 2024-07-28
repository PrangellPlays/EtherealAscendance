package dev.soulsparkstudios.etherealascendance.client.render.sky;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.soulsparkstudios.etherealascendance.EtherealAscendance;
import dev.soulsparkstudios.etherealascendance.EtherealAscendanceClient;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.RotationAxis;
import org.joml.Matrix4f;

@Environment(EnvType.CLIENT)
public class SkyBox {
    public static void renderSpace(MatrixStack matrices, float opacity) {
        try {
            RenderSystem.depthMask(false);
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.setShader(GameRenderer::getPositionTexColorProgram);
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder bufferBuilder = tessellator.getBuffer();
            float d = 50;
            for (int i = 0; i < 6; ++i) {
                matrices.push();
                if (i == 0) {
                    RenderSystem.setShaderTexture(0, EtherealAscendanceClient.SKYBOX_LEFT);
                    matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(90.0F));
                }

                if (i == 1) {
                    RenderSystem.setShaderTexture(0, EtherealAscendanceClient.SKYBOX_RIGHT);
                    matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-90.0F));
                }

                if (i == 2) {
                    RenderSystem.setShaderTexture(0, EtherealAscendanceClient.SKYBOX_UP);
                    matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(180.0F));
                }

                if (i == 3) {
                    RenderSystem.setShaderTexture(0, EtherealAscendanceClient.SKYBOX_FRONT);
                    matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(90.0F));
                }

                if (i == 4) {
                    RenderSystem.setShaderTexture(0, EtherealAscendanceClient.SKYBOX_BACK);
                    matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(-90.0F));
                }
                if (i == 5) {
                    RenderSystem.setShaderTexture(0, EtherealAscendanceClient.SKYBOX_DOWN);
                }
                Matrix4f matrix4f = matrices.peek().getPositionMatrix();
                int alpha = (int)(0xFF * (opacity*.9+.1));
                bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);
                bufferBuilder.vertex(matrix4f, -d, -d, -d).texture(0.0F, 0.0F).color(255, 255, 255, alpha).next();
                bufferBuilder.vertex(matrix4f, -d, -d, d).texture(0.0F, 1.0F).color(255, 255, 255, alpha).next();
                bufferBuilder.vertex(matrix4f, d, -d, d).texture(1.0F, 1.0F).color(255, 255, 255, alpha).next();
                bufferBuilder.vertex(matrix4f, d, -d, -d).texture(1.0F, 0.0F).color(255, 255, 255, alpha).next();
                tessellator.draw();
                matrices.pop();
            }
            RenderSystem.enableDepthTest();
        } catch (NullPointerException e) {
            EtherealAscendance.LOGGER.warn("Error rendering cosmic background!");
            e.printStackTrace();
        }
    }
}
