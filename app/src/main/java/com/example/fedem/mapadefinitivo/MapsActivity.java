package com.example.fedem.mapadefinitivo;

import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity
        extends FragmentActivity
        implements OnMapReadyCallback, View.OnClickListener, View.OnLongClickListener,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnMarkerClickListener, GoogleMap.OnMapLongClickListener{

    private GoogleMap mMap;
    private TextView tvCords;
    private ImageButton imgBoton;
    private Double latitude;
    private Double longitude;
    private GoogleApiClient googleApiClient;
    private LatLng terminalGalvez = new LatLng(-32.030610,-61.223729);
    private LatLng terminalStf = new LatLng(-31.613249,-60.700407);
    private LatLng muniGalvez = new LatLng(-32.029369,-61.224513);
    private LatLng bancoNacionGalvez = new LatLng(-32.030296,-61.222229);
    private LatLng utn = new LatLng(-31.616946,-60.67308);
    Button button ;
    ImageButton imgButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        addComponentes();
        addGoogleApi();
    }
    private void addLiseners(){
        mMap.setOnMarkerClickListener(this);
        mMap.setOnMapLongClickListener(this);

    }
    /**
     * Para agregar los botones o toda esa fruta... En proceso
     */
    private void addComponentes() {
        RelativeLayout.LayoutParams params =  new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        //LinearLayout.LayoutParams params =  new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
       //params.setMargins(40,10,10,10); //left,top,righ,down

        button = new Button(this);
        button.setText("Boton Loco");
        button.setOnClickListener(this);
        addContentView(button, params);

        imgButton = new ImageButton(this);
        imgButton.setImageResource(R.drawable.ic_cast_grey);
        imgButton.setBackgroundColor(Color.TRANSPARENT);
        imgButton.setOnClickListener(this);
       // addContentView(imgButton,params);


    }

    /**
     * Agrega la Api de Google
     */
    private void addGoogleApi() {
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .build();

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
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        addLiseners();
    }

    /**
     * Precargo lugares de interes
     */
    private void addLugaresInteres(){
        //Acomodar para que no serpita, solo para test...
        mMap.clear();
        mMap.addMarker(new MarkerOptions()
                .position(terminalGalvez) //Pongo el lugar
                .title("Terminal de la Ciudad de Galvez")) //Le meto titulo
                .setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
        mMap.addMarker(new MarkerOptions()
                .position(terminalStf) //Pongo el lugar
                .title("Terminal de la Ciudad de Santa Fe")) //Le meto titulo
                .setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN));
         mMap.addMarker(new MarkerOptions()
                 .position(muniGalvez) //Pongo el lugar
                 .title("Municipalidad de la Ciudad de Galvez")) //Le meto titulo
                 .setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
         mMap.addMarker(new MarkerOptions()
                 .position(bancoNacionGalvez) //Pongo el lugar
                 .title("Banco Nacion")) //Le meto titulo
                 .setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
         mMap.addMarker(new MarkerOptions()
                 .position(utn) //Pongo el lugar
                 .title("Tecnologica")) //Le meto titulo
                 .setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));

    }
    //Agarrar donde esta
    private void getCurrentLocation() {
/**
 * Esta fruta la tiro aumatica android para los permisos de Location
 * ------------------------------------------------------------------------------------------------
 */
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
/**
 *-------------------------------------------------------------------------------------------------
 */
        Location location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        if (location != null) {
            longitude = location.getLongitude(); //Guardo la longitud en la global
            latitude = location.getLatitude();  //Guardo la latitud en la global

            LatLng latLng = new LatLng(latitude, longitude);
            //Mover el mapa
            addLugaresInteres();
            moveMap(latLng);
        }
    }


    //Funcion mover el mapa, resive LatLng (var de android que tiene latitud y longitud)
    private void moveMap(LatLng latLng) {

        String msg = latitude + ", "+longitude;

        //Marco el mapa
        mMap.addMarker(new MarkerOptions()
                .position(latLng) //Pongo el lugar
                .anchor(0.0f, 1.0f)
                .title("Aca estas vos gato")) //Le meto titulo
                .setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));

        //Muevo la camara
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

        //Le doy zoom
         mMap.animateCamera(CameraUpdateFactory.zoomTo(15f));


        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }


    @Override
    public void onClick(View v) {
        if(v.getId() == button.getId()){
            String msg = "Tocaste Boton Loco";
            Toast.makeText(this, msg, Toast.LENGTH_LONG).show();

        }

    }

    @Override
    public boolean onLongClick(View v) {

        return false;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        getCurrentLocation(); //Agarro donde anda
    }

    @Override
    public void onConnectionSuspended(int i) {    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        MarkerOptions mark = new MarkerOptions();
        mark.position(latLng);
        mark.title("Agregada por clicklargo");
           // mMap.addMarker(mark).setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE));
        mMap.addMarker(mark).setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_parking));

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if(marker.getPosition() == muniGalvez){


        }
        return false;
    }

/**
 * Para Arrancar y parar la API
 * -------------------------------------------------------------------------------------------------
 */
    @Override
    protected void onStart() {        googleApiClient.connect();        super.onStart();     }
    @Override
    protected void onStop() {        googleApiClient.disconnect();        super.onStop();     }
/**
 * -------------------------------------------------------------------------------------------------
 */
}
