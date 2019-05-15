package com.cam.roomappexample.obj;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Clase {
    @PrimaryKey
    private int _id;
    private String nombre;
    private int credito;

    public Clase() {
    }

    public Clase(String nombre, int credito) {
        this.nombre = nombre;
        this.credito = credito;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCredito() {
        return credito;
    }

    public void setCredito(int credito) {
        this.credito = credito;
    }
}
