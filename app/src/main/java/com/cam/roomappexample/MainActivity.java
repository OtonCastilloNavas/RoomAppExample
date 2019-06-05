package com.cam.roomappexample;

import android.arch.persistence.room.Room;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.cam.roomappexample.obj.Horario;
import com.cam.roomappexample.pojo.ClaseConHorario;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private List<ClaseConHorario> claseList = new ArrayList<>();
    private RvAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        claseList.addAll(DbHorario.getAppDatabase(this).claseDao().ObtenerTodo());
        final RecyclerView rvClases= findViewById(R.id.rvClases);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        rvClases.setLayoutManager(manager);
        adapter = new RvAdapter(claseList);
        adapter.setOnClickDeleteItemListener(new RvAdapter.OnClickDeleteItemListener() {
            @Override
            public void onItemClick(final ClaseConHorario clase, final int pos) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Desea eliminar "+ clase.getClase().getNombre() +"?");
                builder.setNegativeButton("No",null);
                builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DbHorario.getAppDatabase(MainActivity.this).claseDao().Borrar(clase.getClase());
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
            public void onItemClick(ClaseConHorario clase,int pos) {
                showForm(clase,pos);
            }
        });

        adapter.setOnClickItemListener(new RvAdapter.OnClickItemListener() {
            @Override
            public void onItemClick(final Clase clase, final int pos) {
               // Toast.makeText(MainActivity.this, clase.getNombre(), Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle(clase.getNombre());
                final View view = LayoutInflater.from(MainActivity.this)
                        .inflate(R.layout.dialog_horario,null,false);
                builder.setView(view);
                builder.setNegativeButton("cancelar",null);
                builder.setPositiveButton("guardar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText etLugar=view.findViewById(R.id.etLugar);
                        EditText etHora=view.findViewById(R.id.etHora);
                        EditText etDia=view.findViewById(R.id.etDia);

                        Horario horario = new Horario();
                        horario.setLugar(etLugar.getText().toString());
                        horario.setHora(etHora.getText().toString());
                        horario.setDia(etDia.getText().toString());
                        horario.setId_asignatura(clase.get_id());
                        try {
                            DbHorario.getAppDatabase(MainActivity.this).horarioDao().insertar(horario);
                            Toast.makeText(MainActivity.this, "Horario guardado!",
                                    Toast.LENGTH_SHORT).show();
                            claseList.get(pos).getHorarioList().add(horario);
                            adapter.notifyDataSetChanged();
                        }
                        catch (SQLiteConstraintException e)
                        {
                            Toast.makeText(MainActivity.this, "Error \n" +e.getMessage()
                                    , Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        rvClases.setAdapter(adapter);
    }

    private void showForm(final ClaseConHorario clase, final int pos)
    {
        LayoutInflater inflater= LayoutInflater.from(MainActivity.this);
        View view= inflater.inflate(R.layout.dialog_form_clase,null,false);
        final EditText etNombre=view.findViewById(R.id.etNombre);
        final EditText etCredito= view.findViewById(R.id.etCredito);
        if(pos>-1) {
            etCredito.setText(String.valueOf(clase.getClase().getCredito()));
            etNombre.setText(clase.getClase().getNombre());
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Clase");
        builder.setView(view);
        builder.setNegativeButton("cancelar",null);
        builder.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                clase.getClase().setNombre(etNombre.getText().toString());
                clase.getClase().setCredito(Integer.valueOf(etCredito.getText().toString()));
                if(pos>-1) {
                    try {
                        DbHorario.getAppDatabase(MainActivity.this).claseDao().Actualizar(clase.getClase());
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
                        Long id=DbHorario.getAppDatabase(MainActivity.this).claseDao().Insertar(clase.getClase());
                        clase.getClase().set_id(id.intValue());
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
        if(item.getItemId()==R.id.mn_agregar)
            showForm(new ClaseConHorario(),-1);
        else
        {
            Intent intent = new Intent(this,HorarioActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}