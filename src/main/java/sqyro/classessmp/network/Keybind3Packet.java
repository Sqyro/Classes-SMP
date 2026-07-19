package sqyro.classessmp.network;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import sqyro.classessmp.ClassesSMP;

public record Keybind3Packet() implements CustomPacketPayload {
    public static final Type<Keybind3Packet> ID = new Type<>(Identifier.fromNamespaceAndPath(ClassesSMP.MOD_ID, "ability3"));
    public static final StreamCodec<RegistryFriendlyByteBuf, Keybind3Packet> CODEC = StreamCodec.unit(new Keybind3Packet());

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return ID;
    }
}
