package idv.evan.app_03_logcat;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

// Logcat 設定與測試  + Toast實作
public class MainActivity extends AppCompatActivity {
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
    }


    public void onClick(View view) {
        
        Log.i("msg", "Test Logcat");
        Log.i("log", "Test Logcat");
        
        // 短暫查看 Logcat
        Toast.makeText(context, "Test Logcat", Toast.LENGTH_SHORT).show();
    }
}
