package test01.evan.idv.app_12_bmi;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.text.DecimalFormat;

// BMI計算範例 + ImageView換圖 + 橫向畫面切換 onAttachedToWindow()
public class MainActivity extends AppCompatActivity {
    private EditText et_height, et_weight;
    private Context context;
    private ImageView iv_result;
    private Button btn_commit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        iv_result = (ImageView) findViewById(R.id.iv_result);
        et_height = (EditText) findViewById(R.id.et_height);
        et_weight = (EditText) findViewById(R.id.et_weight);
        btn_commit = (Button) findViewById(R.id.btn_commit);
        iv_result.setImageResource(R.drawable.bmi);
        btn_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_height.getText().toString().equals("") || et_weight.getText().toString().equals("")) {
                    Toast.makeText(context, "請輸入身高體重", Toast.LENGTH_SHORT).show();
                    return;
                }
                calcBMI();
            }
        });

    }

    @Override
    public void onAttachedToWindow() { // 每當裝置翻轉時會呼叫 onAttachedToWindow
        super.onAttachedToWindow();
        if (et_height.getText().toString().equals("") || et_weight.getText().toString().equals("")) {
            return;
        }
        calcBMI();
    }

    private void calcBMI() {

        double height = Double.parseDouble(et_height.getText().toString());
        double weight = Double.parseDouble(et_weight.getText().toString());
        DecimalFormat df = new DecimalFormat(".##"); // 十進位格式化
        double bmi = Double.parseDouble(df.format(weight / Math.pow(height / 100, 2))); // 次方計算方法Math.pow()

        if (bmi < 15) {
            iv_result.setImageResource(R.drawable.m_15);
        } else if (bmi >= 15 && bmi < 20) {
            iv_result.setImageResource(R.drawable.m_20);
        } else {
            iv_result.setImageResource(R.drawable.m_25);
        }

        Toast.makeText(context, "BMI = " + String.valueOf(bmi), Toast.LENGTH_SHORT).show(); // 將基本資型態轉為字串 String.valueOf()
    }

}
