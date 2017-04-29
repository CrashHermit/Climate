package com.crashhermit.climate.event;

import com.crashhermit.climate.growth.Growth;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.WorldTickEvent;

import java.util.List;
import java.util.Random;

/**
 * Created by joshua on 4/29/17.
 */
public class MachineEvent
{
    private static Random random = new Random();

    @SubscribeEvent
    public void machineSpeedUpTicks(WorldTickEvent event)
    {
        if (event.phase == TickEvent.Phase.START && event.world.provider.getDimension() == 0)
        {
            List<TileEntity> allTEs = event.world.tickableTileEntities;

            for (int i = 0; i < allTEs.size(); i++)
            {
                TileEntity tile = allTEs.get(i);
                Biome biome = tile.getWorld().getBiome(tile.getPos());
                Block block = tile.getBlockType();

                float growthFactor = (Growth.growthFactorTemperature(biome, block) + Growth.growthFactorRainfall(biome)) / 2.0F;

                System.out.println("--------------------------------------Machine----------------------");
                if( random.nextFloat() <= growthFactor )
                {
                    System.out.println("DENY");
                }
                else
                {
                    ((ITickable) tile).update();
                    System.out.println("ALLOW");
                }
            }

            /*
            for (int i = 0; i < allTEs.size(); i++)
            {
                TileEntity tile = allTEs.get(i);
                allOthers.put(i, tile);
                allTEs.remove(i);
            }


            for(int i = 0; i < allOthers.size(); i++)
            {
                Block block = allOthers.get(i).getBlockType();
                BlockPos pos = allOthers.get(i).getPos();
                Biome biome = allOthers.get(i).getWorld().getBiome(allOthers.get(i).getPos());
                IBlockState blockState = event.world.getBlockState(pos);
                TileEntity tile = allOthers.get(i);
                World world = allOthers.get(i).getWorld();


                if(tile.getWorld().getBlockState(tile.getPos()) == Material.AIR)
                {
                    allOthers.remove(tile);
                }
                if(rand.nextDouble() < 0.9)
                {
                    System.out.println("DENY");
                    System.out.println(tile);
                }
                else
                {
                    ((ITickable) tile).update();
                    System.out.println("ALLOW");
                }
                */
        }
    }
}