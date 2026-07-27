package sqyro.classessmp.network;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import sqyro.classessmp.ClassesSMP;

public record RootSyncPacket(int entityID, boolean Rooted) implements CustomPacketPayload {
    public static final Type<RootSyncPacket> ID = new Type<>(Identifier.fromNamespaceAndPath(ClassesSMP.MOD_ID, "root_sync"));

    public static final StreamCodec<RegistryFriendlyByteBuf, RootSyncPacket> CODEC =
            StreamCodec.of((buf, payload) -> {
                buf.writeInt(payload.entityID());
                buf.writeBoolean(payload.Rooted());
            }, buf -> new RootSyncPacket(buf.readInt(), buf.readBoolean()));


    @Override
    public Type<? extends CustomPacketPayload> type() {
        return ID;
    }
}
