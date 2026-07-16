package sqyro.classessmp.network;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import sqyro.classessmp.ClassesSMP;

import java.util.HashMap;
import java.util.Map;

public record CooldownSyncPacket(Map<String, Integer> cooldowns) implements CustomPacketPayload {
    public static final Type<CooldownSyncPacket> ID = new Type<>(Identifier.fromNamespaceAndPath(ClassesSMP.MOD_ID, "cooldown_sync"));

    public static final StreamCodec<RegistryFriendlyByteBuf, CooldownSyncPacket> CODEC = StreamCodec.composite(ByteBufCodecs.map(HashMap::new, ByteBufCodecs.STRING_UTF8, ByteBufCodecs.INT), CooldownSyncPacket::cooldowns, CooldownSyncPacket::new);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return ID;
    }
}