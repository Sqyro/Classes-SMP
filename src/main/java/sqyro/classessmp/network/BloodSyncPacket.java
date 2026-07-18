package sqyro.classessmp.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import sqyro.classessmp.ClassesSMP;

public record BloodSyncPacket(int Amount) implements CustomPacketPayload {
    public static final Type<BloodSyncPacket> ID = new Type<>(Identifier.fromNamespaceAndPath(ClassesSMP.MOD_ID, "blood_amount"));

    public static final StreamCodec<FriendlyByteBuf, BloodSyncPacket> CODEC = StreamCodec.of((buf, payload) -> buf.writeInt(payload.Amount), buf -> new BloodSyncPacket(buf.readInt()));

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return ID;
    }
}
