package com.ssserebrov;

import java.io.IOException;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

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

        String url = "http://192.168.1.136/tmpfs/auto.jpg";
        int seconds = 15;

        Date startTime = getNextMinuteDateTime();

        Saver saver = new Saver(url, source_folder);


        Timer timer = new Timer(true);

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    saver.saveImage();
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, startTime, 1000*seconds);


        try {
            Thread.sleep(Long.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

















    }

    private static Date getNextMinuteDateTime() {
        Clock minuteTickingClock = Clock.tickMinutes(ZoneId.systemDefault());
        LocalDateTime now =  LocalDateTime.now(minuteTickingClock);
        LocalDateTime roundCeiling =  now.plusMinutes(1);

        return java.util.Date.from(roundCeiling.atZone(ZoneId.systemDefault())
                .toInstant());
    }

    private static Date getNextMinutePlus7secondsDateTime() {
        Clock minuteTickingClock = Clock.tickMinutes(ZoneId.systemDefault());
        LocalDateTime now =  LocalDateTime.now(minuteTickingClock);
        LocalDateTime roundCeiling =  now.plusMinutes(1).plusSeconds(7);

        return java.util.Date.from(roundCeiling.atZone(ZoneId.systemDefault())
                .toInstant());
    }
}