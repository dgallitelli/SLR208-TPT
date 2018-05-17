import com.google.gson.Gson;
import com.google.gson.JsonElement;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import okhttp3.*;

public class JavaOKHTTPPost {
    private static final MediaType JSON
        = MediaType.parse("application/json; charset=utf-8");
    private static final String pathToFileJson = "src/files/file.json";
    private static final String postURL = "https://httpbin.org/post";

    private OkHttpClient client = new OkHttpClient();

    private String post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            assert response.body() != null;
            return response.body().string();
        }
    }

    private String generateJSON(String path){
        if (path != null){
            Gson gson = new Gson();

            try (Reader reader = new FileReader(pathToFileJson)) {
                // Convert JSON to JsonElement, and later to String
                JsonElement json = gson.fromJson(reader, JsonElement.class);

                // Return to JSON2String
                return gson.toJson(json);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        } else
            return "{'winCondition':'HIGH_SCORE',"
                + "'name':'Bowling',"
                + "'round':4,"
                + "'lastSaved':1367702411696,"
                + "'dateStarted':1367702378785,"
                + "'players':["
                + "{'name':'Davide','history':[10,8,6,7,8],'color':-13388315,'total':39},"
                + "{'name':'Gallitelli','history':[6,10,5,10,10],'color':-48060,'total':41}"
                + "]}";
    }

    public static void main(String[] args) throws IOException {
        JavaOKHTTPPost example = new JavaOKHTTPPost();
        String json = example.generateJSON(pathToFileJson);
        String response = example.post(postURL, json);
        System.out.println(response);
    }
}
