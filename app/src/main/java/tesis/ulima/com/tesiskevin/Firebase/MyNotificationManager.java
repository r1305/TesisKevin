package tesis.ulima.com.tesiskevin.Firebase;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import tesis.ulima.com.tesiskevin.MainActivity;
import tesis.ulima.com.tesiskevin.R;

public class MyNotificationManager {

    private Context mCtx;
    private static MyNotificationManager mInstance;

    private MyNotificationManager(Context context) {
        mCtx = context;
    }

    public static synchronized MyNotificationManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new MyNotificationManager(context);
        }
        return mInstance;
    }

    public void displayNotification(String body,String title) {
        Random random=new Random();
        int notificationId=random.nextInt();
        Vibrator v = (Vibrator)mCtx.getSystemService(Context.VIBRATOR_SERVICE);
        // Start without a delay
        // Each element then alternates between vibrate, sleep, vibrate, sleep...
        long[] pattern = {0, 500, 250, 500, 250,500, 250};

        Intent intent = new Intent(mCtx, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(mCtx, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder mBuilder;
        mBuilder = new NotificationCompat.Builder(mCtx);
        mBuilder.setSmallIcon(R.drawable.logo_ulima);
        mBuilder.setContentTitle(title);
        mBuilder.setContentText(body);
        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setOnlyAlertOnce(true);
        mBuilder.setAutoCancel(true);

        NotificationManager nm=(NotificationManager)mCtx.getSystemService(Context.NOTIFICATION_SERVICE);
        mBuilder.setPriority(Notification.PRIORITY_HIGH);
        mBuilder.setVibrate(pattern);

        if (nm != null) {
            nm.notify(notificationId, mBuilder.build());
        }
    }
}
