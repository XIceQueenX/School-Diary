package com.example.projeto1.functions;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projeto1.activity.AttAnotacao;
import com.example.projeto1.R;

import java.util.ArrayList;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.MyViewHolder> {

    private ArrayList<String> id, title, subject, content, materia, date, color;
    private ArrayList<Integer> cor;
    private Context context;
    private OnNoteClickListener mOnNoteClickListener;
    private OnNoteLongClickListener mOnNoteLongClickListener;
    DataBase db;
    RecyclerView noterv;


    public NotesAdapter(Context context, ArrayList id, ArrayList title, ArrayList content, ArrayList cor , ArrayList materia , OnNoteClickListener onNoteClickListener,
                        OnNoteLongClickListener onNoteLongClickListener) {
        this.context = context;
        this.id = id;
        this.title = title;
        this.content = content;
        this.cor = cor;
        this.materia = materia;
        this.mOnNoteClickListener = onNoteClickListener;
        this.mOnNoteLongClickListener = onNoteLongClickListener;
    }

    @NonNull
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_note,
                parent, false);
        return new MyViewHolder(view, mOnNoteClickListener, mOnNoteLongClickListener);
    }

    public void onBindViewHolder(@NonNull NotesAdapter.MyViewHolder holder, int position) {
        int posReverse = id.size() - (position + 1);

        holder.id.setText(String.valueOf(id.get(posReverse)));
        holder.title.setText(String.valueOf(title.get(posReverse)));
        holder.content.setText(String.valueOf(content.get(posReverse)));
        GradientDrawable c = (GradientDrawable) holder.linhadisciplina.getBackground();
        c.setColor(cor.get(posReverse));
//        holder.time.setText(String.valueOf(materia.get(posReverse)));

        holder.layoutnote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AttAnotacao.class);
                intent.putExtra("id", String.valueOf(id.get(posReverse)));
                intent.putExtra("title", String.valueOf(title.get(posReverse)));
                intent.putExtra("content", String.valueOf(content.get(posReverse)));
                intent.putExtra("subject", String.valueOf(materia.get(posReverse)));
                context.startActivity(intent);
            }
        });

    }

    public int getItemCount() {
        return id.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView title, time, content, id;
        OnNoteClickListener onNoteClickListener;
        OnNoteLongClickListener onNoteLongClickListener;
        LinearLayout layoutnote;
        ImageView linhadisciplina;


        public MyViewHolder(@NonNull View itemView, OnNoteClickListener onNoteClickListener,
                            OnNoteLongClickListener onNoteLongClickListener) {
            super(itemView);
            title = itemView.findViewById(R.id.note_title);
            time = itemView.findViewById(R.id.time);
            content = itemView.findViewById(R.id.note_content);
            layoutnote = itemView.findViewById(R.id.layoutnote);
            id = itemView.findViewById(R.id.note_id);
            linhadisciplina = itemView.findViewById(R.id.linhadisciplina);
            this.onNoteClickListener = onNoteClickListener;
            this.onNoteLongClickListener = onNoteLongClickListener;

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            this.onNoteClickListener.onNoteClick(getAdapterPosition());
        }


        @Override
        public boolean onLongClick(View view) {
            this.onNoteLongClickListener.onNoteLongClick(getAdapterPosition());
            return true;
        }
    }

    public interface OnNoteClickListener {
        void onNoteClick(int position);
    }

    public interface OnNoteLongClickListener {
        void onNoteLongClick(int position);
    }


}

