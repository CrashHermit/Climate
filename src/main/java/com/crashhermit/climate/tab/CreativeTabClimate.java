package com.crashhermit.climate.tab;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

/**
 * Created by joshua on 4/23/17.
 */
public class CreativeTabClimate extends CreativeTabs {

    public CreativeTabClimate(int index, String label) {
        super(index, label);
    }

    @Override
    public Item getTabIconItem() {
        return Items.WHEAT;
    }
}
