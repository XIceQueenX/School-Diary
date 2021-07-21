package com.example.projeto1.activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;


import com.example.projeto1.functions.DataBase;
import com.example.projeto1.functions.Globals;
import com.example.projeto1.functions.TestesNotasMainRecyclerAdapter;
import com.example.projeto1.model.TesteModel;

import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projeto1.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestesNotasActivity extends AppCompatActivity {

    private Map<String, List<TesteModel>> testes = new HashMap();
    private ArrayList<Integer> cor;

    private RecyclerView recyclerView;
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);}
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testes_notas);

        onWindowFocusChanged(true);


        loadData();

        TestesNotasMainRecyclerAdapter mainRecyclerAdapter = new TestesNotasMainRecyclerAdapter(testes);

        recyclerView = findViewById(R.id.testes_notas_main_recycler_view);

        recyclerView.setAdapter(mainRecyclerAdapter);

        ImageView backbutton = findViewById(R.id.backbutton15);
        backbutton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(Globals.todosostestes == 1){
                    Intent intent = new Intent(TestesNotasActivity.this, Testes.class);
                    startActivity(intent);
                    finish();
                }else{
                    Intent intent = new Intent(TestesNotasActivity.this, Menu.class);
                    startActivity(intent);
                    finish();
                }

            }

        });

    }


    private void loadData() {

        DataBase db = new DataBase(TestesNotasActivity.this);
        Cursor c = db.readTeste();

        if(c != null && c.moveToFirst()) {

            do {

                int id = c.getInt(0);
                String title = c.getString(1);
                String date = c.getString(2);
                String disciplina = c.getString(6);
                String nota = c.getString(5);
                int color = c.getInt(7);



                TesteModel teste = new TesteModel();

                teste.setId(id);
                teste.setTitle(title);
                teste.setData(date);
                teste.setNota(nota);
                teste.setColor(color);


                List<TesteModel> aux = new ArrayList<>();

                if(testes.containsKey(disciplina)) {
                    aux = testes.get(disciplina);
                }

                aux.add(teste);
                testes.put(disciplina, aux);

            } while (c.moveToNext());
        }

    }
}