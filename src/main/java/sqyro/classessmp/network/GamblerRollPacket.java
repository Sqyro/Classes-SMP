package sqyro.classessmp.network;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import sqyro.classessmp.ClassesSMP;

public record GamblerRollPacket(int Roll) implements CustomPacketPayload {
    public static final Type<GamblerRollPacket> ID = new Type<>(Identifier.fromNamespaceAndPath(ClassesSMP.MOD_ID, "gambler_roll"));

    public static final StreamCodec<RegistryFriendlyByteBuf, GamblerRollPacket> CODEC = StreamCodec.composite(ByteBufCodecs.INT, GamblerRollPacket::Roll, GamblerRollPacket::new);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return ID;
    }
}