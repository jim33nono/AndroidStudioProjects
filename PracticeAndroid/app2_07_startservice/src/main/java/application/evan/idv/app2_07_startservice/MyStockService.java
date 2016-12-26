package application.evan.idv.app2_07_startservice;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.io.IOException;
import java.math.BigDecimal;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;

/**
 * Created by study on 2016/1/22.
 */
public class MyStockService extends Service {
    public static Stock stock;
    private Context context;
    private NotificationManager manager;
    private Handler handler;

    Runnable r = new Runnable() {
        @Override
        public void run() {
            new Thread() {
                @Override
                public void run() {
                    try {
                        createNotification();
                    } catch (IOException e) {
                    }
                }
            }.start();
            handler.postDelayed(this, 1000);
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        handler = new Handler();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        handler.post(r);
        return super.onStartCommand(intent, flags, startId);
//        return Service.START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(r);
    }

    private void createNotification() throws IOException {
        stock = YahooFinance.get("USDTWD=x");
        BigDecimal price = stock.getQuote().getPrice();
        BigDecimal change = stock.getQuote().getChange();

        if (!MainActivity.flag) {
            Intent intent = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            Notification.Builder notification = new Notification.Builder(context);
            notification.setSmallIcon(android.R.drawable.ic_media_play);
            notification.setContentTitle("台灣加權股價指數");
            notification.setContentText(price + "  " + change);
            notification.setContentIntent(pendingIntent);
            notification.setOngoing(true);
            manager.notify(101, notification.build());
        } else {
            manager.cancel(101);
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
