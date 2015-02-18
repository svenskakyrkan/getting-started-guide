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

public class FusedLocationService implements LocationProvider, LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private static final long INTERVAL = 1000 * 30;
    private static final long FASTEST_INTERVAL = 1000 * 5;
    private static final long ONE_MIN = 1000 * 60;
    private static final long REFRESH_TIME = ONE_MIN * 5;
    private static final float MINIMUM_ACCURACY = 50.0f;

    private LocationRequest locationRequest;
    private GoogleApiClient googleApiClient;
    private Location location;
    private FusedLocationProviderApi fusedLocationProviderApi = LocationServices.FusedLocationApi;
    private MainActivity mainActivity;

    public FusedLocationService(MainActivity context) {
        Log.i(FusedLocationService.class.getSimpleName(), "FusedLocationService: context=" + context);
        mainActivity = context;
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(INTERVAL);
        locationRequest.setFastestInterval(FASTEST_INTERVAL);

        googleApiClient = new GoogleApiClient.Builder(context)
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
        Log.i(FusedLocationService.class.getSimpleName(), "onConnected: connectionHint=" + connectionHint);
        fusedLocationProviderApi.requestLocationUpdates(googleApiClient, locationRequest, this);
        /*
        Location currentLocation = fusedLocationProviderApi.getLastLocation(googleApiClient);
        if (currentLocation != null && currentLocation.getTime() > REFRESH_TIME) {
            location = currentLocation;
        } else {

            // Schedule a Thread to unregister location listeners
            Executors.newScheduledThreadPool(1).schedule(new Runnable() {
                @Override
                public void run() {
                    fusedLocationProviderApi.removeLocationUpdates(googleApiClient,
                            FusedLocationService.this);
                }
            }, ONE_MIN, TimeUnit.MILLISECONDS);
        }
        */
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.i(FusedLocationService.class.getSimpleName(), "onLocationChanged: location=" + location);
        //if the existing location is empty or
        //the current location accuracy is greater than existing accuracy
        //then store the current location
        /*
        if (this.location == null || location.getAccuracy() < this.location.getAccuracy()) {
            this.location = location;
            //if the accuracy is not better, remove all location updates for this listener
            if (this.location.getAccuracy() < MINIMUM_ACCURACY) {
                fusedLocationProviderApi.removeLocationUpdates(googleApiClient, this);
            }
        }
        */
        this.location = location;
        mainActivity.findPlaces();
    }

    public Location getLocation() {
        return this.location;
    }

    public LatLong currentLocation() {
        if (location == null) {
            return null;
        } else {
            return new LatLong(location.getLatitude(), location.getLongitude());
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}