package io.github.znemux.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import javax.swing.JProgressBar;

public class Download {
    
    long bytelength, byteprogress;
    int percent;
    JProgressBar bar;
    
    public Download(long bytelength) {
        this.bytelength = bytelength;
        byteprogress = 0;
        percent = -1;
    }
    
    public Download(long bytelength, JProgressBar bar) {
        this.bytelength = bytelength;
        byteprogress = 0;
        percent = -1;
        this.bar = bar;
    }
    
    void updateProgress(int increment) {
        byteprogress += increment;
        int curpercent = (int) (byteprogress * 100l / (double) bytelength);
        if (curpercent != percent) {
            percent = curpercent;
            if (bar != null) bar.setValue(percent);
        }
    }
    
    public void download(String uri, File location) throws IOException, URISyntaxException {
        download(new URI(uri), location);
    }
    
    public void download(URI uri, File location) throws IOException {
        download(uri.toURL(), location);
    }
    
    public void download(URL url, File location) throws IOException {
        try (InputStream in = url.openStream(); FileOutputStream out = new FileOutputStream(location)) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
                updateProgress(bytesRead);
            }
        }
    }
}