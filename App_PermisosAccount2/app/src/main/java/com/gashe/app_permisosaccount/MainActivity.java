package com.gashe.app_permisosaccount;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.security.SecurityPermission;

public class MainActivity extends AppCompatActivity {

    private String[] obtenerCorreosUsuario (){

        String [] lista_correos = null;
        Account [] lista_cuentas = null;

        AccountManager accountManager = (AccountManager)getSystemService(ACCOUNT_SERVICE);
        lista_cuentas = accountManager.getAccounts();

        for (Account cuenta: lista_cuentas){
            if(cuenta.type.equals("com.google")){
                Log.d(getClass().getCanonicalName(), "Cuenta correo: " + cuenta.name);
            }else{
                Log.d(getClass().getCanonicalName(), "Cuenta type: " + cuenta.type);
            }
        }


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

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)){

            Log.d(getClass().getCanonicalName(), "Permiso concedido");
            obtenerCorreosUsuario();

        }else{

            Log.d(getClass().getCanonicalName(), "Permiso denegado");
            finish();

        }
    }


}
