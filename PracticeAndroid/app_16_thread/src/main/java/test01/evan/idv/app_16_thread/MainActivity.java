package test01.evan.idv.app_16_thread;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Random;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;

/* 主執行續 與 子執行續  實作 Thread
/ 股票查詢jar檔 複製到 project >> 'appName' >>libs
/ File >> Structure project >> Dependencies >> dependency 引用相關文件
/ mainfests 打開使用網路權限
*/
public class MainActivity extends AppCompatActivity {
    private Context context;
    private TextView textView, textView2;
    private Button button;
    private EditText editText;
    private String symbol;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        textView = (TextView) findViewById(R.id.textView);
        editText = (EditText) findViewById(R.id.editText);
        button = (Button) findViewById(R.id.button);

        // 內部類別註冊點擊監聽事件
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 取得user輸入內容給 symbol.......重點取的輸入輸出都為字串
                // 輸入 to.String()
                // 輸出 String.valueOf();
                symbol = editText.getText().toString();
//                new RunNumber().start();  // 測試一
                new RunStock(symbol).start(); // 測試二

            }
        });
    }

    // 一、 主執行 + 子執行續  架構
    private class RunNumber extends Thread {
        int number;

        RunNumber() {
        } // 空建構式

        Runnable r = new Runnable() { // 主執行續，才可以改變UI,更新UI
            @Override
            public void run() {
                try {
                    Thread.sleep(30000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Toast.makeText(context, String.valueOf(number), Toast.LENGTH_SHORT).show();
                textView.setText(String.valueOf(number));
            }
        };

        @Override
        public void run() { // 子執行續，工作，不能更新UI
            int n = new Random().nextInt(1000);
            number = n;
            runOnUiThread(r); // 呼叫主執行續
        }
    }

    /* 二、 抓取網路資源 股票
    / 2330.TW 台積電
    / ^TWII
    / USDTWD=x 美金換台幣
    / CNYTWD=x 人民幣換台幣
    / CLG16.NYM
    */
    private class RunStock extends Thread {
        Stock stock;
        String symbol;

        RunStock(String symbol) {
            this.symbol = symbol;
        }

        Runnable r = new Runnable() {
            @Override
            public void run() {
                BigDecimal price = stock.getQuote().getPrice(); // 價格
                BigDecimal change = stock.getQuote().getChangeInPercent(); // 漲跌
//                BigDecimal peg = stock.getStats().getPeg();
//                BigDecimal dividend = stock.getDividend().getAnnualYieldPercent();
                if (change.doubleValue() >= 0) {
                    textView.setTextColor(Color.RED);
                } else {
                    textView.setTextColor(Color.GREEN);
                }
                textView.setText(price.toString() + " \n" + change.toString());
            }
        };

        @Override
        public void run() {

            while (true) {
                try {
                    stock = YahooFinance.get(symbol); // 網路存取相關，需處理IOException
                    Thread.sleep(1000);  //等待(毫秒)，需處理 InterruptedException
                    runOnUiThread(r);
                } catch (IOException | InterruptedException e) { // 一次處理兩種例外  可以用 "|" 來區分
                    e.printStackTrace();
                }
            }
        }
    }

}
