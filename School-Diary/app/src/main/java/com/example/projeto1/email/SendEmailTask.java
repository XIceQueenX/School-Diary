package com.example.projeto1.email;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class SendEmailTask extends AsyncTask<String, Void, Boolean> {

    private Context context;

    public SendEmailTask(Context context) {
        this.context = context;
    }

    @Override
    protected Boolean doInBackground(String... params) {

        GmailSender sender = new GmailSender(params[0], params[1], params[2]);

        try {

            sender.sendMail();
            return true;

        } catch(Exception excpetion) {
            Log.e("Reset", excpetion.getMessage(), excpetion);
        }

        return false;

    }

    protected void onPostExecute(Boolean result) {

        if(!result) {
            Toast.makeText(context, "Não foi possível enviar o email. Por favor, tente mais tarde.", Toast.LENGTH_SHORT).show();
        }

    }

}
