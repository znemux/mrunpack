package io.github.znemux.mrunpack;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ZipUtil {
    
    public static void unzip (Path source, Path target) throws IOException {
        try (ZipInputStream zis = new ZipInputStream(Files.newInputStream(source))) {
            unzip(zis, target);
        }
    }
    
    private static void unzip(ZipInputStream zis, Path target) throws IOException {
        ZipEntry entry;
        while ((entry = zis.getNextEntry()) != null) {
            Path newPath = target.resolve(entry.getName());
            if (!entry.isDirectory()) {
                Files.createDirectories(newPath.getParent());
                try (OutputStream fos = Files.newOutputStream(newPath)) {
                    zis.transferTo(fos);
                }
            }
            zis.closeEntry();
        }
    }
    
    /*public static void zipAll(Path source, Path target) throws IOException {
        try (ZipOutputStream zos = new ZipOutputStream(Files.newOutputStream(target))) {
            if (Files.isDirectory(source)) zipDirectory(zos, source);
            else zipFile(zos, source);
        }
    }
    
    private static void zipDirectory(ZipOutputStream zos, Path source) throws IOException {
        Files.walk(source)
            .filter(path -> !Files.isDirectory(path))
            .forEach(path -> {
                try {
                    Path relativePath = source.relativize(path);
                    zipFile(zos, relativePath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
    }
    
    private static void zipFile(ZipOutputStream zos, Path file) throws IOException {
        ZipEntry zipEntry = new ZipEntry(file.toString());
        zos.putNextEntry(zipEntry);
        Files.copy(file, zos);
        zos.closeEntry();
    }*/
    
}