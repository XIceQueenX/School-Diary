package com.example.projeto1.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.projeto1.R;
import com.example.projeto1.functions.DataBase;
import com.example.projeto1.functions.Globals;
import com.example.projeto1.functions.SpinnerAdapter;
import com.example.projeto1.model.DisciplinaModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.logging.Handler;

import static java.lang.String.valueOf;

public class AddAnotacao extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText title_txt, content_txt;
    FloatingActionButton addanotacao;
    Spinner spinner;


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
        setContentView(R.layout.activity_addanotacao);
        onWindowFocusChanged(true);

        title_txt = findViewById(R.id.title_txt);

        String titulo = title_txt.getText().toString();

        content_txt = findViewById(R.id.content_txt);
        addanotacao = findViewById(R.id.addanotacao);

        ImageView backbutton = findViewById(R.id.backbutton6);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddAnotacao.this, Anotacoes.class);
                startActivity(intent);
                finish();
            }
        });

        spinner= findViewById(R.id.addsubject2);

        ArrayList<DisciplinaModel> disciplinas = getInformation();
        SpinnerAdapter spinnerAdapter = new SpinnerAdapter(getApplicationContext(), disciplinas);
        spinnerAdapter.setDropDownViewResource(R.layout.my_dropdown_item);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {

                if (position == 1) { // "Adicionar Nova Disciplina"
                    Globals.globaladdisciplina = 4;
                    Intent intent = new Intent(AddAnotacao.this, AddDisciplina.class);
                    startActivity(intent);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }

        });

        spinner.setAdapter(spinnerAdapter);


        addanotacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(title_txt.getText().toString().isEmpty()) {
                    if(title_txt.getText().toString().isEmpty()){
                        title_txt.requestFocus();
                        title_txt.setError("Não pode estar vazio");

                    }
                }else{

                    //Spiiner
                    View row = spinner.getSelectedView();

                    if(row instanceof TextView) {
                        ((TextView) row).setError("Campo vazio");
                        return;
                    }

                    int position = ((AppCompatSpinner) row.getParent()).getSelectedItemPosition() - SpinnerAdapter.STATIC_SPINNER_VALUES;
                    DisciplinaModel disciplina = ((SpinnerAdapter) spinner.getAdapter()).getDisciplina(position);


                    DataBase myDB = new DataBase(AddAnotacao.this);

                    myDB.addAnotacao(title_txt.getText().toString().trim(),
                            content_txt.getText().toString().trim(),
                            disciplina.getId());

                    Intent intent = new Intent(AddAnotacao.this, Anotacoes.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }

    public ArrayList<DisciplinaModel> getInformation() {

        ArrayList<DisciplinaModel> disciplinas = new ArrayList<>() ;

        DataBase db = new DataBase(AddAnotacao.this);
        Cursor c = db.readSubjects();

        if(c != null && c.moveToFirst()) {

            do {

                String nome = c.getString(0);
                String cor = c.getString(1);
                int id = c.getInt(3);

                DisciplinaModel model = new DisciplinaModel(id, nome, cor);

                disciplinas.add(model);

            } while (c.moveToNext());
        }
        return disciplinas;

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}