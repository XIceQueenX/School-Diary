package com.example.projeto1.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projeto1.R;
import com.example.projeto1.functions.DataBase;
import com.example.projeto1.util.StringUtil;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;

import petrov.kristiyan.colorpicker.ColorPicker;

public class AttDisciplina extends AppCompatActivity {

    EditText attsubjects, attteacher;
    FloatingActionButton btnattdisciplina;
    TextView colorv;
    Button attcor;
    int cordisciplina1;
    Integer cor;
    String id, subject, prof;


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
        setContentView(R.layout.activity_att_disciplina);
        onWindowFocusChanged(true);


        attcor = findViewById(R.id.attcor);
        attcor.setBackgroundResource(R.drawable.cordisciplina);
        colorv = findViewById(R.id.color2);

        attteacher = findViewById(R.id.addteacher2);

        ImageView backbutton = findViewById(R.id.backbutton16);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AttDisciplina.this, Disciplinas.class);
                startActivity(intent);
                finish();
            }
        });


        attcor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ColorPicker colorPicker = new ColorPicker(AttDisciplina.this);
                ArrayList<String> colors = new ArrayList<>();
                colors.add("#D90000");//vermelho
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
                        .setColorButtonSize(40, 40)
                        .setColorButtonMargin(2, 5, 1, 15)
                        .setTitle("Escolha uma cor")
                        .setOnChooseColorListener(new ColorPicker.OnChooseColorListener() {
                            @Override
                            public void onChooseColor(int position, int color) {
                                cordisciplina1 = color;
                                GradientDrawable c = (GradientDrawable) attcor.getBackground();
                                c.setColor(color);
                                c.setStroke(1, color);
                                colorv.setText(String.valueOf(color));
                            }

                            @Override
                            public void onCancel() {
                                GradientDrawable c = (GradientDrawable) attcor.getBackground();
                                c.setColor(Color.TRANSPARENT);
                                c.setStroke(1, Color.parseColor("#3F1660"));
                                colorv.setText("");
                            }
                        }).show();
            }
        });


        attsubjects = findViewById(R.id.attsubjects);
        btnattdisciplina = findViewById(R.id.btnattdisciplina);


        getIntentData();

        attsubjects.setText(String.valueOf(subject));
        attteacher.setText(String.valueOf(prof));
        colorv.setText(String.valueOf(cordisciplina1));

        GradientDrawable b = (GradientDrawable) attcor.getBackground();
        b.setColor(Integer.valueOf(cordisciplina1));
        b.setStroke(1, Integer.valueOf(cordisciplina1));

        btnattdisciplina.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String nome = attsubjects.getText().toString().trim();
                DataBase myDB = new DataBase(AttDisciplina.this);
                boolean a = myDB.checkSubject(nome);

                if (colorv.getText().toString().isEmpty() || attsubjects.getText().toString().isEmpty()
                        || colorv.getText().toString().isEmpty() && attsubjects.getText().toString().isEmpty() || myDB.checkSubjectatt(nome) == true) {

                    if (colorv.getText().toString().isEmpty()) {
                        attsubjects.setError("Escolha uma cor");
                        attsubjects.requestFocus();
                        return;
                    }


                    if (String.valueOf(cordisciplina1).isEmpty() ) {
                        attsubjects.setError("Escolha uma cor");
                        attsubjects.requestFocus();
                        return;
                    }

                    if (attsubjects.getText().toString().isEmpty()) {
                        attsubjects.setError("Digite o nome da disciplina");
                        attsubjects.requestFocus();
                        return;
                    }

                    if (colorv.getText().toString().isEmpty() && attsubjects.getText().toString().isEmpty()) {
                        attsubjects.setError("Adicione um nome e uma cor a disciplina");
                        attsubjects.requestFocus();
                        return;
                    }

                    if (myDB.checkSubjectatt(nome) == true) {
                        attsubjects.setError("Já existe uma disciplina com esse nome");
                        attsubjects.requestFocus();
                        return;
                    }
                }else {
                    myDB.updateDisciplina(id, attsubjects.getText().toString(), cordisciplina1, attteacher.getText().toString());
                    Intent intent = new Intent(AttDisciplina.this, Disciplinas.class);
                    startActivity(intent);
                    finish();

                }


            }


        });

    }

    public void getIntentData() {
        if (getIntent().hasExtra("id") && getIntent().hasExtra("subject") && getIntent().hasExtra("color") && getIntent().hasExtra("prof")) {

            id = getIntent().getStringExtra("id");
            subject = getIntent().getStringExtra("subject");
            cordisciplina1 = Integer.valueOf(getIntent().getStringExtra("color"));
            prof = getIntent().getStringExtra("prof");


        } else {
            Toast.makeText(this, "Não há dados", Toast.LENGTH_SHORT).show();
        }
    }
}