package com.ssserebrov;

import org.jcodec.api.awt.AWTSequenceEncoder;
import org.jcodec.common.io.FileChannelWrapper;
import org.jcodec.common.io.NIOUtils;
import org.jcodec.common.io.SeekableByteChannel;
import org.jcodec.common.model.Rational;
import org.jcodec.common.tools.MainUtils;
import org.jcodec.common.tools.MainUtils.Cmd;
import org.jcodec.common.tools.MainUtils.Flag;

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

                out = NIOUtils.writableFileChannel(dirHourly + File.separator + dirDaily.getName() + "_" + dirHourly.getName() + ".mp4");
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
                }
            }
        }
    }
}
