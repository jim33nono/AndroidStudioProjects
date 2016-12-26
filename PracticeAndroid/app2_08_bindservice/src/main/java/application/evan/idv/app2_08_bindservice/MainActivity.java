package application.evan.idv.app2_08_bindservice;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import application.evan.idv.app2_08_bindservice.services.BMI;
import application.evan.idv.app2_08_bindservice.services.MyBindService;

public class MainActivity extends AppCompatActivity {
    private Context context;
    private BMI bmi;
    private ServiceConnection coon;
    private EditText et_Height, et_Weight;
    private Button button;
    private TextView tv_Result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;
        et_Height = (EditText) findViewById(R.id.et_Height);
        et_Weight = (EditText) findViewById(R.id.et_Weight);
        button = (Button) findViewById(R.id.button);
        tv_Result = (TextView) findViewById(R.id.tv_Result);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        coon = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                bmi = ((MyBindService.MyBinder) service).getBMI();
                button.setEnabled(true);
                Toast.makeText(context, "OK", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                button.setEnabled(false);
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
            }
        };


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MyBindService.class);
                bindService(intent, coon, Context.BIND_AUTO_CREATE);
            }
        });
    }

    public void onClick(View v) {
        double h = Double.parseDouble(et_Height.getText().toString());
        double w = Double.parseDouble(et_Weight.getText().toString());

//        double result = bmi.calcBMI(h, w);
//        tv_Result.setText(String.valueOf(result));
//        Toast.makeText(context, result + "", Toast.LENGTH_SHORT).show();
        // 塞View給Service 讓 bindService 處理，連執行續也可以不斷更新
        bmi.calcBMI(h, w, tv_Result);
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
