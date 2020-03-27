package com.example.bikebookingapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainPageActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {

    FirebaseAuth auth;
    FirebaseUser user;
    String user_id;
    DatabaseReference reference;
    TextView t1, t2;
    private GoogleMap mMap;
    GoogleApiClient client;
    LatLng startLatLng, endLatLng;
    LocationRequest request;
    String name, email;

    Marker currentMarker;
    Marker destinationMarker;

    Button b4_sourceButton;
    Button b5_destinationButton;

    private AppBarConfiguration mAppBarConfiguration;

    private FusedLocationProviderClient test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        auth = FirebaseAuth.getInstance();
        Toolbar toolbar = findViewById(R.id.toolbar);
        b4_sourceButton = (Button) findViewById(R.id.button2);
        b5_destinationButton = (Button) findViewById(R.id.button4);
        setSupportActionBar(toolbar);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        user = FirebaseAuth.getInstance().getCurrentUser();
        if(user == null){
            Intent myintent = new Intent(MainPageActivity.this, MainActivity.class);
            startActivity(myintent);
            finish();
        } else {
            user_id = user.getUid();
            reference = FirebaseDatabase.getInstance().getReference().child("Users").child(user_id);

            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String name = dataSnapshot.child("name").getValue(String.class);
                    String email = dataSnapshot.child("email").getValue(String.class);

                    t1 = findViewById(R.id.name_text);
                    t2 = findViewById(R.id.email_text);

                    t1.setText(name);
                    t2.setText(email);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        b4_sourceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY).build(MainPageActivity.this);
                    startActivityForResult(intent, 200);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        b5_destinationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY).build(MainPageActivity.this);
                    startActivityForResult(intent, 400);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_page, menu);
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item){
        int id = item.getItemId();
        System.out.println("id = " + id);
        if( id == R.id.nav_payment){

        } else if (id == R.id.nav_trips) {

        } else if (id == R.id.nav_help) {

        } else if (id == R.id.nav_rides) {

        } else if (id == R.id.nav_signOut) {
            System.out.println("id = " + id);
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if(user != null) {
                auth.signOut();
                Intent myintent = new Intent( MainPageActivity.this, MainActivity.class);
                startActivity(myintent);
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "User is already signed out", Toast.LENGTH_LONG).show();
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        client = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        client.connect();
//
//        LatLng local = new LatLng(10.8345502,106.7540201);
//        mMap.addMarker(new MarkerOptions().position(local).title("Marker"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(local, 16));
    }

    @Override
    public void onLocationChanged(Location location) {
//        LocationServices.FusedLocationApi.removeLocationUpdates(client, (com.google.android.gms.location.LocationListener) this);
        if(location == null){
            Toast.makeText(getApplicationContext(), "Location could not be found", Toast.LENGTH_LONG).show();
        } else {
            startLatLng = new LatLng(location.getLatitude(), location.getLongitude());
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            try{
                List<Address> myaddress = geocoder.getFromLocation(startLatLng.latitude, startLatLng.longitude, 1);
                String address = myaddress.get(0).getAddressLine(0);
                String city = myaddress.get(0).getLocality();
                b4_sourceButton.setText(address + " " + city);
            } catch (IOException e){
                e.printStackTrace();
            }
            System.out.println("Current Position this");
            if(currentMarker == null){
                MarkerOptions options = new MarkerOptions();
                options.position(startLatLng);
                currentMarker = mMap.addMarker(options);
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(startLatLng, 15));
            } else {
                currentMarker.setPosition(startLatLng);
            }
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        request = new LocationRequest().create();
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        request.setInterval(5000);
        request.setFastestInterval(500);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        } else {
            LocationServices.FusedLocationApi.requestLocationUpdates(client, request, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        System.out.println("Current Position onConnectionSuspended ");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        System.out.println("Current Position onConnectionFailed ");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 200){
            if(resultCode == RESULT_OK){
                Place place = PlaceAutocomplete.getPlace(this, data);
                String name = place.getName().toString();
                startLatLng = place.getLatLng();

                b4_sourceButton.setText(name);
                System.out.println("startLatLng: " + startLatLng);
                if(currentMarker == null) {
                    MarkerOptions options = new MarkerOptions();
                    options.title("Pickup Location");
                    options.position(startLatLng);

                    currentMarker = mMap.addMarker(options);
                } else {
                    currentMarker.setPosition(startLatLng);
                }


            }
        } else if (requestCode == 400) {
            if(resultCode == RESULT_OK) {
                Place myPlace = PlaceAutocomplete.getPlace(this, data);
                String name = myPlace.getName().toString();
                endLatLng = myPlace.getLatLng();
                b5_destinationButton.setText(name);
                if(destinationMarker == null){
                    MarkerOptions options = new MarkerOptions();
                    options.title("Destination");
                    options.position(endLatLng);

                    destinationMarker = mMap.addMarker(options);
                } else {
                    destinationMarker.setPosition(endLatLng);
                }
            }
        }
    }
}
