import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * A complete Java class that shows how to open a URL, then read data (text) from that URL,
 * HttpURLConnection class (in combination with an InputStreamReader and BufferedReader).
 *
 * @author alvin alexander, http://alvinalexander.com.
 */
public class JavaHTTPGet {

    private String myURL;
    private Map<String, String> messages;

    public static void main(String[] args) {
        try {
            new JavaHTTPGet();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private JavaHTTPGet() throws Exception {
        this.myURL = "https://httpbin.org/get?";
        messages = new LinkedHashMap<>();
        int i = 1;

        // Insert the messages
        this.messages.put("message1", "bonjour");
        this.messages.put("message2", "au revoir");
        this.messages.put("mes sag e3", "special characters : \" ' / \\ % ~ ! @ # $ % ^ & * ( ) etc...");
        this.messages.put("m e s s a g e 4", "C’est l’été à Paris!");

        // Encode the messages and append them to the URL
        StringBuilder finalString = new StringBuilder();
        finalString.append(this.myURL);
        for (String s : this.messages.keySet()) {
            if (i != 1) finalString.append("&");
            finalString.append(URLEncoder.encode(s, "UTF-8"));
            finalString.append("=");
            finalString.append(URLEncoder.encode(this.messages.get(s), "UTF-8"));
            i++;
        }

        // Connect and print the results
        // System.out.println("Message= "+finalString.toString());
        String results = doHttpUrlConnectionAction(finalString.toString());
        System.out.println(results);
    }

    /**
     * Returns the output from the given URL.
     * <p>
     * I tried to hide some of the ugliness of the exception-handling
     * in this method, and just return a high level Exception from here.
     * Modify this behavior as desired.
     *
     * @param desiredUrl url to connect to
     * @return the handler to the connection
     * @throws Exception any exception
     */
    private String doHttpUrlConnectionAction(String desiredUrl)
            throws Exception {
        URL url;
        BufferedReader reader = null;
        StringBuilder stringBuilder;

        try {
            // create the HttpURLConnection
            url = new URL(desiredUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // just want to do an HTTP GET here
            connection.setRequestMethod("GET");

            // uncomment this if you want to write output to this url
            //connection.setDoOutput(true);

            // give it 15 seconds to respond
            connection.setReadTimeout(15 * 1000);
            connection.connect();

            // read the output from the server
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            stringBuilder = new StringBuilder();

            String line = null;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }
            return stringBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            // close the reader; this can throw an exception too, so
            // wrap it in another try/catch block.
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        }
    }
}