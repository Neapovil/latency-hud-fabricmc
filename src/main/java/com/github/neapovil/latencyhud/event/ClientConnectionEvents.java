package com.github.neapovil.latencyhud.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

public final class ClientConnectionEvents
{
    public static final Event<Receive> RECEIVE = EventFactory.createArrayBacked(Receive.class,
            (listeners) -> () -> {
                for (Receive listener : listeners)
                {
                    listener.onReceive();
                }
            });

    @FunctionalInterface
    public interface Receive
    {
        void onReceive();
    }
}
