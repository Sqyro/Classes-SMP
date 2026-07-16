package sqyro.classessmp.network;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import sqyro.classessmp.ClassesSMP;

public record CooldownStartPacket(String abilityID, int Ticks) implements CustomPacketPayload {
    public static final Type<CooldownStartPacket> ID = new Type<>(Identifier.fromNamespaceAndPath(ClassesSMP.MOD_ID, "cooldown_start"));

    public static final StreamCodec<RegistryFriendlyByteBuf, CooldownStartPacket> CODEC = StreamCodec.composite(ByteBufCodecs.STRING_UTF8, CooldownStartPacket::abilityID, ByteBufCodecs.INT, CooldownStartPacket::Ticks, CooldownStartPacket::new);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return ID;
    }
}