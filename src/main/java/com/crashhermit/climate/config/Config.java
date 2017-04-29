package com.crashhermit.climate.config;

import com.crashhermit.climate.climate.Climate;
import com.crashhermit.climate.utilities.MathUtilities;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Iterables;
import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.io.File;
import java.lang.reflect.Array;
import java.util.*;

import static com.crashhermit.climate.climate.Climate.biomeTemperatureMap;

/**
 * Created by joshua on 4/22/17.
 */
public class Config
{
    public static List<Block> blocks = ForgeRegistries.BLOCKS.getValues();
    //public static List<Biome> biomes = ForgeRegistries.BIOMES.getValues();

//    private static HashMap<Biome, Float> biomeTemperatureMap = new HashMap<Biome, Float>();
  //  private static HashMap<Biome, Float> biomeRainfallMap = new HashMap<Biome, Float>();

    private static ArrayList<Float> cropFloatValues = new ArrayList<Float>();
    private static HashMap<Block, ArrayList<Float>> allTheBlocks = new HashMap();
    private static HashMultimap<Block, Float> blockMaps = HashMultimap.create();

    private static MathUtilities mathUtilities = new MathUtilities();

    protected final static String configPath = "config/Configurable_Climate/";
    public static Configuration config = new Configuration(new File(configPath + "ConfigurableClimate.cfg"));

    /*
    public static void blockMap()
    {

        for (int i = 0; i < blocks.size(); i++)
        {
            if (blocks.get(i) instanceof IGrowable || blocks.get(i) instanceof IPlantable)
            {
                cropFloatValues.set(0, 1.0F);
                cropFloatValues.set(1, 0.0F);
                cropFloatValues.set(2, 1.0F);
                cropFloatValues.set(3, 0.0F);
                cropFloatValues.set(4, 0.0F);

                allTheBlocks.put(blocks.get(i), cropFloatValues);
            }
        }

        for (Map.Entry<Block, ArrayList<Float>> entry : allTheBlocks.entrySet())
        {
            Block key = entry.getKey();
            ArrayList<Float> value = entry.getValue();

            for(float aFloat : value)
            {
                System.out.println("Block:Value      " + key + ":" + aFloat);
            }
        }
    }
    */

    public static void loadConfig() throws Exception
    {
        config.load();

        /*
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
        */


        for(Map.Entry<Biome, Float> entry : biomeTemperatureMap.entrySet())
        {
            String key = entry.getKey().getBiomeName();
            Float value = entry.getValue();

            config.getFloat(key + "base temperature", "Biomes", value, -1.0F, 1.0F, "Comment");
        }

        cropFloatValues.add(0, 1.0F);
        cropFloatValues.add(1, 0.0F);
        cropFloatValues.add(2, 2.0F);
        cropFloatValues.add(3, 0.0F);
        cropFloatValues.add(4, 0.0F);

        for (int i = 0; i < blocks.size(); i++)
        {
            if (blocks.get(i) instanceof IGrowable || blocks.get(i) instanceof IPlantable)
            {
                String name = blocks.get(i).toString();

                cropFloatValues.set(0, config.getFloat(blocks.get(i) + "Polynomial Multiplier", name, 1.0F, -1.0F, 1.0F, "Comment"));
                cropFloatValues.set(1, config.getFloat(blocks.get(i) + "Flipped", name, 1.0F, 0, 1, "Comment"));
                cropFloatValues.set(2, config.getFloat(blocks.get(i) + "Polynomial Degree", name, 2.0F, -1.0F, 1.0F, "Comment"));
                cropFloatValues.set(3, config.getFloat(blocks.get(i) + "Polynomial X Offset", name, 0.0F, -1.0F, 1.0F, "Comment"));
                cropFloatValues.set(4, config.getFloat(blocks.get(i) + "Polynomial Y Offset", name, 0.0F, -1.0F, 1.0F, "Comment"));

                allTheBlocks.put(blocks.get(i), cropFloatValues);
                System.out.println(blocks.get(i) + ":" + allTheBlocks.get(blocks.get(i)));
            }
        }

        /*
        for (Map.Entry<Block, ArrayList<Float>> entry : allTheBlocks.entrySet())
        {
            Block key = entry.getKey();
            ArrayList<Float> value = entry.getValue();

            for(float aFloat : value)
            {
                System.out.println("Block:Value      " + key + ":" + aFloat);
            }
        }
        */

        /*
        for (Map.Entry<Block, ArrayList<Float>> entry : allTheBlocks.entrySet())
        {
            Block key = entry.getKey();
            ArrayList<Float> value = entry.getValue();

            for(float aFloat : value)
            {
                allTheBlocks.put(key, config.getFloat(key + "Polynomial Multiplier", "Crop growth", aFloat, -1.0F, 1.0F, "Comment"));
                allTheBlocks.put(key, config.getFloat(key + "Flipped", "Crop flipped", 1.0F, 0, 1, "Comment"));
                allTheBlocks.put(key, config.getFloat(key + "Polynomial Degree", "Crop degree", 2.0F, -1.0F, 1.0F, "Comment"));
                allTheBlocks.put(key, config.getFloat(key + "Polynomial X Offset", "Crop x offset", 0.0F, -1.0F, 1.0F, "Comment"));
                allTheBlocks.put(key, config.getFloat(key + "Polynomial Y Offset", "Crop y offset", 0.0F, -1.0F, 1.0F, "Comment"));
            }
        }
        */


        /*
        for(Map.Entry<Block, Float> entry: blockMaps.entries())
        {
            String key = entry.getKey().getUnlocalizedName();
            Block name = entry.getKey();

            blockMaps.put(name, config.getFloat(key + "Polynomial Multiplier", "Crop growth", 1.0F, -1.0F, 1.0F, "Comment"));
            blockMaps.put(name, config.getFloat(key + "Flipped", "Crop flipped", 1.0F, 0, 1, "Comment"));
            blockMaps.put(name, config.getFloat(key + "Polynomial Degree", "Crop degree", 2.0F, -1.0F, 1.0F, "Comment"));
            blockMaps.put(name, config.getFloat(key + "Polynomial X Offset", "Crop x offset", 0.0F, -1.0F, 1.0F, "Comment"));
            blockMaps.put(name, config.getFloat(key + "Polynomial Y Offset", "Crop y offset", 0.0F, -1.0F, 1.0F, "Comment"));

            System.out.println(Iterables.get(blockMaps.get(entry.getKey()), 4));

        }
        */

        for (Map.Entry entry : blockMaps.entries()) {

            Object key = entry.getKey();
            Object value = entry.getValue();

            //System.out.println("BlockID:Name     " + key + " : " + value);

        }

        config.save();
    }
}
