package com.ssserebrov;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;

class Saver {
    static void saveImage(String url, String destinationFolder) throws IOException {
        saveImage(url, destinationFolder, "");
    }


    static void saveImage(String url, String destinationFolder, String hourStringDir) throws IOException {
        Authenticator.setDefault(new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("admin", "22312231".toCharArray());
            }
        });
        URL urlInput = new URL(url);

        BufferedImage urlImage = ImageIO.read(urlInput);

        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdfD = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat sdfH = new SimpleDateFormat("HH");
        SimpleDateFormat sdfT = new SimpleDateFormat("HH_mm_ss");
        String dateString = sdfD.format(c.getTime());
        String hourString = hourStringDir.isEmpty()
                ? sdfH.format(c.getTime())
                : hourStringDir;
        String timeString = sdfT.format(c.getTime());

        File hourDir = new File(destinationFolder + File.separator + dateString + File.separator + hourString);
        if (!hourDir.exists())
            hourDir.mkdirs();

        File outputFile = new File(hourDir + File.separator + timeString + ".jpg");
        ImageIO.write(urlImage, "jpg", outputFile);
    }
}