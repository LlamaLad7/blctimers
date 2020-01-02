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
 *       You should have received a copy of the GNU Lesser General Public License
 *       along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.llamalad7.blctimers;

import cc.hyperium.Hyperium;
import cc.hyperium.addons.sidebar.config.Configuration;
import cc.hyperium.config.ConfigOpt;

public class TimerSettings {
    @ConfigOpt
    public boolean enabled = true;
    @ConfigOpt
    public int xpos = 2;
    @ConfigOpt
    public int ypos = 2;
    @ConfigOpt
    public int scale = 100;

    public void resetConfig() {
        enabled = true;
        xpos = 2;
        ypos = 2;
        scale = 100;

        Hyperium.CONFIG.save();
    }
}
