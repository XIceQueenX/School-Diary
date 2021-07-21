package com.example.projeto1.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.DialogFragment;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.projeto1.R;
import com.example.projeto1.functions.DataBase;
import com.example.projeto1.functions.Globals;
import com.example.projeto1.functions.SpinnerAdapter;
import com.example.projeto1.functions.TimePickerFragment;
import com.example.projeto1.model.DisciplinaModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static java.lang.String.valueOf;

public class AttHorario extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    String id, name, day, starhour, endday, endhour, adddisciplina, materia;
    TextView editdateinitial, editdatefinal, a;
    Spinner spinner;
    ToggleButton dom, seg, ter, qua, qui, sex, sab;
    int dia, i, diaa;
    String horainicial, horafinal;
    FloatingActionButton atthour;

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
        setContentView(R.layout.activity_att_horario);
        onWindowFocusChanged(true);


        spinner = findViewById(R.id.editsubjecthour);

        ArrayList<DisciplinaModel> disciplinas = getInformation();
        SpinnerAdapter spinnerAdapter = new SpinnerAdapter(getApplicationContext(), disciplinas);
        spinnerAdapter.setDropDownViewResource(R.layout.my_dropdown_item);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {

                if (position == 1) { // "Adicionar Nova Disciplina"
                    Globals.globaladdisciplina = 5;
                    Intent intent = new Intent(AttHorario.this, AddDisciplina.class);
                    startActivity(intent);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }

        });

        spinner.setAdapter(spinnerAdapter);
        getIntentData();


        dom = findViewById(R.id.dom);
        seg = findViewById(R.id.seg);
        ter = findViewById(R.id.ter);
        qua = findViewById(R.id.qua);
        qui = findViewById(R.id.qui);
        sex = findViewById(R.id.sex);
        sab = findViewById(R.id.sab);


        if (dia == 0) {
            dom.setChecked(true);
            seg.setChecked(false);
            ter.setChecked(false);
            qua.setChecked(false);
            qui.setChecked(false);
            sex.setChecked(false);
            sab.setChecked(false);
        }
        if (dia == 1) {
            seg.setChecked(true);
            dom.setChecked(false);
            ter.setChecked(false);
            qua.setChecked(false);
            qui.setChecked(false);
            sex.setChecked(false);
            sab.setChecked(false);
        }
        if (dia == 2) {
            ter.setChecked(true);
            dom.setChecked(false);
            seg.setChecked(false);
            qua.setChecked(false);
            qui.setChecked(false);
            sex.setChecked(false);
            sab.setChecked(false);
        }
        if (dia == 3) {
            qua.setChecked(true);
            dom.setChecked(false);
            ter.setChecked(false);
            seg.setChecked(false);
            qui.setChecked(false);
            sex.setChecked(false);
            sab.setChecked(false);
        }
        if (dia == 4) {
            qui.setChecked(true);
            dom.setChecked(false);
            ter.setChecked(false);
            seg.setChecked(false);
            qua.setChecked(false);
            sex.setChecked(false);
            sab.setChecked(false);
        }
        if (dia == 5) {
            sex.setChecked(true);
            dom.setChecked(false);
            ter.setChecked(false);
            seg.setChecked(false);
            qui.setChecked(false);
            qua.setChecked(false);
            sab.setChecked(false);
        }
        if (dia == 6) {
            sab.setChecked(true);
            dom.setChecked(false);
            ter.setChecked(false);
            seg.setChecked(false);
            qui.setChecked(false);
            sex.setChecked(false);
            qua.setChecked(false);
        }


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
            }
        });


        editdateinitial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = 1;

                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "TimePicker");

            }
        });

        editdatefinal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = 2;

                DialogFragment timePicker2 = new TimePickerFragment();
                timePicker2.show(getSupportFragmentManager(), "TimePicker");

            }
        });

        atthour = findViewById(R.id.atthour);
        atthour.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {


                View row = spinner.getSelectedView();

                if (row instanceof TextView) {
                    ((TextView) row).setError("Campo vazio");
                    return;
                }


                if (editdateinitial.getText().toString().isEmpty()) {
                    editdateinitial.requestFocus();
                    editdateinitial.setError("Não pode estar vazio");
                }

                if (editdatefinal.getText().toString().isEmpty()) {
                    editdatefinal.requestFocus();
                    editdatefinal.setError("Não pode estar vazio");
                }

                boolean hour = checkHour();
                boolean range = checkRange();
                if (hour == false || range == false){
                    if (hour == false) {
                        editdateinitial.setError("A hora inicial não pode ser maior que a hora final e vice versa");
                        editdatefinal.setError("A hora inicial não pode ser maior que a hora final e vice versa");
                        editdateinitial.requestFocus();
                        Toast.makeText(AttHorario.this, "Intervalo de tempo incorreto", Toast.LENGTH_SHORT).show();
                    }
                    if (range == false) {
                        editdateinitial.setError("Hora definida fora do espectro ");
                        editdatefinal.setError("Hora definida fora do espectro ");
                        editdateinitial.requestFocus();
                        Toast.makeText(AttHorario.this, "Hora definida fora do espectro", Toast.LENGTH_SHORT).show();
                }

                }else{
                    int position = ((AppCompatSpinner) row.getParent()).getSelectedItemPosition() - SpinnerAdapter.STATIC_SPINNER_VALUES;

                    DisciplinaModel disciplina = ((SpinnerAdapter) spinner.getAdapter()).getDisciplina(position);


                    DataBase myDB = new DataBase(AttHorario.this);
                    myDB.updateHour(id, String.valueOf(disciplina.getId()), dia, horainicial, horafinal.trim());

                    Intent intent = new Intent(AttHorario.this, HorarioEscolar.class);
                    startActivity(intent);
                    finish();
                }


            }

    });

}

    public void getIntentData() {
        if (getIntent().hasExtra("id")
                && getIntent().hasExtra("name")
                && getIntent().hasExtra("startday")
                && getIntent().hasExtra("starthour")
                && getIntent().hasExtra("endday")
                && getIntent().hasExtra("endhour")) {

            id = getIntent().getStringExtra("id");
            name = getIntent().getStringExtra("name");
            day = getIntent().getStringExtra("startday");
            starhour = getIntent().getStringExtra("starthour");
            endday = getIntent().getStringExtra("endday");
            endhour = getIntent().getStringExtra("endhour");


            switch (day) {
                case "SUNDAY":
                    diaa = 0;
                    break;
                case "MONDAY":
                    diaa = 1;
                    break;
                case "TUESDAY":
                    diaa = 2;
                    break;
                case "WEDNESDAY":
                    diaa = 3;
                    break;
                case "THURSDAY":
                    diaa = 4;
                    break;
                case "FRIDAY":
                    diaa = 5;
                    break;
                case "SATURDAY":
                    diaa = 6;
                    break;
                default:
            }
            dia = diaa;

            horainicial = starhour;
            horafinal = endhour;

            editdateinitial = findViewById(R.id.editdateinitial);
            editdateinitial.setText(horainicial);

            editdatefinal = findViewById(R.id.editdatefinal);
            editdatefinal.setText(horafinal);

        } else {
            Toast.makeText(this, "Não há dados", Toast.LENGTH_SHORT).show();
        }
        int count = spinner.getAdapter().getCount() - SpinnerAdapter.STATIC_SPINNER_VALUES;

        for (int i = 0; i < count; i++) {

            DisciplinaModel disciplina = (DisciplinaModel) spinner.getAdapter().getItem(i);

            if (name.equals(disciplina.getNome())) {
                spinner.setSelection(i + SpinnerAdapter.STATIC_SPINNER_VALUES);
                break;
            }

        }

    }

    public ArrayList<DisciplinaModel> getInformation() {

        ArrayList<DisciplinaModel> disciplinas = new ArrayList<>();

        DataBase db = new DataBase(AttHorario.this);
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

            editdateinitial.setText(horainicial);
        } else {
            horafinal = String.format("%02d:%02d", hourOfDay, minute);

            editdatefinal.setText(horafinal);
        }
    }
}