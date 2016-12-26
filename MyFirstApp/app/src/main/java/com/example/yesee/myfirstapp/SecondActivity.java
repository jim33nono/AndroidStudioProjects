package com.example.yesee.myfirstapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;



public class SecondActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "SecondActivity";
    private Context context;
    private Button btn3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        context = this;
//
//        Bundle bundle = getIntent().getExtras();
//        int g = bundle.getInt("num");

//        Log.i(TAG, getIntent().getStringExtra("name"));

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        findViews();
//
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        Log.i(TAG, "onCreate 11111111111111");

        btn3.setOnClickListener(SecondActivity.this);

    }

    private void findViews (){
        btn3 = (Button)findViewById(R.id.btn3);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn3:
                Intent intent = new Intent(context, ThirdActivity.class);
                startActivity(intent);
                break;


//          default:
//                Intent intent = new Intent(context, LoginActivity.class);
//                startActivity(intent);
//                break;
        }


    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart 22222222222222");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume 33333333333333");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause 444444444444444");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop 555555555555555");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart 66666666666666666");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy 77777777777777777");
    }

}
