package com.crashhermit.climate.main;

import com.crashhermit.climate.calendar.Calendar;
import com.crashhermit.climate.climate.Climate;
import com.crashhermit.climate.event.GrowthEvent;
import com.crashhermit.climate.growth.Growth;
import com.crashhermit.climate.proxy.CommonProxy;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = ClimateMain.MODID, version = ClimateMain.VERSION)
public class ClimateMain
{
    public static final String MODID = "main";
    public static final String VERSION = "1.0";

    @SidedProxy(clientSide = "com.crashhermit.climate.proxy.ClientProxy", serverSide = "com.crashhermit.climate.proxy.CommonProxy")
    public static CommonProxy proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        MinecraftForge.EVENT_BUS.register(new GrowthEvent());
        MinecraftForge.TERRAIN_GEN_BUS.register(new GrowthEvent());
        MinecraftForge.ORE_GEN_BUS.register(new GrowthEvent());

        MinecraftForge.EVENT_BUS.register(new Calendar());
        MinecraftForge.EVENT_BUS.register(new Climate());

        proxy.preInit(event);
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        proxy.init(event);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        Climate.biomeBaseClimate();
        Growth.blockMap();
        proxy.postInit(event);
    }
}
