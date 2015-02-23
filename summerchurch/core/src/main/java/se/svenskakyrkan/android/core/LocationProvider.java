package se.svenskakyrkan.android.core;

/**
 * Gives a way to find the location of a device.
 *
 * @author Henrik Arro
 */
public interface LocationProvider {

    /**
     * Gives the current location of the device.
     *
     * @return the current location of the device
     */
    public LatLong currentLocation();

}
