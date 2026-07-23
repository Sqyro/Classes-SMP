package sqyro.classessmp.network;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import sqyro.classessmp.ClassesSMP;

public record NoiseMeterPacket(int Value) implements CustomPacketPayload {
    public static final Type<NoiseMeterPacket> ID = new Type<>(Identifier.fromNamespaceAndPath(ClassesSMP.MOD_ID, "noise_meter"));
    public static final StreamCodec<RegistryFriendlyByteBuf, NoiseMeterPacket> CODEC = StreamCodec.composite(ByteBufCodecs.INT, NoiseMeterPacket::Value, NoiseMeterPacket::new);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return ID;
    }
}
