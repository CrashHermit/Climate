package com.crashhermit.climate.config;

import com.crashhermit.climate.climate.Climate;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.config.Configuration;

import java.io.File;
import java.util.Map;

/**
 * Created by joshua on 4/22/17.
 */
public class Config
{
    protected final static String configPath = "config/Configurable_Climate/";
    public static Configuration config = new Configuration(new File(configPath + "ConfigurableClimate.cfg"));
    public static void loadConfig() throws Exception
    {
        config.load();
        for(Map.Entry<Biome, Float> entry : Climate.biomeTemperatureMap.entrySet())
        {
            String key = entry.getKey().getBiomeName();
            Float value = entry.getValue();

            config.getFloat(key + "base temperature", "Biomes", value, -1.0F, 1.0F, "Comment");
            System.out.println("Hello!");
        }
        config.save();
    }
}
