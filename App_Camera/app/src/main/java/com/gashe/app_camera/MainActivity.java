package com.gashe.app_camera;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private String ruta_foto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pedirPermisos();

    }

    private Uri crearFicheroImagen(){
        // crear un fichero y obtener su uri
        Uri uri = null;
        String momento_actual = null;
        String nombre_fichero = null;
        File file = null;

        momento_actual = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        nombre_fichero = "fichero_" + momento_actual + ".jpg";

        ruta_foto = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getPath() + "/" +nombre_fichero;
        file = new File(ruta_foto);

        try {
            if(file.createNewFile()){
                Log.d(getClass().getCanonicalName(), "Fichero creado " + ruta_foto);
            }else{
                Log.d(getClass().getCanonicalName(), "Fichero no creado");
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(getClass().getCanonicalName(), "Error creando el fichero", e);
        }

        //uri.fromFile(file);
        uri = Uri.fromFile(file);

        return uri;
    }

    private void tomarFoto(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // si no especificamos un uri para almacenar la foto se hará automaticamente y se guardará en memoria en un bitmap
        Uri fotoUri = crearFicheroImagen();
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fotoUri);


        startActivityForResult(intent, 99);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.d(getClass().getCanonicalName(), "Vuelve de hacer la foto");

        switch (resultCode){
            case RESULT_OK:
                Log.d(getClass().getCanonicalName(), "El usuario ha guardado la foto correctamente");
                Bitmap bitmap = null;
                if(data == null){
                    // el fichero ha sido guardado en la ruta
                    File file = new File(ruta_foto);
                    bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());

                }else{
                    bitmap = (Bitmap) data.getExtras().get("data");
                }

                ImageView imageView = (ImageView) findViewById(R.id.myImage);
                imageView.setImageBitmap(bitmap);

                break;
            case RESULT_CANCELED:
                Log.d(getClass().getCanonicalName(), "El usuario ha cancelado la foto correctamente");
                break;
            default: Log.d(getClass().getCanonicalName(), "Ha ocurrido un error");
        }

    }

    private void pedirPermisos(){
        String [] permisos = new String[]{
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
        ActivityCompat.requestPermissions(this, permisos, 999);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if((grantResults[0] == PackageManager.PERMISSION_GRANTED) && (grantResults[1] == PackageManager.PERMISSION_GRANTED)){
            Log.d(getClass().getCanonicalName(), "PERMISOS CONCEDIDOS");
            tomarFoto();
        }else{
            Log.d(getClass().getCanonicalName(), "PERMISOS DENEGADOS");

        }
    }
}
