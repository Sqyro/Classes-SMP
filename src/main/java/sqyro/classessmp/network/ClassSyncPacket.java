package sqyro.classessmp.network;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import sqyro.classessmp.ClassesSMP;

public record ClassSyncPacket(String classId) implements CustomPacketPayload {
    public static final Type<ClassSyncPacket> ID = new Type<>(Identifier.fromNamespaceAndPath(ClassesSMP.MOD_ID, "class_sync"));
    public static final StreamCodec<RegistryFriendlyByteBuf, ClassSyncPacket> CODEC = StreamCodec.composite(ByteBufCodecs.STRING_UTF8, ClassSyncPacket::classId, ClassSyncPacket::new);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return ID;
    }
}
