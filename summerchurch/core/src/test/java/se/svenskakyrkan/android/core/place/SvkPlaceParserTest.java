package se.svenskakyrkan.android.core.place;

import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.Set;

public class SvkPlaceParserTest {

    @Test
    public void parseFrom() throws Exception {
        PlaceParser placeParser = new SvkPlaceParser();
        InputStream inputStream = SvkPlaceParserTest.class.getResourceAsStream("/summer_churches.json");
        Assert.assertNotNull("Error in test data: JSON resource not found", inputStream);
        Reader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        Set<Place> places = placeParser.parseFrom(reader);
        Assert.assertNotNull("Places should not be null", places);
        Assert.assertEquals("Wrong number of places found: ", 7, places.size());
        for (Place place : places) {
            Assert.assertNotNull("Place description should not be null", place.description());
            Assert.assertFalse("Place description should not be empty", place.description().isEmpty());
        }
    }
}
