package com.github.neapovil.latencyhud;

import com.github.neapovil.latencyhud.event.ClientConnectionEvents;
import com.github.neapovil.latencyhud.listener.ClientConnectionListener;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;

public final class LatencyHud implements ClientModInitializer
{
    @Override
    public void onInitializeClient()
    {
        final ClientConnectionListener clientconnectionlistener = new ClientConnectionListener();
        ClientConnectionEvents.RECEIVE.register(clientconnectionlistener);
        HudRenderCallback.EVENT.register(clientconnectionlistener);
    }
}
