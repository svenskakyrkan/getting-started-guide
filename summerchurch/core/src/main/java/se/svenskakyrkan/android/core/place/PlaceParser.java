package se.svenskakyrkan.android.core.place;

import java.io.IOException;
import java.io.Reader;
import java.util.Set;

/**
 * Defines ways to create {@link Place} objects from textual representations.
 *
 * @author Henrik Arro
 */
public interface PlaceParser {

    /**
     * Creates a set of places from a textual representation.
     *
     * @param reader a reader from which to read the textual representation of the places
     *
     * @return a set of places
     *
     * @throws IOException if reading failed
     */
    Set<Place> parseFrom(Reader reader) throws IOException;

}
