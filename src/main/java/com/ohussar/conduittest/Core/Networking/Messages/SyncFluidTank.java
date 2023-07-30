package com.ohussar.conduittest.Core.Networking.Messages;

import com.ohussar.conduittest.ConduitMain;
import com.ohussar.conduittest.Core.FluidTank;
import com.ohussar.conduittest.Core.Interfaces.ISteamCapabilityProvider;
import com.ohussar.conduittest.Core.Interfaces.ISyncFluidTank;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SyncFluidTank {
    private FluidTank tank;
    private BlockPos pos;
    public SyncFluidTank(FluidTank tank, BlockPos entityBlockPos){
        this.tank = tank;
        this.pos = entityBlockPos;
    }

    public SyncFluidTank(FriendlyByteBuf buf){
        this.tank = new FluidTank(FluidStack.EMPTY,0,0);
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
            if(mine.level.getBlockEntity(pos) instanceof ISyncFluidTank provider){
                ConduitMain.LOGGER.info("AAAA");
                provider.receiveSync(tank);
            }
        });
        return true;
    }
}
