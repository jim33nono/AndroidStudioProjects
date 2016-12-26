package test01.evan.idv.app_17_schulet;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private Context context;
    private List<Integer> list = new ArrayList<>();
    private ArrayAdapter<Integer> adapter;
    private GridView gridView;
    private TextView tvitem;
    private int number, griid = 25; // 5*5 方格
    private long sec = 1000, tsec, csec, cms;
    private boolean startflag = false, playStatus = false;
    private Vibrator vibrator;
    private String s;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("");
        context = this;
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        // 1. 建立 list集合元素
        for (int i = 1; i <= griid; i++) {
            list.add(i);
        }

        // 2. 建立 Array Adapter
        // android.R.layout.simple_dropdown_item_1line 是android內建樣式，也可以自訂一個activity
        // adapter = new ArrayAdapter<Integer>(context, android.R.layout.simple_dropdown_item_1line, list);
        // 自訂layout樣式
        adapter = new ArrayAdapter<Integer>(context, R.layout.gridview_item, list);


        // 3. 宣告 GridView 並放入適配器內容
        gridView = (GridView) findViewById(R.id.gridView);
        gridView.setAdapter(adapter); // 將 adapter 放入 gridView 中

        // 4.註冊 OnItemClickListener 不可以用 OnClickListener，是要取GridView 內的 Item元素
        number = 0;
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 大膽傳型成int,因為之宣告的Adapter裡面是int
                int num = (Integer) parent.getItemAtPosition(position);
//                Snackbar.make(view, num + "", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                //
                View v = view;
                TextView tv = (TextView) view;
//                // equalsIgnoreCase 可以使用此方式比對不分大小寫
//                if (tv.getText().toString().equalsIgnoreCase("*")) {
//                    tv.setText(num + ""); // 目前位置
//                    // tv.setText(list.get(position) + ""); // list 位置
//                    tv.setTextColor(Color.BLACK);
//                } else {
//                    tv.setText("*");
//                    tv.setTextColor(Color.RED);
//                }

                // 判断结果，如果所有的结果是升序的那么是成功的
                if (num == number + 1 && playStatus) {
                    number++;
                    vibrator.vibrate(sec / 4);
                    if (number < list.size()) {
                    } else {
                        startflag = false;
                    }
                } else if (!playStatus) {
                    Snackbar.make(view, "還沒開始遊戲!", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                } else {
                    vibrator.vibrate(sec);
                    Snackbar.make(view, "點錯了，該點數字" + (number + 1), Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                }

                // 判断结果，點擊至最後一個 完成遊戲
                if (number == list.size()) {
                    vibrator.vibrate(sec);
                    playStatus = false;
                    Snackbar.make(view, "完成遊戲!", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                }
            }
        });

        //宣告Timer
        Timer timer01 = new Timer();

        //設定Timer(task為執行內容，0代表立刻開始,間格1秒執行一次)
        timer01.schedule(task, 0, (sec / 60));

        // 浮動按鈕的實體建立 & 註冊點擊監聽事件
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  類似Toast
                //  Snackbar.make(view, "Play GAME !!", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                Snackbar.make(view, "開始遊戲!", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                Collections.shuffle(list); //  Java的方法，用法就是隨機打亂原來的順序,和洗牌一樣............重要
                adapter.notifyDataSetChanged(); // 通知Adapter 資料已經被更改.......重要
                vibrator.vibrate(sec);
                number = 0;
                tsec = 0;
                playStatus = true;
                startflag = true;

                // 倒數計時器
//                new CountDownTimer(sec*30, 1000){
//
//                    @Override
//                    public void onTick(long millisUntilFinished) {
//                        
//                    }
//
//                    @Override
//                    public void onFinish() {
//
//                    }
//                };

                // 取出GridView內容 並將全部內容text文字上色
//                for (int i = 0; i < gridView.getChildCount(); i++) {
//                    // getChildCount() 內容有多少元素
//                    // getChildAt(int) 第幾個元素
//                    View vbg = gridView.getChildAt(i); // 取元素
//                    vbg.setBackgroundColor((i % 2 == 0) ? Color.DKGRAY : Color.GRAY);
//                    // 知道android.R.layout.simple_dropdown_item_1line裡有TextView，可以大膽轉轉型成 TextView
//                    TextView v = (TextView) gridView.getChildAt(i);
//                    v.setGravity(Gravity.CENTER); // TextView 內的 text文字 置於中央
//                    v.setTextSize(20);
//                    v.setTextColor((i % 2 == 0) ? Color.WHITE : Color.RED);
//                }

            }
        });
    }


    // 5、計時器
    // TimerTask無法直接改變元件因此要透過Handler來當橋樑
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    cms = tsec % 100;
                    csec = tsec / 60;
                    if (csec < 10) {
                        s = "0" + csec;
                    } else {
                        s = "" + csec;
                    }
                    if (cms < 10) {
                        s = s + ":0" + cms;
                    } else {
                        s = s + ":" + cms;
                    }
                    //s字串為00:00格式
                    setTitle("時間經過:  " + s);
                    break;
            }
        }
    };

    // 
    private TimerTask task = new TimerTask() {
        @Override
        public void run() {
            if (startflag) {
                tsec++;
                Message message = new Message();
                message.what = 1;
                handler.sendMessage(message);
            }
        }
    };


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
