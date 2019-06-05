package com.cam.roomappexample.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cam.roomappexample.R;
import com.cam.roomappexample.obj.Horario;
import com.cam.roomappexample.pojo.HorarioConClase;

import java.util.List;

public class RvAdapHorario extends RecyclerView.Adapter<RvAdapHorario.HorarioHolder> {

    private List<HorarioConClase> horarioList;

    public RvAdapHorario(List<HorarioConClase> horarioList) {
        this.horarioList = horarioList;
    }

    @NonNull
    @Override
    public HorarioHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_horario,viewGroup,false);
        return new HorarioHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HorarioHolder horarioHolder, int i) {

        horarioHolder.tvClase.setText(horarioList.get(i).getClase().getNombre());
        horarioHolder.tvLugar.setText(horarioList.get(i).getHorario().getLugar());
        horarioHolder.tvHora.setText(horarioList.get(i).getHorario().getHora());
        horarioHolder.tvDia.setText(horarioList.get(i).getHorario().getDia());
    }

    @Override
    public int getItemCount() {
        return horarioList.size();
    }

    public class HorarioHolder extends RecyclerView.ViewHolder
    {
        TextView tvLugar;
        TextView tvDia;
        TextView tvHora;
        TextView tvClase;

        public HorarioHolder(@NonNull View itemView) {
            super(itemView);
            tvClase= itemView.findViewById(R.id.tvClase);
            tvDia= itemView.findViewById(R.id.tvDia);
            tvHora=itemView.findViewById(R.id.tvHora);
            tvLugar=itemView.findViewById(R.id.tvLugar);
        }
    }
}
