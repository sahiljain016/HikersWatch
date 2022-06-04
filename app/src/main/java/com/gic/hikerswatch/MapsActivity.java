package com.gic.hikerswatch;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
//GLOBAL VARIABLES
    TextView latitude ;
    TextView longitude  ;
    TextView accuracy ;
    TextView altitude ;
    TextView address ;
    LocationManager locationManager;
    LocationListener locationListener;
    int lat;
    int lon;
    private GoogleMap mMap;
public void GDBUTTON(View view){
    latitude.animate().translationXBy(4000f).setDuration(2000);
    longitude.animate().translationXBy(4000f).setDuration(2000);
    altitude.animate().translationXBy(4000f).setDuration(2000);
    accuracy.animate().translationXBy(4000f).setDuration(2000);
    address.animate().translationXBy(4000f).setDuration(2000);

}
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

startListing();
}
    }

    public void startListing(){

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){

            locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        }


    }

    public void updateLocationInfo(Location location){

Log.i("bls", location.toString());
lat = (int) location.getLatitude();
 lon = (int) location.getLongitude();
latitude.setText("Latitude: "+ location.getLatitude());
longitude.setText("Longitude: "+ location.getLongitude());
accuracy.setText("Accuracy: "+ location.getAccuracy());
altitude.setText("Altitude "+ location.getAltitude());
        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        try {
            String Address = "Where are you? This location is not on earth!";
            List<Address> AddressList = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(),1);
if(AddressList != null && AddressList.size()>0){

Log.i("Place",AddressList.get(0).toString());
Address = "Address: \n";
if(AddressList.get(0).getSubThoroughfare() != null){
Address+= AddressList.get(0).getSubThoroughfare()+ "";
}
    if(AddressList.get(0).getThoroughfare() != null){
        Address+= AddressList.get(0).getThoroughfare() + "\n";
    }
    if(AddressList.get(0).getLocality() != null){
        Address+= AddressList.get(0).getLocality()+ "\n";
    }
    if(AddressList.get(0).getPostalCode() != null){
        Address+= AddressList.get(0).getPostalCode()+ "\n";
    }
    if(AddressList.get(0).getCountryName() != null){
        Address+= AddressList.get(0).getCountryName();
    }
}
address.setText(Address);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        latitude = (TextView) findViewById(R.id.Latitude);
        longitude = (TextView) findViewById(R.id.Longitude);
        altitude = (TextView) findViewById(R.id.Altitude);
        accuracy = (TextView) findViewById(R.id.Accruacy);
        address = (TextView) findViewById(R.id.Address);


        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
locationListener = new LocationListener() {
    @Override
    public void onLocationChanged(@NonNull Location location) {
updateLocationInfo(location);
    }
};

if(Build.VERSION.SDK_INT > 26){
startListing();
}
else{
    if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
    }
    else{

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if(location != null){

updateLocationInfo(location);

        }
    }

}
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.


    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        // Add a marker in Sydney and move the camera
        LatLng HikerLocation = new LatLng(22,44);
        mMap.addMarker(new MarkerOptions().position(HikerLocation).title("Marker in Sydney").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(HikerLocation, 10));
    }
}