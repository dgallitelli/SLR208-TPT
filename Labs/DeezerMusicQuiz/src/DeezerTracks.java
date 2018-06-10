import java.util.List;

public class DeezerTracks {
    private List<DeezerTrack> data;

    public List<DeezerTrack> getData() {
        return data;
    }

    public void setData(List<DeezerTrack> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "DeezerTracks{" +
                "data=" + data +
                '}';
    }
}
