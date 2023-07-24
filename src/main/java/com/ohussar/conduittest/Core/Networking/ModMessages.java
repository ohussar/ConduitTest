package com.ohussar.conduittest.Core.Networking;

import com.ohussar.conduittest.ConduitMain;

import com.ohussar.conduittest.Core.Networking.Messages.SyncTank;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class ModMessages {
    private static SimpleChannel INSTANCE;

    private static int packetId = 0;
    private static int id() {
        return packetId++;
    }

    public static void register() {
        SimpleChannel net = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(ConduitMain.MODID, "messages"))
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();

        INSTANCE = net;

        //net.messageBuilder(SyncInventoryClient.class, id(), NetworkDirection.PLAY_TO_CLIENT)
        //        .decoder(SyncInventoryClient::new)
        //        .encoder(SyncInventoryClient::toBytes)
        //        .consumerMainThread(SyncInventoryClient::handle)
        //        .add();
        net.messageBuilder(SyncTank.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(SyncTank::new)
                .encoder(SyncTank::toBytes)
                .consumerMainThread(SyncTank::handle)
                .add();
    }

    public static <MSG> void sendToServer(MSG message) {
         INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }

    public static <MSG> void sendToClients(MSG message) {
        INSTANCE.send(PacketDistributor.ALL.noArg(), message);
    }
}
