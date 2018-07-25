package com.ssserebrov;

import java.io.IOException;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Timer;

public class Main {
    static String source_folder = "pics";

    public static void main(String[] args) {
        System.out.print("Start ");

        for (String arg: args) {
            switch (arg) {
                case "save":  save();
                    break;
                case "compile":  compile();
                    break;
                default:
                    break;
            }
        }

//        save();
//        compile();
        //upload();
    }

    private static void upload() {

    }

    private static void compile() {
        try {
            VideoCompliler.complileAllInFolder(source_folder);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void save() {
        String url = "http://content.cam72.su/thumbnails/Gorsad-small.jpg";
        int seconds = 15;

        Date startTime = getNextMinuteDateTime();

        Saver timerTask = new Saver(url, source_folder);
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(timerTask,  startTime, 1000*seconds);
        System.out.println("Start saving");
        try {
            Thread.sleep(Long.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        timer.cancel();
        System.out.println("Finish saving");
    }

    private static Date getNextMinuteDateTime() {
        Clock minuteTickingClock = Clock.tickMinutes(ZoneId.systemDefault());
        LocalDateTime now =  LocalDateTime.now(minuteTickingClock);
        LocalDateTime roundCeiling =  now.plusMinutes(1);

        return java.util.Date.from(roundCeiling.atZone(ZoneId.systemDefault())
                .toInstant());
    }
}