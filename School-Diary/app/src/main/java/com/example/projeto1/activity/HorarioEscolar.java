package com.example.projeto1.activity;

import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Bundle;

import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projeto1.R;
import com.example.projeto1.functions.DataBase;
import com.example.projeto1.functions.Globals;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import me.jlurena.revolvingweekview.DayTime;
import me.jlurena.revolvingweekview.WeekView;
import me.jlurena.revolvingweekview.WeekViewEvent;

import org.threeten.bp.DayOfWeek;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.format.TextStyle;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

/**
 * This is a base activity which contains week view and all the codes necessary to initialize the
 * week view.
 * Created by Raquib-ul-Alam Kanak on 1/3/2014.
 * Website: http://alamkanak.github.io
 */
public class HorarioEscolar extends AppCompatActivity implements WeekView.EventClickListener, WeekView.WeekViewLoader,
        WeekView.EventLongPressListener, WeekView.EmptyViewLongPressListener, WeekView.EmptyViewClickListener,
        /*WeekView.AddEventClickListener,*/ WeekView.DropListener {

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

    private static final int TYPE_DAY_VIEW = 1;
    private static final int TYPE_THREE_DAY_VIEW = 2;
    private static final int TYPE_WEEK_VIEW = 3;
    private static final Random random = new Random();
    protected WeekView mWeekView;
    private int mWeekViewType = TYPE_THREE_DAY_VIEW;

    Animation rotateOpen, rotateClose, fromBottom, toBottom;
    boolean clicked;
    int type;

    FloatingActionButton btnadd ;

    private static int randomColor() {
        return Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256));
    }

    protected String getEventTitle(DayTime time) {
        return String.format(Locale.getDefault(), "Event of %s %02d:%02d", time.getDay().getDisplayName(TextStyle
                .SHORT, Locale.getDefault()), time.getHour(), time.getMinute());
    }

    //@Override
    //public void onAddEventClicked(DayTime startTime, DayTime endTime) {
        //Toast.makeText(this, "Add event clicked.", Toast.LENGTH_SHORT).show();
    //}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onWindowFocusChanged(true);
        setContentView(R.layout.activityhorarioescolar);

        rotateOpen = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_open_animate);
        rotateClose = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_close_animate);
        toBottom = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.to_bottom_animate);
        fromBottom = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.from_bottom_animate);

        Globals.global = 1;

        // Get a reference for the week view in the layout.
        mWeekView = findViewById(R.id.weekView);

        // Show a toast message about the touched event.
        mWeekView.setOnEventClickListener(this);

        // The week view has infinite scrolling horizontally. We have to provide the events of a
        // month every time the month changes on the week view.
        mWeekView.setWeekViewLoader(this);

        // Set long press listener for events.
        mWeekView.setEventLongPressListener(this);

        // Set long press listener for empty view
        mWeekView.setEmptyViewLongPressListener(this);

        // Set EmptyView Click Listener
        mWeekView.setEmptyViewClickListener(this);

        // Set AddEvent Click Listener
        //mWeekView.setAddEventClickListener(this);

        // Set Drag and Drop Listener
        mWeekView.setDropListener(this);

        //mWeekView.setAutoLimitTime(true);
        //mWeekView.setLimitTime(0, 24);

        //mWeekView.setMinTime(10);
        //mWeekView.setMaxTime(20);

        // Set up a date time interpreter to interpret how the date and time will be formatted in
        // the week view. This is optional.
        setupDateTimeInterpreter();

        Typeface customTypeface = Typeface.createFromAsset(this.getAssets(), "fonts/gothic.TTF");
        mWeekView.setTypeface(customTypeface);


        rotateOpen = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_open_animate);
        rotateClose = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_close_animate);
        toBottom = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.to_bottom_animate);
        fromBottom = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.from_bottom_animate);


        clicked = false;
        btnadd = findViewById(R.id.add_btnhorario);
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HorarioEscolar.this, AddHora.class);
                startActivity(intent);
                finish();
            }
        });

        ImageView backbutton = (ImageView)findViewById(R.id.backbutton11);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HorarioEscolar.this, com.example.projeto1.activity.Menu.class);
                startActivity(intent);
                finish();
            }
        });

        Button button2 = (Button)findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mWeekView.setNumberOfVisibleDays(3);

                // Lets change some dimensions to best fit the view.
                mWeekView.setColumnGap((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8,
                        getResources().getDisplayMetrics()));
                mWeekView.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12,
                        getResources().getDisplayMetrics()));
                mWeekView.setEventTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12,
                        getResources().getDisplayMetrics()));
            }
        });
      /*  btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (clicked) {

                    btnadd.startAnimation(rotateClose);
                    /*btnaddmateria.setClickable(false);
                    btnaddtarefa.setClickable(false);

                    btnaddmateria.startAnimation(toBottom);
                    btnaddtarefa.startAnimation(toBottom);

                    clicked = false;
                } else {
                    btnadd.startAnimation(rotateOpen);
                    /*btnaddmateria.setClickable(true);
                    btnaddtarefa.setClickable(true);

                    btnaddmateria.startAnimation(fromBottom);
                    btnaddtarefa.startAnimation(fromBottom);

                    clicked = true;
                }
            }
        });*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onDrop(View view, DayTime day) {
        Toast.makeText(this, "View dropped to " + day.toString(), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onEmptyViewClicked(DayTime day) {
        //Toast.makeText(this, "Empty view" + " clicked: " + getEventTitle(day), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEmptyViewLongPress(DayTime time) {
        int size = getEventTitle(time).length();
        Toast.makeText(this, "Horário Vago: " +  getEventTitle(time).substring(9,10).toUpperCase().concat(getEventTitle(time).substring(10,size)) , Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEventClick(WeekViewEvent event, RectF eventRect) {

        Intent intent = new Intent(HorarioEscolar.this, AttHorario.class);
        intent.putExtra("id", (event.getIdentifier()));
        intent.putExtra("name",(event.getName()));

        intent.putExtra("startday", (event.getStartTime().getDay().toString()));
        intent.putExtra("endday", (event.getEndTime().getDay().toString()));
        String starhour = String.format("%02d:%02d", (event.getStartTime().getHour()),event.getStartTime().getMinute());
        String endhour = String.format("%02d:%02d", (event.getEndTime().getHour()),event.getEndTime().getMinute() );


        intent.putExtra("starthour", (starhour));
        intent.putExtra("endhour", (endhour));
        startActivity(intent);

    }

    @Override
    public void onEventLongPress(WeekViewEvent event, RectF eventRect) {

        new AlertDialog.Builder(this)
                .setTitle("Deletar Aula")
                .setMessage("Tem certeza que deseja deletar essa aula?")

                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        DataBase db = new DataBase(HorarioEscolar.this);
                        db.deleteHour(event.getIdentifier());
                        mWeekView.notifyDatasetChanged();
                    }
                })
                .setNegativeButton(android.R.string.no, null)
                .setIcon(R.drawable.ic_baseline_warning_24)
                .show();
    }

    //@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        setupDateTimeInterpreter();
        switch (type) {
            /*case :
                mWeekView.goToToday();
                return true;*/
            /*case R.id.action_day_view:
                if (mWeekViewType != TYPE_DAY_VIEW) {
                    item.setChecked(!item.isChecked());
                    mWeekViewType = TYPE_DAY_VIEW;
                    mWeekView.setNumberOfVisibleDays(1);

                    // Lets change some dimensions to best fit the view.
                    mWeekView.setColumnGap((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8,
                            getResources().getDisplayMetrics()));
                    mWeekView.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12,
                            getResources().getDisplayMetrics()));
                    mWeekView.setEventTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12,
                            getResources().getDisplayMetrics()));
                }
                return true;*/
            case 1:
                if (mWeekViewType != TYPE_THREE_DAY_VIEW) {
                    item.setChecked(!item.isChecked());
                    mWeekViewType = TYPE_THREE_DAY_VIEW;
                    mWeekView.setNumberOfVisibleDays(3);

                    // Lets change some dimensions to best fit the view.
                    mWeekView.setColumnGap((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8,
                            getResources().getDisplayMetrics()));
                    mWeekView.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12,
                            getResources().getDisplayMetrics()));
                    mWeekView.setEventTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12,
                            getResources().getDisplayMetrics()));
                }
                return true;
           /* case R.id.action_week_view:
                if (mWeekViewType != TYPE_WEEK_VIEW) {
                    item.setChecked(!item.isChecked());
                    mWeekViewType = TYPE_WEEK_VIEW;
                    mWeekView.setNumberOfVisibleDays(7);

                    // Lets change some dimensions to best fit the view.
                    mWeekView.setColumnGap((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2,
                            getResources().getDisplayMetrics()));
                    mWeekView.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10,
                            getResources().getDisplayMetrics()));
                    mWeekView.setEventTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10,
                            getResources().getDisplayMetrics()));
                }
                return true;*/
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public List<? extends WeekViewEvent> onWeekViewLoad() {

        // Populate the week view with some events.
        List<WeekViewEvent> events = new ArrayList<>();

        DataBase db = new DataBase(HorarioEscolar.this);
        Cursor c = db.readHour();

        if(c != null && c.moveToFirst()) {

            do {
                int id = c.getInt(4);
                String disciplina = c.getString(5);
                int dia_semana = c.getInt(1);
                String horaini = c.getString(2);
                String horafim = c.getString(3);
                int cor = c.getInt(7);
                String sala =  c.getString( 6);

                // dia atual
                LocalDateTime aux = LocalDateTime.now();

                LocalDateTime aux1 = LocalDateTime.now();

                // day && hour
                int diff = aux.getDayOfWeek().getValue() - dia_semana;
                aux = aux.minusDays(diff);
                aux = aux.withHour(Integer.parseInt(horaini.substring(0,2))).withMinute(Integer.parseInt(horaini.substring(3,4))).withSecond(0).withNano(0);

                int diffs = aux1.getDayOfWeek().getValue() - dia_semana;
                aux1 = aux1.minusDays(diffs);
                aux1 = aux.withHour(Integer.parseInt(horafim.substring(0,2))).withMinute(Integer.parseInt(horafim.substring(3,4))).withSecond(0).withNano(0);

                DayTime startTime = new DayTime(aux);
                DayTime endTime = new DayTime(aux1);

                WeekViewEvent event = new WeekViewEvent(String.valueOf(id),  sala , disciplina  , startTime, endTime);

                event.setColor(cor);

                events.add(event);

            } while (c.moveToNext());

        }else Toast.makeText(this,"Horário Vazio", Toast.LENGTH_SHORT).show();

        return events;
    }

    /**
     * Set up a date time interpreter which will show short date values when in week view and long
     * date values otherwise.
     */
    private void setupDateTimeInterpreter() {
        mWeekView.setDayTimeInterpreter(new WeekView.DayTimeInterpreter() {
            @Override
            public String interpretDay(int date) {
                return DayOfWeek.of(date).getDisplayName(TextStyle.SHORT, Locale.getDefault()).toUpperCase();
            }

            @Override
            public String interpretTime(int hour, int minutes) {
                String strMinutes = String.format(Locale.getDefault(), "%02d", minutes);

                if (hour == 24) {
                    hour = 0;}
                if (hour == 0){
                    hour = 0; }
                return hour + ":00";
            }
        });
    }

    private final class DragTapListener implements View.OnLongClickListener {
        @Override
        public boolean onLongClick(View v) {
            ClipData data = ClipData.newPlainText("", "");
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
            v.startDrag(data, shadowBuilder, v, 0);
            return true;
        }
    }
}
