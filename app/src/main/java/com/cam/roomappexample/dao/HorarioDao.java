package com.cam.roomappexample.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.cam.roomappexample.obj.Horario;
import com.cam.roomappexample.pojo.HorarioConClase;

import java.util.List;

@Dao
public interface HorarioDao {
    @Insert
    Long insertar(Horario horario);

    @Delete
    void borrar(Horario horario);

    @Update
    void actualizar(Horario horario);

    @Query("Select * from horario")
    List<Horario> obtenerTodo();

    @Query("select * from horario where lugar=:lugar")
    List<Horario> obtenerPorLugar(String lugar);

    @Query("Select * from horario inner join clase on _id=id_asignatura")
    List<HorarioConClase> obtenerConClase();

}





