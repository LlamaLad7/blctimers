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
package com.llamalad7.blctimers.command;

import cc.hyperium.Hyperium;
import cc.hyperium.commands.BaseCommand;
import com.llamalad7.blctimers.BLCTimersMod;
import com.llamalad7.blctimers.utils.CountdownTimer;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.NumberInvalidException;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

import static java.lang.Integer.parseInt;

public class ConfigCommand implements BaseCommand {
    private BLCTimersMod mod;

    public ConfigCommand(BLCTimersMod mod) {
        this.mod = mod;
    }

    @Override
    public String getName() {
        return "timers";
    }

    @Override
    public String getUsage() {
        return "/timers";
    }

    @Override
    public void onExecute(String[] args) {
        if (args.length == 0) {
            showHelp();
        } else {
            switch (args[0]) {
                case "demo":
                    CountdownTimer.demoTimer();
                    break;
                case "set":
                    if (args.length != 3) {
                        showSetHelp();
                    } else {
                        switch (args[1]) {
                            case "enabled":
                                switch (args[2].toLowerCase()) {
                                    case "true":
                                        BLCTimersMod.getSettings().enabled = true;
                                        Hyperium.CONFIG.save();
                                        break;
                                    case "false":
                                        BLCTimersMod.getSettings().enabled = false;
                                        Hyperium.CONFIG.save();
                                        break;
                                    default:
                                        showSetHelp();
                                }
                                break;
                            case "xpos":
                                try {
                                    BLCTimersMod.getSettings().xpos = parseInt(args[2]);
                                    Hyperium.CONFIG.save();
                                } catch (NumberFormatException e) {
                                    showSetHelp();
                                }
                                break;
                            case "ypos":
                                try {
                                    BLCTimersMod.getSettings().ypos = parseInt(args[2]);
                                    Hyperium.CONFIG.save();
                                } catch (NumberFormatException e) {
                                    showSetHelp();
                                }
                                break;
                            default:
                                showSetHelp();
                        }
                    }
                    break;
                case "reset":
                    BLCTimersMod.getSettings().resetConfig();
                    break;
                default:
                    showHelp();


            }
        }
    }

    public void showSetHelp() {
        IChatComponent message = new ChatComponentText("-----------------------------------------------------").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.GOLD));
        message.appendText("\n");
        message.appendSibling(new ChatComponentText("xpos").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.AQUA)));
        message.appendSibling(new ChatComponentText(" - ").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.GRAY)));
        message.appendSibling(new ChatComponentText("Integer").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.YELLOW)));
        message.appendText("\n");
        message.appendSibling(new ChatComponentText("ypos").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.AQUA)));
        message.appendSibling(new ChatComponentText(" - ").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.GRAY)));
        message.appendSibling(new ChatComponentText("Integer").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.YELLOW)));
        message.appendText("\n");
        message.appendSibling(new ChatComponentText("enabled").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.AQUA)));
        message.appendSibling(new ChatComponentText(" - ").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.GRAY)));
        message.appendSibling(new ChatComponentText("Boolean").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.YELLOW)));
        message.appendSibling(new ChatComponentText("-----------------------------------------------------").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.GOLD)));
        Minecraft.getMinecraft().thePlayer.addChatMessage(message);
    }

    public void showHelp() {
        IChatComponent message = new ChatComponentText("-----------------------------------------------------").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.GOLD));
        message.appendText("\n");
        message.appendSibling(new ChatComponentText("/timers demo").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.AQUA)));
        message.appendSibling(new ChatComponentText(" - ").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.GRAY)));
        message.appendSibling(new ChatComponentText("Show a demo timer for a preview of current config").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.YELLOW)));
        message.appendText("\n");
        message.appendSibling(new ChatComponentText("/timers set <name> <value>").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.AQUA)));
        message.appendSibling(new ChatComponentText(" - ").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.GRAY)));
        message.appendSibling(new ChatComponentText("Set the config parameter with a certain name to a certain value").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.YELLOW)));
        message.appendText("\n");
        message.appendSibling(new ChatComponentText("/timers reset").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.AQUA)));
        message.appendSibling(new ChatComponentText(" - ").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.GRAY)));
        message.appendSibling(new ChatComponentText("Reset config").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.YELLOW)));
        message.appendSibling(new ChatComponentText("-----------------------------------------------------").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.GOLD)));
        Minecraft.getMinecraft().thePlayer.addChatMessage(message);
    }
}
