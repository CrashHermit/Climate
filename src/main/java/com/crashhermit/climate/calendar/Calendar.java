package com.crashhermit.climate.calendar;

import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.WorldTickEvent;

/**
 * Created by joshua on 4/20/17.
 * Used to keep track of world time and serve as the main driver for climate
 * INCOMPLETE
 * TODO handle commands from players, different dimensions and servers
 */
public class Calendar
{
    private static float dayTicks;
    private static float dayTicksMax = 24000.0F;

    private static float yearTicks = 0.0F;
    private static float yearTicksMax = 24000.0F * 2.0F; //Config option

    private static float commandTicks = 0.0F;



    public static void setDayTicks(World world)
    {
        dayTicks = (int)world.getWorldTime();
    }

    public static float getDayTicks()
    {
        return dayTicks;
    }
    public static float getDayTicksMax() { return dayTicksMax; }

    public static float getYearTicks() { return yearTicks; }
    public static float getYearTicksMax() {return yearTicksMax; }

    @SubscribeEvent
    public void calendarTime(WorldTickEvent event)
    {
        setDayTicks(event.world);
        //System.out.println("Time:Temperature:GrowthFactor >>" + getDayTicks() + " :  " + Climate.biomeTemperature(Biome.getBiomeForId(1)) + " : " + Growth.growthFactorTemperature(Biome.getBiomeForId(1)));


        if((yearTicks == yearTicksMax) && !(yearTicks < 0) && !(yearTicks > yearTicksMax))
        {
            yearTicks = 0;
        }
        else if (yearTicks < 0)
        {

        }
        else if (yearTicks > yearTicksMax)
        {

        }

        yearTicks++;
    }
}
