package com.ssserebrov;

import org.jcodec.api.awt.AWTSequenceEncoder;
import org.jcodec.common.io.NIOUtils;
import org.jcodec.common.io.SeekableByteChannel;
import org.jcodec.common.model.Rational;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class VideoCompliler {

    static void complileAllInFolder(String pathFolder) throws IOException {

        SeekableByteChannel out = null;

        File[] dirsDaily = new File(pathFolder).listFiles(File::isDirectory);
        for (File dirDaily : dirsDaily) {
            File[] dirsHourly = dirDaily.listFiles(File::isDirectory);
            for (File dirHourly : dirsHourly) {
                File[] files = dirHourly.listFiles();
                if (files.length < 10)
                    continue;

                out = NIOUtils.writableFileChannel(dirDaily.getPath() + File.separator + dirHourly.getName() + ".mp4");
                AWTSequenceEncoder encoder = new AWTSequenceEncoder(out, Rational.R(25, 1));
                try {
                    for (File file : files) {
                        BufferedImage image = ImageIO.read(file);
                        encoder.encodeImage(image);
                    }
                    encoder.finish();
                } finally {
                    NIOUtils.closeQuietly(out);
                    for (File file : files) {
                        file.delete();
                    }
                    dirHourly.delete();
                }
            }
        }
    }

    public static void uploadAll(String source_folder) {
        File[] dirsDaily = new File(source_folder).listFiles(File::isDirectory);
        for (File dirDaily : dirsDaily) {
            File[] dirsHourly = dirDaily.listFiles(File::isDirectory);
            for (File dirHourly : dirsHourly) {
                File[] videoFiles = dirHourly.listFiles((d, name) -> name.endsWith(".mp4"));
                for (File videoFile : videoFiles) {

                }
            }
        }
    }
}
