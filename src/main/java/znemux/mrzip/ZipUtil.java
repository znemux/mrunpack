package znemux.mrzip;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipUtil {

    // Defaults to include the source folder
    static void zip(String archive, String source) {
        zip(archive, source, true);
    }

    // Has the option to only get the contents if the source is a directory
    static void zip(String archive, String source, boolean include) {
        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(archive))) {
            if (include) {
                zipHelper(new File(source), source, zout);
            } else {
                zipHelper(new File(source), "", zout);
            }
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    static void unzip(String archive, String destination) {
        File destDir = new File(destination);
        try (ZipInputStream zin = new ZipInputStream(new FileInputStream(archive))) {
            ZipEntry zipEntry = zin.getNextEntry();
            byte[] buffer = new byte[1024];
            while (zipEntry != null) {
                File newFile = newFile(destDir, zipEntry);
                if (zipEntry.isDirectory()) {
                    if (!newFile.isDirectory() && !newFile.mkdirs()) {
                        throw new IOException("Failed to create directory " + newFile);
                    }
                } else {
                    // fix for Windows-created archives
                    File parent = newFile.getParentFile();
                    if (!parent.isDirectory() && !parent.mkdirs()) {
                        throw new IOException("Failed to create directory " + parent);
                    }
                    try (FileOutputStream fout = new FileOutputStream(newFile)) {
                        int len;
                        while ((len = zin.read(buffer)) > 0) {
                            fout.write(buffer, 0, len);
                        }
                    }
                }
                zipEntry = zin.getNextEntry();
            }
            zin.closeEntry();
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    // Recursively zips each file
    private static void zipHelper(File source, String file, ZipOutputStream zout) throws IOException {
        if (source.isDirectory()) {
            if (file.endsWith("/")) {
                zout.putNextEntry(new ZipEntry(file));
                zout.closeEntry();
            } else {
                zout.putNextEntry(new ZipEntry(file + "/"));
                zout.closeEntry();
            }
            File[] children = source.listFiles();
            for (File childFile : children) {
                zipHelper(childFile, file + "/" + childFile.getName(), zout);
            }
            return;
        }
        try (FileInputStream fin = new FileInputStream(source)) {
            ZipEntry zipEntry = new ZipEntry(file);
            zout.putNextEntry(zipEntry);
            byte[] bytes = new byte[1024];
            int length;
            while ((length = fin.read(bytes)) >= 0) {
                zout.write(bytes, 0, length);
            }
        }
    }
    
    private static File newFile(File destination, ZipEntry entry) throws IOException {
        File destFile = new File(destination, entry.getName());
        String destDirPath = destination.getCanonicalPath();
        String destFilePath = destFile.getCanonicalPath();
        if (!destFilePath.startsWith(destDirPath + File.separator)) {
            throw new IOException("Entry is outside of the target dir: " + entry.getName());
        }
        return destFile;
    }
}
