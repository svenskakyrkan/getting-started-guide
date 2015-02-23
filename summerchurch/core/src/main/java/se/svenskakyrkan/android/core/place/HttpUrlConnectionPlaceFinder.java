package se.svenskakyrkan.android.core.place;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;

import jdk.nashorn.internal.parser.JSONParser;

/**
 * Created by svkhao on 2015-02-11.
 */
public class HttpUrlConnectionPlaceFinder implements PlaceFinder {

    private static final byte[] BUFFER = new byte[1024];

    private String baseUrl;
    private String apiKey;
    private PlaceParser placeParser;

    public HttpUrlConnectionPlaceFinder(String baseUrl, String apiKey, PlaceParser placeParser) {
        if (baseUrl == null || apiKey == null || placeParser == null) {
            throw new IllegalArgumentException("Arguments must not be null: baseUrl=" + baseUrl
                    + ", apiKey=" + apiKey + ", placeParser=" + placeParser);
        }
        this.baseUrl = baseUrl;
        this.apiKey = apiKey;
        this.placeParser = placeParser;
    }

    @Override
    public Set<Place> find(PlaceType placeType, double myLongitude, double myLatitude, int maxDistanceMeters) throws IOException {
        HttpURLConnection connection = null;
        Reader reader = null;
        try {
            StringBuilder urlBuilder = new StringBuilder(baseUrl);
            urlBuilder.append("?is=" + placeType.searchString());
            urlBuilder.append("&nearby=" + myLongitude + "," + myLatitude);
            urlBuilder.append("&nearbyradius=" + maxDistanceMeters);
            URL url = new URL(urlBuilder.toString());
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("SvkAuthSvc-ApiKey", apiKey);
            int sc = connection.getResponseCode();
            if (sc != 200) {
                throw new IOException("HTTP request to URL '" + url + "' failed, status code=" + sc);
            }
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            return placeParser.parseFrom(reader);
        } finally {
            close(reader);
            disconnect(connection);
        }
    }

    private void disconnect(HttpURLConnection connection) {
        if (connection != null) {
            connection.disconnect();
        }
    }

    private void close(Reader reader) {
        if (reader != null) {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
