package com.example.ruansihan.ruangooglemaplocation;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends ActionBarActivity {

    //private GoogleMap mMap;
    //LatLng lk = new LatLng(25.034006, 121.564791);

    public GoogleMap googleMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        //SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        //mMap = mapFragment.getMap();
        //mMap.setMapType(mMap.MAP_TYPE_NORMAL);
        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lk, 18));
        //Marker mk = mMap.addMarker(new MarkerOptions().position(lk).title("Ruansihan").snippet("MeMe"));

        Intent i = new Intent(MapsActivity.this, LocationServiceManager.class);
        startService(i);

        Intent intent = new Intent("SystemState");
        sendBroadcast(intent);

        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getBaseContext());

        // Showing status
        if (status != ConnectionResult.SUCCESS) { // Google Play Services are
            // not available
            int requestCode = 10;
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this,
                    requestCode);
            dialog.show();

        } else { // Google Play Services are available

            // Getting reference to the SupportMapFragment of activity_main.xml
            SupportMapFragment fm = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);

            // Getting GoogleMap object from the fragment
            googleMap = fm.getMap();

            // Enabling MyLocation Layer of Google Map
            googleMap.setMyLocationEnabled(true);
            // Getting LocationManager object from System Service

            // LOCATION_SERVICE
            LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

            // Creating a criteria object to retrieve provider
            Criteria criteria = new Criteria();

            // Getting the name of the best provider
            String provider = locationManager.getBestProvider(criteria, true);

            // Getting Current Location
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            Location location = locationManager.getLastKnownLocation(provider);
            googleMap.animateCamera(CameraUpdateFactory.zoomTo(12));

        }
    }

    @SuppressLint("ShowToast")
    private void drawMarker(Intent i) {

        double lat;
        double lng;
        try {
            lat = i.getDoubleExtra("lat", 0);
            lng = i.getDoubleExtra("lng", 0);

            // googleMap.clear();

            if (lat != 0 && lng != 0) {
                LatLng ll = new LatLng(lat, lng);
                googleMap.addMarker(new MarkerOptions().position(ll));
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(ll));
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            return;
        }
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            drawMarker(intent);
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        registerReceiver(broadcastReceiver, new IntentFilter(
                LocationServiceManager.TAG));
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);
    }



    /**
     * public void onCity(View view){
     //CameraUpdate update = CameraUpdateFactory.newLatLng(lk);
     CameraUpdate update = CameraUpdateFactory.newLatLngZoom(lk,9);
     mMap.animateCamera(update);

     }

     public void onStop(View view){
     CameraUpdate update = CameraUpdateFactory.newLatLngZoom(lk,14);
     mMap.animateCamera(update);

     }

     public void onSurrey(View view){
     CameraUpdate update = CameraUpdateFactory.newLatLngZoom(lk,16);
     mMap.animateCamera(update);
     }

     @Override
     public void onMapReady(GoogleMap googleMap) {

     }
     */

}
