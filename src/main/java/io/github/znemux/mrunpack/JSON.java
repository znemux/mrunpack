package io.github.znemux.mrunpack;

import com.google.gson.Gson;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class JSON {
    public static Gson gson = new Gson();
    
    public static String fromFile(Path jsonFile) throws IOException {
        return Files.readString(jsonFile);
    }
    
    public static Object toObject(Path jsonFile, Class<?> clss) throws IOException {
        return gson.fromJson(fromFile(jsonFile), clss);
    }
}
