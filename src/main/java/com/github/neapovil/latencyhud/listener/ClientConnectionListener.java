package com.github.neapovil.latencyhud.listener;

import java.time.Duration;
import java.time.Instant;

import com.github.neapovil.latencyhud.LatencyHud;
import com.github.neapovil.latencyhud.event.ClientConnectionEvents;
import com.github.neapovil.latencyhud.screen.PositionScreen;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.util.math.MatrixStack;

public final class ClientConnectionListener implements ClientConnectionEvents.PacketReceive, HudRenderCallback
{
    private Instant lastReceivedPacket = null;
    private boolean isLagging = false;

    @Override
    public void onPacketReceive()
    {
        final Instant before = this.lastReceivedPacket;
        final Instant now = Instant.now();

        if (before != null)
        {
            this.isLagging = Duration.between(before, now).toSeconds() > 2;
        }

        this.lastReceivedPacket = now;
    }

    @Override
    public void onHudRender(MatrixStack matrixStack, float tickDelta)
    {
        final MinecraftClient client = MinecraftClient.getInstance();

        if (client.options.debugEnabled)
        {
            return;
        }

        if (client.currentScreen instanceof PositionScreen)
        {
            return;
        }

        final PlayerListEntry playerlistentry = client.getNetworkHandler().getPlayerListEntry(client.player.getUuid());

        if (playerlistentry == null)
        {
            return;
        }

        final LatencyHud mod = LatencyHud.INSTANCE;

        String string = playerlistentry.getLatency() + "ms";
        final String string1 = " (lagging)";

        final int width = client.getWindow().getScaledWidth();

        final int stringwidth = client.textRenderer.getWidth(string);
        final int string1width = client.textRenderer.getWidth(string1);

        // normalize
        float x = mod.getConfig().positionX;
        float y = mod.getConfig().positionY;

        if (y < 1)
        {
            y = 0;
        }

        if ((x + stringwidth + (this.isLagging ? string1width : 0)) > width)
        {
            x += width - (x + stringwidth + (this.isLagging ? string1width : 0));
        }

        client.textRenderer.drawWithShadow(
                matrixStack,
                string + (this.isLagging ? string1 : ""),
                x,
                y,
                0xFFFFFF);
    }
}
