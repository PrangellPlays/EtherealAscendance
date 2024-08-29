package dev.soulsparkstudios.etherealascendance.mixin.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.DownloadingTerrainScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin({DownloadingTerrainScreen.class})
public class DownloadingTerrainScreenMixin {
    public DownloadingTerrainScreenMixin() {
    }

    @Inject(
            method = {"render"},
            at = {@At("HEAD")},
            cancellable = true
    )
    private void render(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        ci.cancel();
    }
}
