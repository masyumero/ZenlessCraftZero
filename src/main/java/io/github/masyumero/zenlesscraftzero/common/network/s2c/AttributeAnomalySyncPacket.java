package io.github.masyumero.zenlesscraftzero.common.network.s2c;

import io.github.masyumero.zenlesscraftzero.api.network.IZCZPacket;
import io.github.masyumero.zenlesscraftzero.common.capability.ZCZCapabilities;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent.Context;
import net.minecraftforge.network.PacketDistributor;

import java.util.function.Supplier;

public record AttributeAnomalySyncPacket(int entityId, CompoundTag tag)
        implements IZCZPacket<AttributeAnomalySyncPacket> {

    public static final AttributeAnomalySyncPacket INSTANCE = new AttributeAnomalySyncPacket(0, null);

    @Override
    public void encode(AttributeAnomalySyncPacket packet, FriendlyByteBuf buffer) {
        buffer.writeInt(packet.entityId);
        buffer.writeNbt(packet.tag);
    }

    @Override
    public AttributeAnomalySyncPacket decode(FriendlyByteBuf buffer) {
        return new AttributeAnomalySyncPacket(buffer.readInt(), buffer.readNbt());
    }

    @Override
    public void handle(AttributeAnomalySyncPacket packet, Supplier<Context> supplier) {
        Context context = supplier.get();

        context.enqueueWork(() ->
                DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
                    Minecraft mc = Minecraft.getInstance();

                    if (mc.level == null) {
                        return;
                    }

                    Entity entity = mc.level.getEntity(packet.entityId);
                    if (entity == null) {
                        return;
                    }

                    entity.getCapability(ZCZCapabilities.ATTRIBUTE_ANOMALY).ifPresent(anomaly -> {
                        anomaly.deserializeNBT(packet.tag);
                    });
                })
        );

        context.setPacketHandled(true);
    }

    public static void syncAttributeAnomaly(Entity entity) {
        if (!(entity.level() instanceof ServerLevel)) {
            return;
        }

        entity.getCapability(ZCZCapabilities.ATTRIBUTE_ANOMALY).ifPresent(anomaly -> {
            CompoundTag data = anomaly.serializeNBT();
            ZCZS2CNetwork.CHANNEL.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> entity), new AttributeAnomalySyncPacket(entity.getId(), data));
        });
    }
}
