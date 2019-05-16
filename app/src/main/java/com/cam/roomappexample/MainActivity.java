package com.cam.roomappexample;

import android.arch.persistence.room.Room;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteConstraintException;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cam.roomappexample.adapter.RvAdapter;
import com.cam.roomappexample.db.DbHorario;
import com.cam.roomappexample.obj.Clase;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DbHorario db;
    private List<Clase> claseList = new ArrayList<>();
    private RvAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db= Room.databaseBuilder(getApplicationContext(),
                DbHorario.class, "horario").allowMainThreadQueries().build();
        claseList.addAll(db.claseDao().ObtenerTodo());
        final RecyclerView rvClases= findViewById(R.id.rvClases);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        rvClases.setLayoutManager(manager);
        adapter = new RvAdapter(claseList);
        adapter.setOnClickDeleteItemListener(new RvAdapter.OnClickDeleteItemListener() {
            @Override
            public void onItemClick(final Clase clase, final int pos) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Desea eliminar "+ clase.getNombre() +"?");
                builder.setNegativeButton("No",null);
                builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.claseDao().Borrar(clase);
                        claseList.remove(clase);
                        Toast.makeText(MainActivity.this, "Eliminado!", Toast.LENGTH_SHORT).show();
                        adapter.notifyItemRemoved(pos);
                       // adapter.notifyDataSetChanged();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        adapter.setOnClickEditItemListener(new RvAdapter.OnClickEditItemListener() {
            @Override
            public void onItemClick(Clase clase,int pos) {
                showForm(clase,pos);
            }
        });
        rvClases.setAdapter(adapter);
    }

    private void showForm(final Clase clase, final int pos)
    {
        LayoutInflater inflater= LayoutInflater.from(MainActivity.this);
        View view= inflater.inflate(R.layout.dialog_form_clase,null,false);
        final EditText etNombre=view.findViewById(R.id.etNombre);
        final EditText etCredito= view.findViewById(R.id.etCredito);
        if(pos>-1) {
            etCredito.setText(String.valueOf(clase.getCredito()));
            etNombre.setText(clase.getNombre());
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Clase");
        builder.setView(view);
        builder.setNegativeButton("cancelar",null);
        builder.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                clase.setNombre(etNombre.getText().toString());
                clase.setCredito(Integer.valueOf(etCredito.getText().toString()));
                if(pos>-1) {
                    try {
                        db.claseDao().Actualizar(clase);
                        adapter.notifyItemChanged(pos);
                    }
                    catch (SQLiteConstraintException e)
                    {
                        Toast.makeText(MainActivity.this, "Error al actualizar \n" +
                                e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    try {
                        db.claseDao().Insertar(clase);
                        claseList.add(0,clase);
                        adapter.notifyItemInserted(0);
                    }
                    catch (SQLiteConstraintException e)
                    {
                        Toast.makeText(MainActivity.this, "Error al insertar \n" +
                                e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
                Toast.makeText(MainActivity.this, "Guardado!", Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog dialog= builder.create();
        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        showForm(new Clase(),-1);
        return super.onOptionsItemSelected(item);
    }
}