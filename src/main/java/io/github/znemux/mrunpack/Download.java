package io.github.znemux.mrunpack;

import static io.github.znemux.mrunpack.Main.frame;
import io.github.znemux.mrunpack.index.File;
import io.github.znemux.mrunpack.index.Index;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Download {
    
    public static long elements = 0;
    public static int progress = 0;
    static int percent = 0;
    
    static void updateProgress(int increment) {
        progress += increment;
        int curpercent = (int)(progress*100l/(double)elements);
        if (curpercent != percent) {
            percent = curpercent;
            frame.updateProgressBar(percent);
        }
    }
    
    static List<File> getFileLinks(Path indexFile) throws IOException {
        Index index = (Index) JSON.toObject(indexFile, Index.class);
        return index.getFiles();
    }
    
    public static void files(List<File> files, Path root) throws IOException, URISyntaxException {
        if (!Files.exists(root)) Files.createDirectories(root);
        elements = files.stream().mapToLong(File::getFileSize).sum();
        for (File file : files) {
            System.out.println("Downloading "+file.toString()+" ("+percent+"%)");
            Download.file(file, root);
        }
    }

    private static void file(File file, Path root) throws IOException, URISyntaxException {
        URI downloadUrl = new URI(file.getDownloads().getFirst());
        Path fullPath = root.resolve(file.getPath());
        Files.createDirectories(fullPath.getParent());
        try (InputStream in = downloadUrl.toURL().openStream(); FileOutputStream out = new FileOutputStream(fullPath.toFile())) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
                updateProgress(4096);
            }
        }
    }
}
