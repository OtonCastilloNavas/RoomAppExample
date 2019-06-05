package com.cam.roomappexample.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.cam.roomappexample.dao.ClaseDao;
import com.cam.roomappexample.dao.HorarioDao;
import com.cam.roomappexample.obj.Clase;
import com.cam.roomappexample.obj.Horario;

@Database(entities = {Clase.class, Horario.class},version = 1)
public abstract class DbHorario extends RoomDatabase {
    public abstract ClaseDao claseDao();
    public abstract HorarioDao horarioDao();
}
