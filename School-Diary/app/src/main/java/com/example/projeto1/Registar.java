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
import android.widget.ImageView;

import com.example.projeto1.activity.Menu;
import com.example.projeto1.functions.DataBase;
import com.example.projeto1.functions.Globals;

public class Registar extends AppCompatActivity {

    EditText nameregister,senharegister,senhaconfirm,emailregister;
    Button register;
    DataBase db;
    Boolean segue=false;

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
        setContentView(R.layout.activity_registar);

        register= findViewById(R.id.register);
        nameregister = findViewById(R.id.nameregister);
        senharegister = findViewById(R.id.senharegister);
        senhaconfirm = findViewById(R.id.senhaconfirm);
        emailregister = findViewById(R.id.emailregister);

        ImageView backbutton12 = (ImageView) findViewById(R.id.backbutton12);
        backbutton12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Registar.this, Login.class);
                startActivity(intent);
                finish();
            }
        });

        db = new DataBase(Registar.this);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name =nameregister.getText().toString().trim();
                String email=emailregister.getText().toString().trim().toUpperCase();
                String pass =senharegister.getText().toString().trim();
                String conf=senhaconfirm.getText().toString().trim();

                Boolean valor = db.checkEmail(email);

                if (TextUtils.isEmpty(name)|| TextUtils.isEmpty(email) || TextUtils.isEmpty(pass)|| TextUtils.isEmpty(conf)){
                    if (TextUtils.isEmpty(name)){
                        nameregister.setError("O campo não pode ser vazio");
                        nameregister.requestFocus();
                    }
                    if (TextUtils.isEmpty(email)){
                        emailregister.setError("O campo não pode ser vazio");
                        emailregister.requestFocus();
                    }
                    if (TextUtils.isEmpty(pass)){
                        senharegister.setError("O campo não pode ser vazio");
                        emailregister.requestFocus();
                    }
                    if (TextUtils.isEmpty(conf)){
                        senhaconfirm.setError("O campo não pode ser vazio");
                        emailregister.requestFocus();
                    }
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    emailregister.setError("Insira um email válido");
                    emailregister.requestFocus();
                }
                else if ((!pass.equals(conf)))
                {
                    senharegister.setError("As senhas não coincidem");
                    senhaconfirm.setError("As senhas não coincidem");
                    senhaconfirm.requestFocus();
                }
                else if(valor == true){
                    emailregister.setError("Email já registado");
                    emailregister.requestFocus();
                }
                else {
                    db.addUser(name,email,pass);
                    segue = true;
                    }
                if(segue){

                    SharedPreferences emailsp = getSharedPreferences("Email", 0);
                    SharedPreferences.Editor editor1 = emailsp.edit();

                    SharedPreferences secao = getSharedPreferences("Secao", 0);
                    SharedPreferences.Editor editor = secao.edit();

                    editor.putString("login", "logado");
                    editor.apply();

                    String r = db.GetUserID(email);
                    editor1.putString("id",""+ r);
                    editor1.apply();

                    String id = emailsp.getString("id","");
                    Globals.user = Integer.valueOf(id);

                    Intent intent = new Intent(Registar.this, Menu.class);
                    startActivity(intent);
                    finish();
                    }
                }
            });
        }
    }
