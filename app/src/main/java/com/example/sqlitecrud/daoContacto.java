package com.example.sqlitecrud;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class daoContacto {
    SQLiteDatabase bd;//Instancia de la base de datos

    ArrayList<Contacto>lista = new ArrayList<Contacto>();// Almacena una lista de objetos de tipo contacto

    Contacto c;//Contacto de la aplicacion
    Context ct;//contexto de la aplicacion
    String nombreBD = "BDContactos";//Nombre de la base de datos

    //Cadena de sentencia sql que crea la tabla contacto, si no existe, define las columnas de las tablas

    String tabla = "create table if not exists contacto(id integer primary key autoincrement, nombre text, apellido text, email text, telefono text, ciudad text)";

    public daoContacto(Context c){
        //Constructor de la clase, acepta un parametro de tipo contect y ultiza para inicializar la clase
        this.ct=c;
        bd=c.openOrCreateDatabase(nombreBD, Context.MODE_PRIVATE,null);
        bd.execSQL(tabla);
    }
    public boolean insertar(Contacto c){
        //metodo para insertar un nuevo contacto en la base de datos
        ContentValues contenedor = new ContentValues();
        contenedor.put("nombre", c.getNombre());
        contenedor.put("apellido", c.getApellido());
        contenedor.put("email", c.getEmail());
        contenedor.put("telefono", c.getTelefono());
        contenedor.put("ciudad", c.getCiudad());
        return (bd.insert("contacto", null, contenedor))>0;
    }
    public boolean eliminar(int id){
        //metodo para elminar el contacto de la BD segun el id proporcionado.
        return (bd.delete("contacto","id="+id,null))>0;
    }
    public boolean editar(Contacto c){
        //metodo para actualizar la informacion de un contacto en la base de datos
        ContentValues contenedor = new ContentValues();
        contenedor.put("nombre", c.getNombre());
        contenedor.put("apellido", c.getApellido());
        contenedor.put("email", c.getEmail());
        contenedor.put("telefono", c.getTelefono());
        contenedor.put("ciudad", c.getCiudad());
        return (bd.update("contacto", contenedor,"id="+c.getId(),null))>0;
    }

    public ArrayList<Contacto>verTodo() {
        //Metodo para recuperar una lista de contacto desde la base de datos
        lista.clear();
        Cursor cursor = bd.rawQuery("select * from contacto",null);
        if (cursor!=null && cursor.getCount()>0){
            cursor.moveToFirst();
            do {
                lista.add(new Contacto(cursor.getInt(0),
                        cursor.getString(1), cursor.getString(2),
                        cursor.getString(3), cursor.getString(4),
                        cursor.getString(5)));
            }while (cursor.moveToNext());
        }
        return lista;
    }
    public Contacto verUno(int posicion){
        //metodo para buscar el contacto segun su id en la base de datos y devolverlo como un objeto contacto
        Cursor cursor = bd.rawQuery("select * from contacto", null);
        cursor.moveToPosition(posicion);
        c=new Contacto(cursor.getInt(0),
                cursor.getString(1),cursor.getString(2),
                cursor.getString(3),cursor.getString(4),
                cursor.getString(5));
        return c;
    }
}
