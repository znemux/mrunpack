package io.github.znemux.mrunpack;

import io.github.znemux.mrunpack.gui.Frame;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.Map;

public class Main {
    
    public static Frame frame;
    
    public static void main(String[] args) throws IOException, URISyntaxException {
        Frame.init();
    }
    
    public static void unpack(Path mrpack) throws IOException, URISyntaxException {
        try {
            Path target = Path.of(mrpack.toString().replaceAll(".mrpack$", ""));
            ZipUtil.unzip(mrpack, target);
            Map links = Download.getFileLinks(target.resolve("modrinth.index.json"));
            System.out.println("Downloading mods...");
            Download.files(links, target.resolve("overrides"));
        } catch (IOException | URISyntaxException ex) {
            ex.printStackTrace();
        }
        frame.unpackButton.setEnabled(true);
        frame.message("Task completed");
    }
    
}
