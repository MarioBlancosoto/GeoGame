package com.example.mbs.geogame;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

public class ActivityL extends AppCompatActivity {
       private VideoView videoX;
       private Button btnR;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_l);
        String uriPath = "android.resource://" + getPackageName() + "/raw/" + "cry";
        Uri uri = Uri.parse(uriPath);
        btnR = (Button)findViewById(R.id.btnX);

        btnR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pantallaInicio = new Intent(ActivityL.this,Main2Activity.class);
                startActivity(pantallaInicio);
                finish();
            }
        });


        videoX = (VideoView) findViewById(R.id.vidL);
        videoX.setVideoURI(uri);
        videoX.requestFocus();
        videoX.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {


                videoX.start();

            }
        });
    }

}