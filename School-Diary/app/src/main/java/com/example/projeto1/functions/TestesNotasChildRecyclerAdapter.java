package com.example.projeto1.functions;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projeto1.functions.DataBase;
import com.example.projeto1.model.TesteModel;

import com.example.projeto1.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.List;

public class TestesNotasChildRecyclerAdapter extends RecyclerView.Adapter<TestesNotasChildRecyclerAdapter.ViewHolder> {

    private List<TesteModel> testes;

    public TestesNotasChildRecyclerAdapter(List<TesteModel> testes) {
        this.testes = testes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        View view = layoutInflater.inflate(R.layout.testes_notas_teste_row, parent, false);

        return new ViewHolder(view);

    }

    private static final String NOTA_EMPTY = "-";

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        TesteModel teste = testes.get(position);

        // test id data base
        holder.id = teste.getId();

         holder.linetest.getBackground().setColorFilter(teste.getColor(), PorterDuff.Mode.SRC_ATOP);

        holder.titleTextView.setText(teste.getTitle());
        holder.dataTextView.setText(teste.getData());
        holder.notaTextView.setText((teste.getNota() == null || teste.getNota().isEmpty()) ? NOTA_EMPTY : teste.getNota());


    }

    @Override
    public int getItemCount() {
        return testes.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder  {

        int id;

        TextView titleTextView,textView42;
        TextView dataTextView;
        TextView notaTextView;
        ImageView linetest,linhadisciplina2;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);



            titleTextView = itemView.findViewById(R.id.testes_notas_titulo_text_view);
            dataTextView = itemView.findViewById(R.id.testes_notas_data_text_view);
            notaTextView = itemView.findViewById(R.id.testes_notas_nota_text_view);
            linetest = itemView.findViewById(R.id.linetest);



            notaTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    final EditText notaEditText = new EditText(itemView.getContext());

                    notaEditText.setInputType(InputType.TYPE_CLASS_PHONE);

                    AlertDialog dialog = new AlertDialog.Builder(itemView.getContext())
                            .setTitle("Adicionar Nota")
                            .setMessage("Coloca a tua nota do teste:")
                            .setView(notaEditText)
                            .setPositiveButton("Guardar", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    String nota = String.valueOf(notaEditText.getText());
                                    if (Double.parseDouble(nota)>21){
                                        Toast.makeText(itemView.getContext(),"Insira uma nota válida!", Toast.LENGTH_SHORT).show();
                                        //notaEditText.setError("Insira uma nota válida");
                                    }else{
                                        boolean result = new DataBase(itemView.getContext()).setNota(id, nota);

                                        if (result) {
                                            Toast.makeText(itemView.getContext(),"Nota atribuida com sucesso.", Toast.LENGTH_SHORT).show();
                                            notaTextView.setText(nota);
                                        } else {
                                            Toast.makeText(itemView.getContext(),"Não foi possível atribuir a nota.", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }

                            })
                            .setNegativeButton("Cancelar", null)
                            .create();

                    dialog.show();

                }
            });

        }
    }

}
