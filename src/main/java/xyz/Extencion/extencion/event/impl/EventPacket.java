package xyz.Extencion.extencion.event.impl;

import net.minecraft.network.packet.Packet;

public class EventPacket {
    private final Packet<?> packet;

    public EventPacket(Packet<?> packet) {
        this.packet = packet;
    }

    public <T extends Packet<?>> T getPacket() {
        return (T) this.packet;
    }

    public static class Send extends EventPacket {
        public Send(Packet<?> packet) {
            super(packet);
        }
    }

    public static class Receive extends EventPacket {
        public Receive(Packet<?> packet) {
            super(packet);
        }
    }

    public static class SendPost extends EventPacket {
        public SendPost(Packet<?> packet) {
            super(packet);
        }
    }

    public static class ReceivePost extends EventPacket {
        public ReceivePost(Packet<?> packet) {
            super(packet);
        }
    }
}