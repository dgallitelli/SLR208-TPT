import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    private static MediaPlayer mediaPlayer;

    public static void main(String[] args) {

        JFXPanel fxPanel = new JFXPanel();

        int userPoints = 0;

        try {
            // Get playList info
            DeezerPlaylist deezerPlaylist = getDeezerPlaylist(1363560485);

            for (DeezerTrack track : deezerPlaylist.getTracks().getData()){
                // Play data one by one and prompt for title
                playTrack(track);

                System.out.println("[IN] Input name of the song: ");
                Scanner input = new Scanner(System.in);
                String titleGuess = input.nextLine();
                stopTrack();
                if (titleGuess.compareTo("STOP")==0) break;
                if (checkTitle(titleGuess, track)) {
                    System.out.println("[OK] Great Job! +1");
                    userPoints +=1;
                } else System.out.println("[ERR] Sorry! The actual title was: "+track.getTitle());
            }
            stopTrack();
            System.out.println("[END] Congrats: you scored "+ userPoints +" points!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Function to load a playlist from deezer and get informations and tracklist
     * @param playListID the ID of the playlist
     * @return the object containing playlist info and tracks
     * @throws IOException
     */
    private static DeezerPlaylist getDeezerPlaylist(int playListID) throws IOException {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.deezer.com/playlist/"+playListID).build();
        Response response = okHttpClient.newCall(request).execute();

        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gs = gsonBuilder.create();
        return gs.fromJson(response.body().string(), DeezerPlaylist.class);
    }

    /**
     * Function to reproduce a track
     * @param myTrack track of type DeezerTrack to reproduce
     */
    private static void playTrack(DeezerTrack myTrack){
        String newURL = myTrack.getPreview().split(":")[1];
        System.out.println("## Reproducing music now ## " +"http:"+newURL);
        Media song = new Media("http:"+newURL);
        mediaPlayer = new MediaPlayer(song);
        mediaPlayer.play();
    }

    /**
     * Function to stop execution of track
     */
    private static void stopTrack(){
        mediaPlayer.stop();
    }

    /**
     * Function to check if the string input by the user is the actual title
     * @param _title Input from the user - title
     * @param _myTrack DeezerTrack of the current track playing
     * @return True if it's the same title, False otherwise
     */
    private static Boolean checkTitle(String _title, DeezerTrack _myTrack){
        _title = _title.toLowerCase();
        String actualTitle = _myTrack.getTitle().toLowerCase();
        return _title.compareTo(actualTitle) == 0 || _title.compareTo(actualTitle) == 0;
    }
}
