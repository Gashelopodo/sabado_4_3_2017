package com.gashe.app_subactivities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(this, SubActivity.class);
        startActivityForResult(intent, 55);
        Log.d(getClass().getCanonicalName(), "SUBACTIVIDAD LANZADA");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.d(getClass().getCanonicalName(), "CODIGO PETICION: " + requestCode + " CODIGO RESPUESTA: " + resultCode + " NOMBRE: " + data.getStringExtra("NAME"));

        //super.onActivityResult(requestCode, resultCode, data);
    }
}
