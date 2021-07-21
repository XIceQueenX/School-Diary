package com.example.projeto1.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.telephony.gsm.GsmCellLocation;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projeto1.R;
import com.example.projeto1.functions.DataBase;
import com.example.projeto1.functions.Globals;
import com.example.projeto1.model.DisciplinaModel;
import com.example.projeto1.functions.SpinnerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import static java.lang.String.valueOf;

public class AttTarefa extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText atttitle, attnotes;
    TextView attdate;
    FloatingActionButton btnupdate, btndelete;
    String title, notes, id, date, subject;
    DatePickerDialog datePickerDialog;
    Calendar calendar;
    String materia,adddisciplina;
    ArrayList<String> arrayid, arrayname, arraycolor;
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
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atttarefa);
        onWindowFocusChanged(true);


        atttitle = findViewById(R.id.addtitle);
        attnotes = findViewById(R.id.addnotes);
        attdate = findViewById(R.id.attdate);

        btnupdate = findViewById(R.id.btnadd);

        arrayid = new ArrayList<>();
        arraycolor = new ArrayList<>();
        arrayname = new ArrayList<>();

        calendar = Calendar.getInstance();

        spinner = findViewById(R.id.attsubject);



        ImageView backbutton = findViewById(R.id.backbutton4);
        backbutton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AttTarefa.this, Tarefa.class);
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
                    Globals.globaladdisciplina = 6;
                    Intent intent = new Intent(AttTarefa.this, AddDisciplina.class);
                    startActivity(intent);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}

        });

        spinner.setAdapter(spinnerAdapter);


        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar dataSelect = Calendar.getInstance();
                dataSelect.set(year,month,dayOfMonth);
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                attdate.setText(format.format(dataSelect.getTime()));
            }
        },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));


        attdate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }

        });



        btnupdate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                title = atttitle.getText().toString().trim();
                notes = attnotes.getText().toString().trim();

                DataBase db = new DataBase(AttTarefa.this);
                db.updateData(id, title, notes);

                Intent intent = new Intent(AttTarefa.this, Tarefa.class);
                startActivity(intent);
                finish();


            }

        });


        getIntentData();

    }

    void getIntentData() {

        Intent intent = getIntent();

        if(!intent.hasExtra("id")
                || !intent.hasExtra("title")
                || !intent.hasExtra("notes")
                || !intent.hasExtra("date")
                || !intent.hasExtra("subject")) {

            Toast.makeText(this, "Não há dados", Toast.LENGTH_SHORT).show();
            return;
        }


        id = intent.getStringExtra("id");
        title = intent.getStringExtra("title");
        notes = intent.getStringExtra("notes");
        date = intent.getStringExtra("date");
        materia = intent.getStringExtra("subject");


        atttitle.setText(title);
        attnotes.setText(notes);
        attdate.setText(date);


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

        DataBase db = new DataBase(AttTarefa.this);
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
