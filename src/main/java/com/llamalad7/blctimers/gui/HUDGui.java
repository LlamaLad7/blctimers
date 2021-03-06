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

import com.llamalad7.blctimers.TimerSettings;
import com.llamalad7.blctimers.utils.CountdownTimer;
import com.llamalad7.blctimers.utils.MCTimer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class HUDGui extends Gui {

    private final int TEXT_COLOR = 0xFFFFFF;

    private final int LINE_HEIGHT = 10;

    private final int ICON_SIZE = 16;

    private final Minecraft mc;

    private TimerSettings settings;


    private int currentHeight;

    public HUDGui(TimerSettings config) {
        mc = Minecraft.getMinecraft();
        currentHeight = 2;
        settings = config;
    }


    @SubscribeEvent
    public void render(RenderGameOverlayEvent.Post event) {
        if (needToRender() && event.type == RenderGameOverlayEvent.ElementType.HOTBAR) {
            renderTimers();
            currentHeight = settings.ypos;
        }
    }

    private boolean needToRender() {

        return !(mc.currentScreen instanceof GuiChat) && !mc.gameSettings.showDebugInfo && settings.enabled;
    }

    private void renderTimers() {
        if (CountdownTimer.needsResetting) {
            CountdownTimer.timers = new ConcurrentHashMap<>();
            CountdownTimer.needsResetting = false;
        }
        if (CountdownTimer.needRemoving.size() != 0) {
            for (String id : CountdownTimer.needRemoving) {
                CountdownTimer.timers.remove(id);
            }
            CountdownTimer.needRemoving = new ArrayList<>();
        }
        GlStateManager.pushMatrix();
        float scale = ((float) settings.scale) / 100;
        GlStateManager.scale(scale, scale, 0);
        for (MCTimer timer : CountdownTimer.timers.values()) {
            drawTimer(timer.item, timer.getFormattedTime());
        }
        GlStateManager.popMatrix();

    }

    private void drawTimer(ItemStack itemStack, String text) {
        float scale = ((float) settings.scale) / 100;
        if (settings.showIcon) {
            RenderHelper.enableGUIStandardItemLighting();
            mc.getRenderItem().renderItemAndEffectIntoGUI(itemStack,
                    (int) (settings.xpos / scale + 1),
                    (int) (currentHeight / scale));
            RenderHelper.disableStandardItemLighting();
        }
        drawString(mc.fontRendererObj, " " + text, (int) (ICON_SIZE + settings.xpos / scale),
                (int) (currentHeight / scale + (ICON_SIZE - LINE_HEIGHT) / 2 + 1),
                TEXT_COLOR);
        currentHeight += ICON_SIZE * scale + 1;
    }
}
