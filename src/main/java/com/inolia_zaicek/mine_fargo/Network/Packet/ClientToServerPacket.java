package com.inolia_zaicek.mine_fargo.Network.Packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraftforge.network.NetworkEvent;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import static com.inolia_zaicek.mine_fargo.Event.TickEvent.*;


public class ClientToServerPacket {
    public Map<ResourceLocation, Integer> baseValueMap = new HashMap<>();
    /// 输入进来的数据
    public float number;

    public ClientToServerPacket(float number) {
        this.number = number;
    }

    public static void encode(ClientToServerPacket msg, FriendlyByteBuf buf) {
        buf.writeFloat(msg.number);
    }

    public static ClientToServerPacket decode(FriendlyByteBuf buf) {
        float number = buf.readFloat();
        return new ClientToServerPacket(number);
    }


    public static void handle(ClientToServerPacket msg, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();
        ctx.enqueueWork(() -> {
            var player = ctxSupplier.get().getSender();
            //1
            if (player != null) {
                if (msg.number == 1) {player.getPersistentData().putFloat(boolean_kill_range_skill, 20);}
                if (msg.number == 2) {player.getPersistentData().putFloat(magnet_soul_stone, 20);}
                if (msg.number == 3) {player.getPersistentData().putFloat(anchor_soul_stone_open, 20);}
                if (msg.number == 4) {player.getPersistentData().putFloat(projectile_tracking_capability, 20);}
                if (msg.number == 5) {player.getPersistentData().putFloat(forlorn_soul_stone_c_to_s, 20);}
            }
        });
        ctx.setPacketHandled(true);
    }
}