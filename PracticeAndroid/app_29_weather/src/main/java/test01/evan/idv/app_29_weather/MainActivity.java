package test01.evan.idv.app_29_weather;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private Context context;
    private ImageView iv_icon;
    private TextView tv_temp, tv_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;
        iv_icon = (ImageView) findViewById(R.id.iv_icon);
        tv_temp = (TextView) findViewById(R.id.tv_temp);
        tv_date = (TextView) findViewById(R.id.tv_date);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new RunWeather().start();
            }
        });

    }

    class RunWeather extends Thread {
        private String json;
        OkHttpClient client = new OkHttpClient();

        String run(String url) throws IOException {
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            Response response = client.newCall(request).execute();
            return response.body().string();
        }

        RunWeather() {
        }

        Runnable r = new Runnable() {
            @Override
            public void run() {
//                Toast.makeText(context, json, Toast.LENGTH_SHORT).show();
                JSONObject jsonObject = null;
                DecimalFormat df = new DecimalFormat(".00"); // ##最多顯示到兩位  ，00代表不足補滿兩位
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd EEE HH:mm:ss");
                try {
                    jsonObject = new JSONObject(json);

                    double temp = jsonObject.getJSONObject("main").getDouble("temp") - 273.15;
                    String icon = jsonObject.getJSONArray("weather").getJSONObject(0).getString("icon");
                    double pressure = jsonObject.getJSONObject("main").getDouble("pressure");
                    double humidity = jsonObject.getJSONObject("main").getDouble("humidity");
                    double temp_min = jsonObject.getJSONObject("main").getDouble("temp_min");
                    double temp_max = jsonObject.getJSONObject("main").getDouble("temp_max");
                    // 溫度
                    tv_temp.setText(df.format(temp) + "℃");
                    // 時間
                    tv_date.setText(sdf.format(new Date()));
                    // 圖型icon
                    Picasso.with(context).load("http://openweathermap.org/img/w/" + icon + ".png").into(iv_icon);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        @Override
        public void run() {

            try {
                json = run("http://api.openweathermap.org/data/2.5/weather?q=Taipei&appid=2de143494c0b295cca9337e1e96b00e0");
            } catch (IOException e) {
                e.printStackTrace();
            }

            runOnUiThread(r);
        }
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
