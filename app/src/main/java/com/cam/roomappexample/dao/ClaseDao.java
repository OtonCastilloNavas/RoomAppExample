package com.cam.roomappexample.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.cam.roomappexample.obj.Clase;

import java.util.List;

@Dao
public interface ClaseDao {

    @Insert
    List<Long> InsertarTodo(Clase... clases);

    @Delete
    void Borrar(Clase clase);

    @Update
    void Actualizar(Clase clase);

    @Insert
    Long Insertar(Clase clase);

    @Query("SELECT * FROM clase")
    List<Clase> ObtenerTodo();

    @Query("SELECT * from clase where nombre=:nombre")
    List<Clase> ObtenerPorNombre(String nombre);

}
