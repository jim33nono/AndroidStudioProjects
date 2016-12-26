package test01.evan.idv.app_21_finance;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;

public class MainActivity extends AppCompatActivity {
    private Context context;
    private ListView lv_row;
    private EditText et_add;
    private Button btn_commit;
    private TextView tv_symbol, tv_price, tv_change;
    private List<Map<String, Object>> stocks = new ArrayList<>();
    private SimpleAdapter adapter;
    private boolean priceSortStatus = false;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

//        tv_symbol = (TextView) findViewById(R.id.tv_symbol);
//        tv_price = (TextView) findViewById(R.id.tv_price);
//        tv_change = (TextView) findViewById(R.id.tv_change);
        lv_row = (ListView) findViewById(R.id.lv_row);
        et_add = (EditText) findViewById(R.id.et_add);
        btn_commit = (Button) findViewById(R.id.btn_commit);

        adapter = new SimpleAdapter(
                context,
                stocks,  // 集合資料，裡面裝著Map資料
                R.layout.row, // 顯示於哪個layout
                new String[]{"symbol", "price", "change"},  // Map內容key對應到哪個實體
                new int[]{R.id.tv_symbol, R.id.tv_price, R.id.tv_change} // 對應的位置
        );

        lv_row.setAdapter(adapter);

        btn_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new RunStocks().start();
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                // 排序規則
//                Collections.sort(stocks, new Comparator<Map<String, Object>>() {
//                    @Override
//                    public int compare(Map<String, Object> lhs, Map<String, Object> rhs) {
//                        int p1 = Integer.parseInt(lhs.get("price").toString());
//                        int p2 = Integer.parseInt(rhs.get("price").toString());
//                        if (priceSortStatus)
//                            return p1 - p2;
//                        else
//                            return p2 - p1;
//                    }
//
//                });
//                adapter.notifyDataSetChanged();
//
//                if (priceSortStatus) {
//                    priceSortStatus = false;
//                    fab.setImageResource(R.drawable.ic_sort_rise);
//                } else {
//                    priceSortStatus = true;
//                    fab.setImageResource(R.drawable.ic_sort_drop);
//                }
            }
        });
    }


    class RunStocks extends Thread {
        Stock stock;
        String symbol = et_add.getText().toString();

        RunStocks() {
        }

        Runnable r = new Runnable() {

            @Override
            public void run() {

                Map<String, Object> map = new HashMap<>();
                map.put("symbol", symbol);
                map.put("price", stock.getQuote().getPrice());
                map.put("change", stock.getQuote().getChange());
                stocks.add(map);
                adapter.notifyDataSetChanged();

            }
        };

        @Override
        public void run() {
            try {
                stock = YahooFinance.get(symbol);
                Thread.sleep(1000);
                runOnUiThread(r);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
//            runOnUiThread(r);
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
