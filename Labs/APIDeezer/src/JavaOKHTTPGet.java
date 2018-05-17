import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class JavaOKHTTPGet {

    private String myURL;
    private Map<String, String> messages;
    private OkHttpClient client;

    public JavaOKHTTPGet(String getUrl, Map<String,String> myMap){
        myURL = getUrl;
        messages = new LinkedHashMap<>();
        client = new OkHttpClient();
        setMessages(myMap);
    }

    public String getMyURL() {
        return myURL;
    }

    private String run() throws IOException {
        // Build the URL with the params
        HttpUrl.Builder httpurl = Objects.requireNonNull(HttpUrl.parse(getMyURL())).newBuilder();
        for (String key : getMessages().keySet()){
            httpurl.addEncodedQueryParameter(
                    URLEncoder.encode(key, "UTF-8"),
                    URLEncoder.encode(getMessages().get(key), "UTF-8")
            );
        }
        HttpUrl finalURL = httpurl.build();

        // Launch the request and read the response
        Request request = new Request.Builder().url(finalURL).build();
        try (Response response = client.newCall(request).execute()) {
            assert response.body() != null;
            return response.body().string();
        }
    }

    private void setMessages(Map<String,String> myMap){
        // Copy the message map
        for (String key : myMap.keySet())
            this.messages.put(key, myMap.get(key));
    }

    private Map<String, String> getMessages(){
        return this.messages;
    }

    public static void main(String[] args) throws IOException {
        // Link to GET API
        String url = "https://httpbin.org/get?";

        // Arguments to be sent
        Map<String,String> messages = new HashMap<>();
        messages.put("message1", "bonjour");
        messages.put("message2", "au revoir");
        messages.put("mes sag e3", "special characters : \" ' / \\ % ~ ! @ # $ % ^ & * ( ) etc...");
        messages.put("m e s s a g e 4", "C’est l’été à Paris!");

        // Create the connection
        JavaOKHTTPGet example = new JavaOKHTTPGet(url,messages);
        String response = example.run();
        System.out.println(response);
    }
}
