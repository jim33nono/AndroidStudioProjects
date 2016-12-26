package application.evan.idv.app2_10_gps;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Locale;

// GPS
// LocationListener GPS位置監聽
// 取得位置詳細資訊
// http://maps.googleapis.com/maps/api/geocode/json?latlng="Latitude","Longitude"&sensor=true&language=zh_tw
// Json  {}getJSONObject ， []getJSONArray
public class MainActivity extends AppCompatActivity implements LocationListener {
    private Context context;
    private TextView tv_Mode, tv_Address_result, tv_Location_result;
    private Location location;
    private EditText et_Address;
//    private LocationManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        tv_Mode = (TextView) findViewById(R.id.tv_Mode);
        tv_Address_result = (TextView) findViewById(R.id.tv_Address_result);
        tv_Location_result = (TextView) findViewById(R.id.tv_Location_result);
        et_Address = (EditText) findViewById(R.id.et_Address);

        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // for Android 6.0 使用前再次提醒權限 6.0 以前可以不用
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 2);
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        // GPS
        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        // AGPS 室內 NETWORK
        manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode,
//                                           String permissions[], int[] grantResults) {
//        if (requestCode == 2 && grantResults.length > 0) {
//            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            }
//            manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
//            manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
//        }
//    }

    // GPS位置更改會呼叫此方法
    @Override
    public void onLocationChanged(Location location) {
        this.location = location;
        tv_Mode.setText("緯度:" + location.getLatitude() +
                "\t經度:" + location.getLongitude() +
                "\n標高:" + location.getAltitude() +
                "\n精確度:" + location.getAccuracy() +
//                "\n速度:" + location.getSpeed() +
//                "\n時間:" + location.getTime() +
                "\n方位:" + location.getBearing());

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    //
    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    public void onClick(View v) {
//        new RunLocation2Address().start();
        switch (v.getId()) {
            case R.id.btn_L2A:
                new RunLocation2Address().start();
                break;
            case R.id.btn_A2L:
                String src = et_Address.getText().toString();
                new RunLocation2Address(src).start();
                break;
            case R.id.btn_MIC:
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                startActivityForResult(intent, 101);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101) {
            ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            et_Address.setText(result.get(0));
        }
    }

    class RunLocation2Address extends Thread {
        String json;
        String return_Address;
        // L2A
        RunLocation2Address() {
        }
        // A2L
        RunLocation2Address(String return_Address) {
            this.return_Address = return_Address;
        }

        OkHttpClient client = new OkHttpClient();
        String run(String url) throws IOException {
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            Response response = client.newCall(request).execute();
            return response.body().string();
        }

        Runnable r = new Runnable() {
            @Override
            public void run() {
                // Toast.makeText(context, json, Toast.LENGTH_SHORT).show();

                try {
                    // 大海撈針 法
                    if (return_Address == null) {
                        json = new JSONObject(json).getJSONArray("results").getJSONObject(0).getString("formatted_address");
//                        Toast.makeText(context, json, Toast.LENGTH_SHORT).show();
                        tv_Address_result.setText(json);
                        et_Address.setText(json);
                    } else {
                        JSONObject loc = new JSONObject(json).getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("location");
                        String lat = loc.getString("lat");
                        String lng = loc.getString("lng");
                        tv_Location_result.setText(lat + " : " + lng);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };

        @Override
        public void run() {
            String url;
            try {
                if (return_Address == null) {
                    url = "http://maps.googleapis.com/maps/api/geocode/json?latlng=%s,%s&sensor=true&language=zh_tw";
                    url = String.format(url, location.getLatitude(), location.getLongitude());
                } else {
                    url = "http://maps.googleapis.com/maps/api/geocode/json?address=%s&sensor=true&language=zh_tw";
                    url = String.format(url, URLEncoder.encode(return_Address, "utf-8"));
                }
                json = run(url);
                runOnUiThread(r);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }


}
