package com.example.camera_video;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity3 extends AppCompatActivity {
    EditText email;
    EditText password;
    EditText nombre;
    Button aceptar;
    Button volver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        aceptar = findViewById(R.id.aceptar);
        volver = findViewById(R.id.volver);
        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gestionBaseDatos bd = new gestionBaseDatos(MainActivity3.this, "usuario", null, 1);
                Usuario usuario = new Usuario();
                email = findViewById(R.id.email);
                password = findViewById(R.id.password);
                nombre = findViewById(R.id.nombre);
                usuario.setNombre(nombre.getText().toString());
                usuario.setEmail(email.getText().toString());
                usuario.setPassword(password.getText().toString());
                if(email.getText().toString().length()==0 && password.getText().toString().length()==0 && nombre.getText().toString().length()==0) {
                    Toast.makeText(MainActivity3.this, "por favor rellene los campos", Toast.LENGTH_SHORT).show();
                }else if(email.getText().toString().length()==0 || password.getText().toString().length()==0 || nombre.getText().toString().length()==0) {
                    Toast.makeText(MainActivity3.this, "por favor rellene el campo vacio", Toast.LENGTH_SHORT).show();
                } else  if(bd.usuarioexiste(usuario.getEmail())==true) {
                    Toast.makeText(MainActivity3.this, "este correo ya está registrado", Toast.LENGTH_LONG).show();
                }
                 else if(bd.insertar(usuario)){
                    Toast.makeText(MainActivity3.this, "Usuario registrado correctamente vaya a login", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity3.this, "problema con el registro", Toast.LENGTH_SHORT).show();
                }

            }

        });
        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity3.this, "Ha vuelto a la página principal", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MainActivity3.this, MainActivity.class);
                startActivity(intent);

            }


        });
    }
}