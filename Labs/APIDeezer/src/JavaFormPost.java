import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class JavaFormPost {

    private final String USER_AGENT = "Mozilla/5.0";
    private final String myURL = "https://httpbin.org/post";

    public static void main(String[] args){
        try {
            new JavaFormPost();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private JavaFormPost() throws IOException {

        //création du fichier texte
        try (Writer out = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream("file.txt"), "UTF-8"))) {
            out.write("C'est un fichier encodé en UTF-8!");
        }
        //fin création du fichier texte

        //création du fichier binaire
        int i = 99;
        DataOutputStream os = null;
        try {
            os = new DataOutputStream(new FileOutputStream("file.bin"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                os.writeInt(i);
                os.close();
            }
        }
        //fin création du fichier binaire

        String charset = "UTF-8";
        // To send : Map {param, value}
        Map<String,String> myMap = new HashMap<>();
        myMap.put("param","value");
        // To send : text file
        File textFile = new File("file.txt");
        // To send : binary file
        File binaryFile = new File("file.bin");

        String boundary = Long.toHexString(System.currentTimeMillis()); // Just generate some unique random value.
        String CRLF = "\r\n"; // Line separator required by multipart/form-data.

        // Setup and create the connection
        HttpURLConnection connection = (HttpURLConnection) new URL(myURL).openConnection();
        connection.setDoOutput(true);
        connection.setRequestProperty("Accepted-Charset", charset);
        connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

        // Send the content!
        try (OutputStream output = connection.getOutputStream()) {
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(output, charset), true);

            // Send normal param:value map.
            writer.append("--").append(boundary).append(CRLF);
            writer.append("Content-Disposition: form-data; name=\"param\"").append(CRLF);
            writer.append("Content-Type: text/plain; charset=").append(charset).append(CRLF);
            writer.append(CRLF).append(myMap.toString()).append(CRLF).flush();

            // Send text file.
            writer.append("--").append(boundary).append(CRLF);
            writer.append("Content-Disposition: form-data; name=\"textFile\"; filename=\"").append(textFile.getName()).append("\"").append(CRLF);
            writer.append("Content-Type: text/plain; charset=").append(charset).append(CRLF); // Text file itself must be saved in this charset!
            writer.append(CRLF).flush();
            Files.copy(textFile.toPath(), output);
            output.flush(); // Important before continuing with writer!
            writer.append(CRLF).flush(); // CRLF is important! It indicates end of boundary.

            // Send binary file.
            writer.append("--").append(boundary).append(CRLF);
            writer.append("Content-Disposition: form-data; name=\"binaryFile\"; filename=\"").append(binaryFile.getName()).append("\"").append(CRLF);
            writer.append("Content-Type: ").append(HttpURLConnection.guessContentTypeFromName(binaryFile.getName())).append(CRLF);
            writer.append("Content-Transfer-Encoding: binary").append(CRLF);
            writer.append(CRLF).flush();
            Files.copy(binaryFile.toPath(), output);
            output.flush(); // Important before continuing with writer!
            writer.append(CRLF).flush(); // CRLF is important! It indicates end of boundary.

            // End of multipart/form-data.
            writer.append("--").append(boundary).append("--").append(CRLF).flush();
        }

        // Get the response
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder output = new StringBuilder();

        String line = null;
        while ((line = reader.readLine()) != null) {
            output.append(line).append("\n");
        }
        System.out.println(output.toString());

    }
}
