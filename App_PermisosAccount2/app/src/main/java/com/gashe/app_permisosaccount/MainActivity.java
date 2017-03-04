package com.gashe.app_permisosaccount;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.security.SecurityPermission;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {



    private String obtenerCorreosUsuario (){

        final SharedPreferences prefs = getSharedPreferences("pref", this.MODE_PRIVATE);

        String lista_correos = null;
        Account [] lista_cuentas = null;

        AccountManager accountManager = (AccountManager)getSystemService(ACCOUNT_SERVICE);
        lista_cuentas = accountManager.getAccounts();

        for (Account cuenta: lista_cuentas){

                Log.d(getClass().getCanonicalName(), "Cuenta correo: " + cuenta.name);

                lista_correos += cuenta.name + ",";

        }

        SharedPreferences.Editor editor_ =  prefs.edit();
        editor_.putString("cuentas", lista_correos);
        editor_.commit();


        return lista_correos;

    }

    private void pedirPermisos(){

        if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.GET_ACCOUNTS)){
            // Mostramos mensaje explicativo
        }

        ActivityCompat.requestPermissions(this, new String[]{ Manifest.permission.GET_ACCOUNTS }, 100);

    }

    boolean tienePermisos(){
        boolean tienePermiso = false;
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.GET_ACCOUNTS) == PackageManager.PERMISSION_GRANTED) tienePermiso = true;

        return  tienePermiso;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(tienePermisos()){

        }else{
            pedirPermisos();
        }

        Button button = (Button)findViewById(R.id.listarCuentas);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
                final SharedPreferences prefs = getSharedPreferences("pref", Context.MODE_PRIVATE);
                String cuentasString = prefs.getString("cuentas", "");
                String [] string_cuentas = cuentasString.split(",");

                ListAdapter listAdapter = new ArrayAdapter<String>(MainActivity.this, R.layout.fila, R.id.myText, string_cuentas);

                ListView listView = (ListView) findViewById(R.id.listadoCuentas);
                listView.setAdapter(listAdapter);



            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)){

            Log.d(getClass().getCanonicalName(), "Permiso concedido");
            obtenerCorreosUsuario();

        }else{

            Log.d(getClass().getCanonicalName(), "Permiso denegado");
            finish();

        }
    }


}
