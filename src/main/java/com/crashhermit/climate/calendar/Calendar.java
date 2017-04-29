package com.crashhermit.climate.calendar;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.WorldTickEvent;

/**
 * Created by joshua on 4/20/17.
 * Used to keep track of world time and serve as the main driver for climate
 * It can currently handle time commands from players that change the world time.
 * TODO cleanup code and make senseable getters
 */
public class Calendar
{
    private static long ticksPost = 0;

    private static float dayTicks;
    private static float dayTicksMax = 24000.0F;

    private static float yearTicks = 0.0F;
    private static float yearTicksMax = 24000.0F * 4.0F; //Config option, multiplier is how many days a year is

    public static void setYearTicksMax(int yearTicksMaxIn ) { yearTicksMax = dayTicksMax * (float)yearTicksMaxIn; }

    @SubscribeEvent
    public void calendarTime(WorldTickEvent event)
    {
        if(event.phase == TickEvent.Phase.START && event.world.provider.getDimension() == 0)
        {
            long ticksPre = event.world.getWorldTime();
           // System.out.println("PRE: " + ticksPre);

            dayTicks++;

            if(ticksPre == dayTicksMax)
            {
                dayTicks = 0.0F;
            }
            else if( (ticksPre - ticksPost) > 1)
            {
                dayTicks += ticksPre - ticksPost - 1;
            }
            else if( (ticksPost - ticksPre) > 1)
            {
                dayTicks -= ticksPost - ticksPre + 1;
            }

            if ( dayTicks / dayTicksMax > 1)
            {
                do
                {
                    dayTicks -= dayTicksMax;
                }
                while(dayTicks / dayTicksMax > 1);
            }

            if( dayTicks < 0.0F && ticksPre == 0.0F)
            {
                dayTicks = 0.0F;
            }
            else
            {
                dayTicks = Math.abs(dayTicks);
            }



            yearTicks++;

            if( ticksPre == yearTicksMax )
            {
                yearTicks = 0.0F;
            }
            else if( (ticksPre - ticksPost) > 1)
            {
                yearTicks += ticksPre - ticksPost - 1;
            }
            else if( (ticksPost - ticksPre) > 1)
            {
                yearTicks -= ticksPost - ticksPre + 1;
            }

            if ( (yearTicks / yearTicksMax) > 1 )
            {
                do
                {
                    yearTicks -= yearTicksMax;
                }
                while(yearTicks / yearTicksMax > 1);
            }

            if( yearTicks < 0.0F && ticksPre == 0.0F)
            {
                yearTicks = 0.0F;
            }
            else
            {
                yearTicks = Math.abs(yearTicks);
            }



            ticksPost = event.world.getWorldTime();


            //System.out.println("ticks:dayTicks:YearTicks   " + ticksPre + ":" + dayTicks + ":" + yearTicks);
        }
    }

    public static float getDayTicks() { return dayTicks; }
    public static float getDayTicksMax() { return dayTicksMax; }

    public static float getYearTicks() { return yearTicks; }
    public static float getYearTicksMax() {return yearTicksMax; }

}
