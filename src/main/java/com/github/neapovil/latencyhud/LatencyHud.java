package com.github.neapovil.latencyhud;

import com.github.neapovil.latencyhud.config.ModConfig;
import com.github.neapovil.latencyhud.event.ClientConnectionEvents;
import com.github.neapovil.latencyhud.listener.ClientConnectionListener;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;

public final class LatencyHud implements ClientModInitializer
{
    public static LatencyHud INSTANCE;
    private final ModConfig modConfig = new ModConfig();

    @Override
    public void onInitializeClient()
    {
        INSTANCE = this;

        this.modConfig.load();

        final ClientConnectionListener clientconnectionlistener = new ClientConnectionListener();
        ClientConnectionEvents.PACKET_RECEIVE.register(clientconnectionlistener);
        HudRenderCallback.EVENT.register(clientconnectionlistener);
    }

    public ModConfig config()
    {
        return this.modConfig;
    }
}
