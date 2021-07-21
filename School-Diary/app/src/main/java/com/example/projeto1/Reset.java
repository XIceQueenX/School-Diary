package com.example.projeto1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import com.example.projeto1.email.SendEmailTask;
import com.example.projeto1.functions.DataBase;
import com.example.projeto1.util.StringUtil;

public class Reset extends AppCompatActivity {

    private String code;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        onWindowFocusChanged(true);
        setContentView(R.layout.activity_reset);

        DataBase db = new DataBase(Reset.this);



        EditText email = findViewById(R.id.reset_email);

        Button enviar = findViewById(R.id.reset_enviar);

        EditText codigo = findViewById(R.id.reset_codigo);
        EditText password = findViewById(R.id.reset_password);

        Button guardar = findViewById(R.id.verificacodigo);

        codigo.setVisibility(View.GONE);
        password.setVisibility(View.GONE);
        guardar.setVisibility(View.GONE);

        email.setVisibility(View.VISIBLE);
        enviar.setVisibility(View.VISIBLE);

        enviar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                String mail = email.getText().toString().trim();

                if (!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString().trim()).matches()){
                    email.setError("Insira um email válido");
                    email.requestFocus();
                }else{
                    sendEmail(mail);
                    codigo.setVisibility(View.VISIBLE);
                    password.setVisibility(View.VISIBLE);
                    guardar.setVisibility(View.VISIBLE);

                    email.setVisibility(View.GONE);
                    enviar.setVisibility(View.GONE);
                }
            }

        });

        guardar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                // confirmar código

                if(!codigo.getText().toString().trim().equals(code)) {

                    Log.d("Reset", "Código introduzido " + codigo  + " não correspondente ao código enviado por email " + code);
                    Toast.makeText(Reset.this, "Código inválido.", Toast.LENGTH_SHORT).show();
                    return;

                }

                if(password.getText().toString().isEmpty()){
                    password.setError("Campo Vazio");
                    password.requestFocus();
                    return;
                }


                boolean result = db.setPassword(email.getText().toString().trim().toUpperCase(),
                        password.getText().toString().trim());

                if (result) {
                    Toast.makeText(Reset.this,"Senha Atualizada com sucesso.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(Reset.this,"Não foi possível atualizar a password.", Toast.LENGTH_LONG).show();
                    return;
                }

                Intent intent = new Intent(Reset.this, Login.class);
                startActivity(intent);
                finish();

            }

        });

        ImageView backbutton13 = (ImageView) findViewById(R.id.backbutton13);
        backbutton13.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Reset.this, Login.class);
                startActivity(intent);
                finish();
            }

        });

    }

    private void sendEmail(String email) {

        Log.d("Reset", "Generate Code");

        this.code = StringUtil.randomAlphanumericString();

        Log.d("Reset", "Code " + this.code);
        Log.d("Reset", "Send email");

        new SendEmailTask(Reset.this).execute("Redefinição de senha - School Diary", "Para proceder a redefinição de senha insira dentro da aplicação o código " + this.code  , email);

    }

}