package com.example.PruebasManu;

import com.example.PruebasManu.database.contracts.Jugador;
import com.example.PruebasManu.database.JugadoresProvider;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;
import android.os.Environment;
import android.text.method.ScrollingMovementMethod;
import java.io.*;

public class MyActivity extends Activity {
    /**
     * Called when the activity is first created.
     */

    private EditText casillaId;
    private EditText casillaNombre;
    private Button botonConsultar;
    private Button botonInsertar;
    private Button botonModificar;
    private Button botonEliminar;
    private TextView etiquetaId;
    private TextView etiquetaNombre;
    private TextView txtResultados;

    private static final String TAG = "MyActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        //Referencias a los controles
        casillaId = (EditText)findViewById(R.id.casillaId);
        casillaNombre = (EditText)findViewById(R.id.casillaNombre);
        txtResultados = (TextView)findViewById(R.id.TxtResultados);
        txtResultados.setMovementMethod(new ScrollingMovementMethod());
        botonConsultar = (Button)findViewById(R.id.botonConsultar);
        botonInsertar = (Button)findViewById(R.id.botonInsertar);
        botonModificar = (Button)findViewById(R.id.botonModificar);
        botonEliminar = (Button)findViewById(R.id.botonEliminar);
        try {
            backupDatabase();
        } catch(IOException e) {
            e.printStackTrace();
        }

        botonConsultar.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                consultar();
            }
        });

        botonInsertar.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                ContentValues values = new ContentValues();
                String nombre = "";

                nombre = casillaNombre.getText().toString();

                values.put(Jugador.COL_NOMBRE, nombre);

                ContentResolver cr = getContentResolver();

                cr.insert(JugadoresProvider.CONTENT_URI, values);

                try {
                    backupDatabase();
                } catch(IOException e) {
                    e.printStackTrace();
                }
                //Consultamos y printeamos el contenido de la tabla Jugadores
                consultar();
            }
        });

        botonModificar.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                ContentValues values = new ContentValues();
                String cod = "";
                String nombre = "";

                //Recuperamos los valores de los campos de texto
                cod = casillaId.getText().toString();
                nombre = casillaNombre.getText().toString();

                values.put(Jugador.COL_NOMBRE, nombre);

                String where = "_ID="+cod;

                ContentResolver cr = getContentResolver();

                cr.update(JugadoresProvider.CONTENT_URI, values, where, null);

                try {
                    backupDatabase();
                } catch(IOException e) {
                    e.printStackTrace();
                }
                //Consultamos y printeamos el contenido de la tabla Jugadores
                consultar();
            }
        });

        botonEliminar.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                ContentResolver cr = getContentResolver();
                String cod = "";

                //Recuperamos los valores de los campos de texto
                cod = casillaId.getText().toString();

                //This defines a one-element String array to contain the selection argument.
                String[] mSelectionArgs = {""};

                // Moves the user's input string to the selection arguments.
                mSelectionArgs[0] = cod;

                cr.delete(JugadoresProvider.CONTENT_URI, "_ID=?", mSelectionArgs);

                try {
                    backupDatabase();
                } catch(IOException e) {
                    e.printStackTrace();
                }
                //Consultamos y printeamos el contenido de la tabla Jugadores
                consultar();
            }
        });

    }

    public void consultar() {
        //Columnas de la tabla a recuperar
        String[] projection = new String[] {
                Jugador._ID,
                Jugador.COL_NOMBRE };

        Uri jugadoresUri =  JugadoresProvider.CONTENT_URI;

        ContentResolver cr = getContentResolver();

        //Hacemos la consulta
        Cursor cur = cr.query(jugadoresUri,
                projection, //Columnas a devolver
                null,       //Condición de la query
                null,       //Argumentos variables de la query
                null);      //Orden de los resultados

        if (cur.moveToFirst())
        {
            String id;
            String nombre;

            int colId = cur.getColumnIndex(Jugador._ID);
            int colNombre = cur.getColumnIndex(Jugador.COL_NOMBRE);

            txtResultados.setText("");

            do
            {

                nombre = cur.getString(colNombre);
                id = cur.getString(colId);

                txtResultados.append("ID: " + id + "\t\t" + "Nombre: " + nombre + "\n");

            } while (cur.moveToNext());
        }
    }

    //Coloca en la memoria SD una copia del archivo de la base de datos
    public static void backupDatabase() throws IOException {
        //Open your local db as the input stream
        String inFileName = "/data/data/com.example.PruebasManu/databases/DBJugadores";
        File dbFile = new File(inFileName);
        FileInputStream fis = new FileInputStream(dbFile);

        String outFileName = Environment.getExternalStorageDirectory()
                + "/DBJugadores.sqlite";
        //Open the empty db as the output stream
        OutputStream output = new FileOutputStream(outFileName);
        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = fis.read(buffer))>0){
            output.write(buffer, 0, length);
        }
        //Close the streams
        output.flush();
        output.close();
        fis.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}