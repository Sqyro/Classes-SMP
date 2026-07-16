package sqyro.classessmp.network;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import sqyro.classessmp.ClassesSMP;

public record FreezeSyncPacket(int entityID, boolean Frozen) implements CustomPacketPayload {
    public static final Type<FreezeSyncPacket> ID = new Type<>(Identifier.fromNamespaceAndPath(ClassesSMP.MOD_ID, "freeze_sync"));

    public static final StreamCodec<RegistryFriendlyByteBuf, FreezeSyncPacket> CODEC =
            StreamCodec.of((buf, payload) -> {
                buf.writeInt(payload.entityID());
                buf.writeBoolean(payload.Frozen());
                }, buf -> new FreezeSyncPacket(buf.readInt(), buf.readBoolean()));


    @Override
    public Type<? extends CustomPacketPayload> type() {
        return ID;
    }
}
