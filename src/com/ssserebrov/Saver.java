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
import java.util.Date;

public class Saver{

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
}
