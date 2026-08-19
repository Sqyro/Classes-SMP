package sqyro.classessmp.network;

import net.minecraft.core.UUIDUtil;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import sqyro.classessmp.ClassesSMP;

import java.util.UUID;

public record ButItRefusedSoundPacket(UUID playerUUID, boolean playing) implements CustomPacketPayload {
    public static final Identifier ID = Identifier.fromNamespaceAndPath(ClassesSMP.MOD_ID, "but_it_refused_sound");

    public static final Type<ButItRefusedSoundPacket> TYPE = new Type<>(ID);

    public static final StreamCodec<RegistryFriendlyByteBuf, ButItRefusedSoundPacket> CODEC = StreamCodec.composite(UUIDUtil.STREAM_CODEC,
            ButItRefusedSoundPacket::playerUUID,
            ByteBufCodecs.BOOL,
            ButItRefusedSoundPacket::playing,
            ButItRefusedSoundPacket::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}