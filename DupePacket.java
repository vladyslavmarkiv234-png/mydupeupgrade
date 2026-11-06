package com.example.mydupe;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class DupePacket {
    public DupePacket() {}

    public static void encode(DupePacket packet, net.minecraft.network.FriendlyByteBuf buffer) {}

    public static DupePacket decode(net.minecraft.network.FriendlyByteBuf buffer) {
        return new DupePacket();
    }

    public static void handle(DupePacket packet, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player == null || player.getServer() == null) return;

            // This feature is deliberately restricted to the integrated singleplayer server.
            if (!player.getServer().isSingleplayer()) return;

            ItemStack held = player.getMainHandItem();
            if (held.isEmpty()) return;

            // Copy the complete held stack, including its components/NBT and enchantments.
            ItemStack duplicate = held.copy();
            boolean inserted = player.getInventory().add(duplicate);

            // If the inventory has no room, put the duplicate on the ground safely.
            if (!inserted && !duplicate.isEmpty()) {
                player.drop(duplicate, false);
            }

            player.inventoryMenu.broadcastChanges();
        });
        context.setPacketHandled(true);
    }
}
