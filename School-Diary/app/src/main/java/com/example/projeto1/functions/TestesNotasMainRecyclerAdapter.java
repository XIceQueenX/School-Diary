package com.example.projeto1.functions;

import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projeto1.model.TesteModel;

import com.example.projeto1.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestesNotasMainRecyclerAdapter extends RecyclerView.Adapter<TestesNotasMainRecyclerAdapter.ViewHolder> {

    private Map<String, List<TesteModel>> testes = new HashMap();

    public TestesNotasMainRecyclerAdapter(Map<String, List<TesteModel>> testes ){
        this.testes = testes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.testes_notas_disciplina_row, parent, false);

        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        String[] disciplinas = new String[testes.size()];

        testes.keySet().toArray(disciplinas);

        String disciplina = disciplinas[position];

        holder.disciplinaNameTextView.setText(disciplina);

        TestesNotasChildRecyclerAdapter recyclerAdapter = new TestesNotasChildRecyclerAdapter(testes.get(disciplina));
        holder.testesNotasChcilRecyclerView.setAdapter(recyclerAdapter);

    }

    @Override
    public int getItemCount() {
        return testes.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView disciplinaNameTextView;
        RecyclerView testesNotasChcilRecyclerView;
        ConstraintLayout grade;
        CardView cardViewgrade;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            disciplinaNameTextView = itemView.findViewById(R.id.testes_notas_disciplina_text_view);
            testesNotasChcilRecyclerView = itemView.findViewById(R.id.testes_notas_child_recycler_view);
            grade = itemView.findViewById(R.id.grade);
            cardViewgrade = itemView.findViewById(R.id.cardViewgrade);

        }

    }



}
