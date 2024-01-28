package com.github.neapovil.latencyhud.listener;

import java.time.Duration;
import java.time.Instant;

import com.github.neapovil.latencyhud.event.ClientConnectionEvents;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.util.math.MatrixStack;

public final class ClientConnectionListener implements ClientConnectionEvents.Receive, HudRenderCallback
{
    private Instant lastReceivedPacket = null;
    private boolean isLagging = false;

    @Override
    public void onReceive()
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

        final PlayerListEntry playerlistentry = client.getNetworkHandler().getPlayerListEntry(client.player.getUuid());

        if (playerlistentry == null)
        {
            return;
        }

        String latency = playerlistentry.getLatency() + "ms";

        if (this.isLagging)
        {
            latency += " (lagging)";
        }

        client.textRenderer.drawWithShadow(
                matrixStack,
                latency,
                client.getWindow().getScaledWidth() - client.textRenderer.getWidth(latency) - 1,
                1,
                0xFFFFFF);
    }
}
