package com.example.projeto1.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.projeto1.R;
import com.example.projeto1.functions.DataBase;
import com.example.projeto1.functions.Globals;
import com.example.projeto1.functions.Subject;

import java.util.ArrayList;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class Disciplinas extends AppCompatActivity {

    ArrayList<String> arraysubject, arrayid,arrayteacher;
    ArrayList<Double> arrayavg;
    ArrayList<Integer> arraycor;
    Subject subject;
    RecyclerView medias;

    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_disciplinas);
        onWindowFocusChanged(true);

        arraysubject = new ArrayList<>();
        arrayavg = new ArrayList<Double>();
        arrayid = new ArrayList<>();
        arraycor = new ArrayList<>();
        arrayteacher = new ArrayList<>();
        medias = findViewById(R.id.medias);

        ImageView backbutton = findViewById(R.id.backbutton17);
        backbutton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (Globals.globaldisciplina == 1){
                    Intent intent = new Intent(Disciplinas.this, Testes.class);
                    startActivity(intent);
                }else { Intent intent = new Intent(Disciplinas.this, Menu.class);
                startActivity(intent);
                }

            }

        });


        storeDataInArrays();
        subject = new Subject(Disciplinas.this,this, arraysubject, arrayavg, arrayid,arraycor,arrayteacher);

        medias.setLayoutManager(new LinearLayoutManager(Disciplinas.this));

        medias.setAdapter(subject);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();

                DataBase db = new DataBase(Disciplinas.this);

                db.deletesubject(arrayid.remove(position));

                recreate();

            }
        }).attachToRecyclerView(medias);


    }

    public void storeDataInArrays() {
        DataBase db = new DataBase(this);
        Cursor cursor = db.AvgSubject();
        while (cursor.moveToNext()) {

            arraysubject.add(cursor.getString(0));

            arrayavg.add(cursor.getDouble(15));

            arrayid.add(String.valueOf(cursor.getInt(3)));

            arraycor.add(cursor.getInt(1));

            arrayteacher.add(cursor.getString(4));

        }

    }
}
