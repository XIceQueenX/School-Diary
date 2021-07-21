package com.example.projeto1.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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

public class AttTeste extends AppCompatActivity {

    String id,name,day,materia,materia1,adddisciplina;
    EditText atttitletest;
    TextView attdatetest;
    Spinner spinner;
    ArrayList<String> nomes_disciplinas;
    ArrayAdapter adapter;
    DatePickerDialog datePickerDialog;
    Calendar calendar;
    FloatingActionButton btnatttest;
    Button btnremovetest;

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
        setContentView(R.layout.activity_att_teste);
        onWindowFocusChanged(true);

        atttitletest = findViewById(R.id.atttitletest );
        attdatetest = findViewById(R.id.attdatetest);

        btnatttest = findViewById(R.id.btnatttest);

        spinner = findViewById(R.id.attsubjecttest);


        ArrayList<DisciplinaModel> disciplinas = getInformation();
        SpinnerAdapter spinnerAdapter = new SpinnerAdapter(getApplicationContext(), disciplinas);
        spinnerAdapter.setDropDownViewResource(R.layout.my_dropdown_item);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {


                if(position == 1) { // "Adicionar Nova Disciplina"
                    Globals.globaladdisciplina = 7;
                    Intent intent = new Intent(AttTeste.this, AddDisciplina.class);
                    startActivity(intent);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}

        });

        spinner.setAdapter(spinnerAdapter);
        getIntentData();

        calendar = Calendar.getInstance();

        btnremovetest = findViewById(R.id.btnremovetest);
        btnremovetest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataBase db = new DataBase(AttTeste.this);
                db.deleteTeste(id);
                Intent intent = new Intent(AttTeste.this, Testes.class);
                startActivity(intent);
                finish();
            }
        });

        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar dataSelect = Calendar.getInstance();
                dataSelect.set(year, month, dayOfMonth);
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                attdatetest.setText(format.format(dataSelect.getTime()));
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        attdatetest.setOnClickListener(v -> datePickerDialog.show());

        btnatttest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String title = atttitletest.getText().toString();
                String date = attdatetest.getText().toString();

                if (title.isEmpty() || date.isEmpty() ){
                    if (title.isEmpty()){
                        atttitletest.setError("Campo Vazio");
                    }

                    if (date.isEmpty()){
                        attdatetest.setError("Campo Vazio");
                    }

                }else {

                    View row = spinner.getSelectedView();

                    if(row instanceof TextView) {
                        ((TextView) row).setError("Campo vazio");
                        return;
                    }

                    int position = ((AppCompatSpinner) row.getParent()).getSelectedItemPosition() - SpinnerAdapter.STATIC_SPINNER_VALUES;

                    DisciplinaModel disciplina = ((SpinnerAdapter) spinner.getAdapter()).getDisciplina(position);

                    DataBase myDB = new DataBase(AttTeste.this);
                    myDB.updateTeste(id,title, date, String.valueOf(disciplina.getId()));

                    Intent intent = new Intent(AttTeste.this, Testes.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }


    void getIntentData(){
        if(getIntent().hasExtra("id") && getIntent().hasExtra("name") && getIntent().hasExtra("day") && getIntent().hasExtra("materia")){
            id = getIntent().getStringExtra("id");
            name = getIntent().getStringExtra("name");
            day = getIntent().getStringExtra("day");
            materia1 = getIntent().getStringExtra("materia");

            atttitletest.setText(name);
            attdatetest.setText(day);

            int count = spinner.getAdapter().getCount() - SpinnerAdapter.STATIC_SPINNER_VALUES;

            for (int i = 0; i < count; i++) {

                DisciplinaModel disciplina = (DisciplinaModel) spinner.getAdapter().getItem(i);

                if(materia1.equals(disciplina.getNome())) {
                    spinner.setSelection(i + SpinnerAdapter.STATIC_SPINNER_VALUES);
                    break;
                }

            }


        } else {
            Toast.makeText(this, "Não há dados", Toast.LENGTH_SHORT).show();
        }

    }

    public ArrayList<DisciplinaModel> getInformation() {

        ArrayList<DisciplinaModel> disciplinas = new ArrayList<>() ;

        DataBase db = new DataBase(AttTeste.this);
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



/*package com.example.projeto1.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projeto1.R;
import com.example.projeto1.functions.DataBase;
import com.example.projeto1.functions.SpinnerAdapter;
import com.example.projeto1.model.DisciplinaModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import static java.lang.String.valueOf;

public class AttTeste extends AppCompatActivity {

    String id, name, day, materia, materia1, adddisciplina;
    EditText atttitletest;
    TextView attdatetest;
    Spinner spinner;
    ArrayList<String> nomes_disciplinas;
    ArrayAdapter adapter;
    DatePickerDialog datePickerDialog;
    Calendar calendar;
    FloatingActionButton btnatttest;
    Button btnremovetest;

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
        setContentView(R.layout.activity_att_teste);

        atttitletest = findViewById(R.id.atttitletest);
        attdatetest = findViewById(R.id.attdatetest);

        btnatttest = findViewById(R.id.btnatttest);

        spinner = findViewById(R.id.attsubjecttest);

        ArrayList<DisciplinaModel> disciplinas = getInformation();
        SpinnerAdapter spinnerAdapter = new SpinnerAdapter(getApplicationContext(), disciplinas);
        spinnerAdapter.setDropDownViewResource(R.layout.my_dropdown_item);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {

                if (position == 1) { // "Adicionar Nova Disciplina"
                    Intent intent = new Intent(AttTeste.this, AddDisciplina.class);
                    startActivity(intent);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }

        });

        spinner.setAdapter(spinnerAdapter);

        getIntentData();

        calendar = Calendar.getInstance();

        btnremovetest = findViewById(R.id.btnremovetest);
        btnremovetest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataBase db = new DataBase(AttTeste.this);
                db.deleteTeste(id);
                Intent intent = new Intent(AttTeste.this, Testes.class);
                startActivity(intent);
                finish();
            }
        });

        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar dataSelect = Calendar.getInstance();
                dataSelect.set(year, month, dayOfMonth);
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                attdatetest.setText(format.format(dataSelect.getTime()));
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        attdatetest.setOnClickListener(v -> datePickerDialog.show());

        btnatttest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String title = atttitletest.getText().toString();

                String date = attdatetest.getText().toString();

                if (title.isEmpty() || date.isEmpty() || materia.isEmpty()) {
                    if (title.isEmpty()) {
                        atttitletest.setError("Campo Vazio");
                    }

                    if (date.isEmpty()) {
                        attdatetest.setError("Campo Vazio");
                    }

                } else {

                    View row = spinner.getSelectedView();

                    if (row instanceof TextView) {
                        ((TextView) row).setError("Campo vazio");
                        return;
                    }

                    int position = ((AppCompatSpinner) row.getParent()).getSelectedItemPosition() - SpinnerAdapter.STATIC_SPINNER_VALUES;

                    DisciplinaModel disciplina = ((SpinnerAdapter) spinner.getAdapter()).getDisciplina(position);

                    DataBase myDB = new DataBase(AttTeste.this);
                    myDB.updateTeste(id, title, date, String.valueOf(disciplina.getId()));

                    Intent intent = new Intent(AttTeste.this, Testes.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }


   /* void getIntentData() {
        if (getIntent().hasExtra("id") && getIntent().hasExtra("name") && getIntent().hasExtra("day")&& getIntent().hasExtra("materia")) {
            id = getIntent().getStringExtra("id");
            name = getIntent().getStringExtra("name");
            day = getIntent().getStringExtra("day");
            materia1 = getIntent().getStringExtra("materia");

            atttitletest.setText(name);
            attdatetest.setText(day);

        } else {
            Toast.makeText(this, "Não há dados", Toast.LENGTH_SHORT).show();
        }

    }*/

   /* void getIntentData() {

        Intent intent = getIntent();

        if(!intent.hasExtra("id")
                || !intent.hasExtra("name")
               // || !intent.hasExtra("day")
                || !intent.hasExtra("materia")) {

            Toast.makeText(this, "Não há dados", Toast.LENGTH_SHORT).show();
            return;
        }

        id = getIntent().getStringExtra("id");
        name = getIntent().getStringExtra("name");
        day = getIntent().getStringExtra("day");
        materia1 = getIntent().getStringExtra("materia");


        atttitletest.setText(name);
        attdatetest.setText(day);


        int count = spinner.getAdapter().getCount() - SpinnerAdapter.STATIC_SPINNER_VALUES;

        for (int i = 0; i < count; i++) {

            DisciplinaModel disciplina = (DisciplinaModel) spinner.getAdapter().getItem(i);

            if(materia.equals(disciplina.getNome())) {
                spinner.setSelection(i + SpinnerAdapter.STATIC_SPINNER_VALUES);
                break;
            }

        }

    }
    public ArrayList<DisciplinaModel> getInformation() {

        ArrayList<DisciplinaModel> disciplinas = new ArrayList<>() ;

        DataBase db = new DataBase(AttTeste.this);
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
}*/