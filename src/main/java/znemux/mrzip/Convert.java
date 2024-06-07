package znemux.mrzip;

import java.io.File;
import java.util.ArrayList;
import znemux.mrzip.gui.Frame;

public class Convert {
    
    static int progress = 0;
    static int complete = 1;
    
    public static class thread extends Thread {
        String mrpack;
        public thread(String mrpack) {
            this.mrpack = mrpack;
        }
        @Override
        public void run() {
            if (mrpack != null) mrpackToZip(mrpack);
        }
    }

    static void mrpackToZip(String mrpack) {
        resetProgress();
        // Unpack the modrinth modpack
        String destination = mrpack.substring(0, mrpack.length() - 7);
        ZipUtil.unzip(mrpack, destination);
        // Get the index to look for mod links
        String index = destination + File.separator + "modrinth.index.json";
        ArrayList<String> links = Download.getLinks(index);
        // Download the mods, then zip the modpack
        Download.files(links, destination + File.separator + "overrides" + File.separator + "mods");
        progress("Zipping modpack...");
        ZipUtil.zip(destination + ".zip", destination + File.separator + "overrides", false);
    }
    
    static void progress(String text) {
        progress++;
        if (Main.gui) {
            Main.frame.progressBar.setValue((int)(progress/(float)complete*100));
            Main.frame.progressBar.updateUI();
        } else {
            System.out.println("("+progress+"/"+complete+") "+text);
        }
    }
    
    static void resetProgress() {
        progress = 0;
        complete = 1;
        if (Main.gui) {
            Main.frame.progressBar.setValue(0);
        }
    }

}
