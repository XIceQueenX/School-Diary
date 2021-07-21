package com.example.projeto1.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.projeto1.R;
import com.example.projeto1.functions.DataBase;
import com.example.projeto1.functions.Globals;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import petrov.kristiyan.colorpicker.ColorPicker;

public class AddDisciplina extends AppCompatActivity {

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
    EditText addsubjects,addteacher;
    FloatingActionButton btnadddisciplina;
    TextView colorv;
    Button addcor;
    int cordisciplina;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_adddisciplina);
        onWindowFocusChanged(true);



        ImageView backbutton = findViewById(R.id.backbutton2);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Globals.globaladdisciplina == 1){
                    Intent intent = new Intent(AddDisciplina.this, AddHora.class);
                    startActivity(intent);
                    finish();
                }else if(Globals.globaladdisciplina == 2){
                    Intent intent = new Intent(AddDisciplina.this, AddTarefas.class);
                    startActivity(intent);
                    finish();
                }else if(Globals.globaladdisciplina == 3){
                    Intent intent = new Intent(AddDisciplina.this, AddTests.class);
                    startActivity(intent);
                    finish();
                }else if(Globals.globaladdisciplina == 4){
                    Intent intent = new Intent(AddDisciplina.this, AddAnotacao.class);
                    startActivity(intent);
                    finish();
                }
                else if(Globals.globaladdisciplina == 5){
                    Intent intent = new Intent(AddDisciplina.this, AttHorario.class);
                    startActivity(intent);
                    finish();
                }
                else if(Globals.globaladdisciplina == 6){
                    Intent intent = new Intent(AddDisciplina.this, AttTarefa.class);
                    startActivity(intent);
                    finish();
                }
                else if(Globals.globaladdisciplina == 7){
                    Intent intent = new Intent(AddDisciplina.this, AttTeste.class);
                    startActivity(intent);
                    finish();
                }
                else if(Globals.globaladdisciplina == 8){
                    Intent intent = new Intent(AddDisciplina.this, AttTeste.class);
                    startActivity(intent);
                    finish();
                }else{
                    Intent intent = new Intent(AddDisciplina.this, Tarefa.class);
                    startActivity(intent);
                    finish();
                }
            }
        });


        addteacher = findViewById(R.id.addteacher);
        colorv = findViewById(R.id.color);
        addcor = findViewById(R.id.button);
        addcor.setBackgroundResource(R.drawable.cordisciplina);

        GradientDrawable c = (GradientDrawable) addcor.getBackground();
        c.setColor(Color.TRANSPARENT);
        c.setStroke(1, Color.parseColor("#3F1660"));

        addcor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ColorPicker colorPicker = new ColorPicker(AddDisciplina.this);
                ArrayList<String> colors = new ArrayList<>();
                colors.add("#D90000"); //vermelho
                colors.add("#FF4444");//salmão
                colors.add("#F4511E");//laranjaescuro
                colors.add("#FF8800");//laranja
                colors.add("#ECB724");//amarelo
                colors.add("#3F51B5");//oceano
                colors.add("#039BE5");//azul
                colors.add("#33B679");//verdeclaro
                colors.add("#0B7F43");//verde
                colors.add("#7986CB");//cinza
                colors.add("#8E24AA");//roxo
                colors.add("#E67C73");//rosa
                colorPicker
                        .setDefaultColorButton(Color.parseColor("#f84c44"))
                        .setColors(colors)
                        .setColumns(6)
                        .setRoundColorButton(true)
                        .setColorButtonSize(40,40)
                        .setColorButtonMargin(2,5, 1, 15)
                        .setTitle("Escolha uma cor")
                        .setOnChooseColorListener(new ColorPicker.OnChooseColorListener() {
                            @Override
                            public void onChooseColor(int position, int color) {
                                cordisciplina = color;
                                GradientDrawable c = (GradientDrawable) addcor.getBackground();
                                c.setColor(color);
                                c.setStroke(1,color);
                                colorv.setText(String.valueOf(color));
                            }
                            @Override
                            public void onCancel() {
                                GradientDrawable c = (GradientDrawable) addcor.getBackground();
                                c.setColor(Color.TRANSPARENT);
                                c.setStroke(1, Color.parseColor("#3F1660"));
                            }
                        }).show();
                    }
                });



        addsubjects = findViewById(R.id.addsubjects);
        btnadddisciplina = findViewById(R.id.btnadddisciplina);
        btnadddisciplina.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String nome = addsubjects.getText().toString().trim();
                DataBase myDB = new DataBase(AddDisciplina.this);
                boolean a =myDB.checkSubject(nome);

                if ((colorv.getText().toString().isEmpty()) || addsubjects.getText().toString().isEmpty() || a == true){
                    if (colorv.getText().toString().isEmpty()){
                        addsubjects.setError("Escolha uma cor");
                        addsubjects.requestFocus();}

                    if (addsubjects.getText().toString().isEmpty()){
                        addsubjects.setError("Digite o nome da disciplina");
                        addsubjects.requestFocus();
                    }
                    if (colorv.getText().toString().isEmpty() && addsubjects.getText().toString().isEmpty()){
                        addsubjects.setError("Adicione um nome e uma cor a disciplina");
                        addsubjects.requestFocus();
                    }

                    if (myDB.checkSubject(nome) == true){
                        addsubjects.setError("Essa disciplina já existe");
                        addsubjects.requestFocus();
                    }

                }else{
                    myDB.addDisciplina(nome,cordisciplina,addteacher.getText().toString());
                    if (Globals.globaladdisciplina == 1){
                        Intent intent = new Intent(AddDisciplina.this, AddHora.class);
                        startActivity(intent);
                        finish();
                    }else if(Globals.globaladdisciplina == 2){
                        Intent intent = new Intent(AddDisciplina.this, AddTarefas.class);
                        startActivity(intent);
                        finish();
                    }else if(Globals.globaladdisciplina == 3){
                        Intent intent = new Intent(AddDisciplina.this, AddTests.class);
                        startActivity(intent);
                        finish();
                    }else if(Globals.globaladdisciplina == 4){
                        Intent intent = new Intent(AddDisciplina.this, AddAnotacao.class);
                        startActivity(intent);
                        finish();
                    }
                    else if(Globals.globaladdisciplina == 5){
                        Intent intent = new Intent(AddDisciplina.this, AttHorario.class);
                        startActivity(intent);
                        finish();
                    }
                    else if(Globals.globaladdisciplina == 6){
                        Intent intent = new Intent(AddDisciplina.this, AttTarefa.class);
                        startActivity(intent);
                        finish();
                    }
                    else if(Globals.globaladdisciplina == 7){
                        Intent intent = new Intent(AddDisciplina.this, AttTeste.class);
                        startActivity(intent);
                        finish();
                    }
                    else if(Globals.globaladdisciplina == 8){
                        Intent intent = new Intent(AddDisciplina.this, AttTeste.class);
                        startActivity(intent);
                        finish();
                    }else{
                        Intent intent = new Intent(AddDisciplina.this, Tarefa.class);
                        startActivity(intent);
                        finish();
                    }
                }

               /* if (Globals.global == 1){
                    finish();
                    Intent intent = new Intent(AddDisciplina.this, AddHora.class);
                    startActivity(intent);
                }
                if (Globals.global == 2){
                    finish();
                    Intent intent = new Intent(AddDisciplina.this, AddTarefas.class);
                    startActivity(intent);
                }*/
               /* if (Globals.global == 3){
                    finish();
                    Intent intent = new Intent(AddDisciplina.this, Testes.class);
                    startActivity(intent);
                }*/
            }
        });

    }

}