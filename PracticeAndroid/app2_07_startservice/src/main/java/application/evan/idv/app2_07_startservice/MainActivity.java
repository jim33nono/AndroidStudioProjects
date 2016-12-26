package application.evan.idv.app2_07_startservice;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.math.BigDecimal;

import yahoofinance.Stock;

// Service 背景服務 獨立start型服務程式
//
// 註冊Service to AndroidMainFests in  <application  />
//<service android:name=".MyService" android:enabled="true" android:exported="true"/>
// exported 代表 被外部APP啟用  同時啟用該App的Service
public class MainActivity extends AppCompatActivity {
    private Context context;
    private Button btn_start, btn_stop;
    private Intent intent;
    private Handler handler;
    public static boolean flag;
    private TextView tv_Result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        flag = true;
        handler = new Handler();
        tv_Result = (TextView) findViewById(R.id.tv_Result);

        // 例1 遞迴 new Date
//         intent = new Intent(context, MyService.class);
        // 例2 背景音樂播放
        intent = new Intent(context, MyMp3Service.class);
        // 例3 股票
//        intent = new Intent(context, MyStockService.class);

        btn_start = (Button) findViewById(R.id.btn_start);
        btn_stop = (Button) findViewById(R.id.btn_stop);

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 啟動Service
                startService(intent);
            }
        });

        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 停止Service
                stopService(intent);
            }
        });
        handler.post(r);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        flag = false;
    }

    Runnable r = new Runnable() {
        @Override
        public void run() {
            Stock stock = MyStockService.stock;
            if (stock != null) {
                BigDecimal price = stock.getQuote().getPrice();
                BigDecimal change = stock.getQuote().getChange();
                tv_Result.setText(price + "  " + change );
            }else {

            }
            handler.postDelayed(r, 1000);
        }
    };

}
