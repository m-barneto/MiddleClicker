package me.mattdokn.middleclicker.mixins;

import io.netty.channel.ChannelHandlerContext;
import io.netty.util.concurrent.GenericFutureListener;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.c2s.play.*;
import net.minecraft.network.packet.s2c.play.PlaySoundS2CPacket;
import net.minecraft.sound.SoundEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientConnection.class)
public class ClientConnectionMixin {

    @Inject(at = {@At(value = "HEAD",
            target = "Lnet/minecraft/network/ClientConnection;sendImmediately(Lnet/minecraft/network/Packet;Lio/netty/util/concurrent/GenericFutureListener;)V",
            ordinal = 0)},
            method = {"sendImmediately(Lnet/minecraft/network/Packet;Lio/netty/util/concurrent/GenericFutureListener;)V"}, cancellable = true)
    public void sendImmediately(Packet<?> packet, GenericFutureListener<?> gfl, CallbackInfo ci) {
        if (packet instanceof ClickSlotC2SPacket) {
            System.out.println("ClickSlotC2SPacket");
            ClickSlotC2SPacket slotC2SPacket = (ClickSlotC2SPacket) packet;
            System.out.println("getSlot " + slotC2SPacket.getSlot());
            System.out.println("getActionType " + slotC2SPacket.getActionType());
            System.out.println("getButton " + slotC2SPacket.getButton());
            System.out.println("getStack#getCount " + slotC2SPacket.getStack().getCount());
            System.out.println("getStack#getName " + slotC2SPacket.getStack().getName().asString());
            System.out.println("getSyncId " + slotC2SPacket.getSyncId());
            System.out.println("getRevision " + slotC2SPacket.getRevision());
        }
        if (packet instanceof ButtonClickC2SPacket) {
            System.out.println("ButtonClickC2SPacket");
        }
        if (packet instanceof PickFromInventoryC2SPacket) {
            System.out.println("PickFromInventoryC2SPacket");
        }
        if (packet instanceof PlayerActionC2SPacket) {
            System.out.println("PlayerActionC2SPacket");
        }
        if (packet instanceof UpdateSelectedSlotC2SPacket) {
            System.out.println("UpdateSelectedSlotC2SPacket");
        }
    }
}
