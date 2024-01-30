package com.github.neapovil.latencyhud.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

public final class ClientConnectionEvents
{
    public static final Event<PacketReceive> PACKET_RECEIVE = EventFactory.createArrayBacked(PacketReceive.class,
            (listeners) -> () -> {
                for (PacketReceive listener : listeners)
                {
                    listener.onPacketReceive();
                }
            });

    @FunctionalInterface
    public interface PacketReceive
    {
        void onPacketReceive();
    }
}
