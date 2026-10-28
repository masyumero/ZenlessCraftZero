package io.github.masyumero.zenlesscraftzero.common.network.s2c;

import io.github.masyumero.zenlesscraftzero.common.anomaly.Attribute;
import io.github.masyumero.zenlesscraftzero.api.network.IZCZPacket;
import io.github.masyumero.zenlesscraftzero.client.indicator.DamageIndicators;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent.Context;

import java.util.function.Supplier;

public record DamageIndicatorPacket(Attribute attribute, double x, double y, double z, float damage,
                                    boolean isAnomalyEffect, boolean isCrit) implements IZCZPacket<DamageIndicatorPacket> {

    public static final DamageIndicatorPacket INSTANCE = new DamageIndicatorPacket(null, 0, 0, 0, 0, false, false);

    @Override
    public void encode(DamageIndicatorPacket packet, FriendlyByteBuf buffer) {
        buffer.writeEnum(packet.attribute);
        buffer.writeDouble(packet.x);
        buffer.writeDouble(packet.y);
        buffer.writeDouble(packet.z);
        buffer.writeFloat(packet.damage);
        buffer.writeBoolean(packet.isAnomalyEffect);
        buffer.writeBoolean(packet.isCrit);
    }

    @Override
    public DamageIndicatorPacket decode(FriendlyByteBuf buffer) {
        return new DamageIndicatorPacket(buffer.readEnum(Attribute.class), buffer.readDouble(), buffer.readDouble(), buffer.readDouble(), buffer.readFloat(), buffer.readBoolean(), buffer.readBoolean());
    }

    @Override
    public void handle(DamageIndicatorPacket packet, Supplier<Context> supplier) {
        Context context = supplier.get();

        context.enqueueWork(() ->
                DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> DamageIndicators.add(packet.attribute, packet.x, packet.y, packet.z, packet.damage, packet.isAnomalyEffect, packet.isCrit))
        );

        context.setPacketHandled(true);
    }
}