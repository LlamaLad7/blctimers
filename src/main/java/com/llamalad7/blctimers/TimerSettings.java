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
package com.llamalad7.blctimers;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;


public class TimerSettings {
    private Configuration config;
    public boolean enabled;
    public int xpos;
    public int ypos;
    public int scale;
    public boolean showText;
    public boolean showIcon;

    public TimerSettings(Configuration config) {
        this.config = config;
    }

    public void saveConfig() {
        updateConfig(false);
        config.save();
    }

    public void loadConfig() {
        config.load();
        updateConfig(true);
    }

    public void resetConfig() {
        Property prop;

        prop = config.get("All", "Enabled", true);
        prop.set(enabled = true);

        prop = config.get("All", "ShowIcons", true);
        prop.set(showIcon = true);

        prop = config.get("All", "ShowNames", true);
        prop.set(showText = true);

        prop = config.get("All", "XPosition", 2);
        prop.set(xpos = 2);

        prop = config.get("All", "YPosition", 2);
        prop.set(ypos = 2);

        prop = config.get("All", "Scale", 100);
        prop.set(scale = 100);

        config.save();
    }

    private void updateConfig(boolean load) {
        Property prop;

        prop = config.get("All", "Enabled", true);
        if (load) enabled = prop.getBoolean();
        else prop.set(enabled);

        prop = config.get("All", "ShowIcons", true);
        if (load) showIcon = prop.getBoolean();
        else prop.set(showIcon);

        prop = config.get("All", "ShowNames", true);
        if (load) showText = prop.getBoolean();
        else prop.set(showText);

        prop = config.get("All", "XPosition", 2);
        if (load) xpos = prop.getInt();
        else prop.set(xpos);

        prop = config.get("All", "YPosition", 2);
        if (load) ypos = prop.getInt();
        else prop.set(ypos);

        prop = config.get("All", "Scale", 100);
        if (load) scale = prop.getInt();
        else prop.set(scale);
    }
}
