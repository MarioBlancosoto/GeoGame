package com.example.mbs.geogame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Instrucciones extends AppCompatActivity {
     Button botonVolver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instrucciones);

        botonVolver =(Button)findViewById(R.id.btnv);


        botonVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pantallaInicio = new Intent(Instrucciones.this,Main2Activity.class);
                startActivity(pantallaInicio);
                finish();
            }
        });



    }
}
