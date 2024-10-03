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
import java.util.HashMap;
import java.util.Map;

public class Download {
    
    public static float elements = 0;
    public static int progress = 0;
    
    static void updateProgress(int increment) {
        progress += increment;
        frame.updateProgressBar((int)(progress*100/elements));
    }
    
    static Map<Path, String> getFileLinks(Path indexFile) throws IOException {
        progress = 0; updateProgress(0);
        Index index = (Index) JSON.toObject(indexFile, Index.class);
        HashMap files = new HashMap<Path, String>();
        for (File file : index.getFiles()) {
            files.put(file.getPath(), file.getDownloads().getFirst());
        }
        elements = files.size();
        return files;
    }
    
    public static void files(Map<Path, String> files, Path root) throws IOException, URISyntaxException {
        if (!Files.exists(root)) Files.createDirectories(root);
        for (Path path : files.keySet()) {
            System.out.println("Downloading "+path.toString());
            Download.file(files.get(path), root.resolve(path));
            updateProgress(1);
        }
    }

    private static void file(String url, Path target) throws IOException, URISyntaxException {
        URI downloadUrl = new URI(url);
        Files.createDirectories(target.getParent());
        try (InputStream in = downloadUrl.toURL().openStream(); FileOutputStream out = new FileOutputStream(target.toFile())) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1)
                out.write(buffer, 0, bytesRead);
        }
    }
}
