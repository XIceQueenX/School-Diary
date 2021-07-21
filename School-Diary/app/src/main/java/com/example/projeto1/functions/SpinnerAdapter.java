package com.example.projeto1.functions;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.projeto1.R;
import com.example.projeto1.model.DisciplinaModel;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class SpinnerAdapter extends ArrayAdapter {

    public static final int STATIC_SPINNER_VALUES = 2;

    private ArrayList<DisciplinaModel> disciplinas;

    public SpinnerAdapter(@NonNull Context context, ArrayList disciplinas) {
        super(context, 0, disciplinas);
        this.disciplinas = disciplinas;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public int getCount() {
        return super.getCount() + STATIC_SPINNER_VALUES;
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {



        if(position == 0) {

            TextView first = new TextView(getContext());
            first.setText("\n");
            return first;

        }

        if(position == 1) {

            TextView second = new TextView(getContext());
            second.setText("\t\tAdicionar Nova Disciplina\n");

            return second;

        }


        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.my_dropdown_item, parent, false);


        TextView nomedisciplina = row.findViewById(R.id.text);
        ImageView cordisciplina = row.findViewById(R.id.cordisciplina2);



        position = position - STATIC_SPINNER_VALUES;

        nomedisciplina.setText(disciplinas.get(position).getNome());

        GradientDrawable c = (GradientDrawable) cordisciplina.getBackground();
        c.setColor(Integer.parseInt(String.valueOf(disciplinas.get(position).getColor())));
        c.setStroke(1, Integer.parseInt(String.valueOf(disciplinas.get(position).getColor())));


        return row;

    }

    public DisciplinaModel getDisciplina(int position) {
        return disciplinas.get(position);
    }

}
