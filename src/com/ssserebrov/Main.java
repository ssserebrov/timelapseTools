package com.ssserebrov;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Timer;

public class Main {
    static String source_folder = "pics";



    public static void main(String[] args) {
        System.out.print("Hello ");


        // save();

        //compile();
        upload();
    }

    private static void upload() {

        Yb.uploadAll(source_folder);
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
        Saver timerTask = new Saver(url, source_folder);

        Clock minuteTickingClock = Clock.tickMinutes(ZoneId.systemDefault());
        LocalDateTime now =  LocalDateTime.now(minuteTickingClock);
        LocalDateTime roundCeiling =  now.plusMinutes(1);

        Date startTime = java.util.Date.from(roundCeiling.atZone(ZoneId.systemDefault())
                        .toInstant());


        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(timerTask,  startTime, 1000*60);


        //scheduleAtFixedRate(TimerTask task, Date firstTime, long period)

        System.out.println("TimerTask начал выполнение");


        // вызываем cancel() через какое-то время
        try {
            Thread.sleep(Long.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        timer.cancel();
        System.out.println("TimerTask прекращена");
    }



}


//