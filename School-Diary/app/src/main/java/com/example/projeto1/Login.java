package com.example.projeto1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projeto1.activity.Menu;
import com.example.projeto1.functions.DataBase;
import com.example.projeto1.functions.Globals;

public class Login extends AppCompatActivity {
    Button btnlogin, btnsignup;
    EditText emaillogin, senhalogin;
    DataBase db;
    TextView id,reset;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        onWindowFocusChanged(true);
        setContentView(R.layout.activity_login);


        SharedPreferences secao = getSharedPreferences("Secao", 0);
        SharedPreferences emailsp = getSharedPreferences("Email", 0);

        SharedPreferences.Editor editor = secao.edit();
        SharedPreferences.Editor editor1 = emailsp.edit();

        if (secao.getString("login", "").equals("logado")) {
            Intent intent = new Intent(Login.this, Menu.class);
            startActivity(intent);
            String id = emailsp.getString("id","");
            Globals.user = Integer.valueOf(id);
            finish();
        }


        btnlogin = findViewById(R.id.btnlogin);
        btnsignup = findViewById(R.id.btnsignup);
        reset = findViewById(R.id.reset);

        emaillogin = findViewById(R.id.emaillogin);
        senhalogin = findViewById(R.id.senhalogin);

        db = new DataBase(Login.this);
        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Registar.class);
                startActivity(intent);
                finish();
            }
        });


        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Reset.class);
                startActivity(intent);
                finish();
            }
        });
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = emaillogin.getText().toString().toUpperCase();
                String senha = senhalogin.getText().toString();

                if (TextUtils.isEmpty(email)||TextUtils.isEmpty(senha) ){

                    if (TextUtils.isEmpty(email)){
                        emaillogin.setError("Campo Vazio");
                        emaillogin.requestFocus();
                    }
                    if (TextUtils.isEmpty(senha)){
                        senhalogin.setError("Campo Vazio");
                        emaillogin.requestFocus();
                    }
                }
                else {
                    Boolean checkuser = db.checkEmailPassword(email,senha);
                    if (checkuser== true){
                        Toast.makeText(Login.this,"Login efetuado com sucesso", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Login.this, Menu.class);
                        startActivity(intent);
                        String r = db.GetUserID(email);
                        editor.putString("login", "logado"); // Storing string
                        editor.apply();
                        editor1.putString("id",""+ r);
                        editor1.apply();

                        String id = emailsp.getString("id","");
                        Globals.user = Integer.valueOf(id);
                        finish();
                    }else {
                        if (db.checkEmail(email) == false){
                            emaillogin.setError("Email não registado");
                            emaillogin.requestFocus();
                        }else senhalogin.setError("Senha inválida");
                        Toast.makeText(Login.this,"Login falhou", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}