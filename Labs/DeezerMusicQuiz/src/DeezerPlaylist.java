import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.List;

public class DeezerPlaylist {

    private int id;
    private String title;
    private String link;
    private DeezerTracks tracks;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public DeezerTracks getTracks() {
        return tracks;
    }

    @Override
    public String toString() {
        return "DeezerPlaylist{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", link='" + link + '\'' +
                ", data=" + tracks +
                '}';
    }
}
