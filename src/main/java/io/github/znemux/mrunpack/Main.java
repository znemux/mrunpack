package io.github.znemux.mrunpack;

import static io.github.znemux.mrunpack.Download.progress;
import io.github.znemux.mrunpack.gui.Frame;
import io.github.znemux.mrunpack.index.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.List;

public class Main {

    public static Frame frame;

    public static void main(String[] args) {
        Frame.init();
    }

    public static void unpack(Path mrpack) throws IOException, URISyntaxException {
        progress = 0;
        Path target = Path.of(mrpack.toString().replaceAll(".mrpack$", ""));
        System.out.println("Extracting " + mrpack + "...");
        ZipUtil.unzip(mrpack, target);
        List<File> links = Download.getFileLinks(target.resolve("modrinth.index.json"));
        System.out.println("Downloading mods...");
        Download.files(links, target.resolve("overrides"));
        System.out.println("Task completed.");
        frame.messageInfo("Task completed");
    }
}
