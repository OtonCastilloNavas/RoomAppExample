package com.cam.roomappexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.cam.roomappexample.adapter.RvAdapHorario;
import com.cam.roomappexample.db.DbHorario;
import com.cam.roomappexample.pojo.HorarioConClase;

import java.util.ArrayList;
import java.util.List;

public class HorarioActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horario);
        RecyclerView rvHorario = findViewById(R.id.rvHorarios);
        List<HorarioConClase> horarioConClases = new ArrayList<>();
        horarioConClases.addAll(DbHorario.getAppDatabase(this).horarioDao().obtenerConClase());
        LinearLayoutManager manager = new LinearLayoutManager(this);
        rvHorario.setLayoutManager(manager);
        RvAdapHorario adapHorario = new RvAdapHorario(horarioConClases);
        rvHorario.setAdapter(adapHorario);
    }
}
