package com.example.mbs.geogame;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Looper;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class GeoGame extends FragmentActivity implements OnMapReadyCallback {
    private static final int LOCATION_REQUEST_CODE = 1;
    private final static int codigo = 0;
    private int aux =0;
    private GoogleMap mMap;
    private LocationManager lmanager;
    private int contador =0;
    private static final String FORMAT = "%02d:%02d:%02d";
    private int tesoro1 =0;
    private int tesoro2 =0;
    private int tesoro3 =0;
    private int lecturasqr =0;
    ArrayList<String> listOfBarcode;
    Button botonComenzar ;
    TextView textoGps;
    TextView tiempo;
    LinearLayout colorFondo;
    Intent intentActual;
    VideoView vcofre;
    //Localizaciones Areas tesoros
    LatLng rotondaRosalia = new LatLng(42.23787355754216, -8.714309334754944);
    LatLng capilla = new LatLng(42.23679812397819, -8.717962503433228);
    LatLng telepizza = new LatLng(42.23674243457500, -8.712683245539665);
    //Localizaciones tesoros
    LatLng tesoroRotonda = new LatLng(42.238035,8.714185);
    LatLng tesoroCapilla = new LatLng(42.236561,8.718081);
    LatLng tesoroTelepizza = new LatLng(42.236562,8.712618);
    double latesoroRotonda = 42.238035;
    double lontesoroRotonda = -8.714185;
    double latesoroCapilla = 42.23679812397819;
    double lontesoroCapilla = -8.717962503433228;
    double latesoroTelepizza = 42.23674243457500;
    double lontesoroTelepizza = -8.712683245539665;

    Location locTesoroRotonda = new Location("Tesoro Rotonda");
    Location locTesoroCapilla = new Location("Tesoro Capilla");
    Location locTesoroTelepizza = new Location("Tesoro Telepizza");

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_textviews);
        botonComenzar = (Button)findViewById(R.id.botonInicio);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        textoGps = (TextView) findViewById(R.id.textoGps);
        colorFondo = (LinearLayout) findViewById(R.id.loMapa);
        vcofre = (VideoView) findViewById(R.id.videoCofre);
        String uriPath = "android.resource://" + getPackageName() + "/raw/" + "video";
        Uri uri = Uri.parse(uriPath);
        vcofre.setVideoURI(uri);
        vcofre.requestFocus();
        intentActual=this.getIntent();
        tiempo = (TextView) findViewById(R.id.tiempo);



        new CountDownTimer(2696000, 1000) {

            @SuppressLint({"DefaultLocale", "SetTextI18n"})
            public void onTick(long millisUntilFinished) {

                tiempo.setText("Tiempo Restante :"+String.format(FORMAT,
                        TimeUnit.MILLISECONDS.toHours(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(
                                TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                                TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
            }

            @SuppressLint("SetTextI18n")
            public void onFinish() {
                                Intent pantallaLoose = new Intent(GeoGame.this, ActivityL.class);
                startActivity(pantallaLoose);
                tiempo.setText("HAS PERDIDO!!!!");
                finish();
            }

        }.start();

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);


        lmanager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
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
        lmanager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, new LocationListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onLocationChanged(Location location) {

                double latRos = 42.23787355754216;
                double longRos = -8.714309334754944;
                double latCap = 42.23679812397819;
                double longCap = -8.717962503433228;
                double latTele = 42.23674243457500;
                double longTele =  -8.712683245539665;
                Location locTele = new Location("Circulo telepizza");
                locTele.setLongitude(longTele);
                locTele.setLatitude(latTele);
                Location locRosalia = new Location("Circulo Rosalia");
                locRosalia.setLatitude(latRos);
                locRosalia.setLongitude(longRos);
                Location locCapilla = new Location(("Circulo Capilla"));
                locCapilla.setLatitude(latCap);
                locCapilla.setLongitude(longCap);
                Location locActual = new Location("Mi localizacion");
                //Loc tesoros
                locTesoroRotonda.setLatitude(latesoroRotonda);
                locTesoroRotonda.setLongitude(lontesoroRotonda);
                locTesoroCapilla.setLatitude(latesoroCapilla);
                locTesoroCapilla.setLongitude(lontesoroCapilla);
                locTesoroTelepizza.setLatitude(latesoroTelepizza);
                locTesoroTelepizza.setLongitude(lontesoroTelepizza);
                locActual.setLongitude(location.getLongitude());
                locActual.setLatitude(location.getLatitude());

                float zoom = 16.0f;
                mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(locActual.getLatitude(),locActual.getLongitude())));

                MediaPlayer mp2 = MediaPlayer.create(getApplicationContext(), R.raw.tension);


                if(locActual.distanceTo(locTesoroRotonda)<10||locActual.distanceTo(locTesoroCapilla)<10||locActual.distanceTo(locTesoroTelepizza)<10){
                   // ( (Vibrator) getSystemService(VIBRATOR_SERVICE)).vibrate(VibrationEffect.createOneShot(3000,100));
                    colorFondo.setBackgroundColor(Color.parseColor("#FF0000"));
                    textoGps.setBackgroundColor(Color.parseColor("#FF0000"));
                    mp2.start();

                    textoGps.setText("Ya deberías poder verlo!!!");




                }else if(locActual.distanceTo(locTesoroRotonda)<20||locActual.distanceTo(locTesoroCapilla)<20||locActual.distanceTo(locTesoroTelepizza)<20){
                   // ( (Vibrator) getSystemService(VIBRATOR_SERVICE)).vibrate(VibrationEffect.createOneShot(6000,150));

                    colorFondo.setBackgroundColor(Color.parseColor("#FF8000"));
                    textoGps.setBackgroundColor(Color.parseColor("#FF8000"));

                    textoGps.setText("Te estás acercando!");
                }else if(locActual.distanceTo(locRosalia)<50||locActual.distanceTo(locCapilla)<50||locActual.distanceTo(locTele)<50){
                   // ( (Vibrator) getSystemService(VIBRATOR_SERVICE)).vibrate(VibrationEffect.createOneShot(9000,250));

                    textoGps.setText("Comienza a buscar!");
                    colorFondo.setBackgroundColor(Color.parseColor("#FFFF00"));
                    textoGps.setBackgroundColor(Color.parseColor("#FFFF00"));
                    mp2.start();


                }






            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        }, Looper.getMainLooper());

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


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);

        } else {
                // Solicitar permiso
                ActivityCompat.requestPermissions(
                        this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        LOCATION_REQUEST_CODE);
            }



        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setIndoorLevelPickerEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.setBuildingsEnabled(true);

        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);






        // Add a marker in Sydney and move the camera
        LatLng Vigo = new LatLng(42.2366, -8.71455160000005);
        mMap.addMarker(new MarkerOptions().position(Vigo).title("Marker in Vigo"));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(Vigo,18));





        Circle circle = mMap.addCircle(new CircleOptions().center(capilla).radius(50).strokeColor(Color.BLACK).fillColor(0x5500ff00).strokeWidth(2));
        Circle circle2 = mMap.addCircle(new CircleOptions().center(rotondaRosalia).radius(50).strokeColor(Color.BLACK).fillColor(0x5500ff00).strokeWidth(2));
        Circle circle3 = mMap.addCircle(new CircleOptions().center(telepizza).radius(50).strokeColor(Color.BLACK).fillColor(0x5500ff00).strokeWidth(2));

        botonComenzar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pantallaQR= new Intent(GeoGame.this, SimpleScanner.class);

                if(intentActual.getExtras()!=null){
                    System.out.println("get Extras?"+pantallaQR.getExtras());
                    pantallaQR.putExtra("contador",intentActual.getExtras().getInt("contador"));
                }else{
                    pantallaQR.putExtra("contador",0);
                }

                startActivityForResult(pantallaQR,codigo);



                //LatLng Vigo = new LatLng(42.2366, -8.71455160000005);




            }
        });

    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Comprobamos si el resultado de la segunda actividad es "RESULT_OK".
        if (resultCode == RESULT_OK) {
            // Comprobamos el codigo de nuestra llamada
            if (requestCode == codigo) {
               // contador de intents intentActual.putExtra("contador",data.getExtras().getInt("contador"));

                String qrvalidate = data.getExtras().getString("retorno");

                if(qrvalidate.contains("primer tesoro")){

                    this.videoCofre();
                    textoGps.setText(qrvalidate);
                    tesoro1++;
                    lecturasqr++;


                    if(tesoro1>1){
                        vcofre.setVisibility(View.GONE);
                     lecturasqr--;
                     textoGps.setText("No seas tramposo!!!");
                    }

                }else if(qrvalidate.contains("segundo tesoro")){
                    this.videoCofre();
                    textoGps.setText(qrvalidate);
                    tesoro2++;
                    lecturasqr++;


                    if(tesoro2>1){
                      lecturasqr--;
                        textoGps.setText("No seas tramposo!!!");
                     vcofre.setVisibility(View.GONE);
                    }

                }else if(qrvalidate.contains("tercer tesoro")){

                    this.videoCofre();
                    textoGps.setText(qrvalidate);
                    tesoro3++;
                    lecturasqr++;

                    if(tesoro3>1){
                    lecturasqr--;
                        vcofre.setVisibility(View.GONE);
                        textoGps.setText("No seas tramposo!!!");
                    }

                }else{
                    vcofre.setVisibility(View.GONE);
                    textoGps.setText("Tesoro no reconocido");
                }
                if(lecturasqr==3){

                    Intent pantallaFinal = new Intent(GeoGame.this, Final.class);
                    startActivity(pantallaFinal);
                    finish();


                }



            }
        }
    }

    public void videoCofre(){
        vcofre.start();
        vcofre.setVisibility(View.VISIBLE);




        vcofre.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                vcofre.setVisibility(View.GONE);
                vcofre.stopPlayback();
                return false;
            }
        });

    }


}
