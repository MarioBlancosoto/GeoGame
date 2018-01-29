package com.example.mbs.geogame;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Slide;
import android.transition.TransitionInflater;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

public class Main2Activity extends AppCompatActivity {
    private VideoView video;
    private Button botonStart;
    private Button botonInst;
    private Button botonSalir;
    private Fragment inst;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        String nombreVideo = "mapa";
        final MediaPlayer mediaPlayer;
        final Intent pantallaMapa = new Intent(Main2Activity.this, GeoGame.class);



        botonStart = findViewById(R.id.btnInicio);
        botonInst = findViewById(R.id.btnInst);
        botonSalir = findViewById(R.id.btnSalir);
        String uriPath = "android.resource://" + getPackageName() + "/raw/" + nombreVideo;
        video = (VideoView) findViewById(R.id.video);
        final MediaPlayer mp1 = MediaPlayer.create(Main2Activity.this, R.raw.btnstart);




        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.musica);
        mediaPlayer.start();
        Uri uri = Uri.parse(uriPath);
        video.setVideoURI(uri);
        video.requestFocus();
        video.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
                video.start();
            }
        });


        botonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mp1.start();
                startActivity(pantallaMapa);


            }
        });

        botonInst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp1.start();

                Intent pantallainst = new Intent(Main2Activity.this, Instrucciones.class);
                startActivity(pantallainst);



            }
        });

        botonSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp1.start();
                onBackPressed();

            }
        });
    }
        public void onBackPressed() {
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Cerrando aplicación")
                    .setMessage("Estás seguro de que deseas salir? tu te lo pierdes...")
                    .setPositiveButton("Si", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            finish();

                        }

                    })
                    .setNegativeButton("No", null)
                    .show();
        }





    }

