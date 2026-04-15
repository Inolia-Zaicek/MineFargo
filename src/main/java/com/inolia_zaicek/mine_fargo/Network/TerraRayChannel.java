package com.inolia_zaicek.mine_fargo.Network;

import com.inolia_zaicek.mine_fargo.MineFargo;
import com.inolia_zaicek.mine_fargo.Network.Packet.ClientToServerPacket;
import com.inolia_zaicek.mine_fargo.Network.Packet.TerraRayPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.Optional;

public class TerraRayChannel {
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(MineFargo.MODID,"mygo_terra"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );
    public static void init(){
        int packetID=0;
        if(ModList.get().isLoaded("botania")) {
            CHANNEL.registerMessage(
                    packetID++,
                    TerraRayPacket.class,
                    TerraRayPacket::encode,
                    TerraRayPacket::decode,
                    TerraRayPacket::handle,
                    Optional.of(NetworkDirection.PLAY_TO_SERVER)
            );
        }
        CHANNEL.registerMessage(packetID++, ClientToServerPacket.class,
                ClientToServerPacket::encode,
                ClientToServerPacket::decode,
                ClientToServerPacket::handle
        );
    }
}
