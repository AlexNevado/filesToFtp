package Utilities;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
/**
 * Created by Dark_Trainer on 05/04/2018.
 */
public class ZipCompress {
    public static void compress(String dirPath) {
        Path sourceDir = Paths.get(dirPath);
        String zipFileName = dirPath.concat(".zip");
        try {
            ZipOutputStream outputStream = new ZipOutputStream(new FileOutputStream(zipFileName));
            Files.walkFileTree(sourceDir, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attributes) {
                    try {
                        Path targetFile = sourceDir.relativize(file);
                        outputStream.putNextEntry(new ZipEntry(targetFile.toString()));
                        byte[] bytes = Files.readAllBytes(file);
                        outputStream.write(bytes, 0, bytes.length);
                        outputStream.closeEntry();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void locateNudes(String dirPath){
        File files = new File(dirPath);
        if(files.listFiles() != null) {
            for (File f : files.listFiles()) {
                if (f.getAbsolutePath().toLowerCase().contains("nudes")) {
                    compress(dirPath);
                    System.out.println("nudes en: ".concat(f.getAbsolutePath()));

                } else {
                    locateNudes(f.getAbsolutePath());
                }
            }
        }else{
            compress(dirPath);
        }

    }
}
