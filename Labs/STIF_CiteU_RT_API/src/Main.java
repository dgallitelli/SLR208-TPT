import com.google.gson.*;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Scanner;

public class Main {

    private static String getSTIF = "https://api-lab-trone-stif.opendata.stif.info/service/tr-vianavigo/departures?";
    private static String line_id = "810:B";
    private static String apikey = "859f5ada71e18bc0c18c7d50bb09a540f43cb5e986fe8dabd3b337c4";

    public static void main(String[] args) throws IOException {

        OkHttpClient okHttpClient = new OkHttpClient();
        Request request;
        Response response;
        Scanner sc = new Scanner(System.in);

        // Ask for input of station name - has to be on RER B line
        System.out.println("[IN] Please insert name of the station (has to be on RER): ");
        String stationName = sc.nextLine();
        stationName = URLEncoder.encode(stationName, "UTF-8");

        // Get the stop_id
        request = new Request.Builder()
                .url("https://opendata.stif.info/api/records/1.0/search//?dataset=liste-arrets-lignes-tc-idf&q="+stationName+"&refine.agency_name=RER").build();
        response = okHttpClient.newCall(request).execute();

        JsonObject jsonObject = new JsonParser().parse(response.body().string()).getAsJsonObject();
        JsonArray records = jsonObject.get("records").getAsJsonArray();
        if (records.size() == 0) {
            System.out.println("[ERR] No station has been found with that name");
            return;
        }
        JsonObject stop_info = records.get(0).getAsJsonObject().get("fields").getAsJsonObject();
        String stop_id = stop_info.get("stop_id").getAsString();
        String route_id = stop_info.get("route_id").getAsString();

        request = new Request.Builder()
                .url(getSTIF+"line_id="+route_id+"&stop_point_id="+stop_id+"&apikey="+apikey).build();
        response = okHttpClient.newCall(request).execute();


        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gs = gsonBuilder.create();
        Train listTrains[] = gs.fromJson(response.body().string(), Train[].class);

        for (Train t : listTrains)
            System.out.println(t.toString());
    }
}
