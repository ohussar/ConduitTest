package com.ohussar.conduittest.Core;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CommonFunctions {


    public static BlockPos[] adjacentBlocks(BlockPos pos){
        return new BlockPos[]{pos.north(), pos.east(), pos.south(), pos.west(), pos.above(), pos.below()};
    }
    // NOT MY CODE, GOT IT FROM:
    // https://forums.minecraftforge.net/topic/74979-1144-rotate-voxel-shapes/
    public static VoxelShape rotateShape(Direction from, Direction to, VoxelShape shape) {
        VoxelShape[] buffer = new VoxelShape[]{shape, Shapes.empty()};

        int times = (to.ordinal() - from.get2DDataValue() + 4) % 4;
        for (int i = 0; i < times; i++) {
            buffer[0].forAllBoxes((minX, minY, minZ, maxX, maxY, maxZ) -> buffer[1] = Shapes.or(buffer[1], Shapes.create(1 - maxZ, minY, minX, 1 - minZ, maxY, maxX)));
            buffer[0] = buffer[1];
            buffer[1] = Shapes.empty();
        }

        return buffer[0];
    }


}
