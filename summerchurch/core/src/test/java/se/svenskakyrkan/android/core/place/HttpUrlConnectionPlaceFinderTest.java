package se.svenskakyrkan.android.core.place;

import org.junit.Assert;
import org.junit.Test;

import java.util.Set;

/**
 * Created by svkhao on 2015-02-12.
 */
public class HttpUrlConnectionPlaceFinderTest {

    @Test
    public void find() throws Exception {
        HttpUrlConnectionPlaceFinder placeFinder = HttpUrlConnectionPlaceFinder.createDefault();
        Set<Place> places = placeFinder.find(PlaceType.SUMMER_CHURCH, 17.6336, 59.8581, 100);
        Assert.assertNotNull("Places should not be null", places);
        Assert.assertEquals("Wrong number of places found: ", 1, places.size());
        Place domkyrkan = places.iterator().next();
        System.out.println(domkyrkan);
        Assert.assertEquals("Place has wrong name: ", "Domkyrkan", domkyrkan.name());
        Assert.assertNotNull("Place description should not be null", domkyrkan.description());
        Assert.assertFalse("Place description should not be empty", domkyrkan.description().isEmpty());
    }
}
