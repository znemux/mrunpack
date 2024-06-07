package znemux.mrzip;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

public class Download {
    
    static ArrayList<String> getLinks(String file) {
        try {
            String content = new String(Files.readAllBytes(Paths.get(file)));
            JSONObject json = new JSONObject(content);
            JSONArray filesArray = json.getJSONArray("files");
            ArrayList<String> links = new ArrayList<>();

            // Iterate over each object in the "files" array
            for (int i = 0; i < filesArray.length(); i++) {
                JSONObject fileObject = filesArray.getJSONObject(i);

                // Get the "downloads" array from each object
                JSONArray downloadsArray = fileObject.getJSONArray("downloads");

                // Iterate over the "downloads" array and add each URL to the list
                for (int j = 0; j < downloadsArray.length(); j++) {
                    String downloadUrl = downloadsArray.getString(j);
                    links.add(downloadUrl);
                }
            }
            Convert.complete += links.size();
            return links;
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
            return null;
        }
    }
    
    static void files(ArrayList<String> downloadsList, String folderPath) {
        try {
            // Create the folder if it doesn't exist
            Path path = Paths.get(folderPath);
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }
            // Iterate through the list and download each item
            for (String downloadUrl : downloadsList) {
                // Extract the file name from the URL
                String fileName = downloadUrl.substring(downloadUrl.lastIndexOf('/') + 1).replace("%2B", "+").replace("%20", " ");
                // Construct the file path
                String filePath = folderPath + File.separator + fileName;
                // Download the file
                Convert.progress("Downloading "+fileName);
                Download.file(downloadUrl, filePath);
            }
        } catch (IOException | URISyntaxException ex) {
            System.err.println(ex.getMessage());
        }

    }

    private static void file(String url, String file) throws IOException, URISyntaxException {
        URI downloadUrl = new URI(url);
        try (InputStream in = downloadUrl.toURL().openStream(); FileOutputStream out = new FileOutputStream(file);) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1)
                out.write(buffer, 0, bytesRead);
        }
    }
    
}
