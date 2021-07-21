package com.example.projeto1.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projeto1.R;
import com.example.projeto1.functions.DataBase;
import com.example.projeto1.functions.Globals;
import com.example.projeto1.functions.SpinnerAdapter;
import com.example.projeto1.model.DisciplinaModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import static java.lang.String.valueOf;

public class AttAnotacao extends AppCompatActivity {

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

    EditText atttitle_txt, attcontent_txt;
    FloatingActionButton attbutton;
    String id, title, content, adddisciplina, materia;
    Spinner spinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attanotacoes);
        onWindowFocusChanged(true);


        attbutton = findViewById(R.id.attbutton);

        atttitle_txt = findViewById(R.id.atttitle_txt);
        attcontent_txt = findViewById(R.id.attcontent_txt);


        spinner = findViewById(R.id.addsubject3);

        ImageView backbutton = findViewById(R.id.backbutton7);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AttAnotacao.this, Anotacoes.class);
                startActivity(intent);
                finish();
            }
        });


        ArrayList<DisciplinaModel> disciplinas = getInformation();

        SpinnerAdapter spinnerAdapter = new SpinnerAdapter(getApplicationContext(), disciplinas);
        spinnerAdapter.setDropDownViewResource(R.layout.my_dropdown_item);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {


                if(position == 1) { // "Adicionar Nova Disciplina"
                    Globals.globaladdisciplina = 8;
                    Intent intent = new Intent(AttAnotacao.this, AddDisciplina.class);
                    startActivity(intent);
                    finish();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}

        });

        spinner.setAdapter(spinnerAdapter);

        getIntentData();

        attbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title = atttitle_txt.getText().toString().trim();
                content = attcontent_txt.getText().toString().trim();

                if (title.isEmpty() || content.isEmpty() ) {
                    if (title.isEmpty()) {
                        atttitle_txt.setError("Não pode ser vazio");
                        atttitle_txt.requestFocus();
                    }

                } else {

                    View row = spinner.getSelectedView();

                    if(row instanceof TextView) {
                        ((TextView) row).setError("Campo vazio");
                        return;
                    }

                    int position = ((AppCompatSpinner) row.getParent()).getSelectedItemPosition() - SpinnerAdapter.STATIC_SPINNER_VALUES;
                    DisciplinaModel disciplina = ((SpinnerAdapter) spinner.getAdapter()).getDisciplina(position);


                    DataBase db = new DataBase(AttAnotacao.this);
                    db.updateNotes(id, title, content, String.valueOf(disciplina.getId()));
                    Intent intent = new Intent(AttAnotacao.this, Anotacoes.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }


    public ArrayList<DisciplinaModel> getInformation() {

        ArrayList<DisciplinaModel> disciplinas = new ArrayList<>() ;

        DataBase db = new DataBase(AttAnotacao.this);
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



    void getIntentData() {

        Intent intent = getIntent();

        if(!intent.hasExtra("id")
                || !intent.hasExtra("title")
                || !intent.hasExtra("content")
                || !intent.hasExtra("subject")) {

            Toast.makeText(this, "Não há dados", Toast.LENGTH_SHORT).show();
            return;
        }


        id = intent.getStringExtra("id");
        title = intent.getStringExtra("title");
        content = intent.getStringExtra("content");
        materia = intent.getStringExtra("subject");



        atttitle_txt.setText(title);
        attcontent_txt.setText(content);


        int count = spinner.getAdapter().getCount() - SpinnerAdapter.STATIC_SPINNER_VALUES;

        for (int i = 0; i < count; i++) {

            DisciplinaModel disciplina = (DisciplinaModel) spinner.getAdapter().getItem(i);

            if(materia.equals(disciplina.getNome())) {
                spinner.setSelection(i + SpinnerAdapter.STATIC_SPINNER_VALUES);
                break;
            }

        }

    }
}