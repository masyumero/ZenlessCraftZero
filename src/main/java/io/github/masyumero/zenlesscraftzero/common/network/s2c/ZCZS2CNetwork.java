package io.github.masyumero.zenlesscraftzero.common.network.s2c;

import io.github.masyumero.zenlesscraftzero.api.network.IZCZPacket;
import io.github.masyumero.zenlesscraftzero.common.utils.ZCZUtil;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent.Context;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class ZCZS2CNetwork {

    private static final String PROTOCOL = "1";
    private static int nextPacketId;

    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            ZCZUtil.rl("main"),
            () -> PROTOCOL,
            PROTOCOL::equals,
            PROTOCOL::equals
    );

    public static void register() {
        registerPacket(
                DamageIndicatorPacket.class,
                DamageIndicatorPacket.INSTANCE::encode,
                DamageIndicatorPacket.INSTANCE::decode,
                DamageIndicatorPacket.INSTANCE::handle
        );
        registerPacket(
                HealthIndicatorPacket.class,
                HealthIndicatorPacket.INSTANCE::encode,
                HealthIndicatorPacket.INSTANCE::decode,
                HealthIndicatorPacket.INSTANCE::handle
        );
        registerPacket(
                AttributeAnomalySyncPacket.class,
                AttributeAnomalySyncPacket.INSTANCE::encode,
                AttributeAnomalySyncPacket.INSTANCE::decode,
                AttributeAnomalySyncPacket.INSTANCE::handle
        );
    }

    public static <P extends IZCZPacket<P>> void registerPacket(Class<P> packetClass, BiConsumer<P, FriendlyByteBuf> encoder, Function<FriendlyByteBuf, P> decoder, BiConsumer<P, Supplier<Context>> handler) {
        CHANNEL.messageBuilder(packetClass, nextPacketId++, NetworkDirection.PLAY_TO_CLIENT)
                .encoder(encoder)
                .decoder(decoder)
                .consumerMainThread(handler)
                .add();
    }

    public static <MSG> void send(ServerLevel level, double x, double y, double z, double r2, MSG message) {
        CHANNEL.send(PacketDistributor.NEAR.with(() -> new PacketDistributor.TargetPoint(x, y, z, r2, level.dimension())), message);
    }
}
