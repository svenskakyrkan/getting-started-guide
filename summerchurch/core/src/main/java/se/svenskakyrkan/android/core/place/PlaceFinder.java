package se.svenskakyrkan.android.core.place;

import java.io.IOException;
import java.util.Set;

/**
 * Defines ways to find {@link Place Places}.
 *
 * @author Henrik Arro
 */
public interface PlaceFinder {

    /**
     * Finds places of the given type, within a maximum distance from a given point.
     *
     * @param placeType the type of place to find
     * @param longitude the longitude of the point from which to search
     * @param latitude the latitude of the point from which to search
     * @param maxDistanceMeters the maximum distance to search, in meters
     *
     * @return a set of places matching the criteria
     *
     * @throws IOException if searching for the places failed
     */
    Set<Place> find(PlaceType placeType, double longitude, double latitude, int maxDistanceMeters) throws IOException;

}
