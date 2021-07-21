package com.example.projeto1.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projeto1.R;
import com.example.projeto1.functions.DataBase;
import com.example.projeto1.functions.DrawableEventRenderer;
import com.example.projeto1.functions.Globals;
import com.example.projeto1.functions.Subject;
import com.priyanka.customcalendarlibrary.AgendaCalendarView;
import com.priyanka.customcalendarlibrary.CalendarManager;
import com.priyanka.customcalendarlibrary.CalendarPickerController;
import com.priyanka.customcalendarlibrary.models.BaseCalendarEvent;
import com.priyanka.customcalendarlibrary.models.CalendarEvent;
import com.priyanka.customcalendarlibrary.models.DayItem;
import com.priyanka.customcalendarlibrary.models.IDayItem;
import com.priyanka.customcalendarlibrary.models.IWeekItem;
import com.priyanka.customcalendarlibrary.models.WeekItem;
import com.priyanka.customcalendarlibrary.widgets.FloatingActionButton;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Testes extends AppCompatActivity implements CalendarPickerController {

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

    ImageView borda, titulo, backbutton8;
    EditText oi;
    TextView notaevent;
    Animation rotateOpen, rotateClose, fromBottom, toBottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_testes);
        onWindowFocusChanged(true);

        rotateOpen = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_open_animate);
        rotateClose = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_close_animate);
        toBottom = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.to_bottom_animate);
        fromBottom = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.from_bottom_animate);


        Button grades = (Button) findViewById(R.id.grades);
        grades.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Globals.todosostestes = 1;
                Intent intent = new Intent(Testes.this, TestesNotasActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Button avgbtn = (Button) findViewById(R.id.avgbtn);
        avgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Globals.globaldisciplina = 1;
                Intent intent = new Intent(Testes.this, Disciplinas.class);
                startActivity(intent);
                finish();
            }
        });
        backbutton8 = findViewById(R.id.backbutton8);
        backbutton8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Testes.this, Menu.class);
                startActivity(intent);
                finish();
            }
        });
        FloatingActionButton addbtn5 = findViewById(R.id.add_btn5);

        addbtn5.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Testes.this, AddTests.class);
                startActivity(intent);
                finish();
            }

        });

    }

    /*private void mockList(List<CalendarEvent> eventList) {

        Calendar startTime1 = Calendar.getInstance();
        Calendar endTime1 = Calendar.getInstance();
        endTime1.add(Calendar.DAY_OF_YEAR, 1);
        BaseCalendarEvent event1 = new BaseCalendarEvent("Evento 1", "Alguma coisa número 1", "Minha casa",
                ContextCompat.getColor(this, R.color.purple_200), startTime1, endTime1, true);
        eventList.add(event1);

        Calendar startTime2 = Calendar.getInstance();
        startTime2.add(Calendar.DAY_OF_YEAR, 1);
        Calendar endTime2 = Calendar.getInstance();
        endTime2.add(Calendar.DAY_OF_YEAR, 1);
        BaseCalendarEvent event2 = new BaseCalendarEvent("Evento 2", "Alguma coisa número 2", "Faço dinheiro",
                ContextCompat.getColor(this, R.color.black), startTime2, endTime2, true);
        eventList.add(event2);

        Calendar startTime3 = Calendar.getInstance();
        Calendar endTime3 = Calendar.getInstance();
        startTime3.set(Calendar.HOUR_OF_DAY, 14);
        startTime3.set(Calendar.MINUTE, 0);
        endTime3.set(Calendar.HOUR_OF_DAY, 15);
        endTime3.set(Calendar.MINUTE, 0);
        DrawableCalendarEvent event3 = new DrawableCalendarEvent("Evento 3", "", "LOL",
                ContextCompat.getColor(this, R.color.roxo), startTime3, endTime3, false, android.R.drawable.ic_dialog_info);
        eventList.add(event3);


        //Meu Evento
        Calendar startTime4 = Calendar.getInstance();
        Calendar endTime4 = Calendar.getInstance();
        startTime4.set(2021, 11, 23);//05.04.17
        endTime4.set(2021, 11, 23);//05.04.17

        BaseCalendarEvent event4 = new BaseCalendarEvent("Opa galerinha", "", "Qualquer coisa",
                ContextCompat.getColor(this, R.color.roxo), startTime4, endTime4, true);
        eventList.add(event4);
    }*/

    @Override
    protected void onResume() {

        super.onResume();


        AgendaCalendarView mAgendaCalendarView = findViewById(R.id.agenda_calendar_view);

        // minimum and maximum date of our calendar
        // 2 month behind, one year ahead, example: March 2015 <-> May 2015 <-> May 2016
        Calendar minDate = Calendar.getInstance();
        Calendar maxDate = Calendar.getInstance();

        minDate.add(Calendar.MONTH, -2);
        minDate.set(Calendar.DAY_OF_MONTH, 1);
        maxDate.add(Calendar.YEAR, 1);


        // get testes

        List<CalendarEvent> eventList = new ArrayList<>();

        // database testes
        DataBase db = new DataBase(Testes.this);
      Cursor c = db.readTeste();

        if (c != null && c.moveToFirst()) {

            do {
                String title = c.getString(1);
                String date = c.getString(2);
                String disciplina = c.getString(6);
                Integer color = c.getInt(7);
                Integer id = c.getInt(0);


                Calendar startTime = Calendar.getInstance();
                String dia = date.substring(8, 10);
                String month = date.substring(6, 7);
                String year = date.substring(0, 4);


                startTime.set(Integer.parseInt(year), Integer.parseInt(month) - 1, Integer.parseInt(dia));
                Calendar endTime = Calendar.getInstance();
                endTime.add(Calendar.HOUR_OF_DAY, 0);

                BaseCalendarEvent event = new BaseCalendarEvent(
                        id.longValue(),
                        color,
                        title,
                        "",
                        disciplina,
                        startTime.getTimeInMillis(),
                        endTime.getTimeInMillis(),
                        0, "P01DT5H0M20S");
                eventList.add(event);
            } while (c.moveToNext());

        }


        CalendarManager calendarManager = CalendarManager.getInstance(getApplicationContext());
        calendarManager.buildCal(minDate, maxDate, Locale.getDefault(), new DayItem(), new WeekItem());
        calendarManager.loadEvents(eventList, new BaseCalendarEvent());


        List<CalendarEvent> readyEvents = calendarManager.getEvents();
        List<IDayItem> readyDays = calendarManager.getDays();
        List<IWeekItem> readyWeeks = calendarManager.getWeeks();

        mAgendaCalendarView.init(Locale.getDefault(), readyWeeks, readyDays, readyEvents, this);
        mAgendaCalendarView.addEventRenderer(new DrawableEventRenderer());
        mAgendaCalendarView.enableCalenderView(true);

    }


    @Override
    public void onDaySelected(IDayItem dayItem) {

    }


    @Override
    public void onEventSelected(CalendarEvent event) {
        if (Math.toIntExact(event.getId()) != 0) {

            Date date = new Date(event.getStartTime().getTime().toString());
            SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");

            String datetext = dt.format(date);

            Intent intent = new Intent(Testes.this, AttTeste.class);

            intent.putExtra("id", String.valueOf(Math.toIntExact(event.getId())));
            intent.putExtra("day", datetext);
            intent.putExtra("name", String.valueOf(event.getTitle()));
            intent.putExtra("materia", String.valueOf(event.getLocation()));


            startActivity(intent);
        }


    }


    @Override
    public void onScrollToDate(Calendar calendar) {

    }

}

