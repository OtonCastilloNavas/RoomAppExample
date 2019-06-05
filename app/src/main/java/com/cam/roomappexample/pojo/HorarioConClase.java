package com.cam.roomappexample.pojo;

import android.arch.persistence.room.Embedded;

import com.cam.roomappexample.obj.Clase;
import com.cam.roomappexample.obj.Horario;

public class HorarioConClase {

    @Embedded
    Clase clase;

    @Embedded
    Horario horario;

    public Clase getClase() {
        return clase;
    }

    public void setClase(Clase clase) {
        this.clase = clase;
    }

    public Horario getHorario() {
        return horario;
    }

    public void setHorario(Horario horario) {
        this.horario = horario;
    }
}
