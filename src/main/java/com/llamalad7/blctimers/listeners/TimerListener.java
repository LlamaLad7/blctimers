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

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.llamalad7.blctimers.utils.CountdownTimer;
import com.llamalad7.blctimers.utils.MCTimer;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S3FPacketCustomPayload;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

public class TimerListener {


        private static TimerListener INSTANCE = new TimerListener();

        private TimerListener() {}

        public static TimerListener getInstance() {
            return INSTANCE;
        }

        @SubscribeEvent
        public void onWorldLoad(WorldEvent.Load event) {
            CountdownTimer.reset();
        }

        @SubscribeEvent
        public void onNetwork2(FMLNetworkEvent event) {
            if (event instanceof FMLNetworkEvent.ClientConnectedToServerEvent) {
                event.manager.channel().pipeline().addAfter("encoder", "test_packet_handler", new SimpleChannelInboundHandler<Packet>() {
                    @Override
                    protected void channelRead0(ChannelHandlerContext ctx, Packet msg) throws Exception {
                        if (msg instanceof S3FPacketCustomPayload) {
                            S3FPacketCustomPayload payload = (S3FPacketCustomPayload) msg;
                            if (payload.getChannelName().equals("badlion:timers")) {
                                String[] data = new String(payload.getBufferData().array()).split("\\|");
                                JsonObject thing = new JsonParser().parse(data[1]).getAsJsonObject();
                                switch (data[0]) {
                                    case "ADD_TIMER":
                                        System.out.println("add");
                                        MCTimer timer = new MCTimer(
                                                thing.get("repeating").getAsBoolean(),
                                                thing.get("name").getAsString(),
                                                thing.get("currentTime").getAsInt()/20,
                                                thing.get("time").getAsInt()/20,
                                                thing.get("item").getAsJsonObject().get("type").getAsString());
                                        System.out.println(thing.get("item").getAsJsonObject().get("type").getAsString());
                                        CountdownTimer.addTimer(thing.get("id").getAsString(), timer);
                                        CountdownTimer.start();
                                        break;
                                    case "SYNC_TIMERS":
                                        System.out.println("sync");
                                        CountdownTimer.setTimer(
                                                thing.get("id").getAsString(),
                                                thing.get("time").getAsInt()/20);
                                        break;
                                }
                            }
                        }
                        ctx.fireChannelRead(msg);
                    }
                });
            }
        }


    }

