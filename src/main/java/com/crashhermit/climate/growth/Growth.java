package com.crashhermit.climate.growth;

import com.crashhermit.climate.climate.Climate;
import com.crashhermit.climate.utilities.MathUtilities;
import com.google.common.collect.HashMultimap;
import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by joshua on 4/21/17.
 * The class that controls growth factors based on biomes for temperature and
 * rainfall. Both methods use the custom "intuitive" and configurable polynomial
 * to generate a value between 0 and 1 as a growth factor.
 */
public class Growth
{
    private static MathUtilities mathUtilities = new MathUtilities();

    private static List<Block> blocks = ForgeRegistries.BLOCKS.getValues();
    private static HashMap<Integer, Block> blockMap = new HashMap<Integer, Block>();
    private static HashMultimap<Block, Float> blockMaps = HashMultimap.create();

    /**
     * Save this block of code for later, it will be used to generate a map of viable blocks
     * and allow me to generate config values for each type.
     */
    public static void blockMap()
    {

        for(int i = 0; i < blocks.size(); i++)
        {
            if (blocks.get(i) instanceof IGrowable || blocks.get(i) instanceof IPlantable)
            {
                blockMaps.put(blocks.get(i), 1.0F);
                blockMaps.put(blocks.get(i), 2.0F);
                blocks.get(i).setTickRandomly(false);
            }
        }

        for (Map.Entry entry : blockMaps.entries()) {

            Object key = entry.getKey();
            Object value = entry.getValue();

            System.out.println("BlockID:Name     " + key + " : " + value);
        }
    }



    public static float growthFactorTemperature(Biome biome)
    {
        return mathUtilities.clampFloat(mathUtilities.quadraticFunction(
                1.0F,
                true,
                2,
                Climate.biomeTemperature(biome),
                0.0F,
                0.0F
        ), 0.0F, 1.0F);
    }

    public static float growthFactorRainfall(Biome biome)
    {
        return mathUtilities.clampFloat(mathUtilities.quadraticFunction(
                1.0F,
                true,
                2,
                Climate.biomeRainfall(biome),
                0.0F,
                0.0F
        ), 0.0F, 1.0F);
    }
}
