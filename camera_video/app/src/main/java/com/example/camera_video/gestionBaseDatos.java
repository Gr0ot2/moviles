package com.example.camera_video;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.Nullable;

public class gestionBaseDatos extends SQLiteOpenHelper {
    String sql="CREATE TABLE usuarios (nombre TEXT,email TEXT, password TEXT)";

    public gestionBaseDatos(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS usuarios");
        sqLiteDatabase.execSQL(sql);
    }
    public Usuario selectUsuario(String email, String password){
        Usuario usuario=new Usuario();
        SQLiteDatabase bd = this.getReadableDatabase();
        if(bd!=null){
            try {
                Cursor c = bd.rawQuery("SELECT * FROM usuarios WHERE email = ? AND password = ?", new String[]{email, password});
                if (c.moveToFirst()) {
                    usuario.setEmail(c.getString(0));
                    usuario.setPassword(c.getString(1));
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        return usuario;
    }
    public boolean usuarioexiste(String email){
        boolean existe = false;
        SQLiteDatabase bd = this.getReadableDatabase();
        if(bd!=null){
            try {
                Cursor c = bd.rawQuery("SELECT * FROM usuarios WHERE email = ? ", new String[]{email});
                if (c.moveToFirst()) {
                    existe = true;
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        return existe;
    }

    public boolean contrasenacorrecta(String password){
        boolean pwd = false;
        SQLiteDatabase bd = this.getReadableDatabase();
        if(bd!=null){
            try {
                Cursor c = bd.rawQuery("SELECT * FROM usuarios WHERE password = ? ", new String[]{password});
                if (c.moveToFirst()) {
                    pwd = true;
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        return pwd;
    }


    public Usuario nombre(String email){
        Usuario usuario=new Usuario();
        SQLiteDatabase bd = this.getReadableDatabase();
        if(bd!=null){
            try {
                Cursor c = bd.rawQuery("SELECT nombre FROM usuarios WHERE email = ? ", new String[]{email});
                if (c.moveToFirst()) {
                    usuario.setNombre(c.getString(0));
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        return usuario;
    }

    public boolean insertar(Usuario usuario){
        boolean insertado = false;
        SQLiteDatabase sql_bd = this.getWritableDatabase();
        if(sql_bd!=null){
            SQLiteStatement statement = sql_bd.compileStatement ("INSERT INTO usuarios (nombre,email,password) VALUES (?,?,?);");
            statement.bindString(1, usuario.getNombre());
            statement.bindString(2, usuario.getEmail());
            statement.bindString(3, usuario.getPassword());

            statement.executeInsert();
            insertado = true;
        }
        return insertado;
    }


}
