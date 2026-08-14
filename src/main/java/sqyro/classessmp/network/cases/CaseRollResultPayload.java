package sqyro.classessmp.network.cases;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import sqyro.classessmp.ClassesSMP;

public record CaseRollResultPayload(ItemStack reward) implements CustomPacketPayload {
    public static final Identifier ID = Identifier.fromNamespaceAndPath(ClassesSMP.MOD_ID, "case_roll_result");

    public static final Type<CaseRollResultPayload> TYPE = new Type<>(ID);

    public static final StreamCodec<RegistryFriendlyByteBuf, CaseRollResultPayload> CODEC = ItemStack.STREAM_CODEC.map(CaseRollResultPayload::new, CaseRollResultPayload::reward);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
