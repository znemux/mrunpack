package io.github.znemux.mrunpack.index;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import java.util.Map;

public class Index {
    @SerializedName("formatVersion")
    private int formatVersion;
    private String game;
    @SerializedName("versionId")
    private String versionId;
    private String name;
    private String summary;
    private List<File> files;
    private Map<String, String> dependencies;

    public int getFormatVersion() {
        return formatVersion;
    }

    public String getGame() {
        return game;
    }

    public String getVersionId() {
        return versionId;
    }

    public String getName() {
        return name;
    }
    
    public String getSummary() {
        return summary;
    }

    public List<File> getFiles() {
        return files;
    }

    public Map<String, String> getDependencies() {
        return dependencies;
    }
}

