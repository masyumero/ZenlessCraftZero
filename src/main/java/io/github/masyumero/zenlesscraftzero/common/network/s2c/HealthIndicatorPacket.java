package io.github.masyumero.zenlesscraftzero.common.network.s2c;

import io.github.masyumero.zenlesscraftzero.api.network.IZCZPacket;
import io.github.masyumero.zenlesscraftzero.client.indicator.HealthIndicator;
import io.github.masyumero.zenlesscraftzero.common.anomaly.AttributeAnomaly;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent.Context;

import java.util.function.Supplier;

public record HealthIndicatorPacket(AttributeAnomaly attributeAnomaly, int entityId) implements IZCZPacket<HealthIndicatorPacket> {

    public static final HealthIndicatorPacket INSTANCE = new HealthIndicatorPacket();

    private HealthIndicatorPacket() {
        this(null, 0);
    }

    @Override
    public void encode(HealthIndicatorPacket packet, FriendlyByteBuf buffer) {
        buffer.writeEnum(packet.attributeAnomaly);
        buffer.writeInt(packet.entityId);
    }

    @Override
    public HealthIndicatorPacket decode(FriendlyByteBuf buffer) {
        return new HealthIndicatorPacket(buffer.readEnum(AttributeAnomaly.class), buffer.readInt());
    }

    @Override
    public void handle(HealthIndicatorPacket packet, Supplier<Context> supplier) {
        Context context = supplier.get();

        context.enqueueWork(() ->
                DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> HealthIndicator.INSTANCE.add(packet.attributeAnomaly, packet.entityId))
        );

        context.setPacketHandled(true);
    }
}
