package com.example.projeto1.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.example.projeto1.R;
import com.example.projeto1.functions.DataBase;
import com.example.projeto1.functions.Globals;
import com.example.projeto1.functions.SpinnerAdapter;
import com.example.projeto1.model.DisciplinaModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import static java.lang.String.valueOf;

public class AddTests extends AppCompatActivity {

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

    TextView adddatetest;
    DatePickerDialog datePickerDialog;
    Calendar calendar;
    Spinner spinner;
    String materia, adddisciplina = "Selecione uma disciplina";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addtests);
        onWindowFocusChanged(true);

        Globals.global = 3;

        ImageView backbutton = findViewById(R.id.backbutton21);
        backbutton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Globals.globaladdisciplina = 3;
                Intent intent = new Intent(AddTests.this, Testes.class);
                startActivity(intent);
                finish();
            }
        });

        spinner = findViewById(R.id.addsubjecttest);

        ArrayList<DisciplinaModel> disciplinas = getInformation();
        SpinnerAdapter spinnerAdapter = new SpinnerAdapter(getApplicationContext(), disciplinas);
        spinnerAdapter.setDropDownViewResource(R.layout.my_dropdown_item);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {

                if(position == 1) { // "Adicionar Nova Disciplina"
                    Intent intent = new Intent(AddTests.this, AddDisciplina.class);
                    startActivity(intent);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}

        });

        spinner.setAdapter(spinnerAdapter);

        calendar = Calendar.getInstance();

        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar dataSelect = Calendar.getInstance();
                dataSelect.set(year, month, dayOfMonth);
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                adddatetest.setText(format.format(dataSelect.getTime()));
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));


        adddatetest = findViewById(R.id.adddatetest);
        adddatetest.setOnClickListener(v -> datePickerDialog.show());

        FloatingActionButton btnadddisciplina = findViewById(R.id.btnaddta);
        btnadddisciplina.setOnClickListener(v -> {


            EditText addtitletest = findViewById(R.id.addtitletest);
            String title = addtitletest.getText().toString().trim();
            String date = adddatetest.getText().toString().trim();


            Switch notifySwitchButton = findViewById(R.id.switch1);
            int notify = notifySwitchButton.isChecked() ? 1 : 0;


            if (title.isEmpty() || date.isEmpty() ){
                if (title.isEmpty()){
                    addtitletest.setError("Campo Vazio");
                }

                if (date.isEmpty()){
                    adddatetest.setError("Campo Vazio");
                }


            }else {

                //Spiiner
                View row = spinner.getSelectedView();

                if(row instanceof TextView) {
                    ((TextView) row).setError("Campo vazio");
                    return;
                }

                int position = ((AppCompatSpinner) row.getParent()).getSelectedItemPosition() - SpinnerAdapter.STATIC_SPINNER_VALUES;
                DisciplinaModel disciplina = ((SpinnerAdapter) spinner.getAdapter()).getDisciplina(position);


                DataBase myDB = new DataBase(AddTests.this);
                myDB.addTeste(title, date, disciplina.getId(), notify);
                Intent intent = new Intent(AddTests.this, Testes.class);
                startActivity(intent);
                finish();
            }
        });

    }

    public ArrayList<DisciplinaModel> getInformation() {

        ArrayList<DisciplinaModel> disciplinas = new ArrayList<>() ;

        DataBase db = new DataBase(AddTests.this);
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

}