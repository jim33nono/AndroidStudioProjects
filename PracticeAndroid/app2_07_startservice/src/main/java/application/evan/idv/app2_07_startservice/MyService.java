package application.evan.idv.app2_07_startservice;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.util.Date;

/**
 * Created by study on 2016/1/22.
 */
public class MyService extends Service {
    private Handler handler;

    // 宣告 & 建立實體
    @Override
    public void onCreate() {
        super.onCreate();
        handler = new Handler();
    }

    // 遞迴
    Runnable r = new Runnable() {
        @Override
        public void run() {
            System.out.println(new Date());
            handler.postDelayed(this, 1000);
        }

    };

    // 商業邏輯
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        handler.post(r);

        return super.onStartCommand(intent, flags, startId);
    }

    // 銷毀
    @Override
    public void onDestroy() {
        super.onDestroy();

        handler.removeCallbacks(r);
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
