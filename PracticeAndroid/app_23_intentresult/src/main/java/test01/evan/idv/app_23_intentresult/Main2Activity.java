package test01.evan.idv.app_23_intentresult;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {
    private Context context;
    private TextView textView;
    private int sum = 0;
    private int responseCode = 0; // 回應碼

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        textView = (TextView) findViewById(R.id.textView);

        Bundle bundle = getIntent().getExtras();

        int x = Integer.parseInt(bundle.getString("x"));
        int y = Integer.parseInt(bundle.getString("y"));
        switch (bundle.getInt("requestCode")) {
            case 101:
                responseCode = 201;
                sum = x + y;
                break;
            case 102:
                responseCode = 202;
                sum = x * y;
                break;
        }
        textView.setText(String.valueOf(sum));
    }

    public void onClick(View view) {
        Intent intent = new Intent();
        intent.putExtra("sum", sum);
        setResult(responseCode, intent);
        finish();
    }
}
