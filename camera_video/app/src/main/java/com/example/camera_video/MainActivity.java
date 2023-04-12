package com.example.camera_video;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText email;
    EditText password;
    Button aceptar;
    Button login;
    String nombre;
    ImageView imagen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        aceptar = findViewById(R.id.aceptar);
        login = findViewById(R.id.login);
        imagen = findViewById(R.id.imageView);
        imagen.setImageResource(R.drawable.art_134_01);

        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = findViewById(R.id.email);
                password = findViewById(R.id.password);
                gestionBaseDatos bd = new gestionBaseDatos(MainActivity.this, "usuario", null, 1);



                Usuario usuario ;
                usuario = bd.selectUsuario(email.getText().toString(), password.getText().toString());
                if (usuario != null) {
                    Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                    usuario.setEmail(email.getText().toString());
                    usuario.setPassword(password.getText().toString());
                    if(email.getText().toString().length()==0 && password.getText().toString().length()==0){
                        Toast.makeText(MainActivity.this, "por favor rellene los campos", Toast.LENGTH_SHORT).show();
                    }else if(email.getText().toString().length()==0 || password.getText().toString().length()==0){
                        Toast.makeText(MainActivity.this, "por favor rellene el campo vacio", Toast.LENGTH_SHORT).show();
                    }else if(bd.usuarioexiste(usuario.getEmail())==false){
                    Toast.makeText(MainActivity.this, "este correo no está registrado", Toast.LENGTH_LONG).show();
                        Intent intent1 = new Intent(MainActivity.this, MainActivity3.class);
                        startActivity(intent1);
                    }
                    else if(bd.contrasenacorrecta(usuario.getPassword())==false) {
                        Toast.makeText(MainActivity.this, "por favor inserte su contraseña correcta", Toast.LENGTH_SHORT).show();
                    }  else if (bd.usuarioexiste(email.getText().toString())) {
                        Toast.makeText(MainActivity.this, "Usuario registrado", Toast.LENGTH_LONG).show();
                        intent.putExtra("dato", new String(bd.nombre(usuario.getEmail()).getNombre()));
                        startActivityForResult(intent, 1);
                    }


                }
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Está en el login", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MainActivity.this, MainActivity3.class);
                startActivity(intent);
            }
        });
    }
}