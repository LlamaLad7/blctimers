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


import cc.hyperium.event.EventBus;
import cc.hyperium.event.InvokeEvent;
import cc.hyperium.event.client.TickEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CountdownTimer {
    private static CountdownTimer INSTANCE = new CountdownTimer();
    public static boolean isRunning = false;
    public static int tick = 0;
    public static boolean needsResetting = false;
    public static List<String> needRemoving = new ArrayList<>();
    public static Map<String, MCTimer> timers = new ConcurrentHashMap<>();

    public CountdownTimer() {
    }

    public static CountdownTimer getInstance() {
        return INSTANCE;
    }

    @InvokeEvent
    public void tickEvent(TickEvent event) {
        tick += 1;
        if (tick == 20) {
            tick = 0;
            for (Map.Entry<String, MCTimer> entry : timers.entrySet()) {
                MCTimer timer = entry.getValue();
                timer.time--;
                if (timer.time == 0) {
                    if (timer.repeated) {
                        timer.time = timer.startTime;
                    } else {
                        needRemoving.add(entry.getKey());
                    }
                }
            }
        }
    }

    public static void addTimer(String id, MCTimer timer) {
        timers.put(id, timer);
    }

    public static void demoTimer() {
        addTimer(Integer.toString(timers.size()), new MCTimer(true, "Demo Timer", 10, 10, "diamond"));
        start();
    }

    public static void setTimer(String id, int time) {
        if (timers.containsKey(id)) {
            timers.get(id).time = time;
        }
    }

    public static void start() {
        if (!isRunning) {
            isRunning = true;
            EventBus.INSTANCE.register(CountdownTimer.getInstance());
        }
    }

    public static void stop() {
        isRunning = false;
        EventBus.INSTANCE.unregister(CountdownTimer.getInstance());
    }

    public static void reset() {
        needsResetting = true;
        tick = 0;
        stop();
    }
}
