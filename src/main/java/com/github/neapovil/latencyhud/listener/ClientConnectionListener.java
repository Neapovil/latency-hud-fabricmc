package com.github.neapovil.latencyhud.listener;

import com.github.neapovil.latencyhud.LatencyHud;
import com.github.neapovil.latencyhud.event.ClientConnectionEvents;
import com.github.neapovil.latencyhud.screen.PositionScreen;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.PlayerListEntry;

import java.time.Duration;
import java.time.Instant;

public final class ClientConnectionListener implements ClientConnectionEvents.PacketReceive, HudRenderCallback
{
    private final LatencyHud mod = LatencyHud.INSTANCE;
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
    public void onHudRender(DrawContext drawContext, float tickDelta)
    {
        final MinecraftClient client = MinecraftClient.getInstance();

        if (client.options.getReducedDebugInfo().getValue())
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

        String string = playerlistentry.getLatency() + "ms";
        final String string1 = " (lagging)";

        final int width = client.getWindow().getScaledWidth();

        final int stringwidth = client.textRenderer.getWidth(string);
        final int string1width = client.textRenderer.getWidth(string1);

        // normalize
        float x = mod.config().positionX;
        float y = mod.config().positionY;

        if (y < 1)
        {
            y = 0;
        }

        if ((x + stringwidth + (this.isLagging ? string1width : 0)) > width)
        {
            x += width - (x + stringwidth + (this.isLagging ? string1width : 0));
        }

        drawContext.drawTextWithShadow(
            client.textRenderer,
            string + (this.isLagging ? string1 : ""),
            (int) x,
            (int) y,
            0xFFFFFF);
    }
}
