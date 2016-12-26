package test01.evan.idv.app_20_mcdonalds;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// ListView 與 SimpleAdapter
// Collections.sort排序 + Map<>集合 + SimpleAdapter適配器
// ListView.setOnItemLongClickListener 長按item監聽器
// 使用menu動態 增加map資料，搭配 menu_main.xml 檔案設定
// 135行 onOptionsItemSelected >> menu監聽器使用
// 麥當勞菜單
public class MainActivity extends AppCompatActivity {
    private Context context;
    private ListView listView;
    private SimpleAdapter adapter;
    private boolean pricesort = false; // false 為由小排到大
    // 將Map 放到一個集合裡，注意Map的泛型
    private List<Map<String, Object>> foods = new ArrayList<>();

    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        listView = (ListView) findViewById(R.id.listView);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        
        // 定義資料
        // 建立第一筆資料
        Map<String, Object> one = new HashMap<>();
        one.put("name", "大麥克");
        one.put("price", 90);
        one.put("imageId", R.drawable.big_mac);
        // 建立第二筆資料
        Map<String, Object> two = new HashMap<>();
        two.put("name", "薯條");
        two.put("price", 30);
        two.put("imageId", R.drawable.french_fries);

        // 先預設放入兩筆資料
        foods.add(one);
        foods.add(two);

        // 設定 適配器
        adapter = new SimpleAdapter(
                context, //
                foods,  // 集合資料，裡面裝著Map資料
                R.layout.row, // 顯示於哪個layout
                new String[]{"name", "price", "imageId"},  // Map內容key對應到哪個實體
                new int[]{R.id.name, R.id.price, R.id.imageId} // 對應的位置
        );

        // 將適配器放入listView內
        listView.setAdapter(adapter);
        
        // 長按list內的item 動作刪除資料
        listView.setLongClickable(true); // 預防長按功能預設被關閉，先打開長按控鍵
        // setOnItemLongClickListener >> 註冊長按Item監聽器 
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                foods.remove(position); // 當長按動作時，foods集合 remove刪除目前position點擊位置的資料
                adapter.notifyDataSetChanged(); // 通知adapter 資料已經更新
                return false;
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // 預設為 FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        //因為要更改 fab裡的圖  所以把其宣告於實體變數
        fab = (FloatingActionButton) findViewById(R.id.fab);
        // 註冊fab點擊監聽器
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 實作排序功能，須給Collections比對規則，實作compare
                // 回家作業轉換排序
                Collections.sort(foods, new Comparator<Map<String, Object>>() {
                    @Override
                    public int compare(Map<String, Object> lhs, Map<String, Object> rhs) {
                        int p1 = Integer.parseInt(lhs.get("price").toString());
                        int p2 = Integer.parseInt(rhs.get("price").toString());
                        // 排序規則
                        // 不可以這裡改變開關狀態，這裡只是實作規則compare給Collections
                        if (pricesort)
                            return p1 - p2;
                        else
                            return p2 - p1;
                    }
                });
                
                // 排序規則後，通資adapter改變資料內容
                adapter.notifyDataSetChanged();
                // 排序完更改開關狀態
                if (pricesort) {
                    pricesort = false;
                    // 改變fab圖示，不是用setBackground()，這是改變底色
                    fab.setImageResource(R.drawable.ic_sort_rise);
                } else {
                    pricesort = true;
                    fab.setImageResource(R.drawable.ic_sort_drop);
                }
            }
        });
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

        Map<String, Object> map = new HashMap<>();

        switch (id) {
            case R.id.action_settings:
                map.put("name", "可樂");
                map.put("price", 45);
                map.put("imageId", R.drawable.coca_cola);
                break;
            case R.id.action_settings2:
                map.put("name", "沙拉");
                map.put("price", 60);
                map.put("imageId", R.drawable.salad);
                break;
            case R.id.action_settings3:
                map.put("name", "冰淇淋");
                map.put("price", 55);
                map.put("imageId", R.drawable.ice_cream);
                break;
        }
        foods.add(map);
        adapter.notifyDataSetChanged();


        return super.onOptionsItemSelected(item);
    }
}
