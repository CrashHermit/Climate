package com.crashhermit.climate.event;

import com.crashhermit.climate.calendar.*;
import com.crashhermit.climate.growth.Growth;
import com.crashhermit.climate.utilities.MathUtilities;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSapling;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.event.terraingen.SaplingGrowTreeEvent;
import net.minecraftforge.event.world.BlockEvent.CropGrowEvent.Pre;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.util.*;


/**
 * Created by joshua on 4/18/17.
 * The class that controls the actual growth events for saplings
 * and crops registered under the respective events. Some modded
 * crops and saplings will not be detected if the authors did
 * not include the events in their blocks.
 */
public class GrowthEvent
{
    /*
    public static List<Biome> biomes = ForgeRegistries.BIOMES.getValues();
    public static List<Block> blocks = ForgeRegistries.BLOCKS.getValues();
    public static HashMap<Integer, Float> biomeTemperatureMap = new HashMap<Integer, Float>();
    public static HashMap<Integer, Float> biomeRainfallMap = new HashMap<Integer, Float>();
    public static HashMap<Integer, Block> blockMap = new HashMap<Integer, Block>();

    public static void hashMaps()
    {
        MathUtilities mathUtilities = new MathUtilities();

        for(int i = 0; i < biomes.size(); i++)
        {
            int biomeID = Biome.getIdForBiome(biomes.get(i));

            float temperature = Biome.getBiome(Biome.getIdForBiome(biomes.get(i))).getTemperature();
            float rainfall = Biome.getBiome(Biome.getIdForBiome(biomes.get(i))).getRainfall();

            biomeTemperatureMap.put(biomeID, temperature);
            biomeRainfallMap.put(biomeID, rainfall);

        }

        float maxTemperature = Collections.max(biomeTemperatureMap.values());
        float minTemperature = Collections.min(biomeTemperatureMap.values());


        for (Map.Entry<Integer,Float> entry : biomeTemperatureMap.entrySet()) {

            float normalizedTemperature = (mathUtilities.normalizer(entry.getValue(), minTemperature, maxTemperature) * 2.0F) - 1.0F;
            biomeTemperatureMap.put(entry.getKey(), normalizedTemperature);

            Integer key = entry.getKey();
            Float value = entry.getValue();

            System.out.println("BiomeID:Temperature     " + key + " : " + value);

        }

        float maxRainfall = Collections.max(biomeRainfallMap.values());
        float minRainfall = Collections.min(biomeRainfallMap.values());

        for (Map.Entry<Integer,Float> entry : biomeRainfallMap.entrySet()) {

            float normalizedRainfall = (mathUtilities.normalizer(entry.getValue(), minRainfall, maxRainfall) * 2.0F) - 1.0F;
            biomeRainfallMap.put(entry.getKey(), normalizedRainfall);

            Integer key = entry.getKey();
            Float value = entry.getValue();

            System.out.println("BiomeID:Rainfall     " + key + " : " + value);
        }

        //Save this block of code for later, it will be used to generate a map of viable blocks
        //and allow me to generate config values for each type.

        for(int i = 0; i < blocks.size(); i++)
        {
            int blockID = Block.getIdFromBlock(blocks.get(i));
            Block blockName = Block.getBlockById(blockID);

            blockMap.put(blockID, blockName);
        }

        for (Map.Entry<Integer,Block> entry : blockMap.entrySet()) {

            Integer key = entry.getKey();
            Block value = entry.getValue();

            System.out.println("BlockID:Name     " + key + " : " + value);
        }
    }
    */

    /*****************************************
     * The event that detects sapling growth and will deny or allow based on
     * growth factors from the growth class.
     * TODO get lightvalues, canSeeSky, and height from the block position to process
     * @param event
     */
    @SubscribeEvent
    public void saplingGrowthEvent(SaplingGrowTreeEvent event)
    {
        if(!(event.getWorld().getBlockState(event.getPos()).getBlock() instanceof BlockSapling))
            return;


        float random = (float)Math.random();
        World world = event.getWorld();
        Block block = event.getWorld().getBlockState(event.getPos()).getBlock();
        int meta = event.getWorld().getBlockState(event.getPos()).getBlock().getMetaFromState(event.getWorld().getBlockState(event.getPos()));
        BlockPos pos = event.getPos();

        Biome biome = event.getWorld().getBiome(pos);
        int lightLevel = event.getWorld().getBlockState(pos).getBlock().getLightValue(event.getWorld().getBlockState(pos), world, pos);
        boolean seeSky = event.getWorld().canSeeSky(pos);

        float growthFactor = (Growth.growthFactorTemperature(biome, block) + Growth.growthFactorRainfall(biome)) / 2.0F;

        //System.out.println("----------------------------------Sapling------------------------");
        if( random >= growthFactor )
        {
            event.setResult(Event.Result.DENY);
            //System.out.println("DENY");
            System.out.println(event.getWorld().getBlockState(event.getPos()));
        }
        else
        {
            event.setResult(Event.Result.ALLOW);
            //System.out.println("ALLOW");
            System.out.println(event.getWorld().getBlockState(event.getPos()));
        }
        /*
        System.out.println("random: " + random);
        System.out.println("growth: " + growthFactor);
        System.out.println("lightlevel: " + lightLevel);
        System.out.println("seeSky: " + seeSky);
        System.out.println("block: " + block);
        System.out.println("meta: " + meta);
        System.out.println("------------------------------------Sapling----------------------");
        System.out.println("                                                                ");
        */
    }


    /*****************************************
     * The event that detects crop growth and will deny or allow based on
     * growth factors from the growth class.
     * TODO get lightvalues, canSeeSky, and height from the block position to process
     * @param event
     */
    @SubscribeEvent
    public void cropGrowthEvent(Pre event)
    {

        float random = (float)Math.random();
        World world = event.getWorld();
        Block block = event.getState().getBlock();
        BlockPos pos = event.getPos();

        Biome biome = event.getWorld().getBiome(pos);
        int lightLevel = event.getWorld().getBlockState(pos).getBlock().getLightValue(event.getWorld().getBlockState(pos), world, pos);
        boolean seeSky = event.getWorld().canSeeSky(pos);

        float growthFactor = (Growth.growthFactorTemperature(biome, block) + Growth.growthFactorRainfall(biome)) / 2.0F;

        //System.out.println("--------------------------------------Crop----------------------");
        if( random >= growthFactor )
        {
            event.setResult(Event.Result.DENY);
            //System.out.println("DENY");
            //System.out.println(event.getWorld().getBlockState(event.getPos()));
        }
        else
        {
            event.setResult(Event.Result.ALLOW);
            //System.out.println("ALLOW");
            //System.out.println(event.getWorld().getBlockState(event.getPos()));
        }
        /*
        System.out.println("random: " + random);
        System.out.println("growth: " + growthFactor);
        System.out.println("lightlevel: " + lightLevel);
        System.out.println("seeSky: " + seeSky);
        System.out.println("block: " + block);
        System.out.println("---------------------------------------Crop---------------------");
        System.out.println("                                                                ");
        */
    }

}
