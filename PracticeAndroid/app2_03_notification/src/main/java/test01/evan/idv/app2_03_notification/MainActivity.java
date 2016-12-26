package test01.evan.idv.app2_03_notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

// 實作Notification(抬頭顯示器)
// 項1、普通信息通知 + 內容設定 + 亂數產生多個nid
// 項2、仿下載進度條 + 跳轉業面
public class MainActivity extends AppCompatActivity {
    private NotificationManager manager;
    private Context context;
    private Handler handler;
    private int progress = 0; // 進度
    private static final int DOWNLOAD_NID = 999;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;
        handler = new Handler();
        // 取得通知服務管理器
        manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 項1、亂數產生notify註冊編號
                // int nid = new Random().nextInt(1000000);
                // 項1、呼叫通知 (如果 nid 相同則更新現有通知信息，不同則建立新信息)
                // createNotification(nid);

                // 項2、呼叫下載通知
                handler.postDelayed(r, 0);
            }
        });
    }

    // 項2、下載遞迴
    Runnable r = new Runnable() {

        @Override
        public void run() {
            createNotification_download(DOWNLOAD_NID);
            handler.postDelayed(r, 50);
            if (progress > 100) {
                handler.removeCallbacks(r);
            }

        }
    };

    // 項2、建立下載通知
    private void createNotification_download(int nid) {

        // 點擊通知 轉畫面
        Intent intent = new Intent(context, AdActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, nid, intent, PendingIntent.FLAG_CANCEL_CURRENT);


        Notification.Builder builder = new Notification.Builder(context);

        builder.setContentTitle("Download"); // 標題
        // 做判斷 如果進度完成改變Title 與打開 setAutoCancel 與 setContentIntent換畫面
        if (progress >= 100) {
            builder.setContentTitle("Download Completed");
            builder.setAutoCancel(true);
            builder.setContentIntent(pendingIntent);
        }
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentText("Progress= " + ((int) (progress / 100.0 * 100)) + "%"); // 內容
        builder.setContentInfo("INFO"); // 副標題
        builder.setProgress(100, ++progress, false); // 進度條

        builder.setOngoing(true); // 長期顯示，無法清除的Notification
//        builder.setAutoCancel(true); // 點擊後消失 Notification
//
//        builder.setContentIntent(pendingIntent);
        //註冊通知信息
        manager.notify(nid, builder.build());
    }


    // 項1、建立通知
    private void createNotification(int nid) {

        // 點擊通知 轉畫面
        Intent intent = new Intent(context, AdActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, nid, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        Notification.Builder builder = new Notification.Builder(context);
        builder.setSmallIcon(R.mipmap.ic_launcher); // icon圖標
        builder.setContentTitle("Hello Notification"); // 標題
        builder.setContentText("TEXT" + "Nid: " + nid); // 內容
        builder.setContentInfo("INFO"); // 副標題

        builder.setOngoing(true); // 長期顯示，無法清除的Notification
        builder.setAutoCancel(true); // 點擊後消失 Notification

        builder.setContentIntent(pendingIntent);
        //註冊通知信息
        manager.notify(nid, builder.build());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
