package io.github.znemux.mrunpack.index;

import java.nio.file.Path;
import java.util.List;

public class File {
    private String path;
    private Hashes hashes;
    private Env env;
    private List<String> downloads;
    private long fileSize;

    public Path getPath() {
        return Path.of(path);
    }

    public Hashes getHashes() {
        return hashes;
    }

    public Env getEnv() {
        return env;
    }

    public List<String> getDownloads() {
        return downloads;
    }

    public long getFileSize() {
        return fileSize;
    }
}
