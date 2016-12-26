package idv.evan.app_01_hello;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

// Hello Android 
public class MainActivity extends AppCompatActivity {
    private TextView text;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        text = (TextView) findViewById(R.id.text);
        
        text.setText("HELLO ANDROID");
    }
}
