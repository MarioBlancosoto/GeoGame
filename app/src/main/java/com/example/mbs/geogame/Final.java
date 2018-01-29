package com.example.mbs.geogame;

import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.VideoView;

public class Final extends AppCompatActivity {
    private VideoView videoc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);
        String uriPath = "android.resource://" + getPackageName() + "/raw/" + "slowclap";
        Uri uri = Uri.parse(uriPath);



        videoc = (VideoView) findViewById(R.id.slowvid);
        videoc.setVideoURI(uri);
        videoc.requestFocus();
        videoc.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {


                videoc.start();
            }
        });

    }
}
