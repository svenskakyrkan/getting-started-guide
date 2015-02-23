package se.svenskakyrkan.android.summerchurch;

import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import se.svenskakyrkan.android.core.LatLong;
import se.svenskakyrkan.android.core.LocationProvider;

/**
 * An implementation of the {@link LocationProvider} interface, using the
 * <a href="https://developer.android.com/reference/com/google/android/gms/location/package-summary.html">
 * fused location provider</a> API.
 * <p>
 * This implementation is tightly coupled to {@link MainActivity}; it will update the display each time the
 * location changes.
 *
 * @author Henrik Arro
 */
public class GoogleApiClientLocationProvider implements LocationProvider, LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private static final long INTERVAL = 1000 * 30;
    private static final long FASTEST_INTERVAL = 1000 * 5;

    private static final FusedLocationProviderApi FUSED_LOCATION_PROVIDER_API = LocationServices.FusedLocationApi;

    private LocationRequest locationRequest;
    private GoogleApiClient googleApiClient;
    private Location location;
    private MainActivity mainActivity;

    public GoogleApiClientLocationProvider(MainActivity mainActivity) {
        Log.d(GoogleApiClientLocationProvider.class.getSimpleName(), "FusedLocationService: mainActivity=" + mainActivity);
        this.mainActivity = mainActivity;
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(INTERVAL);
        locationRequest.setFastestInterval(FASTEST_INTERVAL);

        googleApiClient = new GoogleApiClient.Builder(mainActivity)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        if (googleApiClient != null) {
            googleApiClient.connect();
        }
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        Log.d(GoogleApiClientLocationProvider.class.getSimpleName(), "onConnected: connectionHint=" + connectionHint);
        FUSED_LOCATION_PROVIDER_API.requestLocationUpdates(googleApiClient, locationRequest, this);
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d(GoogleApiClientLocationProvider.class.getSimpleName(), "onLocationChanged: location=" + location);
        this.location = location;
        mainActivity.findPlaces();
    }

    @Override
    public LatLong currentLocation() {
        if (location == null) {
            return null;
        } else {
            return new LatLong(location.getLatitude(), location.getLongitude());
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d(GoogleApiClientLocationProvider.class.getSimpleName(), "onConnectionSuspended: i=" + i);
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d(GoogleApiClientLocationProvider.class.getSimpleName(), "onConnectionFailed: connectionResult=" + connectionResult);
    }
}