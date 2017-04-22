package com.crashhermit.climate.calendar;

import net.minecraft.command.ICommand;
import net.minecraft.world.World;
import net.minecraftforge.event.CommandEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.WorldTickEvent;

import java.util.List;

/**
 * Created by joshua on 4/20/17.
 * Used to keep track of world time and serve as the main driver for climate
 * INCOMPLETE
 * TODO handle commands from players, different dimensions and servers
 */
public class Calendar
{
    private static long ticksPost = 0;

    private static float dayTicks;
    private static float dayTicksMax = 24000.0F;

    private static float yearTicks = 0.0F;
    private static float yearTicksMax = 24000.0F * 2.0F; //Config option

    private static float commandTicks = 0.0F;

    public static void setDayTicks(long ticksIn)
    {
        if( ((int)ticksIn % (int)dayTicksMax) == 0 )
        {
            dayTicks = 0.0F;
        }
        else
        {
            dayTicks = ticksIn;
        }

    }

    public static void setYearTicks(long ticksIn)
    {

        if( (int)ticksIn % (int)yearTicksMax == 0 )
        {
            yearTicks = 0.0F;
        }
        else
        {
            yearTicks = ticksIn;
        }
    }

    public static void setYearTicksMax(int yearTicksMaxIn ) { yearTicksMax = dayTicksMax * (float)yearTicksMaxIn; }

    public static float getDayTicks()
    {
        return dayTicks;
    }
    public static float getDayTicksMax() { return dayTicksMax; }

    public static float getYearTicks() { return yearTicks; }
    public static float getYearTicksMax() {return yearTicksMax; }

    @SubscribeEvent
    public void commandTime (CommandEvent event)
    {


    }


    @SubscribeEvent
    public void calendarTime(WorldTickEvent event)
    {
        if(event.phase == TickEvent.Phase.END && event.world.provider.getDimension() == 0)
        {
            long ticksPre = event.world.getWorldTime();
           // System.out.println("PRE: " + ticksPre);

            if( (ticksPre % dayTicksMax) == 0 )
            {
                dayTicks = 0.0F;
                System.out.println("FIREDAY");
            }
            if( (ticksPre - ticksPost) > 1)
            {
                dayTicks += ticksPre - ticksPost;
            }
            if ( dayTicks / dayTicksMax > 1)
            {
                do
                {
                    dayTicks -= dayTicksMax;
                }
                while(dayTicks / dayTicksMax > 1);
            }


                dayTicks++;


            if( ticksPre % yearTicksMax == 0 )
            {
                yearTicks = 0.0F;
                System.out.println("FIREYEAR");
            }
            if( (ticksPre - ticksPost) > 1)
            {
                yearTicks += ticksPre - ticksPost;
            }
            if ( (yearTicks / yearTicksMax) > 1 )
            {
                do
                {
                    yearTicks -= yearTicksMax;
                }
                while(yearTicks / yearTicksMax > 1);
            }

                yearTicks++;


            ticksPost = event.world.getWorldTime();
            //System.out.println("POST: " + ticksPost);

            System.out.println("ticks:dayTicks:YearTicks " + ticksPost + ":" + dayTicks + ":" + yearTicks);
        }




        /*
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
        */
    }

}
