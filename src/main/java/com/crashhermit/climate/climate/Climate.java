package com.crashhermit.climate.climate;

import com.crashhermit.climate.calendar.Calendar;
import com.crashhermit.climate.utilities.MathUtilities;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by joshua on 4/20/17.
 * The purpose of this class is to control various aspects of the climate
 * It creates the base temperature and rainfall values for all the biomes
 * and handles modded biomes also. The
 */
public class Climate
{
    public static List<Biome> biomes = ForgeRegistries.BIOMES.getValues();
    public static HashMap<Biome, Float> biomeTemperatureMap = new HashMap<Biome, Float>();
    private static HashMap<Biome, Float> biomeRainfallMap = new HashMap<Biome, Float>();
    private static MathUtilities mathUtilities = new MathUtilities();

    private static float biomeTemperatureDailyXOffset = 0.0F;
    private static float biomeTemperatureDailyYOffset = 0.0F;
    private static float biomeTemperatureDailyAmplitude = 100.0F;

    private static float biomeTemperatureYearlyXOffset = 0.0F;
    private static float biomeTemperatureYearlyYOffset = 0.0F;
    private static float biomeTemperatureYearlyAmplitude = 100.0F;

    private static float biomeTemperatureDailyToYearlyRatio = 100.0F;


    private static float biomeRainfallDailyXOffset = 0.0F;
    private static float biomeRainfallDailyYOffset = 0.0F;
    private static float biomeRainfallDailyAmplitude = 100.0F;

    private static float biomeRainfallYearlyXOffset = 0.0F;
    private static float biomeRainfallYearlyYOffset = 0.0F;
    private static float biomeRainfallYearlyAmplitude = 100.0F;

    private static float biomeRainfallDailyToYearlyRatio = 100.0F;

    /****************************************
     * A method used to create a map of all the biomes base temperatures, rainfalls, and normalize them
     * to take on values between -1 and 1
     */
    public static void biomeBaseClimate()
    {

        for (int i = 0; i < biomes.size(); i++)
        {
            Biome biomeID = biomes.get(i);

            float temperature = Biome.getBiome(Biome.getIdForBiome(biomes.get(i))).getTemperature();
            float rainfall = Biome.getBiome(Biome.getIdForBiome(biomes.get(i))).getRainfall();

            biomeTemperatureMap.put(biomeID, temperature);
            biomeRainfallMap.put(biomeID, rainfall);

        }

        float maxTemperature = Collections.max(biomeTemperatureMap.values());
        float minTemperature = Collections.min(biomeTemperatureMap.values());

        for (Map.Entry<Biome, Float> entry : biomeTemperatureMap.entrySet())
        {
            //Forgot about the case where the biome is normalized to 0
            //need to fix that
            float normalizedTemperature = (mathUtilities.normalizer(entry.getValue(), minTemperature, maxTemperature) * 2.0F) - 1.0F;
            biomeTemperatureMap.put(entry.getKey(), normalizedTemperature);



            Biome key = entry.getKey();
            Float value = entry.getValue();

            System.out.println("BiomeID:Temperature     " + key + " : " + value);

        }

        float maxRainfall = Collections.max(biomeRainfallMap.values());
        float minRainfall = Collections.min(biomeRainfallMap.values());

        for (Map.Entry<Biome, Float> entry : biomeRainfallMap.entrySet())
        {

            float normalizedRainfall = (mathUtilities.normalizer(entry.getValue(), minRainfall, maxRainfall) * 2.0F) - 1.0F;
            biomeRainfallMap.put(entry.getKey(), normalizedRainfall);



            Biome key = entry.getKey();
            Float value = entry.getValue();

            System.out.println("BiomeID:Rainfall     " + key + " : " + value);
        }
    }

    /****************************************************************
     * A bunch of getters that serve to clamp the values coming in from config files
     * and do basic error checking.
     * TODO create error checking mechanisms to handle bad values from config files.
     */

    public static float getBiomeBaseTemperature(Biome biome)
    {
        return biomeTemperatureMap.get(biome);
    }

    public static float getBiomeBaseRainfall(Biome biome)
    {
        return biomeRainfallMap.get(biome);
    }

    public static float getBiomeTemperatureDailyXOffset() { return mathUtilities.clampFloat(biomeTemperatureDailyXOffset / 100.0F, 0.0F, 1.0F); }
    public static float getBiomeTemperatureDailyYOffset() { return mathUtilities.clampFloat(biomeTemperatureDailyYOffset / 100.0F, -1.0F, 1.0F); }
    public static float getBiomeTemperatureDailyAmplitude() { return mathUtilities.clampFloat(biomeTemperatureDailyAmplitude / 100.0F, -1.0F, 1.0F); }

    public static float getBiomeTemperatureYearlyXOffset() { return mathUtilities.clampFloat(biomeTemperatureYearlyXOffset / 100.0F, 0.0F, 1.0F); }
    public static float getBiomeTemperatureYearlyYOffset() { return mathUtilities.clampFloat(biomeTemperatureYearlyYOffset / 100.0F, 0.0F, 1.0F); }
    public static float getBiomeTemperatureYearlyAmplitude() { return mathUtilities.clampFloat(biomeTemperatureYearlyAmplitude / 100.0F, -1.0F, 1.0F); }

    public static float getBiomeTemperatureDailyToYearlyRatio() { return mathUtilities.clampFloat(biomeTemperatureDailyToYearlyRatio / 100.0F, 0.0F, 1.0F); }



    public static float getBiomeRainfallDailyXOffset() { return mathUtilities.clampFloat(biomeRainfallDailyXOffset / 100.0F, 0.0F, 1.0F); }
    public static float getBiomeRainfallDailyYOffset() { return mathUtilities.clampFloat(biomeRainfallDailyYOffset / 100.0F, -1.0F, 1.0F); }
    public static float getBiomeRainfallDailyAmplitude() { return mathUtilities.clampFloat(biomeRainfallDailyAmplitude / 100.0F, -1.0F, 1.0F); }

    public static float getBiomeRainfallYearlyXOffset() { return mathUtilities.clampFloat(biomeRainfallYearlyXOffset / 100.0F, 0.0F, 1.0F); }
    public static float getBiomeRainfallYearlyYOffset() { return mathUtilities.clampFloat(biomeRainfallYearlyYOffset / 100.0F, 0.0F, 1.0F); }
    public static float getBiomeRainfallYearlyAmplitude() { return mathUtilities.clampFloat(biomeRainfallYearlyAmplitude / 100.0F, -1.0F, 1.0F); }

    public static float getBiomeRainfallDailyToYearlyRatio() { return mathUtilities.clampFloat(biomeRainfallDailyToYearlyRatio / 100.0F, 0.0F, 1.0F); }

    /****************************************
     * A method used to calculate the combined daily and yearly temperature variations
     * A custom sin function is used to create a more controllable periodic function
     * @param biome
     * @return
     */

    public static float biomeTemperature(Biome biome)
    {

       float biomeTemperatureDaily = mathUtilities.sinFunctionDaily
                (
                Calendar.getDayTicks(),
                Calendar.getYearTicksMax(),
                getBiomeTemperatureDailyXOffset(),
                getBiomeBaseTemperature(biome),
                getBiomeTemperatureDailyAmplitude()
                );

        float biomeTemperatureYearly =
                mathUtilities.sinFunctionYearly
                (
                Calendar.getYearTicks(),
                Calendar.getYearTicksMax(),
                getBiomeTemperatureYearlyXOffset(),
                getBiomeBaseTemperature(biome),
                getBiomeTemperatureYearlyAmplitude()
                );


        System.out.println("YearTicks " + Calendar.getYearTicks());
        System.out.println("YearTicksMax " + Calendar.getYearTicksMax());
        System.out.println("xoffset " + getBiomeTemperatureYearlyXOffset());
        System.out.println("yoffset " + getBiomeTemperatureYearlyYOffset());
        System.out.println("amplitude " + getBiomeTemperatureYearlyAmplitude());
        System.out.println("dailytoyearlyRatio " + getBiomeTemperatureDailyToYearlyRatio());
        System.out.println("biomeTemperatureYearly " + biomeTemperatureYearly);


        return (1.0F - getBiomeTemperatureDailyToYearlyRatio()) * biomeTemperatureDaily + (getBiomeTemperatureDailyToYearlyRatio()) * biomeTemperatureYearly;

    }

    /*************************************************************
     * A method used to calculate the combined daily and yearly rainfall variations
     * A custom sin function is used to create a more controllable periodic function
     * @param biome
     * @return
     */

    public static float biomeRainfall(Biome biome)
    {

        float biomeRainfallDaily = mathUtilities.sinFunctionDaily
                (
                        Calendar.getDayTicks(),
                        Calendar.getYearTicksMax(),
                        getBiomeRainfallDailyXOffset(),
                        getBiomeBaseRainfall(biome),
                        getBiomeRainfallDailyAmplitude()
                );

        float biomeRainfallYearly = mathUtilities.sinFunctionYearly
                (
                        Calendar.getYearTicks(),
                        Calendar.getYearTicksMax(),
                        getBiomeRainfallYearlyXOffset(),
                        getBiomeBaseRainfall(biome),
                        getBiomeRainfallYearlyAmplitude()
                );

        return ((1.0F - getBiomeRainfallDailyToYearlyRatio()) * biomeRainfallDaily + (getBiomeRainfallDailyToYearlyRatio()) * biomeRainfallYearly);

    }
}
