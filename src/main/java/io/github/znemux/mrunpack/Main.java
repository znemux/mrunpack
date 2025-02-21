package io.github.znemux.mrunpack;

import io.github.znemux.mrunpack.gui.Frame;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;

public class Main {
    
    public static void main(String[] args) {
        Frame.start();
    }

    public static void unpack(Path path) throws IOException, URISyntaxException {
        new Mrpack(path).extract().download();
    }
}