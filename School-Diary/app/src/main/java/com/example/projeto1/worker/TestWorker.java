package com.example.projeto1.worker;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.projeto1.activity.Menu;

import com.example.projeto1.R;
import com.example.projeto1.functions.DataBase;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class TestWorker extends Worker {

    private static final String CHANNEL_ID = "1";
    private static final String CHANNEL_NAME = "test_channel_name";
    private static final String CHANNEL_DESCRIPTION = "test_channel_description";


    public TestWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {

        Log.d("TestWorker", "Test Notifications!!!");

        sendNotification();

        return Result.success();

    }

    private void sendNotification() {

        Context context = getApplicationContext();


        // get tests from database since date now

        LocalDate now = LocalDate.now();

        DataBase db = new DataBase(context);
        Cursor c = db.readTesteNoti(now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));


        // if there is no tests then do nothing

        if (c.getCount() == 0) {
            Log.d("TestWorker", "There are no tests after " + now.toString());
            return;
        }


        // if there is tests then send notification
        // get notification manager and create notification channel
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);


        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
        channel.setDescription(CHANNEL_DESCRIPTION);
        notificationManager.createNotificationChannel(channel);


        // send a notification for each test

        int notificationId = 0;

        if (c.moveToFirst()) {

            do {
                int noti = c.getInt(4);
                if (noti == 1) {
                    String title = c.getString(1);
                    LocalDate date = LocalDate.parse(c.getString(2), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    String disciplina = c.getString(6);

                    long days = ChronoUnit.DAYS.between(now, date);

                    Log.d("TestWorker", "Missed " + days + " days until the test");

                    // should notify if miss 0, 3 or 7 days for the test
                   /*if(days != 0 && days != 3 && days != 7) {
                        // continue;
                    }*/

                    if (days == 0 || days == 3 || days == 7) {
                        String message = days == 0 ? "Boa sorte! Hoje você tem teste de " + disciplina
                                : "Lembre-se faltam " + days + " dias para o teste de " + disciplina + "!";

                        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                                .setSmallIcon(R.drawable.ic_stat_name)
                                .setContentTitle(title)
                                .setContentText(message)
                                .setAutoCancel(true)
                                .setCategory(NotificationCompat.CATEGORY_REMINDER);
                        builder.setPriority(NotificationCompat.PRIORITY_HIGH);


                        Intent intent = new Intent(context, Menu.class);
                        PendingIntent pi = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                        builder.setFullScreenIntent(pi, true);

                        builder.setVisibility(NotificationCompat.VISIBILITY_PUBLIC);


                        notificationManager.notify(notificationId, builder.build());

                        notificationId++;
                    }
                }

            } while (c.moveToNext());


        }

    }

}
