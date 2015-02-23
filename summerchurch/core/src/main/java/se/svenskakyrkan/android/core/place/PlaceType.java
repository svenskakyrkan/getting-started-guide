package se.svenskakyrkan.android.core.place;

/**
 * Represents different place types from the <a href="http://api.svenskakyrkan.se/platser/v3-latest/doc/placetypes/">places API</a>.
 *
 * @author Henrik Arro
 */
public enum PlaceType {

    BELL_TOWER("belltower"),
    CEMETERY("cemetery"),
    CHAPEL("chapel"),
    CHURCH("church"),
    CHURCH_OF_PILGRIMAGE("churchofpilgrimage"),
    CREMATORIUM("crematorium"),
    INTERNATIONAL_PROJECT("internationalproject"),
    MOUNTAIN_CHURCH("mountainchurch"),
    PARISH_HOME("parishhome"),
    POINT_OF_INTEREST_CHURCH("pointofinterestchurch"),
    ROAD_CHURCH("roadchurch"),
    RUIN("ruin"),
    SECRETARIAT("secretariat"),
    SUMMER_CHURCH("summerchurch");

    private String searchString;

    private PlaceType(String searchString) {
        this.searchString = searchString;
    }

    /**
     * gives the value that should be used for the "is" parameter when searching for this type of place.
     *
     * @return the string to use when searching
     */
    public String searchString() {
        return searchString;
    }
}
