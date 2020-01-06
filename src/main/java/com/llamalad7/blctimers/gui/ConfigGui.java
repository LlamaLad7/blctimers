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
package com.llamalad7.blctimers.gui;

import com.llamalad7.blctimers.BLCTimersMod;
import com.llamalad7.blctimers.TimerSettings;
import com.llamalad7.blctimers.utils.CountdownTimer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fml.client.config.GuiSlider;

import java.io.IOException;

import static net.minecraft.init.Items.diamond;

public class ConfigGui extends GuiScreen {
    private TimerSettings settings;
    private boolean dragging;
    private GuiSlider scaleSlider;
    private GuiButton enabledButton;

    public ConfigGui(BLCTimersMod mod) {
        dragging = false;
        this.settings = mod.getSettings();
    }

    @Override
    public void initGui() {
        //label = new GuiLabel(mc.fontRendererObj, 0, width / 2 - 50, height / 2 - 75, 100, 20, 0xFFFFFF);
        buttonList.add(enabledButton = new GuiButton(0, width / 2 - 50, height / 2 - 50, 100, 20, "Timers: " + getColoredBool(settings.enabled)));
        buttonList.add(scaleSlider = new GuiSlider(1, width / 2 - 50, height / 2 - 25, 100, 20, "Scale: ", "%", 10, 200, settings.scale, false, true));
        buttonList.add(new GuiButton(2, width / 2 - 50, height / 2, 100, 20, "Reset Config"));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();

        super.drawScreen(mouseX, mouseY, partialTicks);
        drawCenteredString(mc.fontRendererObj, EnumChatFormatting.GREEN + EnumChatFormatting.BOLD.toString() + "Timers Mod " + EnumChatFormatting.RESET + "by " + EnumChatFormatting.AQUA + EnumChatFormatting.BOLD.toString() + "LlamaLad7", width / 2, height / 2 - 75, 0xFFFFFF);
        if (dragging) {
            settings.xpos = mouseX;
            settings.ypos = mouseY;
        }
        settings.scale = scaleSlider.getValueInt();
        if (settings.enabled && CountdownTimer.timers.size() == 0)
            drawTimer(new ItemStack(diamond), "Demo Timer: 0:10");
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if (mouseButton == 0) {
            for (GuiButton b : buttonList) {
                if (b.isMouseOver()) return;
            }
            dragging = true;

        }

    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        super.mouseReleased(mouseX, mouseY, mouseButton);
        dragging = false;
    }

    private void drawTimer(ItemStack itemStack, String text) {
        GlStateManager.pushMatrix();
        float scale = ((float) settings.scale) / 100;
        GlStateManager.scale(scale, scale, 0);
        RenderHelper.enableGUIStandardItemLighting();
        mc.getRenderItem().renderItemAndEffectIntoGUI(itemStack,
                (int) (settings.xpos / scale + 1),
                (int) (settings.ypos / scale));
        RenderHelper.disableStandardItemLighting();
        drawString(mc.fontRendererObj, " " + text, (int) (16 + settings.xpos / scale),
                (int) (settings.ypos / scale + (16 - 10) / 2 + 1),
                0xFFFFFF);
        GlStateManager.popMatrix();
    }

    @Override
    public void onGuiClosed() {
        settings.saveConfig();
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        switch (button.id) {
            case 0:
                settings.enabled = !settings.enabled;
                button.displayString = "Timers: " + getColoredBool(settings.enabled);
                break;
            case 2:
                settings.resetConfig();
                scaleSlider.setValue(settings.scale);
                scaleSlider.updateSlider();
                enabledButton.displayString = "Timers: " + getColoredBool(settings.enabled);
                break;
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    private String getColoredBool(boolean bool) {
        if (bool) {
            return EnumChatFormatting.GREEN + "Enabled";
        }

        return EnumChatFormatting.RED + "Disabled";
    }
}
