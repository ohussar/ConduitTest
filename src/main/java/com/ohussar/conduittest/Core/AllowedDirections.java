package com.ohussar.conduittest.Core;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;

public class AllowedDirections {
    //east = right, west = left
    public boolean right = false;
    public boolean left = false;
    public boolean back = false;
    public boolean front = false;
    public boolean up = false;
    public boolean down = false;

    public AllowedDirections setValue(String property, boolean value){
        switch(property){
            case "right":
                this.right = value; break;
            case "left":
                this.left = value; break;
            case "back":
                this.back = value; break;
            case "front":
                this.front = value; break;
            case "up":
                this.up = value; break;
            case "down":
                this.down = value; break;
        }
        return this;
    }


    public boolean isAllowed(BlockPos pos, BlockPos compare, Direction facing){
        boolean value = false;

        if(right){
            value |= pos.relative(facing.getClockWise()).equals(compare);
        }
        if(left){
            value |= pos.relative(facing.getCounterClockWise()).equals(compare);
        }
        if(back){
            value |= pos.relative(facing.getOpposite()).equals(compare);
        }
        if(front){
            value |= pos.relative(facing).equals(compare);
        }
        if(up){
            value |= pos.above().equals(compare);
        }
        if(down){
            value |= pos.below().equals(compare);
        }

        return value;
    }
}
