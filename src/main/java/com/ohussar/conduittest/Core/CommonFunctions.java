package com.ohussar.conduittest.Core;

import net.minecraft.core.BlockPos;

public class CommonFunctions {


    public static BlockPos[] adjacentBlocks(BlockPos pos){
        return new BlockPos[]{pos.north(), pos.east(), pos.south(), pos.west(), pos.above(), pos.below()};
    }




}
