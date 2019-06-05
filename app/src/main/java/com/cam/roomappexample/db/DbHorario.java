package com.cam.roomappexample.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.cam.roomappexample.dao.ClaseDao;
import com.cam.roomappexample.dao.HorarioDao;
import com.cam.roomappexample.obj.Clase;
import com.cam.roomappexample.obj.Horario;

@Database(entities = {Clase.class, Horario.class},version = 1)
public abstract class DbHorario extends RoomDatabase {
    private static DbHorario INSTANCE;
    public abstract ClaseDao claseDao();
    public abstract HorarioDao horarioDao();
    public static DbHorario getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), DbHorario.class, "db")
                            .allowMainThreadQueries()
                            .build();
        }
        return INSTANCE;
    }
    public static void destroyInstance() {
        INSTANCE = null;
    }
}