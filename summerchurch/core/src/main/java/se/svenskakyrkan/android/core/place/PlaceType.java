package se.svenskakyrkan.android.core.place;

/**
 * Created by svkhao on 2015-02-10.
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

    public String searchString() {
        return searchString;
    }
}
