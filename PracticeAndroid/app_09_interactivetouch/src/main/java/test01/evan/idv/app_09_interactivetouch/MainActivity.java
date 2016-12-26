package test01.evan.idv.app_09_interactivetouch;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

// Touch 監聽事件
public class MainActivity extends AppCompatActivity {
    private Context context;
    private TextView textView;
    private Button button;
    private Runnable r = new Runnable() {
        @Override
        public void run() {
            int n = new Random().nextInt(100); // 0~99
            textView.setText(n + "");
            handler.postDelayed(r, 200); // 遞迴 r 在呼叫自己 r
        }
    };

    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;
        textView = (TextView) findViewById(R.id.textView);
        button = (Button) findViewById(R.id.button);
        button.setOnTouchListener(new Touch()); // 設定Touch監聽器事件

        button.setOnClickListener(new View.OnClickListener() { // 測試與OnTouch共存監聽事件處理
            @Override
            public void onClick(View v) {
                Toast.makeText(context, " OnClick", Toast.LENGTH_SHORT).show();
            }
        });

        // 也可以下面作法
//        button.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//
//                int action = event.getAction(); // 取的使用者目前的動作
//                switch (action) {
//                    case MotionEvent.ACTION_DOWN: // 壓下
//                        handler.postDelayed(r, 0);
//                        break;
//                    case MotionEvent.ACTION_MOVE: // 滑動
//                        handler.postDelayed(r, 0);
//                        break;
//                    case MotionEvent.ACTION_UP:   // 放開
//                        handler.removeCallbacks(r);
//                        break;
//                return false;
//            }
//        });
    }

    private class Touch implements View.OnTouchListener { // 內部類別

        @Override
        public boolean onTouch(View v, MotionEvent event) { // event 包著使用者的行為
            int action = event.getAction(); // 取的使用者目前的動作
            switch (action) {
                case MotionEvent.ACTION_DOWN: // 壓下
                    handler.postDelayed(r, 0);
                    break;
                case MotionEvent.ACTION_MOVE: // 滑動
                    textView.setX(event.getX()); //  X移動
                    textView.setY(event.getY()); //  y移動
                    handler.postDelayed(r, 0);
                    Toast.makeText(context, "X:" + event.getX() + "Y:" + event.getY(), Toast.LENGTH_SHORT).show(); // 取得目前移動的座標
                    break;
                case MotionEvent.ACTION_UP:   // 放開
                    handler.removeCallbacks(r);
                    break;
            }
            /* false表示 還可以繼續這個view的事件處裡，
            /  true 表示只能在OnTouch裡動作，
            /  其他監聽事件無法使用，避免一個view裡同時有多個監聽事件。
            */
            return true;
        }
    }
}
