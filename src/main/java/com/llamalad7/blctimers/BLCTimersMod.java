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

import cc.hyperium.Hyperium;
import cc.hyperium.event.EventBus;
import cc.hyperium.event.InvokeEvent;
import cc.hyperium.event.client.InitializationEvent;
import cc.hyperium.event.client.PreInitializationEvent;
import cc.hyperium.internal.addons.IAddon;
import com.llamalad7.blctimers.command.ConfigCommand;
import com.llamalad7.blctimers.gui.HUDGui;
import com.llamalad7.blctimers.listeners.TimerListener;
import com.llamalad7.blctimers.listeners.UpdateListener;

public class BLCTimersMod implements IAddon {
    public static final String MODID = "blctimers";
    public static final String VERSION = "1.2";
    public static final String NAME = "BLC Timers";
    private static TimerSettings settings;

    @Override
    public void onLoad() {
        EventBus.INSTANCE.register(this);
    }

    @InvokeEvent
    public void onInitialization(InitializationEvent event) {
        Hyperium.CONFIG.register(settings);
        Hyperium.INSTANCE.getHandlers().getCommandHandler().registerCommand(new ConfigCommand(this));
        EventBus.INSTANCE.register(new HUDGui(settings));
        EventBus.INSTANCE.register(TimerListener.getInstance());
        EventBus.INSTANCE.register(UpdateListener.getInstance());
    }

    @InvokeEvent
    public void onPreInitialization(PreInitializationEvent event) {
        settings = new TimerSettings();
    }

    @Override
    public void onClose() {
        // Log that the addon is being closed
        Hyperium.LOGGER.info("Closing...");
    }

    public static TimerSettings getSettings() {
        return settings;
    }
}
