package test01.evan.idv.app_23_intentresult;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Context context;
    private EditText editText, editText2;
    private int requestCode = 0; // 請求碼

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        editText = (EditText) findViewById(R.id.editText);
        editText2 = (EditText) findViewById(R.id.editText2);
    }

    public void onClick(View view) {
        Intent intent = new Intent();
        intent.setClass(context, Main2Activity.class);
        Bundle bundle = new Bundle();
        // 注入
        bundle.putString("x", editText.getText().toString());
        bundle.putString("y", editText2.getText().toString());

        switch (view.getId()) {
            case R.id.button:
                requestCode = 101;
                break;
            case R.id.button2:
                requestCode = 102;
                break;
        }

        bundle.putInt("requestCode", requestCode);
        intent.putExtras(bundle);
        startActivityForResult(intent, requestCode);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 101 && resultCode == 201) {
//            editText.setText(data.getIntExtra("sum", 0) + "");
            Toast.makeText(context, data.getIntExtra("sum", 0) + "", Toast.LENGTH_SHORT).show();
        } else if (requestCode == 102 && resultCode == 202) {
            Toast.makeText(context, data.getIntExtra("sum", 0) + "", Toast.LENGTH_SHORT).show();
        }

    }
}
