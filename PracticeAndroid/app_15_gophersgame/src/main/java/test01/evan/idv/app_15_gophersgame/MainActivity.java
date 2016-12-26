package test01.evan.idv.app_15_gophersgame;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/* 打地鼠遊戲
/  manifests 去除Activity 標題
/  manifests 取得裝置震動元件權限 
/  裝置震動元件取得使用 Vibrator
/  實作點擊事件 OnClick & OnTouch
/  實作多執行續 Runnable run()
/  View 動態產生設定: 
/  ----文字內容 setText()
/  ----文字大小 setTextSize()
/  ----文字顏色 setTextColor()
/  ----圖片內容 setImageResource()
/  使用Boolean做狀態開關控制
/
*/

public class MainActivity extends AppCompatActivity {
    private Context context;
    private int[] movie;
    private ImageView[] imageArray;
    private TextView textView;
    private Vibrator vibrator; // 裝置震動元件
    private Button btn_play, btn_stop, btn_reset;
    private int score; // 計分
    private long sec = 1000;
    private Handler handler;
    private Animation[] animation;
    private boolean playStatus = false, stopStatus = false; // 遊戲狀態開關

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        // create mouse animation
        movie = new int[]{
                R.drawable.mouse1, R.drawable.mouse2, R.drawable.mouse3, R.drawable.mouse2, R.drawable.mouse1, R.drawable.hole
        };

        //  findViewById ImageView
        imageArray = new ImageView[]{
                (ImageView) findViewById(R.id.imageView),
                (ImageView) findViewById(R.id.imageView2),
                (ImageView) findViewById(R.id.imageView3),
                (ImageView) findViewById(R.id.imageView4),
                (ImageView) findViewById(R.id.imageView5),
        };
        // 批量設定 ImageView 初始圖片
        for (int i = 0; i < imageArray.length; i++) {
            imageArray[i].setImageResource(R.drawable.hole);
        }
        // findViewById 
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE); // 取得裝置震動元件
        textView = (TextView) findViewById(R.id.textView);
        textView.setTextColor(Color.RED); // 設定顯示文字顏色，(Color.RED)系統預設顏色
        textView.setTextSize(50); // 設定顯示文字大小
        btn_play = (Button) findViewById(R.id.btn_play);
        btn_stop = (Button) findViewById(R.id.btn_stop);
        btn_reset = (Button) findViewById(R.id.btn_reset);
        btn_play.setOnClickListener(new OnClick()); // 設定監聽事件
        btn_stop.setOnClickListener(new OnClick());
        btn_reset.setOnClickListener(new OnClick());
        btn_play.setText("開始"); // 設定按鈕顯示文字
        btn_stop.setText("停止");
        btn_reset.setText("重置");
        handler = new Handler();

        // 設定每個ImageView 的 OnTouch 點擊監聽事件
        animation = new Animation[6];
        for (int i = 0; i < imageArray.length; i++) {
            animation[i] = new Animation(imageArray[i]);
            imageArray[i].setOnTouchListener(new OnTouch(animation[i]));
        }
    }

    // button OnClick
    private class OnClick implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            int btnID = view.getId(); // 取得button view 的 ID
            vibrator.vibrate(sec / 4); // 任何監聽過的button，每次按下都會震動

            switch (btnID) {
                case R.id.btn_play: // 開始button
                    if (!playStatus && !stopStatus) {
                        playStatus = true;
                        Toast.makeText(context, "開始遊戲", Toast.LENGTH_SHORT).show();
                        btn_play.setTextColor(Color.BLACK); // 設定按鈕文字顯示顏色
                        for (Animation a : animation) {
                            handler.post(a);
                        }
                    } else if (playStatus && !stopStatus) {
                        Toast.makeText(context, "正在遊戲", Toast.LENGTH_SHORT).show();
                    } else if (!playStatus && stopStatus) {
                        Toast.makeText(context, "繼續遊戲", Toast.LENGTH_SHORT).show();
                        btn_play.setTextColor(Color.BLACK);
                        for (Animation a : animation) {
                            handler.post(a);
                        }
                        playStatus = true;
                        stopStatus = false;
                        btn_play.setText("開始");
                    }
                    break;
                case R.id.btn_stop: // 暫停button
                    if (!playStatus && !stopStatus) {
                        Toast.makeText(context, "還沒開始遊戲", Toast.LENGTH_SHORT).show();
                    } else if (playStatus && !stopStatus) {
                        playStatus = false;
                        stopStatus = true;
                        Toast.makeText(context, "暫停遊戲", Toast.LENGTH_SHORT).show();
                        btn_play.setText("繼續");
                        btn_play.setTextColor(Color.RED);
                    } else if (!playStatus && stopStatus) {
                        Toast.makeText(context, "遊戲已暫停", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.btn_reset: // 重置button
                    if (!playStatus && !stopStatus) {
                        Toast.makeText(context, "還沒開始遊戲", Toast.LENGTH_SHORT).show();
                    } else {
                        playStatus = false;
                        stopStatus = false;
                        score = 0;
                        for (int i = 0; i < imageArray.length; i++) {
                            imageArray[i].setImageResource(R.drawable.hole);
                        }
                        Toast.makeText(context, "遊戲已重置", Toast.LENGTH_SHORT).show();
                        textView.setText("");
                        btn_play.setText("開始");
                        btn_play.setTextColor(Color.BLACK);
                    }
                    break;
            }
        }
    }

    // 執行續
    private class Animation implements Runnable {
        int mouseID;
        ImageView imageView;
        boolean hit;

        Animation(ImageView imageView) {
            this.imageView = imageView;
        }

        @Override
        public void run() {

            if (!playStatus) {
                mouseID = 0;
                return;
            }
            if (hit) {
                imageView.setImageResource(R.drawable.knock);
                hit = false;
                mouseID = 0;
                handler.postDelayed(this, sec);
            } else {
                mouseID = mouseID % movie.length;
                imageView.setImageResource(movie[mouseID]);
                // 重點 每個mouseMovie出現間隔
                int random = (int) (Math.random() * sec * 5) % 3 + 1;
                handler.postDelayed(this, (random * 300));
                mouseID = ++mouseID % movie.length;
            }

        }
    }

    // ImageViews OnTouch
    private class OnTouch implements View.OnTouchListener {
        Animation a;

        OnTouch(Animation a) {
            this.a = a;
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            int action = event.getAction(); // 取得使用者的點擊動作
            if (playStatus) {
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        if (movie[a.mouseID] == R.drawable.mouse3) {
                            vibrator.vibrate(sec / 4);  // 只有成功打擊才會震動
                            a.hit = true;
                            textView.setText("得分 : " + String.valueOf(++score));
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                }
            }
            return true;
        }
    }

}


