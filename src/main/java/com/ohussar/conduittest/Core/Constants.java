package com.ohussar.conduittest.Core;

public class Constants {

    public static double MOL_PER_M3 = 0.036;
    public static double GAS_CONSTANT = 0.082;
    public static double TO_BAR = 1.013;

    public static double SPECIFIC_HEAT = 452;

    public static final double PRESSURE_MULT = 0.5;


    public static double getPressure(double steamvolume, double containervolume){
        return (steamvolume/containervolume) * PRESSURE_MULT;
    }
    public static double Pressure(double volume, double steamvolume, double temp){
        return ((MOL_PER_M3 * GAS_CONSTANT * temp * steamvolume) / volume) * TO_BAR;
    }

    /**
     * Assuming that the shape is a square, Volume = s^3 ; Surface area is Sa = 6s^2,
     * rearranging the equation we have that: V = (Sa * s)/6 -> Sa = (6*V)/s
     * @param volume
     * @param sideSize
     * @return Returns the surface area
     */

    public static double VolumeToSurfaceArea(double volume, double sideSize){
        return (6*volume) / sideSize;
    }

    public static double WattsToTemperatureTransfer(double mass, double time, double wattsEnergy){
        double T = (wattsEnergy * time) / (SPECIFIC_HEAT * mass);
        return T;
    }

    public static double TemperatureToWatts(double mass, double deltaT, double time){
        return SPECIFIC_HEAT * mass * deltaT * time;
    }

}
