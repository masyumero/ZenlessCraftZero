package io.github.masyumero.zenlesscraftzero.common.network.s2c;

import io.github.masyumero.zenlesscraftzero.api.anomaly.Attribute;
import io.github.masyumero.zenlesscraftzero.common.utils.ZCZUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class DamageIndicatorNetwork {

    private static final String PROTOCOL = "1";

    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            ZCZUtil.rl("main"),
            () -> PROTOCOL,
            PROTOCOL::equals,
            PROTOCOL::equals
    );

    public static void register() {
        CHANNEL.messageBuilder(DamageIndicatorPacket.class, 0, NetworkDirection.PLAY_TO_CLIENT)
                .encoder(DamageIndicatorPacket::encode)
                .decoder(DamageIndicatorPacket::decode)
                .consumerMainThread(DamageIndicatorPacket::handle)
                .add();
    }

    public static void send(ServerLevel level, Attribute attribute, double x, double y, double z, float damage, boolean isAnomalyEffect) {
        CHANNEL.send(
                PacketDistributor.NEAR.with(() -> new PacketDistributor.TargetPoint(x, y, z, 64.0, level.dimension())),
                new DamageIndicatorPacket(attribute, x, y, z, damage, isAnomalyEffect)
        );
    }
}
