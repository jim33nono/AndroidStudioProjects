package com.example.yesee.dialogprcatice;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Context context;
    private Button btn;
    private TextView tv_wAccount;
    private TextView tv_wPassword;
     Dialog loginDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        findViews();
        btn.setOnClickListener(MainActivity.this);

    }

    public void findViews(){
        btn = (Button) findViewById(R.id.btn);
        tv_wAccount = (TextView)findViewById(R.id.tv_wAccount);
        tv_wPassword = (TextView)findViewById(R.id.tv_wPassword);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn:
                lauchLogin();
                // launch login page!!
        }
    }

    public void lauchLogin(){
        // Create Object of Dialog class
        loginDialog = new Dialog(this);
// Set GUI of login screen

        //loginDialog.setContentView(R.layout.row);
        // 類似像一開始onOreate的方式去set view 不太好

        //用LayoutInflater 澎漲一個row layout 丟到view
        View view = LayoutInflater.from(context).inflate(R.layout.row, null);

        //動態構建的布局
        ViewGroup.LayoutParams lp = new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        // LayoutParams
//        lp.width = 500; 可以設定dialog畫面寬度

//        setContentView（） 会导致先前添加的被移除， 即替换性的；
//        而 addContentView() 不会移除先前添加的UI组件，即是累积性的
        loginDialog.addContentView(view,lp);

        loginDialog.setTitle("Welcome Login");

// Init button of login GUI
        Button btnLogin = (Button) loginDialog.findViewById(R.id.btnLogin);
        Button btnCancel = (Button) loginDialog.findViewById(R.id.btnCancel);
        final EditText et_account = (EditText)loginDialog.findViewById(R.id.et_account);
        final EditText et_password = (EditText)loginDialog.findViewById(R.id.et_password);

// Attached listener for login GUI button
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginDialog.dismiss();
//                String account = "aa";
//                String password = "123";
//
//                if( account.equals(et_account.getText().toString()) &&  password.equals(et_password.getText().toString()) )
//                {
//                    // Validate Your login credential here than display message
//                    Toast.makeText(MainActivity.this,
//                            "Login Sucessfull", Toast.LENGTH_LONG).show();
//
//                    // Redirect to dashboard / home screen.
//                    loginDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
//                        @Override
//                        public void onDismiss(DialogInterface dialogInterface) {
////                            tv_wAccount.setText(et_account.getText().toString());
////                            tv_wPassword.setText(et_password.getText().toString());
//
//                        }
//                    });
//                    loginDialog.cancel();
//                    loginDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
//                        @Override
//                        public void onCancel(DialogInterface dialogInterface) {
//
//                        }
//                    });

//                }
//                else
//                {
//                    Toast.makeText(MainActivity.this,
//                            "Please enter Username and Password", Toast.LENGTH_LONG).show();
//
//                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                loginDialog.cancel();
                loginDialog.hide();
            }
        });


        loginDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            String account = "aa";
            String password = "123";

            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                if (account.equals(et_account.getText().toString()) &&  password.equals(et_password.getText().toString())){
                    loginDialog.cancel();
                    Toast.makeText(MainActivity.this,
                            "Login Sucessfull", Toast.LENGTH_LONG).show();

                            tv_wAccount.setText(et_account.getText().toString());
                            tv_wPassword.setText(et_password.getText().toString());

                }else{
                    loginDialog.show();
                    Toast.makeText(MainActivity.this,
                            "Please enter Username and Password", Toast.LENGTH_LONG).show();
                }
            }
        });


// Make dialog box visible.
        loginDialog.show();
        loginDialog.setCancelable(false);
    }
}