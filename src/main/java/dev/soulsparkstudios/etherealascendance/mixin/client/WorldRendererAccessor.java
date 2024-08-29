package dev.soulsparkstudios.etherealascendance.mixin.client;

import net.minecraft.client.gl.VertexBuffer;
import net.minecraft.client.render.WorldRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(WorldRenderer.class)
public interface WorldRendererAccessor {
    @Accessor("starsBuffer")
    VertexBuffer etherealascendance$getStars();

    @Accessor("lightSkyBuffer")
    VertexBuffer etherealascendance$getSky();
}