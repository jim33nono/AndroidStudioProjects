package application.evan.idv.app2_11_map;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private Context context;
    private GoogleMap mMap;
    private String addr_tmp;
    private TextToSpeech tts; // 語音
    private List<Marker> markers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        context = this;

        // 建立語音系統
        tts = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                // 查看狀況
                Toast.makeText(context, status + "", Toast.LENGTH_SHORT).show();
            }
        });
        // 設定語音語系
        tts.setLanguage(Locale.TAIWAN);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
            case R.id.button2:
                addr_tmp = ((Button) v).getText().toString();
                new RunAddress2Location(((Button) v).getText().toString()).start();
                break;
            case R.id.button3:
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                startActivityForResult(intent, 101);
                break;
            case R.id.button4:
                mMap.clear();
                markers = new ArrayList<>();
                break;
            case R.id.button5:
                float[] distances = new float[1];
                // 測兩點距離

                if (markers != null) {

                }
                Location.distanceBetween(
                        markers.get(0).getPosition().latitude,
                        markers.get(0).getPosition().longitude,
                        markers.get(1).getPosition().latitude,
                        markers.get(1).getPosition().longitude,
                        distances
                );
                Toast.makeText(context, distances[0] + " m", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    // 地址,地標轉緯經度
    class RunAddress2Location extends Thread {
        String json;
        OkHttpClient client = new OkHttpClient();
        String addr;

        RunAddress2Location(String addr) {
            this.addr = addr;
        }

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

                try {
                    JSONObject loc = new JSONObject(json).getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("location");
                    String lat = loc.getString("lat");
                    String lng = loc.getString("lng");
                    //Toast.makeText(context, lat + "," + lng, Toast.LENGTH_SHORT).show();
                    addMarker(lat, lng, addr);
                } catch (JSONException e) {

                }
            }
        };

        @Override
        public void run() {
            String url = "http://maps.googleapis.com/maps/api/geocode/json?address=%s&sensor=true&language=zh_tw";
            try {
                url = String.format(url, URLEncoder.encode(addr, "utf-8"));
                json = run(url);
                runOnUiThread(r);
            } catch (Exception e) {

            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101) {
            ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            addr_tmp = result.get(0);
            Toast.makeText(context, addr_tmp + "", Toast.LENGTH_SHORT).show();
            new RunAddress2Location(addr_tmp).start();
            // 語音 tts
            tts.speak("本班機即將飛往" + addr_tmp + ",感謝您的搭乘,祝您旅途愉快 !", TextToSpeech.QUEUE_FLUSH, null);
        }

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

//        // 設定緯經度
//        LatLng sydney = new LatLng(-34, 151);
//        LatLng trainstation = new LatLng(24.990123, 121.312178);
//        // 設定標點位置，可設定多個
//        mMap.addMarker(new MarkerOptions().position(sydney).title("雪梨"));
//        mMap.addMarker(new MarkerOptions().position(trainstation).title("桃園"));
//        // 畫面呈現位置  中心點
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(trainstation));

        // UI 控制
        UiSettings ui = mMap.getUiSettings();
        ui.setZoomControlsEnabled(true);
        ui.setScrollGesturesEnabled(true);
        ui.setZoomGesturesEnabled(true);
        ui.setTiltGesturesEnabled(true);
        ui.setRotateGesturesEnabled(true);
    }

    public void addMarker(String lat, String lng, String title) {
        LatLng latLng = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));
        Marker marker = mMap.addMarker(new MarkerOptions()
                .position(latLng)
                .title(title)
                .snippet("sub title")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                .draggable(true));

        markers.add(marker);

        // 預設
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
        // 動畫效果
//        CameraPosition cp = new CameraPosition.Builder()
//                .target(latLng)
//                .zoom(17)
//                .bearing(300)
//                .tilt(67)
//                .build();
//        CameraUpdate updata = CameraUpdateFactory.newCameraPosition(cp);
//        mMap.animateCamera(updata , 10000 , null);

        // 動畫縮減code
//        CameraUpdate update = CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder()
//          .target(latLng)
//                .zoom(17)
//                .bearing(300)
//                .tilt(67)
//                .build());

        // 移動速度
//        mMap.animateCamera(update, 3000, new GoogleMap.CancelableCallback() {
//            @Override
//            public void onFinish() {
//                Toast.makeText(context, "到達: " + addr_tmp, Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onCancel() {
//
//            }
//        });


    }

}
