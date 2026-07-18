package sqyro.classessmp.network;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import sqyro.classessmp.ClassesSMP;
import net.minecraft.resources.Identifier;

public record Keybind1Packet() implements CustomPacketPayload {
    public static final Type<Keybind1Packet> ID = new Type<>(Identifier.fromNamespaceAndPath(ClassesSMP.MOD_ID, "ability1"));
    public static final StreamCodec<RegistryFriendlyByteBuf, Keybind1Packet> CODEC = StreamCodec.unit(new Keybind1Packet());

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return ID;
    }
}
