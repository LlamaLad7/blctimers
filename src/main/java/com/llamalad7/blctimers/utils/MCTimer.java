/*
 *       Copyright (C) 2019-present LlamaLad7 <https://github.com/lego3708>
 *
 *       This program is free software: you can redistribute it and/or modify
 *       it under the terms of the GNU General Public License as published
 *       by the Free Software Foundation, either version 3 of the License, or
 *       (at your option) any later version.
 *
 *       This program is distributed in the hope that it will be useful,
 *       but WITHOUT ANY WARRANTY; without even the implied warranty of
 *       MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *       GNU Lesser General Public License for more details.
 *
 *       You should have received a copy of the GNU General Public License
 *       along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.llamalad7.blctimers.utils;

import com.llamalad7.blctimers.BLCTimersMod;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class MCTimer {
    public boolean repeated;
    public String name;
    public int time;
    public ItemStack item;
    public int startTime;

    public MCTimer(boolean r, String n, int t, int startT, String itemName) {
        repeated = r;
        name = n;
        time = t;
        startTime = startT;
        item = new ItemStack(Item.getByNameOrId(itemName.toLowerCase()));
    }

    public String getFormattedTime() {
        if (BLCTimersMod.getSettings().showText) return name + ": " + Math.floorDiv(time, 60) + ":" + String.format("%02d", Math.floorMod(time, 60));
        else return Math.floorDiv(time, 60) + ":" + String.format("%02d", Math.floorMod(time, 60));
    }
}
