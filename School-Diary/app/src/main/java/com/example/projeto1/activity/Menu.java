package com.example.projeto1.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.TextAppearanceSpan;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.projeto1.Login;
import com.example.projeto1.R;
import com.example.projeto1.functions.DataBase;
import com.example.projeto1.functions.Globals;
import com.google.android.material.navigation.NavigationView;

import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Menu extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private Menu a;

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

    public String datahoje() {
        return new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(new Date());
    }

    public void hourMessage(TextView msg) {
        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);

        if (hour >= 4 && hour < 12) {

            msg.setText("Bom dia");

        } else if (hour >= 12 && hour < 19) {

            msg.setText("Boa Tarde");

        } else if (hour >= 19 || hour >= 0 && hour < 4) {

            msg.setText("Boa Noite");
        }
    }

    TextView textView21, tarefa1, tarefa2,tarefa3, tarefa4,tarefa5,tarefa6;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ImageView bolinha1,bolinha2,bolinha3,bolinha4,bolinha5,bolinha6;

    ArrayList<String> arraytarefa,subject;
    ArrayList<Integer>cor;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        onWindowFocusChanged(true);
        setContentView(R.layout.activity_main);


        String timeStamp = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());



        bolinha1 = findViewById(R.id.bolinha1); bolinha2 = findViewById(R.id.bolinha2);bolinha3 = findViewById(R.id.bolinha3);
        bolinha4 = findViewById(R.id.bolinha4);
        bolinha5 = findViewById(R.id.bolinha5);
        bolinha6 = findViewById(R.id.bolinha6);


        arraytarefa = new ArrayList<>();
        subject = new ArrayList<>();
        cor = new ArrayList<>();


        storeDataInArrays(timeStamp);



        toolbar = findViewById(R.id.toolbar1);

        drawerLayout = findViewById(R.id.drawer_layout);

        navigationView = findViewById(R.id.nav_view);
        Button button3 = (Button)findViewById(R.id.button3);

        button3.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View v) {

                if(!drawerLayout.isDrawerOpen(Gravity.START)) drawerLayout.openDrawer(Gravity.START);
                else drawerLayout.closeDrawer(Gravity.END);
            }
        });

        DataBase db = new DataBase(Menu.this);

        View header = navigationView.getHeaderView(0);
        String nameCap = StringUtils.capitalize(db.getName());

        TextView name = header.findViewById(R.id.nome);
        TextView saudacao = header.findViewById(R.id.saudacao);
        TextView email = header.findViewById(R.id.email);

        hourMessage(saudacao);
        name.setText(nameCap);
        email.setText(db.getEmail());



        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);

        android.view.Menu menu = navigationView.getMenu();

        MenuItem a = menu.findItem(R.id.titlevisu);

        SpannableString s = new SpannableString(a.getTitle());
        s.setSpan(new TextAppearanceSpan(this, R.style.TextAppearance44), 0, s.length(), 0);
        a.setTitle(s);

        MenuItem c = menu.findItem(R.id.visu);

        SpannableString b = new SpannableString(c.getTitle());
        b.setSpan(new TextAppearanceSpan(this, R.style.TextAppearance44), 0, b.length(), 0);
        c.setTitle(b);

        Globals.global = 0;
        TextView datamenu, diasemana;
        ImageView horario, gravacoes, testes;

        textView21 = findViewById(R.id.textView21);

        textView21.setText(String.valueOf(Globals.user));

        datamenu = findViewById(R.id.datamenu);

        datamenu.setText(datahoje());

        diasemana = findViewById(R.id.diasemana);




        int dia = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        switch (dia) {
            case Calendar.SUNDAY:
                diasemana.setText("Domingo");
                break;
            case Calendar.MONDAY:
                diasemana.setText("Segunda");
                break;
            case Calendar.TUESDAY:
                diasemana.setText("Terça");
                break;
            case Calendar.WEDNESDAY:
                diasemana.setText("Quarta");
                break;
            case Calendar.THURSDAY:
                diasemana.setText("Quinta");
                break;
            case Calendar.FRIDAY:
                diasemana.setText("Sexta");
                break;
            case Calendar.SATURDAY:
                diasemana.setText("Sábado");
                break;
            default:
        }

        ImageView tarefa = (ImageView) findViewById(R.id.tarefa);

        tarefa.setOnClickListener(view -> {

            Intent intent = new Intent(Menu.this, Tarefa.class);
            startActivity(intent);

        });

        horario = findViewById(R.id.horario);

        horario.setOnClickListener(view -> {
            Intent intent = new Intent(Menu.this, HorarioEscolar.class);
            startActivity(intent);

        });

        gravacoes = findViewById(R.id.gravacao);

        gravacoes.setOnClickListener(view -> {
            Intent intent = new Intent(Menu.this, Anotacoes.class);
            startActivity(intent);

        });

        testes = findViewById(R.id.testes);

        testes.setOnClickListener(view -> {
            Intent intent = new Intent(Menu.this, Testes.class);
            startActivity(intent);
        });
    }

    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_horario:

                Intent intent = new Intent(Menu.this, HorarioEscolar.class);
                startActivity(intent);

                break;
            case R.id.nav_tarefa:

                Intent intent2 = new Intent(Menu.this, Tarefa.class);
                startActivity(intent2);

                break;
            case R.id.nav_teste:

                Intent intent3 = new Intent(Menu.this, Testes.class);
                startActivity(intent3);

                break;
            case R.id.nav_anotacao:

                Intent intent4 = new Intent(Menu.this, Anotacoes.class);
                startActivity(intent4);

                break;
            case R.id.subjects:

                Intent intent5 = new Intent(Menu.this, Disciplinas.class);
                startActivity(intent5);

                break;
            case R.id.tests:

                Intent intent6 = new Intent(Menu.this, TestesNotasActivity.class);
                startActivity(intent6);

                break;
            case R.id.logout:

                SharedPreferences secao = getSharedPreferences("Secao", 0);
                SharedPreferences emailsp = getSharedPreferences("Email", 0);

                SharedPreferences.Editor editor = secao.edit();
                SharedPreferences.Editor editor1 = emailsp.edit();

                editor.remove("login");
                editor.clear();
                editor.apply();

                editor1.remove("id");
                editor1.clear();
                editor1.apply();

                Intent intent7 = new Intent(Menu.this, Login.class);
                startActivity(intent7);

                finish();
                break;
        }
        return true;

    }



    public void storeDataInArrays(String data){
        DataBase db = new DataBase(Menu.this);

        tarefa1 = findViewById(R.id.tarefa1);
        tarefa2 = findViewById(R.id.tarefa2);
        tarefa3 = findViewById(R.id.tarefa3);
        tarefa4 = findViewById(R.id.tarefa4);
        tarefa5 = findViewById(R.id.tarefa5);
        tarefa6 = findViewById(R.id.tarefa6);

        Cursor cursor = db.readTa(data);
        if(cursor.getCount() == 0){
            tarefa1.setVisibility(View.INVISIBLE);
            tarefa2.setVisibility(View.INVISIBLE);
            tarefa3.setVisibility(View.INVISIBLE);
            tarefa4.setVisibility(View.INVISIBLE);
            tarefa5.setVisibility(View.INVISIBLE);
            tarefa6.setVisibility(View.INVISIBLE);

            bolinha1.setVisibility(View.INVISIBLE);
            bolinha2.setVisibility(View.INVISIBLE);
            bolinha3.setVisibility(View.INVISIBLE);
            bolinha4.setVisibility(View.INVISIBLE);
            bolinha5.setVisibility(View.INVISIBLE);
            bolinha6.setVisibility(View.INVISIBLE);


        }else{

            while (cursor.moveToNext()){
                arraytarefa.add(cursor.getString(1) + " - " + cursor.getString(6));
            }
            if (cursor.getCount() == 3 ){

                tarefa1.setText(arraytarefa.get(0));
                tarefa2.setText(arraytarefa.get(1));
                tarefa3.setText(arraytarefa.get(2));

                bolinha1.setVisibility(View.VISIBLE);
                bolinha2.setVisibility(View.VISIBLE);
                bolinha3.setVisibility(View.VISIBLE);
                bolinha4.setVisibility(View.INVISIBLE);
                bolinha5.setVisibility(View.INVISIBLE);
                bolinha6.setVisibility(View.INVISIBLE);

                tarefa1.setVisibility(View.VISIBLE);
                tarefa2.setVisibility(View.VISIBLE);
                tarefa3.setVisibility(View.VISIBLE);
                tarefa4.setVisibility(View.INVISIBLE);
                tarefa5.setVisibility(View.INVISIBLE);
                tarefa6.setVisibility(View.INVISIBLE);

            } else if (cursor.getCount() == 4){
                tarefa1.setVisibility(View.VISIBLE);
                tarefa2.setVisibility(View.VISIBLE);
                tarefa3.setVisibility(View.VISIBLE);
                tarefa4.setVisibility(View.VISIBLE);
                tarefa5.setVisibility(View.INVISIBLE);
                tarefa6.setVisibility(View.INVISIBLE);

                tarefa1.setText(arraytarefa.get(0));
                tarefa2.setText(arraytarefa.get(1));
                tarefa3.setText(arraytarefa.get(2));
                tarefa4.setText(arraytarefa.get(3));


                bolinha1.setVisibility(View.VISIBLE);
                bolinha2.setVisibility(View.VISIBLE);
                bolinha3.setVisibility(View.VISIBLE);
                bolinha4.setVisibility(View.VISIBLE);
                bolinha5.setVisibility(View.INVISIBLE);
                bolinha6.setVisibility(View.INVISIBLE);
            }
            else if (cursor.getCount() == 2){
                tarefa1.setVisibility(View.VISIBLE);
                tarefa2.setVisibility(View.VISIBLE);
                tarefa3.setVisibility(View.INVISIBLE);
                tarefa4.setVisibility(View.INVISIBLE);
                tarefa5.setVisibility(View.INVISIBLE);
                tarefa6.setVisibility(View.INVISIBLE);

                tarefa1.setText(arraytarefa.get(0));
                tarefa2.setText(arraytarefa.get(1));
                tarefa3.setVisibility(View.GONE);

                bolinha1.setVisibility(View.VISIBLE);
                bolinha2.setVisibility(View.VISIBLE);
                bolinha3.setVisibility(View.INVISIBLE);
                bolinha3.setVisibility(View.INVISIBLE);
                bolinha4.setVisibility(View.INVISIBLE);
                bolinha5.setVisibility(View.INVISIBLE);
                bolinha6.setVisibility(View.INVISIBLE);

            }else if(cursor.getCount() == 1){
                tarefa1.setText(arraytarefa.get(0));
                tarefa1.setVisibility(View.VISIBLE);
                tarefa2.setVisibility(View.GONE);
                tarefa3.setVisibility(View.GONE);
                tarefa4.setVisibility(View.GONE);
                tarefa5.setVisibility(View.GONE);
                tarefa6.setVisibility(View.GONE);

                bolinha1.setVisibility(View.VISIBLE);
                bolinha2.setVisibility(View.INVISIBLE);
                bolinha3.setVisibility(View.INVISIBLE);
                bolinha4.setVisibility(View.INVISIBLE);
                bolinha5.setVisibility(View.INVISIBLE);
                bolinha6.setVisibility(View.INVISIBLE);

            }
            else if(cursor.getCount() == 5){
                tarefa1.setText(arraytarefa.get(0));
                tarefa2.setText(arraytarefa.get(1));
                tarefa3.setText(arraytarefa.get(2));
                tarefa4.setText(arraytarefa.get(3));
                tarefa5.setText(arraytarefa.get(4));
               // tarefa6.setText(arraytarefa.get(5));

                tarefa1.setVisibility(View.VISIBLE);
                tarefa2.setVisibility(View.VISIBLE);
                tarefa3.setVisibility(View.VISIBLE);
                tarefa4.setVisibility(View.VISIBLE);
                tarefa5.setVisibility(View.VISIBLE);
                tarefa6.setVisibility(View.GONE);

                bolinha1.setVisibility(View.VISIBLE);
                bolinha2.setVisibility(View.VISIBLE);
                bolinha3.setVisibility(View.VISIBLE);
                bolinha4.setVisibility(View.VISIBLE);
                bolinha5.setVisibility(View.VISIBLE);
                bolinha6.setVisibility(View.INVISIBLE);
            }
            else {
                tarefa1.setText(arraytarefa.get(0));
                tarefa2.setText(arraytarefa.get(1));
                tarefa3.setText(arraytarefa.get(2));
                tarefa4.setText(arraytarefa.get(3));
                tarefa5.setText(arraytarefa.get(4));
                tarefa6.setText(arraytarefa.get(5));

                tarefa1.setVisibility(View.VISIBLE);
                tarefa2.setVisibility(View.VISIBLE);
                tarefa3.setVisibility(View.VISIBLE);
                tarefa4.setVisibility(View.VISIBLE);
                tarefa5.setVisibility(View.VISIBLE);
                tarefa6.setVisibility(View.VISIBLE);

                bolinha1.setVisibility(View.VISIBLE);
                bolinha2.setVisibility(View.VISIBLE);
                bolinha3.setVisibility(View.VISIBLE);
                bolinha4.setVisibility(View.VISIBLE);
                bolinha5.setVisibility(View.VISIBLE);
                bolinha6.setVisibility(View.VISIBLE);
            }
        }
    }



    /*public void storeDataTest(String data){
        DataBase db = new DataBase(Menu.this);

        Cursor cursor = db.readTesteMain(data);
        if(cursor.getCount() == 0){

        }else{

            teste01 = findViewById(R.id.teste01);
            //tarefa2 = findViewById(R.id.tarefa2);
            //tarefa3 = findViewById(R.id.tarefa3);

            while (cursor.moveToNext()){
                arrayteste.add(cursor.getString(1));
            }
            teste01.setText(arrayteste.get(0));
           /* if (cursor.getCount() > 2 ){

                tarefa1.setText(arraytarefa.get(0));
                tarefa2.setText(arraytarefa.get(1));
                tarefa3.setText(arraytarefa.get(2)+" ...");

            }
            else if (cursor.getCount() == 2){

                tarefa1.setText(arraytarefa.get(0));
                tarefa2.setText(arraytarefa.get(1));
                tarefa3.setVisibility(View.GONE);

            }else if(cursor.getCount() == 1){

                tarefa1.setText(arraytarefa.get(0));
                tarefa2.setVisibility(View.GONE);
                tarefa3.setVisibility(View.GONE);

            }
        }
    }*/



}