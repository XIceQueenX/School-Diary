package com.example.projeto1.functions;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.example.projeto1.activity.AttTarefa;
import com.example.projeto1.R;
import com.example.projeto1.activity.Menu;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<String> id, title, notes, date, subject, done;
    private ArrayList<Integer> cor;
    private Activity activity;
    private boolean edit;
    public int btndone, click;
    private final ViewBinderHelper binderHelper = new ViewBinderHelper();

    public CustomAdapter(Activity activity, Context context, ArrayList id, ArrayList title, ArrayList notes, ArrayList date, ArrayList subject, ArrayList done, ArrayList cor) {
        binderHelper.setOpenOnlyOne(true);
        this.activity = activity;
        this.context = context;
        this.id = id;
        this.title = title;
        this.notes = notes;
        this.date = date;
        this.subject = subject;
        this.done = done;
        this.cor = cor;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.linha_recyclerview, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.MyViewHolder holder, int position) {
        //Seta as informacções nas textviews
        holder.id_txt.setText(String.valueOf(id.get(position)));
        holder.title_txt.setText(String.valueOf(title.get(position)));
        holder.notes_txt.setText(String.valueOf(notes.get(position)));
        holder.subject_txt.setText(String.valueOf(subject.get(position)));
        holder.donetxt.setText(String.valueOf(done.get(position)));
        GradientDrawable c = (GradientDrawable) holder.cordisciplina.getBackground();
        c.setColor(cor.get(position));
        c.setStroke(1, cor.get(position));

        if (notes.get(position).isEmpty()){
            holder.textView12.setVisibility(View.GONE);
            holder.notes_txt.setVisibility(View.GONE);
        }

        click = Integer.valueOf(done.get(position));

        if (click == 1) {
            holder.done.setImageResource(R.drawable.done);
        }
        if (click == 0) {
            holder.done.setImageResource(R.drawable.undone);
        }
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AttTarefa.class);
                intent.putExtra("id", String.valueOf(id.get(position)));
                intent.putExtra("title", String.valueOf(title.get(position)));
                intent.putExtra("notes", String.valueOf(notes.get(position)));
                intent.putExtra("date", String.valueOf(date.get(position)));
                intent.putExtra("subject",String.valueOf(subject.get(position)));
                holder.swipeLayout.close(true);
                activity.startActivityForResult(intent, 1);
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binderHelper.setOpenOnlyOne(true);
                DataBase db = new DataBase(context);
                holder.swipeLayout.close(true);
                db.deleteData(id.remove(position));
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, id.size());
            }
        });

        holder.done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataBase db = new DataBase(context);
                if (btndone == 0) {
                    btndone = 1;
                    holder.done.setImageResource(R.drawable.done);
                    db.done(id.get(position), btndone);
                } else {
                    btndone = 0;
                    db.done(id.get(position), btndone);
                    holder.done.setImageResource(R.drawable.undone);

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title_txt, notes_txt, id_txt, subject_txt, donetxt,textView12;
        LinearLayout layouttarefa;
        public ImageView delete, edit, done, undone;
        private SwipeRevealLayout swipeLayout;
        ImageView cordisciplina;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            id_txt = itemView.findViewById(R.id.id_txt);
            title_txt = itemView.findViewById(R.id.titulotarefa_txt);
            notes_txt = itemView.findViewById(R.id.anotacoestarefa_txt);
            subject_txt = itemView.findViewById(R.id.materia_txt);
            swipeLayout = itemView.findViewById(R.id.cor);
            edit = itemView.findViewById(R.id.edit);
            delete = itemView.findViewById(R.id.delete);
            done = itemView.findViewById(R.id.done);
            donetxt = itemView.findViewById(R.id.donetxt);
            cordisciplina = itemView.findViewById(R.id.cordisciplina);
            textView12 = itemView.findViewById(R.id.textView12);

        }

    }

    public String getData(int position) {
        return id.get(position);

    }


}




