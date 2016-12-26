package test01.evan.idv.app_10_bingo;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

// Bingo
public class MainActivity extends AppCompatActivity {

    private Context context;
    private TextView textView;
    private ImageView imageView, imageView2, imageView3;
    private Button button;
    private int[] bingo = {R.drawable.apple,
            R.drawable.bar,
            R.drawable.bell,
            R.drawable.mango,
            R.drawable.seven,
            R.drawable.star,
            R.drawable.watermelon};
    private Handler handler = new Handler();

    private Runnable r = new Runnable() {
        @Override
        public void run() {
            imageView.setImageResource(bingo[new Random().nextInt(bingo.length)]);
            imageView2.setImageResource(bingo[new Random().nextInt(bingo.length)]);
            imageView3.setImageResource(bingo[new Random().nextInt(bingo.length)]);
            handler.postDelayed(r, 150); // 遞迴 r 在呼叫自己 r
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        button = (Button) findViewById(R.id.button);
        imageView = (ImageView) findViewById(R.id.imageView);
        imageView2 = (ImageView) findViewById(R.id.imageView2);
        imageView3 = (ImageView) findViewById(R.id.imageView3);
        textView = (TextView) findViewById(R.id.textView);
        textView.setText("開始遊戲");

        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                int action = event.getAction(); // 取的使用者目前的動作
                switch (action) {
                    case MotionEvent.ACTION_DOWN: // 壓下
                        handler.post(r);
                        break;
                    case MotionEvent.ACTION_MOVE: // 滑動
                        handler.postDelayed(r, 150);
                        break;
                    case MotionEvent.ACTION_UP:   // 放開
                        handler.removeCallbacks(r);
                        if (imageView == imageView2 && imageView2 == imageView3) {
                            textView.setText("中獎了");
                        }
                }
                return true;
            }
        });
        if (imageView.getId() == imageView2.getId()) {
            Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
        }

    }

}
