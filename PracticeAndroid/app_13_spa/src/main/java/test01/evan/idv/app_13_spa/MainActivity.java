package test01.evan.idv.app_13_spa;

import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
// 取的裝置系統震動功能並實施
public class MainActivity extends AppCompatActivity {
    private Vibrator vibrator;
    private ImageButton imageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE); // 取得系統震動元件
        imageButton = (ImageButton) findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {  // 建立圖片按鈕監聽事件
            @Override
            public void onClick(View v) {
                vibrator.vibrate(1000 * 10);
            }
        });

    }
}
