package io.github.masyumero.zenlesscraftzero.common.network.s2c;

import io.github.masyumero.zenlesscraftzero.api.anomaly.Attribute;
import io.github.masyumero.zenlesscraftzero.client.indicator.DamageIndicators;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent.Context;

import java.util.function.Supplier;

public record DamageIndicatorPacket(Attribute attribute, double x, double y, double z, float damage, boolean isAnomalyEffect) {
    public static void encode(DamageIndicatorPacket packet, FriendlyByteBuf buffer) {
        buffer.writeEnum(packet.attribute);
        buffer.writeDouble(packet.x());
        buffer.writeDouble(packet.y());
        buffer.writeDouble(packet.z());
        buffer.writeFloat(packet.damage());
        buffer.writeBoolean(packet.isAnomalyEffect());
    }

    public static DamageIndicatorPacket decode(FriendlyByteBuf buffer) {
        return new DamageIndicatorPacket(buffer.readEnum(Attribute.class), buffer.readDouble(), buffer.readDouble(), buffer.readDouble(), buffer.readFloat(), buffer.readBoolean());
    }

    public static void handle(DamageIndicatorPacket packet, Supplier<Context> supplier) {
        Context context = supplier.get();

        context.enqueueWork(() ->
                DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> DamageIndicators.add(packet.attribute(), packet.x(), packet.y(), packet.z(), packet.damage(), packet.isAnomalyEffect()))
        );

        context.setPacketHandled(true);
    }
}