package com.example.projeto1.activity;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.projeto1.R;
import com.example.projeto1.functions.CustomAdapter;
import com.example.projeto1.functions.DataBase;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;



public class Tarefa extends AppCompatActivity {


    boolean clicked = false;

    RecyclerView recyclerview_tarefas;
    FloatingActionButton add_btn, btnaddmateria, btnaddtarefa;
    Animation rotateOpen, rotateClose, fromBottom, toBottom;

    DataBase db;
    ArrayList<String> arrayid, arraytitle, arraynotes, arraydate, arraysubject,arraydone;
    ArrayList<Integer> cor;
    CustomAdapter customAdapter;
    int n;
    ImageView imgnodata, backbutton;
    TextView nodata,totaltarefa,totaltodo;

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

    boolean b1 = false, b2 = false, b3 = false, b4 = false, b5 = false, b6 = false, b7 = false, edit = false;

    //@RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("WrongViewCast")
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        onWindowFocusChanged(true);
        setContentView(R.layout.activity_tarefa);

        rotateOpen = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_open_animate);
        rotateClose = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_close_animate);
        toBottom = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.to_bottom_animate);
        fromBottom = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.from_bottom_animate);

        Button btn1 = findViewById(R.id.btn1);
        TextView tvdata1 = findViewById(R.id.tvdata1);
        TextView tvdia1 = findViewById(R.id.tvdia1);

        Button btn2 = findViewById(R.id.btn2);
        TextView tvdata2 = findViewById(R.id.tvdata2);
        TextView tvdia2 = findViewById(R.id.tvdia2);

        Button btn3 = findViewById(R.id.btn3);
        TextView tvdata3 = findViewById(R.id.tvdata3);
        TextView tvdia3 = findViewById(R.id.tvdia3);

        Button btn4 = findViewById(R.id.btn4);
        TextView tvdata4 = findViewById(R.id.tvdata4);
        TextView tvdia4 = findViewById(R.id.tvdia4);

        Button btn5 = findViewById(R.id.btn5);
        TextView tvdata5 = findViewById(R.id.tvdata5);
        TextView tvdia5 = findViewById(R.id.tvdia5);
        Button btn6 = findViewById(R.id.btn6);
        TextView tvdata6 = findViewById(R.id.tvdata6);
        TextView tvdia6 = findViewById(R.id.tvdia6);

        Button btn7 = findViewById(R.id.btn7);
        TextView tvdata7 = findViewById(R.id.tvdata7);
        TextView tvdia7 = findViewById(R.id.tvdia7);

        backbutton = findViewById(R.id.backbutton);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //onBackPressed();
                Intent intent = new Intent(Tarefa.this, Menu.class);
                startActivity(intent);
            }
        });


        nodata = findViewById(R.id.nodata);
        imgnodata = findViewById(R.id.imgnodata);

        nodata.setVisibility(View.INVISIBLE);
        imgnodata.setVisibility(View.INVISIBLE);

        recyclerview_tarefas = findViewById(R.id.recyclerview_tarefas);
        add_btn = findViewById(R.id.add_btn);



        clicked = false;

        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (clicked) {

                    add_btn.startAnimation(rotateClose);
                    btnaddmateria.setClickable(false);
                    btnaddtarefa.setClickable(false);

                    btnaddmateria.startAnimation(toBottom);
                    btnaddtarefa.startAnimation(toBottom);

                    clicked = false;
                } else {
                    add_btn.startAnimation(rotateOpen);
                    btnaddmateria.setClickable(true);
                    btnaddtarefa.setClickable(true);

                    btnaddmateria.startAnimation(fromBottom);
                    btnaddtarefa.startAnimation(fromBottom);

                    clicked = true;
                }
            }
        });

        btnaddtarefa = findViewById(R.id.btnaddtarefa);
        btnaddtarefa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pagtarefa = new Intent(Tarefa.this, AddTarefas.class);
                startActivity(pagtarefa);
                add_btn.startAnimation(rotateClose);
                btnaddmateria.setClickable(false);
                btnaddtarefa.setClickable(false);

                btnaddmateria.startAnimation(toBottom);
                btnaddtarefa.startAnimation(toBottom);

                clicked = false;
            }
        });

        btnaddmateria = findViewById(R.id.btnaddmateria);
        btnaddmateria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent disciplina = new Intent(Tarefa.this, AddDisciplina.class);
                startActivity(disciplina);
                add_btn.startAnimation(rotateClose);
                btnaddmateria.setClickable(false);
                btnaddtarefa.setClickable(false);

                btnaddmateria.startAnimation(toBottom);
                btnaddtarefa.startAnimation(toBottom);

                clicked = false;
            }
        });

        db = new DataBase(Tarefa.this);
        arrayid = new ArrayList<>();
        arraytitle = new ArrayList<>();
        arraynotes = new ArrayList<>();
        arraydate = new ArrayList<>();
        arraysubject = new ArrayList<>();
        arraydone = new ArrayList<>();
        cor = new ArrayList<>();


        customAdapter = new CustomAdapter(Tarefa.this, this, arrayid, arraytitle, arraynotes, arraydate, arraysubject,arraydone,cor);
        recyclerview_tarefas.setLayoutManager(new LinearLayoutManager(Tarefa.this));

        recyclerview_tarefas.setAdapter(customAdapter);

// Definindo Valor do Array qye vai pro metodo de filtragem por datas
        SimpleDateFormat formatodata = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String[] data = new String[7];
        for(int i = 0; i < 7;i++){
            Calendar calendar = new GregorianCalendar();
            calendar.add(Calendar.DATE  , i);
            data[i] = formatodata.format(calendar.getTime());
        }
        //Array dias exibidos pelas textviews
        SimpleDateFormat formatodia = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String[] dia = new String[7];
        for(int i = 0; i < 7;i++){
            Calendar calendar = new GregorianCalendar();
            calendar.add(Calendar.DATE  , i);
            dia[i] = formatodia.format(calendar.getTime());
        }

        tvdata1.setText(dia[0]);
        tvdata2.setText(dia[1]);
        tvdata3.setText(dia[2]);
        tvdata4.setText(dia[3]);
        tvdata5.setText(dia[4]);
        tvdata6.setText(dia[5]);
        tvdata7.setText(dia[6]);

        //Definir diasemana EEE- 3 letras
        SimpleDateFormat sdf = new SimpleDateFormat("EEE");
        String[] diassemana = new String[7];
        for (int i = 0; i < 7; i++) {
            Calendar diasemana = new GregorianCalendar();
            diasemana.add(Calendar.DATE, i);
            diassemana[i] = sdf.format(diasemana.getTime());
        }

        tvdia1.setText(diassemana[0].substring(0,3).toUpperCase());
        tvdia2.setText(diassemana[1].substring(0,3).toUpperCase());
        tvdia3.setText(diassemana[2].substring(0,3).toUpperCase());
        tvdia4.setText(diassemana[3].substring(0,3).toUpperCase());
        tvdia5.setText(diassemana[4].substring(0,3).toUpperCase());
        tvdia6.setText(diassemana[5].substring(0,3).toUpperCase());
        tvdia7.setText(diassemana[6].substring(0,3).toUpperCase());

        storeDataInArrays(String.valueOf(data[0]));
        btn1.getBackground().setColorFilter(Color.parseColor("#D2CED0"), PorterDuff.Mode.SRC_ATOP);
        tvdata1.setTextColor(Color.parseColor("#3E006F"));
        tvdia1.setTextColor(Color.parseColor("#3E006F"));


        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b1 = true;
                if (b1 == true) {
                    Clear();
                    storeDataInArrays(String.valueOf(data[0]));
                   /* n = db.countAll(data[0]);
                    totaltarefa.setText(String.valueOf(n));
                    long d = db.countNotDone(data[0]);
                    totaltarefa.setText(String.valueOf(d));
                    totaltodo.setText(String.valueOf(d));*/
                    customAdapter.notifyDataSetChanged();

                }

                if (b1 == true){
                    btn1.getBackground().setColorFilter(Color.parseColor("#D2CED0"), PorterDuff.Mode.SRC_ATOP);
                    tvdata1.setTextColor(Color.parseColor("#3E006F"));
                    tvdia1.setTextColor(Color.parseColor("#3E006F"));

                    btn2.getBackground().setColorFilter(Color.parseColor("#20006F"), PorterDuff.Mode.SRC_ATOP);
                    tvdata2.setTextColor(Color.parseColor("#D2CED0"));
                    tvdia2.setTextColor(Color.parseColor("#D2CED0"));

                    btn3.getBackground().setColorFilter(Color.parseColor("#20006F"), PorterDuff.Mode.SRC_ATOP);
                    tvdata3.setTextColor(Color.parseColor("#D2CED0"));
                    tvdia3.setTextColor(Color.parseColor("#D2CED0"));

                    btn4.getBackground().setColorFilter(Color.parseColor("#20006F"), PorterDuff.Mode.SRC_ATOP);
                    tvdata4.setTextColor(Color.parseColor("#D2CED0"));
                    tvdia4.setTextColor(Color.parseColor("#D2CED0"));

                    btn5.getBackground().setColorFilter(Color.parseColor("#20006F"), PorterDuff.Mode.SRC_ATOP);
                    tvdata5.setTextColor(Color.parseColor("#D2CED0"));
                    tvdia5.setTextColor(Color.parseColor("#D2CED0"));

                    btn6.getBackground().setColorFilter(Color.parseColor("#20006F"), PorterDuff.Mode.SRC_ATOP);
                    tvdata6.setTextColor(Color.parseColor("#D2CED0"));
                    tvdia6.setTextColor(Color.parseColor("#D2CED0"));

                    btn7.getBackground().setColorFilter(Color.parseColor("#20006F"), PorterDuff.Mode.SRC_ATOP);
                    tvdata7.setTextColor(Color.parseColor("#D2CED0"));
                    tvdia7.setTextColor(Color.parseColor("#D2CED0"));
                }
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                b2 =true;
                if (b2 == true) {
                    Clear();
                    storeDataInArrays(String.valueOf(data[1]));
                    customAdapter.notifyDataSetChanged();
                }

                if (b2 == true){
                    btn2.getBackground().setColorFilter(Color.parseColor("#D2CED0"), PorterDuff.Mode.SRC_ATOP);
                    tvdata2.setTextColor(Color.parseColor("#3E006F"));
                    tvdia2.setTextColor(Color.parseColor("#3E006F"));

                    btn3.getBackground().setColorFilter(Color.parseColor("#20006F"), PorterDuff.Mode.SRC_ATOP);
                    tvdata3.setTextColor(Color.parseColor("#D2CED0"));
                    tvdia3.setTextColor(Color.parseColor("#D2CED0"));

                    btn1.getBackground().setColorFilter(Color.parseColor("#20006F"), PorterDuff.Mode.SRC_ATOP);
                    tvdata1.setTextColor(Color.parseColor("#D2CED0"));
                    tvdia1.setTextColor(Color.parseColor("#D2CED0"));

                    btn4.getBackground().setColorFilter(Color.parseColor("#20006F"), PorterDuff.Mode.SRC_ATOP);
                    tvdata4.setTextColor(Color.parseColor("#D2CED0"));
                    tvdia4.setTextColor(Color.parseColor("#D2CED0"));

                    btn5.getBackground().setColorFilter(Color.parseColor("#20006F"), PorterDuff.Mode.SRC_ATOP);
                    tvdata5.setTextColor(Color.parseColor("#D2CED0"));
                    tvdia5.setTextColor(Color.parseColor("#D2CED0"));

                    btn6.getBackground().setColorFilter(Color.parseColor("#20006F"), PorterDuff.Mode.SRC_ATOP);
                    tvdata6.setTextColor(Color.parseColor("#D2CED0"));
                    tvdia6.setTextColor(Color.parseColor("#D2CED0"));

                    btn7.getBackground().setColorFilter(Color.parseColor("#20006F"), PorterDuff.Mode.SRC_ATOP);
                    tvdata7.setTextColor(Color.parseColor("#D2CED0"));
                    tvdia7.setTextColor(Color.parseColor("#D2CED0"));
                }
            }

        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b3= true;
                if (b3 == true) {
                    Clear();
                    storeDataInArrays(String.valueOf(data[2]));
                    customAdapter.notifyDataSetChanged();
                }
                if (b3 == true){
                    btn3.getBackground().setColorFilter(Color.parseColor("#D2CED0"), PorterDuff.Mode.SRC_ATOP);
                    tvdata3.setTextColor(Color.parseColor("#3E006F"));
                    tvdia3.setTextColor(Color.parseColor("#3E006F"));

                    btn2.getBackground().setColorFilter(Color.parseColor("#20006F"), PorterDuff.Mode.SRC_ATOP);
                    tvdata2.setTextColor(Color.parseColor("#D2CED0"));
                    tvdia2.setTextColor(Color.parseColor("#D2CED0"));

                    btn1.getBackground().setColorFilter(Color.parseColor("#20006F"), PorterDuff.Mode.SRC_ATOP);
                    tvdata1.setTextColor(Color.parseColor("#D2CED0"));
                    tvdia1.setTextColor(Color.parseColor("#D2CED0"));

                    btn4.getBackground().setColorFilter(Color.parseColor("#20006F"), PorterDuff.Mode.SRC_ATOP);
                    tvdata4.setTextColor(Color.parseColor("#D2CED0"));
                    tvdia4.setTextColor(Color.parseColor("#D2CED0"));

                    btn5.getBackground().setColorFilter(Color.parseColor("#20006F"), PorterDuff.Mode.SRC_ATOP);
                    tvdata5.setTextColor(Color.parseColor("#D2CED0"));
                    tvdia5.setTextColor(Color.parseColor("#D2CED0"));

                    btn6.getBackground().setColorFilter(Color.parseColor("#20006F"), PorterDuff.Mode.SRC_ATOP);
                    tvdata6.setTextColor(Color.parseColor("#D2CED0"));
                    tvdia6.setTextColor(Color.parseColor("#D2CED0"));

                    btn7.getBackground().setColorFilter(Color.parseColor("#20006F"), PorterDuff.Mode.SRC_ATOP);
                    tvdata7.setTextColor(Color.parseColor("#D2CED0"));
                    tvdia7.setTextColor(Color.parseColor("#D2CED0"));
                }

            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b4 =true;
                if (b4 == true) {
                    Clear();
                    storeDataInArrays(String.valueOf(data[3]));
                    customAdapter.notifyDataSetChanged();
                }
                if (b4 == true){
                    btn4.getBackground().setColorFilter(Color.parseColor("#D2CED0"), PorterDuff.Mode.SRC_ATOP);
                    tvdata4.setTextColor(Color.parseColor("#3E006F"));
                    tvdia4.setTextColor(Color.parseColor("#3E006F"));

                    btn3.getBackground().setColorFilter(Color.parseColor("#20006F"), PorterDuff.Mode.SRC_ATOP);
                    tvdata3.setTextColor(Color.parseColor("#D2CED0"));
                    tvdia3.setTextColor(Color.parseColor("#D2CED0"));

                    btn1.getBackground().setColorFilter(Color.parseColor("#20006F"), PorterDuff.Mode.SRC_ATOP);
                    tvdata1.setTextColor(Color.parseColor("#D2CED0"));
                    tvdia1.setTextColor(Color.parseColor("#D2CED0"));

                    btn2.getBackground().setColorFilter(Color.parseColor("#20006F"), PorterDuff.Mode.SRC_ATOP);
                    tvdata2.setTextColor(Color.parseColor("#D2CED0"));
                    tvdia2.setTextColor(Color.parseColor("#D2CED0"));

                    btn5.getBackground().setColorFilter(Color.parseColor("#20006F"), PorterDuff.Mode.SRC_ATOP);
                    tvdata5.setTextColor(Color.parseColor("#D2CED0"));
                    tvdia5.setTextColor(Color.parseColor("#D2CED0"));

                    btn6.getBackground().setColorFilter(Color.parseColor("#20006F"), PorterDuff.Mode.SRC_ATOP);
                    tvdata6.setTextColor(Color.parseColor("#D2CED0"));
                    tvdia6.setTextColor(Color.parseColor("#D2CED0"));

                    btn7.getBackground().setColorFilter(Color.parseColor("#20006F"), PorterDuff.Mode.SRC_ATOP);
                    tvdata7.setTextColor(Color.parseColor("#D2CED0"));
                    tvdia7.setTextColor(Color.parseColor("#D2CED0"));
                }
            }
        });

        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b5 =true;
                if (b5 == true) {
                    Clear();
                    storeDataInArrays(String.valueOf(data[4]));
                    customAdapter.notifyDataSetChanged();
                }
                if (b5 == true){
                    btn5.getBackground().setColorFilter(Color.parseColor("#D2CED0"), PorterDuff.Mode.SRC_ATOP);
                    tvdata5.setTextColor(Color.parseColor("#3E006F"));
                    tvdia5.setTextColor(Color.parseColor("#3E006F"));

                    btn3.getBackground().setColorFilter(Color.parseColor("#20006F"), PorterDuff.Mode.SRC_ATOP);
                    tvdata3.setTextColor(Color.parseColor("#D2CED0"));
                    tvdia3.setTextColor(Color.parseColor("#D2CED0"));

                    btn1.getBackground().setColorFilter(Color.parseColor("#20006F"), PorterDuff.Mode.SRC_ATOP);
                    tvdata1.setTextColor(Color.parseColor("#D2CED0"));
                    tvdia1.setTextColor(Color.parseColor("#D2CED0"));

                    btn4.getBackground().setColorFilter(Color.parseColor("#20006F"), PorterDuff.Mode.SRC_ATOP);
                    tvdata4.setTextColor(Color.parseColor("#D2CED0"));
                    tvdia4.setTextColor(Color.parseColor("#D2CED0"));

                    btn2.getBackground().setColorFilter(Color.parseColor("#20006F"), PorterDuff.Mode.SRC_ATOP);
                    tvdata2.setTextColor(Color.parseColor("#D2CED0"));
                    tvdia2.setTextColor(Color.parseColor("#D2CED0"));

                    btn6.getBackground().setColorFilter(Color.parseColor("#20006F"), PorterDuff.Mode.SRC_ATOP);
                    tvdata6.setTextColor(Color.parseColor("#D2CED0"));
                    tvdia6.setTextColor(Color.parseColor("#D2CED0"));

                    btn7.getBackground().setColorFilter(Color.parseColor("#20006F"), PorterDuff.Mode.SRC_ATOP);
                    tvdata7.setTextColor(Color.parseColor("#D2CED0"));
                    tvdia7.setTextColor(Color.parseColor("#D2CED0"));
                }
            }
        });

        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b6 =true;
                if (b6 == true) {
                    Clear();
                    storeDataInArrays(String.valueOf(data[5]));
                    customAdapter.notifyDataSetChanged();
                }
                if (b6 == true){
                    btn6.getBackground().setColorFilter(Color.parseColor("#D2CED0"), PorterDuff.Mode.SRC_ATOP);
                    tvdata6.setTextColor(Color.parseColor("#3E006F"));
                    tvdia6.setTextColor(Color.parseColor("#3E006F"));

                    btn3.getBackground().setColorFilter(Color.parseColor("#20006F"), PorterDuff.Mode.SRC_ATOP);
                    tvdata3.setTextColor(Color.parseColor("#D2CED0"));
                    tvdia3.setTextColor(Color.parseColor("#D2CED0"));

                    btn1.getBackground().setColorFilter(Color.parseColor("#20006F"), PorterDuff.Mode.SRC_ATOP);
                    tvdata1.setTextColor(Color.parseColor("#D2CED0"));
                    tvdia1.setTextColor(Color.parseColor("#D2CED0"));

                    btn4.getBackground().setColorFilter(Color.parseColor("#20006F"), PorterDuff.Mode.SRC_ATOP);
                    tvdata4.setTextColor(Color.parseColor("#D2CED0"));
                    tvdia4.setTextColor(Color.parseColor("#D2CED0"));

                    btn5.getBackground().setColorFilter(Color.parseColor("#20006F"), PorterDuff.Mode.SRC_ATOP);
                    tvdata5.setTextColor(Color.parseColor("#D2CED0"));
                    tvdia5.setTextColor(Color.parseColor("#D2CED0"));

                    btn2.getBackground().setColorFilter(Color.parseColor("#20006F"), PorterDuff.Mode.SRC_ATOP);
                    tvdata2.setTextColor(Color.parseColor("#D2CED0"));
                    tvdia2.setTextColor(Color.parseColor("#D2CED0"));

                    btn7.getBackground().setColorFilter(Color.parseColor("#20006F"), PorterDuff.Mode.SRC_ATOP);
                    tvdata7.setTextColor(Color.parseColor("#D2CED0"));
                    tvdia7.setTextColor(Color.parseColor("#D2CED0"));
                }
            }
        });

        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b7 =true;
                if (b7 == true) {
                    Clear();
                    storeDataInArrays(String.valueOf(data[6]));
                    customAdapter.notifyDataSetChanged();
                }

                if (b7 == true){
                    btn7.getBackground().setColorFilter(Color.parseColor("#D2CED0"), PorterDuff.Mode.SRC_ATOP);
                    tvdata7.setTextColor(Color.parseColor("#3E006F"));
                    tvdia7.setTextColor(Color.parseColor("#3E006F"));

                    btn3.getBackground().setColorFilter(Color.parseColor("#20006F"), PorterDuff.Mode.SRC_ATOP);
                    tvdata3.setTextColor(Color.parseColor("#D2CED0"));
                    tvdia3.setTextColor(Color.parseColor("#D2CED0"));

                    btn1.getBackground().setColorFilter(Color.parseColor("#20006F"), PorterDuff.Mode.SRC_ATOP);
                    tvdata1.setTextColor(Color.parseColor("#D2CED0"));
                    tvdia1.setTextColor(Color.parseColor("#D2CED0"));

                    btn4.getBackground().setColorFilter(Color.parseColor("#20006F"), PorterDuff.Mode.SRC_ATOP);
                    tvdata4.setTextColor(Color.parseColor("#D2CED0"));
                    tvdia4.setTextColor(Color.parseColor("#D2CED0"));

                    btn5.getBackground().setColorFilter(Color.parseColor("#20006F"), PorterDuff.Mode.SRC_ATOP);
                    tvdata5.setTextColor(Color.parseColor("#D2CED0"));
                    tvdia5.setTextColor(Color.parseColor("#D2CED0"));

                    btn6.getBackground().setColorFilter(Color.parseColor("#20006F"), PorterDuff.Mode.SRC_ATOP);
                    tvdata6.setTextColor(Color.parseColor("#D2CED0"));
                    tvdia6.setTextColor(Color.parseColor("#D2CED0"));

                    btn2.getBackground().setColorFilter(Color.parseColor("#20006F"), PorterDuff.Mode.SRC_ATOP);
                    tvdata2.setTextColor(Color.parseColor("#D2CED0"));
                    tvdia2.setTextColor(Color.parseColor("#D2CED0"));
                }
            }
        });


    }
    //Refresh ao Att
    @Override
    protected  void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode,data);
        if (requestCode == 1){
            recreate();
        }
    }
    public void storeDataInArrays(String data){
        Cursor cursor = db.readAllData(data);
        if(cursor.getCount() == 0){
            nodata.setVisibility(View.VISIBLE);
            imgnodata.setVisibility(View.VISIBLE);
        }else{
            while (cursor.moveToNext()){
                arrayid.add(cursor.getString(0));
                arraytitle.add(cursor.getString(1));
                arraynotes.add(cursor.getString(2));
                arraydate.add(cursor.getString(3));
                arraydone.add(cursor.getString(4));
                arraysubject.add(cursor.getString(6));
                cor.add(cursor.getInt(7));
            }
            nodata.setVisibility(View.INVISIBLE);
            imgnodata.setVisibility(View.INVISIBLE);
        }
    }

    public void Clear (){
        arraydate.clear();
        arrayid.clear();
        arraysubject.clear();
        arraynotes.clear();
        arraytitle.clear();
        arraydone.clear();
        cor.clear();

    }
}




