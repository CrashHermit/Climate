package com.crashhermit.climate.main;

import com.crashhermit.climate.calendar.Calendar;
import com.crashhermit.climate.climate.Climate;
import com.crashhermit.climate.config.Config;
import com.crashhermit.climate.event.GrowthEvent;
import com.crashhermit.climate.growth.Growth;
import com.crashhermit.climate.proxy.CommonProxy;
import com.crashhermit.climate.tab.CreativeTabClimate;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = ClimateMain.MODID, version = ClimateMain.VERSION, name = ClimateMain.NAME)
public class ClimateMain
{
    public static final String MODID = "climate";
    public static final String VERSION = "1.0";
    public static final String NAME = "Climate";

    @SidedProxy(clientSide = "com.crashhermit.climate.proxy.ClientProxy", serverSide = "com.crashhermit.climate.proxy.CommonProxy")
    public static CommonProxy proxy;

    @Mod.Instance
    public static ClimateMain instance;

    public static CreativeTabClimate tabClimate;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        tabClimate = new CreativeTabClimate(CreativeTabs.getNextID(), "tab_climate");
        proxy.preInit(event);
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        proxy.init(event);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) throws Exception
    {
        MinecraftForge.EVENT_BUS.register(new Calendar());
        MinecraftForge.EVENT_BUS.register(new GrowthEvent());
        MinecraftForge.TERRAIN_GEN_BUS.register(new GrowthEvent());
        MinecraftForge.ORE_GEN_BUS.register(new GrowthEvent());
        Climate.biomeBaseClimate();
        Growth.blockMap();
        Config.loadConfig();
        proxy.postInit(event);
    }
}
