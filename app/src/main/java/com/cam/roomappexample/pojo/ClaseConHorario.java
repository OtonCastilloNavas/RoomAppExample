package com.cam.roomappexample.pojo;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Relation;

import com.cam.roomappexample.obj.Clase;
import com.cam.roomappexample.obj.Horario;

import java.util.ArrayList;
import java.util.List;

public class ClaseConHorario {

    @Embedded
    Clase clase;

    @Relation(parentColumn ="_id" ,
            entityColumn = "id_asignatura",
            entity = Horario.class)
    List<Horario> horarioList;

    public ClaseConHorario() {
        clase=new Clase();
        horarioList= new ArrayList<>();

    }

    public ClaseConHorario(Clase clase, List<Horario> horarioList) {
        this.clase = clase;
        this.horarioList = horarioList;
    }

    public Clase getClase() {
        return clase;
    }

    public void setClase(Clase clase) {
        this.clase = clase;
    }

    public List<Horario> getHorarioList() {
        return horarioList;
    }

    public void setHorarioList(List<Horario> horarioList) {
        this.horarioList = horarioList;
    }
}