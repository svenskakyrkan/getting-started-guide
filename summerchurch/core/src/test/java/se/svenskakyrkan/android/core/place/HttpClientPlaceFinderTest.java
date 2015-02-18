package se.svenskakyrkan.android.core.place;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.net.URL;
import java.util.Set;

/**
 * Created by svkhao on 2015-02-12.
 */
public class HttpClientPlaceFinderTest {

    private static final String API_URL = "http://api-t.svenskakyrkan.se/platser/v3/place";
    private static final String API_KEY = "49891762-3778-4c0f-a741-ea831d571269";
    private PlaceParser placeParser = new SvkPlaceParser();

    @Test
    public void find() throws Exception {
        HttpClientPlaceFinder placeFinder = new HttpClientPlaceFinder(API_URL, API_KEY, placeParser);
        Set<Place> places = placeFinder.find(PlaceType.SUMMER_CHURCH, 17.6336, 59.8581, 100);
        Assert.assertNotNull("Places should not be null", places);
        Assert.assertEquals("Wrong number of places found: ", 1, places.size());
        Place domkyrkan = places.iterator().next();
        System.out.println(domkyrkan);
        Assert.assertEquals("Place has wrong name: ", "Domkyrkan", domkyrkan.name());
    }
}
