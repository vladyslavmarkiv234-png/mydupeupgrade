package com.example.mydupe;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

@Mod(MyDupe.MOD_ID)
public class MyDupe {
    public static final String MOD_ID = "mydupe";
    private static final String PROTOCOL = "1";

    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(MOD_ID, "main"),
            () -> PROTOCOL,
            PROTOCOL::equals,
            PROTOCOL::equals
    );

    public MyDupe() {
        CHANNEL.registerMessage(
                0,
                DupePacket.class,
                DupePacket::encode,
                DupePacket::decode,
                DupePacket::handle
        );
    }
}
