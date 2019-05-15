package com.cam.roomappexample;

import android.arch.persistence.room.Room;
import android.database.sqlite.SQLiteConstraintException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.cam.roomappexample.db.DbHorario;
import com.cam.roomappexample.obj.Clase;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DbHorario db= Room.databaseBuilder(getApplicationContext(),
                DbHorario.class, "horario").allowMainThreadQueries().build();
        try {
            Clase clase= new Clase("Android",2);
            clase.set_id(2);
            Long i = db.claseDao().Insertar(clase);
            Toast.makeText(this, "cantidad " + i, Toast.LENGTH_SHORT).show();
        }
        catch (SQLiteConstraintException e)
        {
            Toast.makeText(this, "Error al insertar", Toast.LENGTH_SHORT).show();
        }

    }
}
