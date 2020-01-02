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
package com.llamalad7.blctimers.utils;


import java.util.*;

public class CountdownTimer extends TimerTask {
    private static CountdownTimer INSTANCE = new CountdownTimer();
    public static boolean isRunning = false;
    public static boolean isResetting = false;
    private static final Timer timer = new Timer();
    public static List<String> needRemoving = new ArrayList<>();
    public static Map<String, MCTimer> timers = new HashMap<>();

    public CountdownTimer() {
    }

    public static CountdownTimer getInstance() {
        return INSTANCE;
    }

    @Override
    public void run() {
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
            timer.schedule(INSTANCE, 1000, 1000);
        }
    }

    public static void stop() {
        isRunning = false;
        INSTANCE.cancel();
        INSTANCE = new CountdownTimer();
    }

    public static void reset() {
        isResetting = true;
        stop();
        timers = new HashMap<String, MCTimer>();
        isResetting = false;
    }
}
