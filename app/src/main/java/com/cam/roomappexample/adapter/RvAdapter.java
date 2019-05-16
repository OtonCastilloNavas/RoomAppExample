package com.cam.roomappexample.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.cam.roomappexample.R;
import com.cam.roomappexample.obj.Clase;

import java.util.List;

public class RvAdapter extends RecyclerView.Adapter<RvAdapter.ClaseHolder> {

    private List<Clase> claseList;

    private OnClickDeleteItemListener onClickDeleteItemListener;
    private OnClickEditItemListener onClickEditItemListener;

    public interface OnClickDeleteItemListener
    {
        void onItemClick(Clase clase, int pos);
    }

    public interface OnClickEditItemListener
    {
        void onItemClick(Clase clase, int pos);
    }

    public void setOnClickEditItemListener(OnClickEditItemListener onClickEditItemListener) {
        this.onClickEditItemListener = onClickEditItemListener;
    }

    public void setOnClickDeleteItemListener(OnClickDeleteItemListener onClickDeleteItemListener) {
        this.onClickDeleteItemListener = onClickDeleteItemListener;
    }

    public RvAdapter(List<Clase> claseList) {
        this.claseList = claseList;
    }

    @NonNull
    @Override
    public ClaseHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view= inflater.inflate(R.layout.item_clase,viewGroup,false);
        return new ClaseHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClaseHolder claseHolder, int i) {
        claseHolder.tvNombre.setText(claseList.get(i).getNombre());
        claseHolder.tvCredito.setText(String.valueOf(claseList.get(i).getCredito()));
    }

    @Override
    public int getItemCount() {
        return claseList.size();
    }

    public class ClaseHolder extends RecyclerView.ViewHolder
    {
        private TextView tvNombre;
        private TextView tvCredito;
        private Button btBorrar;
        private Button btEditar;

        public ClaseHolder(@NonNull View itemView) {
            super(itemView);
            tvCredito=itemView.findViewById(R.id.tvCredito);
            tvNombre=itemView.findViewById(R.id.tvNombre);
            btBorrar= itemView.findViewById(R.id.btBorrar);
            btBorrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickDeleteItemListener.onItemClick(claseList.get(getAdapterPosition()), getAdapterPosition());
                }
            });

            btEditar=  itemView.findViewById(R.id.btEditar);

            btEditar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickEditItemListener.onItemClick(claseList.get(getAdapterPosition()),getAdapterPosition());
                }
            });
        }
    }
}