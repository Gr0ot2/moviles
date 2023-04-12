package com.example.camera_video;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class MainActivity2 extends AppCompatActivity {
    Uri imagen;
    ImageView vista;
    VideoView video;
    Button camara;
    TextView name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Bundle extras= getIntent().getExtras();
        String datos =extras.getString("dato");
        camara = (Button)findViewById(R.id.camara);
        vista = (ImageView) findViewById(R.id.vista);
        video = (VideoView)findViewById(R.id.videoView);
        name= (TextView)findViewById(R.id.nombre);
        name.setText(datos);
        gestionBaseDatos db = new gestionBaseDatos(MainActivity2.this, "usuario", null, 1);
        camara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions(MainActivity2.this, new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    openCameraVideo();
                }
            }
        });
    }

    public void openCameraVideo(){

        ContentValues values = new ContentValues();
        imagen=getContentResolver().insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, values);
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imagen);
        resultLaunchervideo.launch(intent);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if(1 == requestCode) {
            if( (grantResults[0] == PackageManager.PERMISSION_GRANTED)  && grantResults[1]== PackageManager.PERMISSION_GRANTED){

                Toast.makeText(this, "Permisos correctos ", Toast.LENGTH_LONG).show();
                openCameraVideo();

            } else {
                Toast.makeText(this, "Permisos incorrectos" , Toast.LENGTH_LONG).show();
                Intent intent=new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }
        }else{
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


    ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode()== Activity.RESULT_OK){
                        InputStream inputStream = null;
                        try {
                            inputStream = getContentResolver().openInputStream(imagen);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        Drawable drawable = Drawable.createFromStream(inputStream, imagen.toString());
                        vista.setImageDrawable(drawable);

                    }
                }
            });

    ActivityResultLauncher<Intent> resultLaunchervideo = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode()==RESULT_OK){
                        video.setVideoURI(imagen);
                        video.start();

                    }
                }
            });
}