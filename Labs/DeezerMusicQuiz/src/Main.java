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

    public static void main(String[] args) {

        JFXPanel fxPanel = new JFXPanel();

        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gs = gsonBuilder.create();

        try {
            DeezerTrack myTrack = gs.fromJson(deezerGET(), DeezerTrack.class);
            System.out.println("## Reproducing music now ## " +myTrack.getPreview());
            // System.out.println(myTrack.toString());
            Media song = new Media(myTrack.getPreview());
            MediaPlayer mediaPlayer = new MediaPlayer(song);
            mediaPlayer.play();
            Scanner input = new Scanner(System.in);
            System.out.println("[IN] Input name of the song: ");
            String titleGuess = input.nextLine();
            if (checkTitle(titleGuess, myTrack))
                System.out.println("[OK] Congratulations! You won!");
            else
                System.out.println("[ERR] Sorry, wrong answer.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String deezerGET() throws IOException {
        OkHttpClient okHttpClient = new OkHttpClient();
        String deezerTrackURL = "https://api.deezer.com/track/3135556";
        Request request = new Request.Builder()
                .url(deezerTrackURL).build();
        Response response = okHttpClient.newCall(request).execute();
        return response.body().string();
    }

    /**
     * Function to check if the string input by the user is the actual title
     * @param _title Input from the user - title
     * @return True if it's the same title, False otherwise
     */
    private static Boolean checkTitle(String _title, DeezerTrack _myTrack){
        return _title.compareTo(_myTrack.getTitle()) == 0;
    }
}
