package com.example.mydupe;

import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;

@Mod.EventBusSubscriber(modid = MyDupe.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ClientChatHandler {
    @SubscribeEvent
    public static void onChat(ClientChatEvent event) {
        if (!event.getMessage().trim().equalsIgnoreCase(".dupe")) return;

        Minecraft minecraft = Minecraft.getInstance();

        // Do not send the shortcut while connected to a multiplayer server.
        if (minecraft.getSingleplayerServer() == null) return;

        event.setCanceled(true);
        MyDupe.CHANNEL.send(PacketDistributor.SERVER.noArg(), new DupePacket());
    }
}
