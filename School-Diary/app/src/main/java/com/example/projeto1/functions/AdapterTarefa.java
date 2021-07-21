package com.example.projeto1.functions;

import android.content.Context;
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
import com.example.projeto1.R;

import java.util.ArrayList;

public class AdapterTarefa extends RecyclerView.Adapter<AdapterTarefa.MyViewHolder>{


    private Context context;
    private ArrayList<String>  title;
    private ArrayList<Integer> cor;

    public AdapterTarefa(Context context, ArrayList title, ArrayList cor){
        this.context = context;
        this.title = title;
        this.cor = cor;
    }

    @NonNull
    @Override
    public AdapterTarefa.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.linhatarefa, parent, false);
        return new AdapterTarefa.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterTarefa.MyViewHolder holder, int position) {
        holder.tarefarv.setText(String.valueOf(title.get(position)));
        //GradientDrawable c = (GradientDrawable) holder.bolinha.getBackground();
        //c.setColor(Integer.valueOf(cor.get(position)));
    }

    @Override
    public int getItemCount() {
        return title.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tarefarv;
        ImageView bolinha;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tarefarv = itemView.findViewById(R.id.tarefarv);
            bolinha = itemView.findViewById(R.id.bolinha);

        }

    }
}
