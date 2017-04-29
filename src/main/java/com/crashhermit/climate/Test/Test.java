package com.crashhermit.climate.Test;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;

import java.util.Random;

/**
 * Created by joshua on 4/28/17.
 */
public class Test extends TileEntity implements ITickable
{

    private byte speed;
    private Random rand;

    @Override
    public void update() {
        if (this.worldObj.isRemote)
            return;
    }

    protected int speed(final int base) {
        return base;
    }

    private void tickBlock(final BlockPos pos)
    {
        final IBlockState blockState = this.worldObj.getBlockState(pos);
        final Block block = blockState.getBlock();


        if (block.getTickRandomly()) {
            for (int i = 0; i < this.speed; i++) {
                if (getWorld().getBlockState(pos) != blockState)
                    break;
                block.updateTick(this.worldObj, pos, blockState, this.rand);
            }
        }
    }
}
