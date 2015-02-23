package se.svenskakyrkan.android.core.place;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.Reader;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by svkhao on 2015-02-18.
 */
public class SvkPlaceParser implements PlaceParser {

    @Override
    public Set<Place> parseFrom(Reader reader) {
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = jsonParser.parse(reader).getAsJsonObject();
        int totalHits = jsonObject.get("TotalHits").getAsInt();
        JsonArray jsonArray = jsonObject.get("Results").getAsJsonArray();
        Set<Place> places = new HashSet<Place>();
        for (int i = 0; i < jsonArray.size(); i++) {
            jsonObject = jsonArray.get(i).getAsJsonObject();
            String name = jsonObject.get("Name").getAsString();
            JsonArray coordinates = jsonObject.get("Geolocation").getAsJsonObject().get("Coordinates").getAsJsonArray();
            double longitude = coordinates.get(0).getAsDouble();
            double latitude = coordinates.get(1).getAsDouble();
            JsonObject placeTypes = jsonObject.getAsJsonObject("PlaceTypes");
            Place place = new Place(name, longitude, latitude);
            JsonObject summerChurch = placeTypes.getAsJsonObject("SummerChurch");
            if (summerChurch != null) {
                String description = "";
                if (summerChurch.get("Description") != null) {
                    description = summerChurch.get("Description").getAsString();
                }
                place.setDescription(description);
                if (summerChurch.get("Cafeteria").getAsBoolean()) {
                    String cafeteriaDescription = "Yes";
                    if (summerChurch.get("CafeteriaDescription") != null) {
                        cafeteriaDescription = summerChurch.get("CafeteriaDescription").getAsString();
                    }
                    place.setCafeteriaDescription(cafeteriaDescription);
                }
                if (summerChurch.get("Guide").getAsBoolean()) {
                    String guideDescription = "Yes";
                    if (summerChurch.get("GuideDescription") != null) {
                        guideDescription = summerChurch.get("GuideDescription").getAsString();
                    }
                    place.setGuideDescription(guideDescription);
                }
            }
            places.add(place);
        }
        return places;
    }
}
