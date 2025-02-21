package io.github.znemux.mrunpack;

import com.google.gson.Gson;
import static io.github.znemux.mrunpack.gui.Frame.frame;
import io.github.znemux.util.Download;
import io.github.znemux.mrunpack.index.File;
import io.github.znemux.mrunpack.index.Index;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import net.lingala.zip4j.ZipFile;

public class Mrpack {
    private final Path mrpath;
    private Path root;
    private List<File> files;
    private final Gson gson;

    public Mrpack(Path mrpath) {
        this.mrpath = mrpath;
        gson = new Gson();
    }
    
    public Mrpack extract() throws IOException {
        root = Path.of(mrpath.toString().replaceAll(".mrpack$", ""));
        new ZipFile(mrpath.toString()).extractAll(root.toString());
        return this;
    }
    
    public void download() throws IOException, URISyntaxException {
        setFilesFromIndex();
        Download dl = new Download(files.stream().mapToLong(File::getFileSize).sum(), frame.progressBar);
        for (File file : files) {
            Path path = root.resolve("overrides").resolve(file.toPath());
            Files.createDirectories(path.getParent());
            dl.download(file.getDownloads().getFirst(), path.toFile());
        }
    }
    
    private void setFilesFromIndex() throws IOException {
        files = gson.fromJson(Files.readString(root.resolve("modrinth.index.json")), Index.class).getFiles();
    }
}