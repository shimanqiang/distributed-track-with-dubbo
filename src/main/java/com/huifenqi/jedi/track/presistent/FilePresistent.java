package com.huifenqi.jedi.track.presistent;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Created by t3tiger on 2017/9/13.
 */
public class FilePresistent implements TrackPresistent {
    private String dirPath;

    public FilePresistent() {
        dirPath = System.getProperty("jedi.track.presistent.path", "/data/track/");
        File dir = new File(dirPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    @Override
    public void presistent(String content) {
        RandomAccessFile raf = null;
        FileChannel channel = null;
        try {
            File file = new File(dirPath, LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) + ".txt");
            raf = new RandomAccessFile(file, "rw");
            raf.seek(raf.length());
            channel = raf.getChannel();
            ByteBuffer buffer = ByteBuffer.allocate(512);
            buffer.put(content.getBytes(Charset.defaultCharset()));
            buffer.put("\r\n".getBytes());
            buffer.flip();
            channel.write(buffer);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e1) {
            e1.printStackTrace();
        } finally {
            if (channel != null) {
                try {
                    channel.close();
                } catch (IOException e) {
                    channel = null;
                }
            }
            if (raf != null) {
                try {
                    raf.close();
                } catch (IOException e) {
                    raf = null;
                }
            }
        }


    }
}
