package test01.evan.idv.app_08_interactiveview;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

/* 元件互動   (老師Demo AppLucky) setText 放入
/  多執行續  Runnable Handler
/  實作遞迴功能


*/
public class MainActivity extends AppCompatActivity {
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
    private boolean stop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textView);
        button = (Button) findViewById(R.id.button);
    }

    public void onClick(View view) {
        if (stop) {
            handler.removeCallbacks(r);
            button.setText("STATE");

        } else {
            handler.postDelayed(r, 0);
            button.setText("STOP");
        }
        stop = !stop;

//        int n = new Random().nextInt();
//        textView.setText(n + "");
//        String str = (String) textView.getText();
//
//        switch (str) {
//            case "7":
//                textView.setText("8");
//                textView.setTextColor(Color.BLUE);
//                break;
//            case "8":
//                textView.setText("7");
//                textView.setTextColor(Color.RED);
//        }

    }


}
