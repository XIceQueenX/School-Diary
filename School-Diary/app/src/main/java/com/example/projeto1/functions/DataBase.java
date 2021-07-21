package com.example.projeto1.functions;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;


import androidx.annotation.Nullable;

import com.example.projeto1.util.StringUtil;

import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DataBase extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "PAP.db";
    private static final int DATABASE_VERSION = 16;

    // Tabela Tarefas
    private static final String TABLE_NAME = "TarefasTrabalhos";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TITLE = "titulo";
    private static final String COLUMN_NOTES = "anotacoes";
    private static final String COLUMN_DONE = "feito";
    private static final String COLUMN_SUBJECT = "disciplina";
    private static final String COLUMN_DATE = "data";

    // Tabela Disciplina
    private static final String TABLE_SUBJECT = "Disciplina";
    private static final String COLUMN_NAME_SUBJECT = "nome";
    private static final String COLUMN_COLOR_SUBJECT = "cor";
    private static final String COLUMN_ID_SUBJECT = "id_disciplina";
    private static final String COLUMN_IDUSER_SUBJECT = "id_usuario_dis";
    private static final String COLUMN_TEACHER = "teacher";

    // Tabela Horario
    private static final String TABLE_SCHEDULE = "Horario";
    private static final String COLUMN_SCHEDULE_ID_SUBJECT = "disciplina";
    private static final String COLUMN_SCHEDULE_DAY = "dia_semana";
    private static final String COLUMN_HORAINI_SUBJECT = "horaini";
    private static final String COLUMN_HORAFIM_SUBJECT = "horafim";
    private static final String COLUMN_ID_SCHEDULE = "id";
    private static final String COLUMN_CLASS = "sala";

    // Tabela Anotações
    private static final String TABLE_NOTES = "Anotacoes";
    private static final String COLUMN_ID_NOTES = "id_anotacoes";
    private static final String COLUMN_TITLE_NOTES = "titulo_anotacoes";
    private static final String COLUMN_CONTENT_NOTES = "conteudo_anotacoes";
    private static final String COLUMN_SUBJECT_NOTES = "disciplina_anotacoes";

    // Tabela Teste
    private static final String TABLE_TESTE = "Teste";
    private static final String COLUMN_ID_TESTE = "id";
    private static final String COLUMN_TITLE_TESTE = "titulo";
    private static final String COLUMN_DATE_TESTE = "data";
    private static final String COLUMN_SUBJECT_TESTE = "disciplina";
    private static final String COLUMN_NOTIFY_TESTE = "notificar";
    private static final String COLUMN_GRADE = "nota";


    // Tabela Utilizador
    private static final String TABLE_USER = "Utilizador";
    private static final String COLUMN_USER_ID = "id_utilizador";
    private static final String COLUMN_USER_NAME = "nome_utilizador";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASSWORD = "password";

    //
    private static final String QueryCriaTabelaDisciplina = "CREATE TABLE " + TABLE_SUBJECT +
            " (" + COLUMN_NAME_SUBJECT + " TEXT NOT NULL, " +
            COLUMN_COLOR_SUBJECT + " TEXT NOT NULL, " +
            COLUMN_IDUSER_SUBJECT + " INTEGER NOT NULL," +
            COLUMN_ID_SUBJECT + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_TEACHER + " TEXT );";

    private static final String QueryCriaTabelaHora = "CREATE TABLE " + TABLE_SCHEDULE +
            " (" + COLUMN_SCHEDULE_ID_SUBJECT + " INTEGER NOT NULL ," +
            COLUMN_SCHEDULE_DAY + " TEXT NOT NULL, " +
            COLUMN_HORAINI_SUBJECT + " TEXT NOT NULL, " +
            COLUMN_HORAFIM_SUBJECT + " TEXT NOT NULL, " +
            COLUMN_ID_SCHEDULE + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_CLASS + " TEXT , " +
            "FOREIGN KEY (" + COLUMN_SCHEDULE_ID_SUBJECT + ") REFERENCES " + TABLE_SCHEDULE + "(" + COLUMN_ID_SUBJECT + "));";

    private static final String QueryCriaTabelaTarefa = "CREATE TABLE " + TABLE_NAME +
            " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_TITLE + " TEXT NOT NULL, " +
            COLUMN_NOTES + " TEXT, " +
            COLUMN_DATE + " TEXT NOT NULL, " +
            COLUMN_DONE + " INTEGER DEFAULT 0 , " +
            COLUMN_SUBJECT + " INTEGER NOT NULL," +
            " FOREIGN KEY (" + COLUMN_SUBJECT + ") REFERENCES " + TABLE_SUBJECT + "(" + COLUMN_ID_SUBJECT + "))";

    private static final String QueryCriaTabelaAnotacoes = "CREATE TABLE " + TABLE_NOTES +
            "(" + COLUMN_ID_NOTES + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_TITLE_NOTES + " TEXT NOT NULL, " +
            COLUMN_CONTENT_NOTES + " TEXT , " +
            COLUMN_SUBJECT_NOTES + " INTEGER NOT NULL);";

    private static final String QueryCriaTabelaTeste = "CREATE TABLE " + TABLE_TESTE +
            "(" + COLUMN_ID_TESTE + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_TITLE_TESTE + " TEXT NOT NULL, " +
            COLUMN_DATE_TESTE + " TEXT NOT NULL," +
            COLUMN_SUBJECT_TESTE + " INTEGER NOT NULL," +
            COLUMN_NOTIFY_TESTE + " INTEGER ," + // 0 (false) and 1 (true)
            COLUMN_GRADE + " DOUBLE ," +
            "FOREIGN KEY (" + COLUMN_SUBJECT_TESTE + ") REFERENCES " + TABLE_SCHEDULE + "(" + COLUMN_ID_SUBJECT + "));";

    private static final String QueryCriaTabelaUtilizador = "CREATE TABLE " + TABLE_USER +
            "(" + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_USER_NAME + " TEXT NOT NULL, " +
            COLUMN_EMAIL + " TEXT NOT NULL ," +
            COLUMN_PASSWORD + " TEXT NOT NULL);";

    public DataBase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("PRAGMA foreign_keys=ON");
        db.execSQL(QueryCriaTabelaDisciplina);
        db.execSQL(QueryCriaTabelaTarefa);
        db.execSQL(QueryCriaTabelaAnotacoes);
        db.execSQL(QueryCriaTabelaHora);
        db.execSQL(QueryCriaTabelaTeste);
        db.execSQL(QueryCriaTabelaUtilizador);

    }

    private String date_;

    // i = oldversion  && i1 = newversion
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i2) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SCHEDULE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SUBJECT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TESTE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(db);
    }

    public void addTarefa(String title, String note, String date, Integer subject) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TITLE, StringUtils.capitalize(title));
        cv.put(COLUMN_NOTES, StringUtils.capitalize(note));
        cv.put(COLUMN_DATE, date);
        cv.put(COLUMN_SUBJECT, subject);
        long result = db.insert(TABLE_NAME, null, cv);
        if (result == -1) {
            Toast.makeText(context, "Falhou", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Adicionado com Sucesso!", Toast.LENGTH_SHORT).show();
        }
    }

    public void addDisciplina(String name, int cor,String teacher) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME_SUBJECT, StringUtils.capitalize(name));
        cv.put(COLUMN_COLOR_SUBJECT, cor);
        cv.put(COLUMN_IDUSER_SUBJECT, Globals.user);
        cv.put(COLUMN_TEACHER,StringUtils.capitalize(teacher));

        long result = db.insert(TABLE_SUBJECT, null, cv);
        if (result == -1) {
            Toast.makeText(context, "Falhou", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Adicionado com Sucesso!", Toast.LENGTH_SHORT).show();
        }
    }

    public void updateDisciplina(String row_id, String name, int cor, String professor) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME_SUBJECT, StringUtils.capitalize(name));
        cv.put(COLUMN_COLOR_SUBJECT, cor);
        cv.put(COLUMN_IDUSER_SUBJECT, Globals.user);
        cv.put(COLUMN_TEACHER, professor);

        long result = db.update(TABLE_SUBJECT, cv, "id_disciplina=?", new String[]{row_id});

        if (result == -1) {
            Toast.makeText(context, "Falha ao Atualizar", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Sucesso ao Atualizar", Toast.LENGTH_SHORT).show();
        }
    }

    public void addHora(Integer subject, int dia_semana, String horainicial, String horafinal,String sala) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_SCHEDULE_ID_SUBJECT, subject);
        cv.put(COLUMN_SCHEDULE_DAY, dia_semana);
        cv.put(COLUMN_HORAINI_SUBJECT, horainicial);
        cv.put(COLUMN_HORAFIM_SUBJECT, horafinal);
        cv.put(COLUMN_CLASS,sala);

        long result = db.insert(TABLE_SCHEDULE, null, cv);
        if (result == -1) {
            Toast.makeText(context, "Falhou", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Adicionado com Sucesso!", Toast.LENGTH_SHORT).show();
        }
    }

    public void updateHour(String row_id, String subject, int dia_semana, String horainicial, String horafinal) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_SCHEDULE_ID_SUBJECT, subject);
        cv.put(COLUMN_SCHEDULE_DAY, dia_semana);
        cv.put(COLUMN_HORAINI_SUBJECT, horainicial);
        cv.put(COLUMN_HORAFIM_SUBJECT, horafinal);

        long result = db.update(TABLE_SCHEDULE, cv, "id=?", new String[]{row_id});

        if (result == -1) {
            Toast.makeText(context, "Falha ao Atualizar", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Sucesso ao Atualizar", Toast.LENGTH_SHORT).show();
        }

    }

    public Cursor readAllData(String data_) {
        String query = "SELECT * FROM TarefasTrabalhos,Disciplina,Utilizador where TarefasTrabalhos.disciplina = Disciplina.id_disciplina and Disciplina.id_usuario_dis = Utilizador.id_utilizador and Disciplina.id_usuario_dis =" + Globals.user + " and data like '%" + data_ + "%'";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    public Cursor readSubjects() {

        String query = "SELECT * FROM Disciplina,Utilizador where Disciplina.id_usuario_dis = Utilizador.id_utilizador and Disciplina.id_usuario_dis =" + Globals.user;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    public Cursor readHour() {
        String query = "SELECT * FROM Horario,Disciplina,Utilizador where  Disciplina.id_usuario_dis = Utilizador.id_utilizador and Horario.disciplina = Disciplina.id_disciplina and Disciplina.id_usuario_dis =" + Globals.user;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }

        return cursor;
    }

    public void updateData(String row_id, String title, String notes) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TITLE, title);
        cv.put(COLUMN_NOTES, notes);

        //Pega o valor da row da recycler view
        long result = db.update(TABLE_NAME, cv, "id=?", new String[]{row_id});

        if (result == -1) {
            Toast.makeText(context, "Falha ao Atualizar", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Sucesso ao Atualizar", Toast.LENGTH_SHORT).show();
        }

    }

    public void done(String row_id, int done) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_DONE, done);
        db.update(TABLE_NAME, cv, "id=?", new String[]{row_id});

        /*if (result == -1){
            Toast.makeText(context,"Marcado como Feito", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context,"Marcado como não concluído", Toast.LENGTH_SHORT).show();
        }*/
    }

    public void deleteData(String row_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "id=?", new String[]{row_id});

        if (result == -1) {
            Toast.makeText(context, "Erro ao deletar", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Deletado com sucesso", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteHour(String row_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_SCHEDULE, "id=?", new String[]{row_id});

        if (result == -1) {
            Toast.makeText(context, "Erro ao deletar", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Deletado com sucesso", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteNote(String row_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NOTES, "id_anotacoes=?", new String[]{row_id});

        if (result == -1) {
            Toast.makeText(context, "Erro ao deletar", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Deletado com sucesso", Toast.LENGTH_SHORT).show();
        }
    }

    public void addAnotacao(String title, String content, Integer subject) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TITLE_NOTES, StringUtils.capitalize(title));
        cv.put(COLUMN_CONTENT_NOTES, StringUtils.capitalize(content));
        cv.put(COLUMN_SUBJECT_NOTES, subject);

        long result = db.insert(TABLE_NOTES, null, cv);
        if (result == -1) {
            Toast.makeText(context, "Falhou", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Adicionado com Sucesso!", Toast.LENGTH_SHORT).show();
        }
    }

    public Cursor readNotesFilter(String s) {
        Cursor cursor = null;
        SQLiteDatabase db = this.getReadableDatabase();

        String query;
        query = "SELECT * FROM Anotacoes,Disciplina,Utilizador where Anotacoes.disciplina_anotacoes = Disciplina.id_disciplina and Disciplina.id_usuario_dis = Utilizador.id_utilizador  AND  Disciplina.id_usuario_dis = " + Globals.user + " and (Disciplina.nome like '%" + s + "%' or Anotacoes.titulo_anotacoes like '%" + s + "%')";
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    public Cursor readTask(String s) {
        Cursor cursor = null;
        SQLiteDatabase db = this.getReadableDatabase();

        String query;
        query = "SELECT * FROM Anotacoes,Disciplina,Utilizador where Anotacoes.disciplina_anotacoes = Disciplina.id_disciplina and Disciplina.id_usuario_dis = Utilizador.id_utilizador  AND  Disciplina.id_usuario_dis = " + Globals.user + " and (Anotacoes.disciplina_anotacoes like '%" + s + "%' or Anotacoes.titulo_anotacoes like '%" + s + "%')";
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    public Cursor readNotes() {
        String query = "SELECT * FROM Anotacoes,Disciplina,Utilizador where Anotacoes.disciplina_anotacoes = Disciplina.id_disciplina and Disciplina.id_usuario_dis = Utilizador.id_utilizador and Disciplina.id_usuario_dis =" + Globals.user;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    public void updateNotes(String row_id, String title, String content, String subject) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TITLE_NOTES, title);
        cv.put(COLUMN_CONTENT_NOTES, content);
        cv.put(COLUMN_SUBJECT_NOTES, subject);

        long result = db.update(TABLE_NOTES, cv, "id_anotacoes=?", new String[]{row_id});

        if (result == -1) {
            Toast.makeText(context, "Falha ao Atualizar", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Sucesso ao Atualizar", Toast.LENGTH_SHORT).show();
        }
    }

    public void addTeste(String title, String date, Integer disciplina, int notificacao) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_TITLE_TESTE, StringUtils.capitalize(title));
        cv.put(COLUMN_DATE_TESTE, date);
        cv.put(COLUMN_SUBJECT_TESTE, disciplina);
        cv.put(COLUMN_NOTIFY_TESTE, notificacao);

        long result = db.insert(TABLE_TESTE, null, cv);
        if (result == -1) {
            Toast.makeText(context, "Falhou", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Adicionado com Sucesso!", Toast.LENGTH_SHORT).show();
        }
    }

    public void updateTeste(String row_id, String title, String date, String disciplina) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TITLE_TESTE, title);
        cv.put(COLUMN_DATE_TESTE, date);
        cv.put(COLUMN_SUBJECT_TESTE, disciplina);

        long result = db.update(TABLE_TESTE, cv, "id=?", new String[]{row_id});

        if (result == -1) {
            Toast.makeText(context, "Falha ao Atualizar", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Sucesso ao Atualizar", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteTeste(String row_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_TESTE, "id=?", new String[]{row_id});

        if (result == -1) {
            Toast.makeText(context, "Erro ao deletar", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Deletado com sucesso", Toast.LENGTH_SHORT).show();
        }
    }

    public void deletesubject(String row_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_SUBJECT, "id_disciplina=?", new String[]{row_id});

        if (result == -1) {
            Toast.makeText(context, "Erro ao deletar", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Deletado com sucesso", Toast.LENGTH_SHORT).show();
        }
    }

    public Cursor readTeste() {

        String query = "SELECT * FROM Teste,Disciplina,Utilizador where Teste.disciplina = Disciplina.id_disciplina and Disciplina.id_usuario_dis = Utilizador.id_utilizador and Disciplina.id_usuario_dis =" + Globals.user;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    public Cursor readTeste(String date) {

        String query =
                "SELECT * FROM Teste, Disciplina, Utilizador " +
                        "WHERE Teste.disciplina = Disciplina.id_disciplina " +
                        "AND Disciplina.id_usuario_dis = Utilizador.id_utilizador " +
                        "AND Disciplina.id_usuario_dis = " + Globals.user + " " +
                        "AND Teste.data >= " + date;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null) {
            cursor = db.rawQuery(query, null);
        }

        return cursor;
    }

   /* public Cursor readTeste() {

        String query = "SELECT * FROM Teste,Disciplina,Utilizador where Teste.disciplina = Disciplina.id_disciplina and Disciplina.id_usuario_dis = Utilizador.id_utilizador and Disciplina.id_usuario_dis =" + Globals.user;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }*/

    public boolean setNota(int id, String nota) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_GRADE, nota);

        long result = db.update(TABLE_TESTE, cv, COLUMN_ID_TESTE + "=?", new String[]{String.valueOf(id)});

        return result == 1;

    }

    public Cursor readTesteNoti(String date) {

        String query =
                "SELECT * FROM Teste, Disciplina, Utilizador " +
                        "WHERE Teste.disciplina = Disciplina.id_disciplina " +
                        "AND Disciplina.id_usuario_dis = Utilizador.id_utilizador " +
                        "AND Disciplina.id_usuario_dis = " + Globals.user + " " +
                        "AND Teste.data >= " + date;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }

        return cursor;

    }

    public void addUser(String name, String email, String password) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_USER_NAME, name);
        cv.put(COLUMN_EMAIL, email);
        cv.put(COLUMN_PASSWORD, password);

        long result = db.insert(TABLE_USER, null, cv);
        if (result == -1) {
            Toast.makeText(context, "Falha ao Adicionar Usuário", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "Sucesso ao Adicionar", Toast.LENGTH_LONG).show();
        }

    }

    public boolean setPassword(String email, String password) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_PASSWORD, password);

        int count = db.update(TABLE_USER, cv, "email=?", new String[]{email});

        return count > 0;

    }

    public Boolean checkEmailPassword(String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM Utilizador where email=? and password=?", new String[]{email, password});
        if (cursor.getCount() > 0) {
            return true;
        } else return false;

    }


    public Boolean checkEmail(String email) {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM Utilizador where email=? ", new String[]{email});
        if (cursor.getCount() > 0)
            return true;
        else return false;

    }

    public Boolean checkSubject(String subject) {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT Disciplina.nome FROM Disciplina,Utilizador where Disciplina.nome=? and Utilizador.id_utilizador = Disciplina.id_usuario_dis ", new String[]{subject});
        if (cursor.getCount() > 0)
            return true;
        else return false;

    }


    public Boolean checkSubjectatt(String subject) {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT Disciplina.nome FROM Disciplina,Utilizador where Disciplina.nome=? and Utilizador.id_utilizador = Disciplina.id_usuario_dis ", new String[]{subject});
        if (cursor.getCount() > 1)
            return true;
        else return false;

    }

    public String GetUserID(String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        String where = COLUMN_EMAIL + " LIKE '%" + email + "%'";
        Cursor c = db.query(true, TABLE_USER, null,
                where, null, null, null, null, null);
        String p = "";
        if (c.moveToFirst() && c.getCount() >= 1) {

            do {
                if (c.getCount() > 0) p = c.getString(c.getColumnIndex(COLUMN_USER_ID));
                else p = "";

            } while (c.moveToNext());
        }
        return p;
    }


    public Cursor AvgSubject() {
        String query = "SELECT *, AVG(Teste.nota) FROM Disciplina,Teste,Utilizador where Teste.disciplina = Disciplina.id_disciplina and Disciplina.id_usuario_dis = Utilizador.id_utilizador and Disciplina.id_usuario_dis =" + Globals.user + " GROUP BY Disciplina.id_disciplina";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

   public String getName() {
        SQLiteDatabase db = this.getReadableDatabase();
        String result = DatabaseUtils.stringForQuery(db,
                "SELECT nome_utilizador FROM Utilizador where id_utilizador = " + Globals.user, null);
        return result;
    }


    public String getEmail() {
        SQLiteDatabase db = this.getReadableDatabase();
        String result = DatabaseUtils.stringForQuery(db,
                "SELECT email FROM Utilizador where id_utilizador = " + Globals.user   , null);
        return result.toLowerCase();
    }


    public Cursor readTa(String data_) {
        String query = "SELECT * FROM TarefasTrabalhos,Disciplina,Utilizador where TarefasTrabalhos.disciplina = Disciplina.id_disciplina and Disciplina.id_usuario_dis = Utilizador.id_utilizador and Disciplina.id_usuario_dis =" + Globals.user + " and data like '%" + data_ + "%' and feito = 0";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

}






