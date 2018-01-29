package com.example.mbs.geogame;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class SimpleScanner extends AppCompatActivity implements ZXingScannerView.ResultHandler{

    private ZXingScannerView mScannerView;
    static final String TAG = "escaneoQR";
    private static final int MY_PERMISSIONS = 1 ;
    private int aux;
    Intent intentActual;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_scanner);

        intentActual=this.getIntent();

        mScannerView = new ZXingScannerView(this);

        setContentView(mScannerView);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                             != PackageManager.PERMISSION_GRANTED) {
                                // Should we show an explanation?
                                      if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                                      Manifest.permission.CAMERA)) {
                               // Show an expanation to the user *asynchronously* -- don't block
                                        // this thread waiting for the user's response! After the user
                                               // sees the explanation, try again to request the permission.
                                                  } else {
                              // No explanation needed, we can request the permission.
                        ActivityCompat.requestPermissions(this,
                                                      new String[]{Manifest.permission.CAMERA},
                                                       MY_PERMISSIONS);
                               // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                        // app-defined int constant. The callback method gets the
                                               // result of the request.
                                      }
                    }


    }




    public void onResume() {
               super.onResume();
               mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
               mScannerView.startCamera();          // Start camera on resume
    }

    @Override
    public void handleResult(Result result) {

        mScannerView.resumeCameraPreview(this);
               // Instancio el Intent para mandar el resultado del Qr
               Intent intento= new Intent();
               intento.putExtra("retorno", result.getText() );

               intento.putExtra("contador",intentActual.getExtras().getInt("contador")+1);

               setResult(RESULT_OK, intento);
               // cierro la cámara
                      finish();

    }
}
