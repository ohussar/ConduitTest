package com.ohussar.conduittest.Core;

public class DirectionHolder {

    public int north;
    public int east;
    public int south;
    public int west;
    public int up;
    public int down;

    public DirectionHolder(int north, int east, int south, int west, int up, int down){
        this.north = north;
        this.east = east;
        this.south = south;
        this.west = west;
        this.up = up;
        this.down = down;
    }
     /**
     Returns the directions as arrays
     @return int[] with the form: {north, east, south, west, up, down}
     */
    public int[] toArray(){
        return new int[]{north, east, south, west, up, down};
    }

    public boolean noNeighbors(){
        return north == 0 && east == 0 && south == 0 && west == 0 && up == 0 && down == 0;
    }

}
