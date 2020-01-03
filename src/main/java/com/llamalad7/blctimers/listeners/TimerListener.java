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
package com.llamalad7.blctimers.listeners;

import cc.hyperium.event.InvokeEvent;
import cc.hyperium.event.network.PacketReceivedEvent;
import cc.hyperium.event.world.WorldLoadEvent;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.llamalad7.blctimers.utils.CountdownTimer;
import com.llamalad7.blctimers.utils.MCTimer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.Unpooled;
import io.netty.buffer.UnpooledHeapByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraft.network.play.server.S3FPacketCustomPayload;

public class TimerListener {


    private static TimerListener INSTANCE = new TimerListener();

    private TimerListener() {
    }

    public static TimerListener getInstance() {
        return INSTANCE;
    }

    @InvokeEvent
    public void onWorldLoad(WorldLoadEvent event) {
        CountdownTimer.reset();
    }

    @InvokeEvent
    public void onNetwork(PacketReceivedEvent event) {
        Packet packet = (Packet) event.getPacket();
        if (packet instanceof S3FPacketCustomPayload) {
            S3FPacketCustomPayload payload = (S3FPacketCustomPayload) packet;
            if (payload.getChannelName().equals("badlion:timers")) {
                String[] data = new String(payload.getBufferData().array()).split("\\|");
                JsonObject parsedPacket = new JsonParser().parse(data[1]).getAsJsonObject();
                switch (data[0]) {
                    case "ADD_TIMER":
                        MCTimer timer = new MCTimer(
                                parsedPacket.get("repeating").getAsBoolean(),
                                parsedPacket.get("name").getAsString(),
                                parsedPacket.get("currentTime").getAsInt() / 20,
                                parsedPacket.get("time").getAsInt() / 20,
                                parsedPacket.get("item").getAsJsonObject().get("type").getAsString());
                        CountdownTimer.addTimer(parsedPacket.get("id").getAsString(), timer);
                        CountdownTimer.start();
                        break;
                    case "SYNC_TIMERS":
                        CountdownTimer.setTimer(
                                parsedPacket.get("id").getAsString(),
                                parsedPacket.get("time").getAsInt() / 20);
                        break;
                }
            }
        }
    }


}

