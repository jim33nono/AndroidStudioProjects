package com.example.yesee.myfirstapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MyActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MyActivity";
    private Context context;
    private TextView tv_result;
    private EditText et_volt, et_ampere, et_hour, et_kWh;
    private double volt, ampere, hour, kWh, sum=0;
    private Button btn, btn2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        context = this;

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        findViews();

//
//        TextView tv = new TextView(context);
//        tv.setText(getString(R.string.action_settings));
//
//        Log.i(TAG, et_volt.getText().toString() + "Test");

        btn.setOnClickListener(MyActivity.this);
        btn2.setOnClickListener(MyActivity.this);
    }



    private void findViews (){
        et_volt = (EditText)findViewById(R.id.et_volt);
        et_ampere = (EditText)findViewById(R.id.et_ampere);
        et_hour = (EditText)findViewById(R.id.et_hour);
        et_kWh = (EditText)findViewById(R.id.et_kWh);
        btn = (Button)findViewById(R.id.btn);
        btn2 = (Button)findViewById(R.id.btn2);
        tv_result = (TextView)findViewById(R.id.tv_result);

    }


    private void count(){

        boolean bool_volt = (et_volt == null && et_volt.getText().toString().isEmpty());
        if (bool_volt || et_ampere.getText().toString().isEmpty()
                || et_hour.getText().toString().isEmpty() || et_kWh.getText().toString().isEmpty()){
            tv_result.setText("Data Type Error");
        }
        else{
        sum = (( volt * ampere / 1000 ) * ( hour * 30 ) * kWh);
        Log.i(TAG, "in click !" + sum);
        tv_result.setText( "$" + sum );
        }

        Toast.makeText(context,"ABC",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()){
            case R.id.btn:
                count();
                break;
            case R.id.btn2:
                intent.setClass(context, SecondActivity.class);

//                User user = new User();
//                user.setName("dsfsdfds");
//
//                Bundle bundle = new Bundle();
//                bundle.putInt("num", 123);
//                intent.putExtras(bundle);

//                intent.putExtra("user", (Parcelable) user);
//                intent.putExtra("name","123");
                startActivity(intent);
                break;

//          default:
//                Intent intent = new Intent(context, LoginActivity.class);
//                startActivity(intent);
//                break;
        }


    }
//    @Override
//    protected void onStart() {
//        super.onStart();
//        Log.i(TAG, "onStart 22222222222222");
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        Log.i(TAG, "onResume 33333333333333");
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        Log.i(TAG, "onPause 444444444444444");
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        Log.i(TAG, "onStop 555555555555555");
//    }
//
//    @Override
//    protected void onRestart() {
//        super.onRestart();
//        Log.i(TAG, "onRestart 66666666666666666");
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        Log.i(TAG, "onDestroy 77777777777777777");
//    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my, menu);
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
