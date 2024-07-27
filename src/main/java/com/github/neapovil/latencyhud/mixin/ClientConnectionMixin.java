package com.github.neapovil.latencyhud.mixin;

import com.github.neapovil.latencyhud.event.ClientConnectionEvents;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.packet.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientConnection.class)
public class ClientConnectionMixin
{
    @Inject(method = "channelRead0", at = @At("HEAD"))
    public void channelRead0(ChannelHandlerContext channelHandlerContext, Packet<?> packet, CallbackInfo info)
    {
        ClientConnectionEvents.PACKET_RECEIVE.invoker().onPacketReceive();
    }
}
