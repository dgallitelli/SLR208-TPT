import okhttp3.*;

import java.io.File;
import java.io.IOException;

public class JavaOKHTTPPostFiles {

    private static final MediaType MEDIA_TYPE_MARKDOWN
            = MediaType.parse("text/x-markdown; charset=utf-8");
    private static final String mdFilePath = "src/files/okhttpreadme.md";
    private static final String postURL = "https://httpbin.org/post";

    private final OkHttpClient client;

    public JavaOKHTTPPostFiles() {
        client = new OkHttpClient();
    }

    public void run() throws Exception {
        File file = new File(mdFilePath);

        Request request = new Request.Builder().url(postURL)
                .post(RequestBody.create(MEDIA_TYPE_MARKDOWN, file)).build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            assert response.body() != null;
            System.out.println(response.body().string());
        }
    }

    public static void main(String[] args){
        JavaOKHTTPPostFiles poster = new JavaOKHTTPPostFiles();
        try {
            poster.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
