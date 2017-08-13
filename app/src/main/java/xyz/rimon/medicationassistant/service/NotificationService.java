package xyz.rimon.medicationassistant.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import xyz.rimon.medicationassistant.MainActivity_;
import xyz.rimon.medicationassistant.R;
import xyz.rimon.medicationassistant.commons.Commons;
import xyz.rimon.medicationassistant.commons.Logger;
import xyz.rimon.medicationassistant.domains.Drug;
import xyz.rimon.medicationassistant.utils.DateUtils;
import xyz.rimon.medicationassistant.utils.StorageUtils;

/**
 * Created by SAyEM on 8/12/17.
 */

public class NotificationService extends Service {
    private static boolean ALREADY_RUNNING = false;

    public NotificationService() {
        //timeCounter = timeCounter + (2*60*1000);
    }

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {
        if (!ALREADY_RUNNING) {
            ALREADY_RUNNING = true;
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    while (System.currentTimeMillis() < System.currentTimeMillis() + 1000 * 60) {
                        synchronized (this) {
                            try {
                                List<Drug> drugList = Commons.getMatchedDrugs();
                                if (!drugList.isEmpty()) {
                                    showNotification(drugList);
                                }
                                Logger.i("SERVICE_RUNNING", String.valueOf(System.currentTimeMillis()));
                                Thread.sleep(1000 * 60);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            };

            Thread thread = new Thread(runnable);
            thread.start();
        } else {
            Logger.i("ALREADY_RUNNING", "ALREADY_RUNNING");
        }
        return Service.START_STICKY;
    }

    private void showNotification(List<Drug> drugList) {
        NotificationCompat.Builder notiBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(getResources().getString(R.string.msg_medicationTime))
                .setContentText(Commons.getDrugNamesString(drugList) + " " + getResources().getString(R.string.msg_needsToTake))
                .setAutoCancel(true);

        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        notiBuilder.setSound(alarmSound);
        Intent resultIntent = new Intent(this, MainActivity_.class);
//        resultIntent.putExtra("event", drug);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity_.class);

        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        notiBuilder.setContentIntent(resultPendingIntent);


        int mNotificationId = 001;
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notiBuilder.getNotification().flags |= Notification.FLAG_AUTO_CANCEL;
        notificationManager.notify(mNotificationId, notiBuilder.build());

    }

//    void showHadithNotification() {
//        NotificationCompat.Builder notiBuilder = new NotificationCompat.Builder(this)
//                .setSmallIcon(R.mipmap.ic_launcher)
//                .setContentTitle(getResources().getString(R.string.notiTitleText))
//                .setContentText(getResources().getString(R.string.notiContentText))
//                .setAutoCancel(true);
//
//        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//        notiBuilder.setSound(alarmSound);
//        Intent resultIntent = new Intent(this, HadithActivity.class);
//        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
//        stackBuilder.addParentStack(HadithActivity.class);
//
//        // Adds the Intent that starts the Activity to the top of the stack
//        stackBuilder.addNextIntent(resultIntent);
//        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
//        notiBuilder.setContentIntent(resultPendingIntent);
//
//
//        int mNotificationId = 001;
//        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//        notiBuilder.getNotification().flags |= Notification.FLAG_AUTO_CANCEL;
//        notificationManager.notify(mNotificationId, notiBuilder.build());
//    }


    @Override
    public void onDestroy() {
        Logger.d("SERVICE_RUNNING: ", "onDestroy Called");
        sendBroadcast(new Intent("YouWillNeverKillMe"));
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

}