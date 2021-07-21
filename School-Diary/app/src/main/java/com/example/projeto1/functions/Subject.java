package com.example.projeto1.functions;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projeto1.R;
import com.example.projeto1.activity.AttDisciplina;
import com.example.projeto1.activity.AttTeste;
import com.example.projeto1.activity.Testes;

import java.util.ArrayList;

public class Subject extends RecyclerView.Adapter<Subject.MyViewHolder> {

    private Context context;
    private ArrayList<String> subject, avg, arrayid, arrayteacher;
    private ArrayList<Integer> cor;
    private Handler handler = new Handler();
    private Activity activity;


    public Subject(Activity activity, Context context, ArrayList subject, ArrayList avg, ArrayList arrayid, ArrayList cor, ArrayList arrayteacher) {
        this.context = context;
        this.subject = subject;
        this.avg = avg;
        this.arrayid = arrayid;
        this.cor = cor;
        this.activity = activity;
        this.arrayteacher = arrayteacher;
    }

    @NonNull
    @Override
    public Subject.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.avgsubject, parent, false);
        return new Subject.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Subject.MyViewHolder holder, int position) {

        holder.subject.setText(String.valueOf(subject.get(position)));



        holder.layoutsubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AttDisciplina.class);
                intent.putExtra("id", String.valueOf(arrayid.get(position)));

                intent.putExtra("subject", subject.get(position));

                intent.putExtra("color", String.valueOf(cor.get(position)));
                intent.putExtra("prof", String.valueOf(arrayteacher.get(position)));

                context.startActivity(intent);
            }
        });

        String t = String.valueOf(avg.get(position));
        int a = Integer.parseInt(String.valueOf(Math.round(Float.parseFloat(t)))) * 100;

        int b = a / 100;

        holder.avg.setText(String.valueOf(b));
        holder.id_subject.setText(arrayid.get(position));

        if (arrayteacher.get(position).isEmpty()) {
            holder.teacher.setVisibility(View.INVISIBLE);

        } else holder.teacher.setText("Professor: " + arrayteacher.get(position));

        holder.notascard.setCardBackgroundColor(cor.get(position));

        if (a == 0) {
            holder.progressBar.setProgress(0);

            ObjectAnimator.ofInt(holder.progressBar, "progress", 0).setDuration(1500).start();
        } else ObjectAnimator.ofInt(holder.progressBar, "progress", a).setDuration(1500).start();



        if (a >= 0 && a <= 900) {
            holder.progressBar.getProgressDrawable().setColorFilter(Color.parseColor("#DB0A13"), android.graphics.PorterDuff.Mode.SRC_IN);

        } else if (a > 900 && a <= 1400) {
            holder.progressBar.getProgressDrawable().setColorFilter(Color.parseColor("#FDE740"), android.graphics.PorterDuff.Mode.SRC_IN);
        } else
            holder.progressBar.getProgressDrawable().setColorFilter(Color.parseColor("#018A2C"), android.graphics.PorterDuff.Mode.SRC_IN);



    }

    @Override
    public int getItemCount() {
        return subject.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView subject, avg, teacher, id_subject;
        CardView notascard;
        ConstraintLayout layoutsubject;
        ProgressBar progressBar;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            subject = itemView.findViewById(R.id.subject);
            avg = itemView.findViewById(R.id.avg);
            notascard = itemView.findViewById(R.id.notascard);
            progressBar = itemView.findViewById(R.id.progressBar);
            teacher = itemView.findViewById(R.id.teacher);
            id_subject = itemView.findViewById(R.id.id_subject);
            layoutsubject = itemView.findViewById(R.id.layoutsubject);

        }
    }
}
