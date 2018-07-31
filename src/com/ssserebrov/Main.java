package com.ssserebrov;

import java.io.IOException;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class Main {
    private static String source_folder = "pics";
    private static String url = "http://192.168.1.136/tmpfs/auto.jpg";

    public static void main(String[] args) {
        System.out.print("Start ");

        for (String arg : args) {
            switch (arg) {
                case "save":
                    save();
                    saveDaily();
                    break;
                case "compile":
                    compile();
                    break;
                default:
                    break;
            }
        }

        // better rework this
        try {
            Thread.sleep(Long.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void upload() {

    }

    private static void compile() {
        int seconds = 60 * 60 * 2;

        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    VideoCompliler.complileAllInFolder(source_folder);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        },0, 1000 * seconds);
    }

    private static void save() {
        int seconds = 15;
        Date startTime = getNextMinuteDateTime();

        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    Saver.saveImage(url, source_folder);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, startTime, 1000 * seconds);
    }

    private static void saveDaily() {
        int seconds = 60;
        Date startTime = getNextMinutePlus7secondsDateTime();

        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    Saver.saveImage(url, source_folder, "24");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, startTime, 1000 * seconds);
    }

    private static Date getNextMinuteDateTime() {
        Clock minuteTickingClock = Clock.tickMinutes(ZoneId.systemDefault());
        LocalDateTime now = LocalDateTime.now(minuteTickingClock);
        LocalDateTime roundCeiling = now.plusMinutes(1);

        return java.util.Date.from(roundCeiling.atZone(ZoneId.systemDefault())
                .toInstant());
    }

    private static Date getNextMinutePlus7secondsDateTime() {
        Clock minuteTickingClock = Clock.tickMinutes(ZoneId.systemDefault());
        LocalDateTime now = LocalDateTime.now(minuteTickingClock);
        LocalDateTime roundCeiling = now.plusMinutes(1).plusSeconds(7);

        return java.util.Date.from(roundCeiling.atZone(ZoneId.systemDefault())
                .toInstant());
    }
}