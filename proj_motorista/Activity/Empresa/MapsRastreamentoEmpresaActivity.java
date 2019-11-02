package com.example.patrick.proj_motorista.Activity.Empresa;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.example.patrick.proj_motorista.Entidades.Localizacao;
import com.example.patrick.proj_motorista.Entidades.Viagens;
import com.example.patrick.proj_motorista.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;


public class MapsRastreamentoEmpresaActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private Marker currentLocationMaker;
    private LatLng currentLocationLatLong;
    private DatabaseReference mDatabase;
    private Viagens viagem;
    private Localizacao localizacao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_rastreamento_empresa);
        Intent intent = getIntent();
        viagem = (Viagens) intent.getSerializableExtra("viagem");
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        mDatabase = FirebaseDatabase.getInstance().getReference();
        getMarkers();


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

        // Add a marker in Sydney and move the camera
        LatLng fraiburgo = new LatLng(-27.0209102, -50.9221686);

        CameraPosition cameraPosition = new CameraPosition.Builder().zoom(12).target(fraiburgo).build();


        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    private void getMarkers() {

        if(viagem.getStatus().equals("em andamento")) {
            mDatabase.child("Localizacao").orderByChild("idViagem").equalTo(viagem.getId())
                    .addValueEventListener(
                            new ValueEventListener() {
                                Localizacao loc;
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    //Get map of users in datasnapshot
                                    for (DataSnapshot objSnapshot:dataSnapshot.getChildren()) {
                                        loc = objSnapshot.getValue(Localizacao.class);
                                        loc.setId(objSnapshot.getKey());
                                        getLocationsRealTime(loc);
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    //handle databaseError
                                }
                            });
        }
        else{
            mDatabase.child("Localizacao").orderByChild("idViagem").equalTo(viagem.getId())
                    .addValueEventListener(
                            new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    //Get map of users in datasnapshot
                                    if (dataSnapshot.getValue() != null)
                                        getAllLocations((Map<String, Object>) dataSnapshot.getValue());
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    //handle databaseError
                                }
                            });
        }
    }

    private void getAllLocations(Map<String, Object> locations) {


        for (Map.Entry<String, Object> entry : locations.entrySet()) {

            Date newDate = new Date(Long.valueOf(entry.getKey()));
            Map singleLocation = (Map) entry.getValue();
            LatLng latLng = new LatLng((Double) singleLocation.get("latitude"), (Double) singleLocation.get("longitude"));
            addGreenMarker(newDate, latLng);

        }


    }

    private void getLocationsRealTime(Localizacao loc){
        Date newDate = new Date(Long.valueOf(loc.getId()));
        double latitude = loc.getLatitude();
        double longitude = loc.getLongitude();
        LatLng latLng = new LatLng(latitude, longitude);
        addGreenMarker(newDate, latLng);
    }

    private void addGreenMarker(Date newDate, LatLng latLng) {
        SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title(dt.format(newDate));
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        mMap.addMarker(markerOptions);
    }


}