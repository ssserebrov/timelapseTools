package com.ssserebrov;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimerTask;

public class Saver extends TimerTask {

    private final String url;
    private final String destinationFolder;

    public Saver(String url, String destinationFolder) {

        this.url = url;
        this.destinationFolder = destinationFolder;
    }

    public void saveImage() throws IOException, InterruptedException {
        URL urlInput = new URL(this.url);
        BufferedImage urlImage = ImageIO.read(urlInput);

        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdfH = new SimpleDateFormat("yyyyMMdd" + File.separator + "HH");
        SimpleDateFormat sdfT = new SimpleDateFormat("HH_mm_ss");
        String day = sdfH.format(c.getTime());
        String fileName = sdfT.format(c.getTime());
       // String hour = sdf.format(c.getTime());

        File file = new File(day);
        if (!file.exists())
            file.mkdirs();

        //saveCurrentTime
       // createFolder
        File outputFile = new File(this.destinationFolder + File.separator + day + File.separator + fileName + ".jpg");
        ImageIO.write(urlImage, "jpg", outputFile);
    }

    @Override
    public void run() {
        System.out.println("TimerTask начал свое выполнение в:" + new Date());
        try {
            saveImage();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("TimerTask закончил свое выполнение в:" + new Date());
    }
}
