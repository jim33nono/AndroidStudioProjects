package test01.evan.idv.app_19;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

// TCQ 考題 現實20分鐘 (電費計算器)
// 對應題目  Lab 電費計算機.ppt
public class MainActivity extends AppCompatActivity {
    private List<Integer> list = new ArrayList<>();
    private Button btn_calculate;
    private EditText et_ampere, et_hours_day, et_degree;
    private Context context;
    private ArrayAdapter<Integer> adapter;
    private Spinner sp_volt;
    private double w, v, a, h, d, reslut;
    private TextView tv_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        list.add(110);
        list.add(220);

        tv_result = (TextView) findViewById(R.id.tv_result);
        et_ampere = (EditText) findViewById(R.id.et_ampere);
        et_hours_day = (EditText) findViewById(R.id.et_hours_day);
        et_degree = (EditText) findViewById(R.id.et_degree);
        btn_calculate = (Button) findViewById(R.id.btn_calculate);
        sp_volt = (Spinner) findViewById(R.id.sp_volt);
        sp_volt.setBackgroundColor(Color.WHITE);
        adapter = new ArrayAdapter<Integer>(context, android.R.layout.simple_dropdown_item_1line, list);
        sp_volt.setAdapter(adapter);
        btn_calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculate();

            }
        });
    }

    public void calculate() {
        v = Integer.parseInt(sp_volt.getSelectedItem().toString());
        a = Double.parseDouble(et_ampere.getText().toString());
        h = Double.parseDouble(et_hours_day.getText().toString());
        d = Double.parseDouble(et_degree.getText().toString());
        w = v * a;
        reslut = ((w / 1000) * (h * 30) * d);
        tv_result.setText("$" + String.valueOf((int) reslut));

    }
}
