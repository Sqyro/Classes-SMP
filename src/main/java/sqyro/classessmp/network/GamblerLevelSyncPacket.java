package sqyro.classessmp.network;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import sqyro.classessmp.ClassesSMP;

public record GamblerLevelSyncPacket(int Level) implements CustomPacketPayload {
    public static final Type<GamblerLevelSyncPacket> ID = new Type<>(Identifier.fromNamespaceAndPath(ClassesSMP.MOD_ID, "gambler_level_sync"));

    public static final StreamCodec<RegistryFriendlyByteBuf, GamblerLevelSyncPacket> CODEC = StreamCodec.composite(ByteBufCodecs.INT, GamblerLevelSyncPacket::Level, GamblerLevelSyncPacket::new);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return ID;
    }
}