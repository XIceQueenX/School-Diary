package com.example.projeto1.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.DialogFragment;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.projeto1.R;
import com.example.projeto1.functions.Globals;
import com.example.projeto1.functions.SpinnerAdapter;
import com.example.projeto1.functions.TimePickerFragment;
import com.example.projeto1.functions.DataBase;
import com.example.projeto1.model.DisciplinaModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;

import static java.lang.String.valueOf;

public class AddHora extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    Spinner spinner;
    TextView adddateinitial, adddatefinal;
    int i, dia;
    FloatingActionButton addhour;
    ToggleButton dom, seg, ter, qua, qui, sex, sab;
    String horainicial, horafinal;
    String oi = "ola";


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
        setContentView(R.layout.activity_addhora);
        onWindowFocusChanged(true);

        dom = findViewById(R.id.dom);
        seg = findViewById(R.id.seg);
        ter = findViewById(R.id.ter);
        qua = findViewById(R.id.qua);
        qui = findViewById(R.id.qui);
        sex = findViewById(R.id.sex);
        sab = findViewById(R.id.sab);
        EditText sala = (EditText) findViewById(R.id.sala);


        //Check individual items.
        dom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dom.setChecked(true);
                seg.setChecked(false);
                ter.setChecked(false);
                qua.setChecked(false);
                qui.setChecked(false);
                sex.setChecked(false);
                sab.setChecked(false);
                dia = 0;

            }
        });

        seg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seg.setChecked(true);
                dom.setChecked(false);
                ter.setChecked(false);
                qua.setChecked(false);
                qui.setChecked(false);
                sex.setChecked(false);
                sab.setChecked(false);
                dia = 1;
                oi = "n";
            }
        });

        ter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ter.setChecked(true);
                dom.setChecked(false);
                seg.setChecked(false);
                qua.setChecked(false);
                qui.setChecked(false);
                sex.setChecked(false);
                sab.setChecked(false);
                dia = 2;
                oi = "n";
            }
        });

        qua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                qua.setChecked(true);
                dom.setChecked(false);
                ter.setChecked(false);
                seg.setChecked(false);
                qui.setChecked(false);
                sex.setChecked(false);
                sab.setChecked(false);
                dia = 3;
                oi = "n";
            }
        });
        qui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                qui.setChecked(true);
                dom.setChecked(false);
                ter.setChecked(false);
                seg.setChecked(false);
                qua.setChecked(false);
                sex.setChecked(false);
                sab.setChecked(false);
                dia = 4;
                oi = "n";
            }
        });
        sex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sex.setChecked(true);
                dom.setChecked(false);
                ter.setChecked(false);
                seg.setChecked(false);
                qui.setChecked(false);
                qua.setChecked(false);
                sab.setChecked(false);
                dia = 5;
                oi = "n";
            }
        });
        sab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sab.setChecked(true);
                dom.setChecked(false);
                ter.setChecked(false);
                seg.setChecked(false);
                qui.setChecked(false);
                sex.setChecked(false);
                qua.setChecked(false);
                dia = 6;
                oi = "n";
            }
        });

        spinner = findViewById(R.id.addsubjecthour);

        adddateinitial = findViewById(R.id.adddateinitial);
        adddatefinal = findViewById(R.id.adddatefinal);

        adddateinitial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = 1;

                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "TimePicker");
            }
        });

        adddatefinal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = 2;

                DialogFragment timePicker2 = new TimePickerFragment();
                timePicker2.show(getSupportFragmentManager(), "TimePicker");
            }
        });


        ArrayList<DisciplinaModel> disciplinas = getInformation();

        SpinnerAdapter spinnerAdapter = new SpinnerAdapter(getApplicationContext(), disciplinas);
        spinnerAdapter.setDropDownViewResource(R.layout.my_dropdown_item);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {

                if (position == 1) { // "Adicionar Nova Disciplina"
                    Globals.globaladdisciplina = 1;
                    Intent intent = new Intent(AddHora.this, AddDisciplina.class);
                    startActivity(intent);
                    finish();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }

        });

        spinner.setAdapter(spinnerAdapter);

        addhour = findViewById(R.id.edithour);

        addhour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View row = spinner.getSelectedView();

                if (row instanceof TextView) {

                    ((TextView) row).setError("Campo vazio");
                    return;
                }


                if (adddateinitial.getText().toString().isEmpty()) {

                    adddateinitial.requestFocus();
                    adddateinitial.setError("Não pode estar vazio");
                    return;

                }

                if (adddatefinal.getText().toString().isEmpty()) {

                    adddatefinal.requestFocus();
                    adddatefinal.setError("Não pode estar vazio");
                    return;
                }


                boolean hour = checkHour();
                boolean range = checkRange();

                if (hour == false) {

                    adddateinitial.setError("A hora inicial não pode ser maior que a hora final e vice versa");
                    adddateinitial.requestFocus();
                    Toast.makeText(AddHora.this, "Intervalo de tempo incorreto", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (range == false) {

                    adddateinitial.setError("Hora definida fora do espectro ");
                    adddateinitial.requestFocus();
                    Toast.makeText(AddHora.this, "Hora definida fora do espectro", Toast.LENGTH_SHORT).show();
                    return;
                }


                int position = ((AppCompatSpinner) row.getParent()).getSelectedItemPosition() - SpinnerAdapter.STATIC_SPINNER_VALUES;
                DisciplinaModel disciplina = ((SpinnerAdapter) spinner.getAdapter()).getDisciplina(position);

                if (oi == "ola"){
                    Toast.makeText(AddHora.this, "Selecione um dia", Toast.LENGTH_SHORT).show();
                }else{
                    DataBase myDB = new DataBase(AddHora.this);
                    myDB.addHora(disciplina.getId(), dia, horainicial, horafinal, sala.getText().toString());
                    finish();
                    Intent intent = new Intent(AddHora.this, HorarioEscolar.class);
                    startActivity(intent);
                }

            }

        });


        ImageView backbutton = findViewById(R.id.backbutton10);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddHora.this, HorarioEscolar.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public ArrayList<DisciplinaModel> getInformation() {

        ArrayList<DisciplinaModel> disciplinas = new ArrayList<>();

        DataBase db = new DataBase(AddHora.this);
        Cursor c = db.readSubjects();

        if (c != null && c.moveToFirst()) {

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

    public boolean checkHour() {
        LocalTime start = LocalTime.parse(horainicial);
        LocalTime end = LocalTime.parse(horafinal);

        Duration duration = Duration.between(start, end);

        if (duration.isNegative()) {
            return false;
        } else return true;
    }


    public boolean checkRange() {
        LocalTime start = LocalTime.parse(horainicial);
        LocalTime end = LocalTime.parse(horafinal);

        if (start.isBefore(LocalTime.parse("07:00:00")) || end.isAfter(LocalTime.parse("21:00:00"))) {

            return false;

        } else return true;
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        if (i == 1) {

            horainicial = String.format("%02d:%02d", hourOfDay, minute);

            adddateinitial.setText(horainicial);

        } else {

            horafinal = String.format("%02d:%02d", hourOfDay, minute);

            adddatefinal.setText(horafinal);
        }


    }
}