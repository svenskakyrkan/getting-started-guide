package se.svenskakyrkan.android.core.place;

import java.util.Set;
import java.util.UUID;

/**
 *
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

    public String name() {
        return name;
    }

    public double longitude() {
        return longitude;
    }

    public double latitude() {
        return latitude;
    }

    public String description() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String cafeteriaDescription() {
        return cafeteriaDescription;
    }

    public void setCafeteriaDescription(String cafeteriaDescription) {
        this.cafeteriaDescription = cafeteriaDescription;
    }

    public String guideDescription() {
        return guideDescription;
    }

    public void setGuideDescription(String guideDescription) {
        this.guideDescription = guideDescription;
    }

    @Override
    public String toString() {
        return "Place{" +
                "name='" + name + '\'' +
                ", description=" + description +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", cafeteriaDescription=" + cafeteriaDescription +
                ", guideDescription=" + guideDescription +
                '}';
    }
}
