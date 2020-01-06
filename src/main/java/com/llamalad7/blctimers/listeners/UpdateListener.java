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
package com.llamalad7.blctimers.listeners;

import cc.hyperium.event.EventBus;
import cc.hyperium.event.InvokeEvent;
import cc.hyperium.event.world.EntityJoinWorldEvent;
import cc.hyperium.mods.sk1ercommon.Multithreading;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.llamalad7.blctimers.BLCTimersMod;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.URL;

import static java.lang.Float.parseFloat;

public class UpdateListener {
    private static UpdateListener INSTANCE = new UpdateListener();

    private UpdateListener() {
    }

    public static UpdateListener getInstance() {
        return INSTANCE;
    }

    @InvokeEvent
    public void clientTickEvent(EntityJoinWorldEvent event) {
        Multithreading.runAsync(() -> {
            try {
                EventBus.INSTANCE.unregister(this);
                URL url = new URL("https://raw.githubusercontent.com/lego3708/blctimers/hyperium/latest.json");
                InputStream is = url.openStream();
                StringWriter writer = new StringWriter();
                IOUtils.copy(is, writer, "UTF-8");
                JsonObject data = new JsonParser().parse(writer.toString()).getAsJsonObject();
                if (data.get("version").getAsFloat() > parseFloat(BLCTimersMod.VERSION)) {
                    IChatComponent separator = new ChatComponentText("-----------------------------------------------------").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.GOLD));
                    Minecraft.getMinecraft().thePlayer.addChatMessage(separator);
                    Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "There is a Timers Mod update!"));
                    Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.AQUA + data.get("message").getAsString()));
                    Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.GOLD + "Please reinstall through the Hyperium Installer!"));
                    Minecraft.getMinecraft().thePlayer.addChatMessage(separator);
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        });

    }
}
