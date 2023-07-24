package com.ohussar.conduittest.Core.Networking.Messages;

import com.ohussar.conduittest.ConduitMain;
import com.ohussar.conduittest.Core.Interfaces.ISteamCapabilityProvider;
import com.ohussar.conduittest.Core.SteamTank;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SyncTank {
    private SteamTank tank;
    private BlockPos pos;
    public SyncTank(SteamTank tank, BlockPos entityBlockPos){
        this.tank = tank;
        this.pos = entityBlockPos;
    }

    public SyncTank(FriendlyByteBuf buf){
        this.tank = new SteamTank(0,0,0);
        this.tank.fromBytes(buf);
        this.pos = buf.readBlockPos();
    }

    public void toBytes(FriendlyByteBuf buf){
        this.tank.toBytes(buf);
        buf.writeBlockPos(pos);
    }


    public boolean handle(Supplier<NetworkEvent.Context> supplier){
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            Minecraft mine = Minecraft.getInstance();
            if(mine.level.getBlockEntity(pos) instanceof ISteamCapabilityProvider<?> provider){
                provider.syncTank(tank);
            }
        });
        return true;
    }
}
