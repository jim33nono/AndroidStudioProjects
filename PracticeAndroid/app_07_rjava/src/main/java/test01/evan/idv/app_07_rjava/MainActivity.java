package test01.evan.idv.app_07_rjava;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

// @+id/button  --->> R.Java  --->> Java & i18N規則
public class MainActivity extends AppCompatActivity {
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
    }


    public void onClick(View view) {

        int id = view.getId();

        switch (id) {
            case R.id.button:
                Toast.makeText(context, R.string.message, Toast.LENGTH_SHORT).show();
                break;
            case R.id.button2:
                Toast.makeText(context, R.string.message2, Toast.LENGTH_SHORT).show();
                break;
        }

        Log.i("msg", "Test Logcat");
        Log.i("log", "Test Logcat");
    }
}
