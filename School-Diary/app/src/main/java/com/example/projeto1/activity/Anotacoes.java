package com.example.projeto1.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projeto1.R;
import com.example.projeto1.functions.DataBase;
import com.example.projeto1.functions.NotesAdapter;
import com.example.projeto1.functions.VerticalSpacingItemDecorator;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class Anotacoes extends AppCompatActivity implements NotesAdapter.OnNoteClickListener,
        NotesAdapter.OnNoteLongClickListener,  View.OnClickListener {

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

    RecyclerView notesrv;
    FloatingActionButton addnote;
    DataBase db;
    ArrayList<String> arrayid, arraytitle, arraycontent,materia;
    NotesAdapter notesAdapter;
    ImageView backbutton5;
    ArrayList<Integer> cor;
    EditText search;
    String s;
    Animation rotateOpen, rotateClose, fromBottom, toBottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anotacoes);
        onWindowFocusChanged(true);



        notesrv = findViewById(R.id.notes);
        addnote = findViewById(R.id.addnote);

        db = new DataBase(Anotacoes.this);
        arrayid = new ArrayList<>();
        arraytitle = new ArrayList<>();
        arraycontent = new ArrayList<>();
        cor = new ArrayList<>();
        materia = new ArrayList<>();


        backbutton5 = findViewById(R.id.backbutton5);
        backbutton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Anotacoes.this, Menu.class);
                startActivity(intent);
            }
        });

        storeDataInArrays();


        notesAdapter = new NotesAdapter(Anotacoes.this,arrayid, arraytitle, arraycontent,cor , materia , this,this);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
        notesrv.setLayoutManager(staggeredGridLayoutManager);
        VerticalSpacingItemDecorator itemDecorator = new VerticalSpacingItemDecorator(10);
        notesrv.addItemDecoration(itemDecorator);
        notesrv.setAdapter(notesAdapter);

        search = findViewById(R.id.search);
        s = search.getText().toString();



        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence == "") storeDataInArrays();
                else {
                    Clear();
                    Filter(charSequence.toString().trim());
                    notesAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });


        addnote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(Anotacoes.this, AddAnotacao.class);
                    startActivity(intent);
            }
        });


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                int posReverse = arrayid.size() - (position + 1);

                db.deleteNote(arrayid.remove(posReverse));
                notesAdapter.notifyDataSetChanged();

            }
        }).attachToRecyclerView(notesrv);

    }
/*
    @Override
    public void onClick(View v) {

    }

    @Override
    public void onNoteClick(int position) {

    }

    @Override
    public void onNoteLongClick(int position) {

    }
*/
    public void storeDataInArrays(){
        Cursor cursor = db.readNotes();

        ImageView imageView = (ImageView)findViewById(R.id.imageView41);
        TextView textview = (TextView)findViewById(R.id.textView31);

        if(cursor.getCount() == 0){
            imageView.setVisibility(View.VISIBLE);
            textview.setVisibility(View.VISIBLE);
        }else{
            while (cursor.moveToNext()){
                arrayid.add(cursor.getString(0));
                arraytitle.add(cursor.getString(1));
                arraycontent.add(cursor.getString(2));
                materia.add(cursor.getString(4));
                cor.add(cursor.getInt(5));

            }
            imageView.setVisibility(View.INVISIBLE);
            textview.setVisibility(View.INVISIBLE);
        }
    }

    public void Filter(String s){
        ImageView imageView = (ImageView)findViewById(R.id.imageView41);
        TextView textview = (TextView)findViewById(R.id.textView31);

        Cursor cursor = db.readNotesFilter(s);
        if(cursor.getCount() == 0){

            imageView.setVisibility(View.VISIBLE);
            textview.setVisibility(View.VISIBLE);

        }else{
            while (cursor.moveToNext()){
                arrayid.add(cursor.getString(0));
                arraytitle.add(cursor.getString(1));
                arraycontent.add(cursor.getString(2));
                materia.add(cursor.getString(4));
                cor.add(cursor.getInt(5));
            }
        }
        imageView.setVisibility(View.INVISIBLE);
        textview.setVisibility(View.INVISIBLE);
    }

    public void Clear (){
        arrayid.clear();
        arraytitle.clear();
        arraycontent.clear();
        materia.clear();
        cor.clear();
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onNoteClick(int position) {

    }

    @Override
    public void onNoteLongClick(int position) {

    }
}