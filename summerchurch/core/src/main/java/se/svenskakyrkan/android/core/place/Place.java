package se.svenskakyrkan.android.core.place;

import java.util.Set;
import java.util.UUID;

/**
 * Represents information about a place from the <a href="http://api.svenskakyrkan.se/platser/v3-latest/doc/">places API</a>.
 *
 * @author Henrik Arro
 */
public class Place {
    private String name;
    private double longitude;
    private double latitude;
    private String description;
    private String cafeteriaDescription;
    private String guideDescription;

    public Place(String name, double longitude, double latitude) {
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    /**
     * Gives the name of the place.
     *
     * @return the name of the place
     */
    public String name() {
        return name;
    }

    /**
     * Gives the longitude of the place, in degrees, if known.
     *
     * @return the longitude of the place, or <code>null</code>
     */
    public double longitude() {
        return longitude;
    }

    /**
     * Gives the latitude of the place, in degrees, if known.
     *
     * @return the latitude of the place, or <code>null</code>
     */
    public double latitude() {
        return latitude;
    }

    /**
     * Gives the description of the place, if known.
     *
     * @return the description of the place, or <code>null</code>
     */
    public String description() {
        return description;
    }

    /**
     * Sets a new description for the place.
     *
     * @param description the new description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gives the description of the places' cafeteria, if known.
     *
     * @return the description of the places' cafeteria, or <code>null</code>
     */
    public String cafeteriaDescription() {
        return cafeteriaDescription;
    }

    /**
     * Sets a new cafeteria description for the place.
     *
     * @param cafeteriaDescription the new cafeteria description
     */
    public void setCafeteriaDescription(String cafeteriaDescription) {
        this.cafeteriaDescription = cafeteriaDescription;
    }

    /**
     * Gives the description of the place's guided tours, if known.
     *
     * @return the description of the place's guided tours, or <code>null</code>
     */
    public String guideDescription() {
        return guideDescription;
    }

    /**
     * Sets a new description of the place's guided tours.
     *
     * @param guideDescription the new description of guided tours
     */
    public void setGuideDescription(String guideDescription) {
        this.guideDescription = guideDescription;
    }

    @Override
    public String toString() {
        return "Place{" +
                "name='" + name + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", description=" + description +
                ", cafeteriaDescription=" + cafeteriaDescription +
                ", guideDescription=" + guideDescription +
                '}';
    }
}
