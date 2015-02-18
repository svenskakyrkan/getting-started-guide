package se.svenskakyrkan.android.core.place;

import java.io.IOException;
import java.util.Set;

/**
 * Created by svkhao on 2015-02-10.
 */
public interface PlaceFinder {

    Set<Place> find(PlaceType placeType, double longitude, double latitude, int maxDistanceMeters) throws IOException;

}
