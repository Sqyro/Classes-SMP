package sqyro.classessmp.network.cases;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import sqyro.classessmp.ClassesSMP;

public record OpenCasePayload() implements CustomPacketPayload {
    public static final Identifier ID = Identifier.fromNamespaceAndPath(ClassesSMP.MOD_ID, "open_case");

    public static final Type<OpenCasePayload> TYPE = new Type<>(ID);

    public static final StreamCodec<RegistryFriendlyByteBuf, OpenCasePayload> CODEC = StreamCodec.unit(new OpenCasePayload());

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}