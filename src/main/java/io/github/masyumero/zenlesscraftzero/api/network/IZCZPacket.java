package io.github.masyumero.zenlesscraftzero.api.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent.Context;

import java.util.function.Supplier;

public interface IZCZPacket<P extends IZCZPacket<P>> {

    void encode(P packet, FriendlyByteBuf buffer);

    P decode(FriendlyByteBuf buffer);

    void handle(P packet, Supplier<Context> supplier);
}
