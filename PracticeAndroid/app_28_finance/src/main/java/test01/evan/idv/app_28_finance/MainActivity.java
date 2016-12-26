package test01.evan.idv.app_28_finance;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;

public class MainActivity extends AppCompatActivity {
    private Context context;
    private ListView listView;
    private long sec = 1000;  // 一秒
    private List<Map<String, Object>> list = new ArrayList<>();
    private SimpleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        listView = (ListView) findViewById(R.id.listView);
        list = new ArrayList<>();
        adapter = new SimpleAdapter(
                context,
                list,
                R.layout.stock,
                new String[]{"symbol", "price", "change"},
                new int[]{R.id.tv_symbol, R.id.tv_price, R.id.tv_change}
        ) { // 直接匿名類別覆寫方法
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
//                return super.getView(position, convertView, parent); // 預設
                // 改寫
                View v = super.getView(position, convertView, parent);
                Map map = (Map) getItem(position);
                double change = Double.parseDouble(map.get("change").toString());
                TextView tv_price = (TextView) v.findViewById(R.id.tv_price);
                TextView tv_change = (TextView) v.findViewById(R.id.tv_change);

                if (change >= 0) {
                    tv_price.setTextColor(Color.RED);
                    tv_change.setTextColor(Color.RED);
                } else {
                    tv_price.setTextColor(Color.GREEN);
                    tv_change.setTextColor(Color.GREEN);
                }
                return v;
            }
        };

        listView.setAdapter(adapter);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                final EditText editText = new EditText(context);
                editText.setSingleLine(true);
                builder.setIcon(R.drawable.ic_stock); // 設定圖標
                builder.setTitle("股票查詢"); // 設定標題
                builder.setMessage("請輸入股票代碼:"); // 設定內容信息
                builder.setView(editText);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new RunStocks(editText.getText().toString()).start();
                    }
                });
                builder.setNeutralButton("Cancel", null);
                builder.show();
            }
        });
    }

    class RunStocks extends Thread {
        Stock stock;
        String symbol;

        RunStocks(String symbol) {
            this.symbol = symbol;
        }

        Runnable r = new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, symbol.toString() + "," + stock.getQuote().getPrice() + "," + stock.getQuote().getChange(), Toast.LENGTH_SHORT).show();
                Map<String, Object> map = new HashMap<>();
                map.put("symbol", symbol.toString());
                map.put("price", stock.getQuote().getPrice());
                map.put("change", stock.getQuote().getChange());
                list.add(map);
                adapter.notifyDataSetChanged();
            }
        };

        @Override
        public void run() {
            try {
                stock = YahooFinance.get(symbol);
                Thread.sleep(sec);
                runOnUiThread(r);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
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
