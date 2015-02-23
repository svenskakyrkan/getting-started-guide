package se.svenskakyrkan.android.summerchurch;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import se.svenskakyrkan.android.core.LatLong;
import se.svenskakyrkan.android.core.LocationProvider;
import se.svenskakyrkan.android.core.place.HttpUrlConnectionPlaceFinder;
import se.svenskakyrkan.android.core.place.Place;
import se.svenskakyrkan.android.core.place.PlaceFinder;
import se.svenskakyrkan.android.core.place.PlaceType;
import se.svenskakyrkan.android.core.place.SvkPlaceParser;

public class MainActivity extends Activity implements GoogleMap.OnMarkerClickListener {

    private static final int SEARCH_RADIUS_METERS = 25000;

    private static final int REQUEST_CODE_RECOVER_PLAY_SERVICES = 1001;

    private GoogleMap googleMap;

    private Button btnFusedLocation;
    private TextView tvLocation;
    private Set<Place> places = new HashSet<Place>();

    private LocationProvider locationProvider;
    private PlaceFinder placeFinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(getClass().getSimpleName(), "onCreate: bundle=" + savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!checkPlayServices()) {
            finish();
        }
        locationProvider = getLocationProvider();
        placeFinder = getPlaceFinder();

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        googleMap = mapFragment.getMap();
        googleMap.getUiSettings().setZoomGesturesEnabled(true);
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.setMyLocationEnabled(true);
        googleMap.setOnMarkerClickListener(this);
    }

    protected LocationProvider getLocationProvider() {
        return new FusedLocationService(this);
    }

    protected PlaceFinder getPlaceFinder() {
        return HttpUrlConnectionPlaceFinder.createDefault();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private boolean checkPlayServices() {
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (status != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(status)) {
                showErrorDialog(status);
            } else {
                Toast.makeText(this, "This device is not supported.", Toast.LENGTH_LONG).show();
                finish();
            }
            return false;
        }
        return true;
    }

    void showErrorDialog(int code) {
        GooglePlayServicesUtil.getErrorDialog(code, this,
                REQUEST_CODE_RECOVER_PLAY_SERVICES).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE_RECOVER_PLAY_SERVICES:
                if (resultCode == RESULT_CANCELED) {
                    Toast.makeText(this, "Google Play Services must be installed.",
                            Toast.LENGTH_SHORT).show();
                    finish();
                }
                return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (item.getItemId()) {
            case R.id.action_update:
                findPlaces();
                return true;
            case R.id.action_settings:
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    public void findPlaces() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new PlaceFinderTask().execute();
            }
        });
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if (places != null) {
            for (Place place : places) {
                if (place.name().equals(marker.getTitle())) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    StringBuilder sb = new StringBuilder(place.description());
                    if (place.cafeteriaDescription() != null) {
                        sb.append("<br/><b>Cafeteria:</b> " + place.cafeteriaDescription());
                    }
                    if (place.guideDescription() != null) {
                        sb.append("<br/><b>Guide:</b> " + place.guideDescription());
                    }
                    builder.setTitle(place.name()).setMessage(Html.fromHtml(sb.toString()));
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    return true;
                }
            }
        }
        return false;
    }

    private class PlaceFinderTask extends AsyncTask<String, Void, Set<Place>> {
        @Override
        protected Set<Place> doInBackground(String... params) {
            Log.i(getClass().getSimpleName(), "doInBackground: params=" + Arrays.asList(params));
            Set<Place> places = null;
            LatLong location = locationProvider.currentLocation();
            if (location != null) {
                try {
                    int searchRadius = SEARCH_RADIUS_METERS;
                    for (int i = 0; i < 5; i++) {
                        places = placeFinder.find(PlaceType.SUMMER_CHURCH, location.longitude, location.latitude, searchRadius);
                        Log.i(getClass().getSimpleName(), "doInBackground: searchRadius=" + searchRadius + ", places=" + places);
                        if (!places.isEmpty()) {
                            break;
                        }
                        searchRadius *= 2;
                    }
                } catch (IOException e) {
                    Log.e(getClass().getSimpleName(), "Error getting places from REST service", e);
                }
            }
            return places;
        }

        @Override
        protected void onPostExecute(Set<Place> places) {
            Log.i(getClass().getSimpleName(), "onPostExecute: places=" + places);
            MainActivity.this.places = places;
            if (places != null && !places.isEmpty()) {
                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                for (Place place : places) {
                    LatLng point = new LatLng(place.latitude(), place.longitude());
                    Marker marker = googleMap.addMarker(new MarkerOptions().position(point).title(place.name()));
                    builder.include(point);
                }
                googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 100));
            }
        }
    }
}
