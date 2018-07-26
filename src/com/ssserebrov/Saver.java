package com.ssserebrov;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.net.URLConnection;
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

        Authenticator.setDefault(new Authenticator() {

            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("admin", "22312231".toCharArray());
            }
        });
        URL urlInput = new URL(this.url);



        String loginPassword = "admin" + ":" + "22222222";
       // String encoded = new sun.misc.BASE64Encoder().encode (loginPassword.getBytes());
//        String encoded2 = new sun.misc.BASE64Encoder().encode (loginPassword.getBytes());
//
//
//
//
//
//
//
//
//        byte[] bytes = new byte[57];
//        String enc1 = new sun.misc.BASE64Encoder().encode(bytes);
        String encoded = new String(java.util.Base64.getEncoder().encode(loginPassword.getBytes()));


//
//        URLConnection conn = urlInput.openConnection();
//        conn.setRequestProperty ("Authorization", "Basic " + encoded);






        BufferedImage urlImage = ImageIO.read(urlInput);

        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdfH = new SimpleDateFormat("yyyyMMdd" + File.separator + "HH");
        SimpleDateFormat sdfT = new SimpleDateFormat("HH_mm_ss");
        String day = sdfH.format(c.getTime());
        String fileName = sdfT.format(c.getTime());
       // String hour = sdf.format(c.getTime());

        File dirHourly = new File(this.destinationFolder + File.separator + day);
        if (!dirHourly.exists())
            dirHourly.mkdirs();

        //saveCurrentTime
       // createFolder
        File outputFile = new File(dirHourly + File.separator + fileName + ".jpg");
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
