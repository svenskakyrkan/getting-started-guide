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
 * An implementation of the {@link PlaceFinder} interface that uses an <code>HttpUrlConnection</code>
 * to connect to the <a href="http://api.svenskakyrkan.se/platser/v3-latest/doc/">places API</a>.
 *
 * @author Henrik Arro
 */
public class HttpUrlConnectionPlaceFinder implements PlaceFinder {

    /**
     * The default URL to use to connect to the places API.
     */
    public static final String PLACES_URL = "http://api.svenskakyrkan.se/platser/v3/place";

    /**
     * The default API key to use to authenticate requests to the places API.
     */
    // TODO: Replace this with your own API key.
    public static final String API_KEY = "b95eeb81-65fe-49db-8029-a85234a2247a";

    private static final byte[] BUFFER = new byte[1024];

    private String baseUrl;
    private String apiKey;
    private PlaceParser placeParser;

    /**
     * Creates a new place finder.
     *
     * @param baseUrl the base URL of the places API
     * @param apiKey the API key to use to authenticate the requests
     * @param placeParser the {@link PlaceParser} to use to parse the result
     */
    public HttpUrlConnectionPlaceFinder(String baseUrl, String apiKey, PlaceParser placeParser) {
        if (baseUrl == null || apiKey == null || placeParser == null) {
            throw new IllegalArgumentException("Arguments must not be null: baseUrl=" + baseUrl
                    + ", apiKey=" + apiKey + ", placeParser=" + placeParser);
        }
        this.baseUrl = baseUrl;
        this.apiKey = apiKey;
        this.placeParser = placeParser;
    }

    /**
     * Creates a new place finder with default settings.
     *
     * @return a new place finder using default settings
     */
    public static HttpUrlConnectionPlaceFinder createDefault() {
        return new HttpUrlConnectionPlaceFinder(PLACES_URL, API_KEY, new SvkPlaceParser());
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
