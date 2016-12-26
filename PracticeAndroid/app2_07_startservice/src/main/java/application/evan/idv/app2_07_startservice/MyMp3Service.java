package application.evan.idv.app2_07_startservice;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.IBinder;

public class MyMp3Service extends Service {
    private MediaPlayer player;
    private Context context;
    private NotificationManager manager;
    private Handler handler;


    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        handler = new Handler();
        player = MediaPlayer.create(context, R.raw.love_paradise);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        player.start();
        handler.post(r);
//        createNotification();

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        handler.removeCallbacks(r);
        player.stop();
        player.release();
        manager.cancel(100);
    }

    Runnable r = new Runnable() {
        @Override
        public void run() {
            handler.postDelayed(this, 1000);
            createNotification();
        }
    };

    private void createNotification() {


        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//        無進度
//        Notification.Builder notification = new Notification.Builder(context);
//        notification.setSmallIcon(android.R.drawable.ic_media_play);
//        notification.setContentTitle("Mp3");
//        notification.setContentText("Love Paradise");
//        notification.setContentIntent(pendingIntent);
//        notification.setOngoing(true);
//        manager.notify(101, notification.build());

        Notification.Builder notification = new Notification.Builder(context);

        notification.setContentTitle("Play Mp3"); // 標題
        // 做判斷 如果進度完成改變Title 與打開 setAutoCancel 與 setContentIntent換畫面

        notification.setSmallIcon(android.R.drawable.ic_media_play);
        notification.setContentText("Progress= " + (player.getCurrentPosition() / player.getDuration()) + "%"); // 內容
        notification.setContentIntent(pendingIntent);
        notification.setContentInfo("mp3"); // 副標題
        notification.setProgress(player.getDuration(), player.getCurrentPosition(), false); // 進度條

        notification.setOngoing(true); // 長期顯示，無法清除的Notification
//        builder.setAutoCancel(true); // 點擊後消失 Notification
//
//        builder.setContentIntent(pendingIntent);
        //註冊通知信息
        manager.notify(100, notification.build());


    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
