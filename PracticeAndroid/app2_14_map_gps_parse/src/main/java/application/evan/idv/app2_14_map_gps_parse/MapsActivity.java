package application.evan.idv.app2_14_map_gps_parse;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.view.View;
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
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener {

    private GoogleMap mMap;
    private Context context;
    private Location location;
    private LocationManager manager;
    private Marker startMarker, endMarker, car;
    private LatLng latLng;
    private Polyline navPolyline;
    private String navMode;
    private List<LatLng> poly;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        context = this;

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // for Android 6.0 使用前再次提醒權限 6.0 以前可以不用
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 2);
            return;
        } else {
            // GPS
            manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
            // AGPS 室內 NETWORK
            manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
        }
    }

    // for android 6.0
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == 2 && grantResults.length > 0) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            }
            manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
            manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        UiSettings ui = mMap.getUiSettings();
        ui.setZoomControlsEnabled(true);
        ui.setScrollGesturesEnabled(true);
        ui.setZoomGesturesEnabled(true);
        ui.setTiltGesturesEnabled(true);
        ui.setRotateGesturesEnabled(true);

    }

    @Override
    public void onLocationChanged(Location location) {
        if (mMap != null) {
            latLng = new LatLng(location.getLatitude(), location.getLongitude());
            if (startMarker == null) {
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
            }
            startMarker = mMap.addMarker(new MarkerOptions().position(latLng).title("Marker in Sydney"));

            mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
                @Override
                public void onMarkerDragStart(Marker marker) {

                }

                @Override
                public void onMarkerDrag(Marker marker) {

                }

                @Override
                public void onMarkerDragEnd(Marker marker) {
                    new RunNavigate().start();
                }
            });
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_YMS:
                if (mMap != null) {
                    endMarker = mMap.addMarker(new MarkerOptions()
                            .draggable(true)
                            .position(new LatLng(24.982099, 121.307670))
                            .title("陽明公園")
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                    double lat = (startMarker.getPosition().latitude + endMarker.getPosition().latitude) / 2;
                    double lng = (startMarker.getPosition().longitude + endMarker.getPosition().longitude) / 2;
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), 15));
                }
                break;
            case R.id.btn_navigate:

                if (endMarker != null) {
                    navMode = "";
                    new RunNavigate().start();
                }
                break;
            case R.id.btn_walking:
                if (endMarker != null) {
//                    navMode = ""; // 預設(汽車)
//                    navMode = "&mode=driving"; // 汽車
//                    navMode = "&mode=walking"; // 步行
//                    navMode = "&mode=bicycling"; // 腳踏車
                    navMode = "&mode=transit"; // 大眾交通工具
                    new RunNavigate().start();
                }
                break;
            case R.id.btn_clear:
//                mMap.clear();
//                navPolyline = null;
//                endMarker = null;
                new RunCar().start();
                break;
        }
    }

    // 取得 導航Url 中的路線資訊 ，使用okHttp doGet方式
    class RunNavigate extends Thread {
        String json;
        String url;
        OkHttpClient client = new OkHttpClient();

        RunNavigate() {
            // 導航Url  傳入起始緯經度 + 終點緯經度
            url = "https://maps.googleapis.com/maps/api/directions/json?origin=%s,%s&destination=%s,%s&sensor=false" + navMode;
            url = String.format(url, startMarker.getPosition().latitude, startMarker.getPosition().longitude, endMarker.getPosition().latitude, endMarker.getPosition().longitude);
        }

        Runnable r = new Runnable() {
            @Override
            public void run() {
                try {
                    // 解析Json for 總路線資訊 "points"
                    String points = new JSONObject(json).getJSONArray("routes").getJSONObject(0).getJSONObject("overview_polyline").getString("points");
//                    Toast.makeText(context, url, Toast.LENGTH_SHORT).show();
                    drawNavigatePolyline(points);
                } catch (JSONException e) {
                    Toast.makeText(context, "無法導航", Toast.LENGTH_SHORT).show();
                }
            }
        };

        String run(String url) throws IOException {
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            Response response = client.newCall(request).execute();
            return response.body().string();
        }

        @Override
        public void run() {
            try {
                json = run(url);
                runOnUiThread(r);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 解碼折線點的方法
     */
    private List<LatLng> decodePoly(String encoded) {
        List<LatLng> poly = new ArrayList<LatLng>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng((((double) lat / 1E5)),
                    (((double) lng / 1E5)));
            poly.add(p);
        }

        return poly;
    }

    private void drawNavigatePolyline(String points) {
        poly = decodePoly(points);

        if (navPolyline != null) {
            navPolyline.remove();
        }
        PolylineOptions options = new PolylineOptions();
        for (LatLng latLng : poly) {
            options.add(latLng);
        }
        options.width(5);
        options.color(Color.MAGENTA);
        navPolyline = mMap.addPolyline(options);

    }

    // 小車車
    class RunCar extends Thread {
        LatLng latLng;
        boolean isArrived;
        Runnable r = new Runnable() {
            @Override
            public void run() {
                if (car != null) car.remove();

                car = mMap.addMarker(new MarkerOptions()
                        .draggable(true)
                        .position(latLng)
                        .title("")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.car)));
                if (isArrived) {
                    // Toast.makeText(context, "到了", Toast.LENGTH_SHORT).show();
                    createNotification();
                }
            }
        };

        @Override
        public void run() {
            for (LatLng latLng : poly) {
                this.latLng = latLng;
                runOnUiThread(r);
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            isArrived = true;
            runOnUiThread(r);

        }
    }


    private void createNotification() {

        String smallstreetview_url = "https://maps.googleapis.com/maps/api/streetview?size=250x250&location=%s,%s";
        smallstreetview_url = String.format(smallstreetview_url, endMarker.getPosition().latitude, endMarker.getPosition().longitude);

        Picasso.with(context).load(smallstreetview_url).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                int notificationId = 001;
                Intent viewIntent = new Intent(context, MapsActivity.class);
                PendingIntent viewPendingIntent =
                        PendingIntent.getActivity(context, 0, viewIntent, 0);

                NotificationCompat.Builder notificationBuilder =
                        new NotificationCompat.Builder(context)
                                .setLargeIcon(bitmap)
                                .setSmallIcon(R.drawable.car)
                                .setContentTitle("到了")
                                .setContentText("陽明公園")
                                .setContentIntent(viewPendingIntent);

                NotificationManagerCompat notificationManager =
                        NotificationManagerCompat.from(context);

                notificationManager.notify(notificationId, notificationBuilder.build());
                Toast.makeText(context, "OK", Toast.LENGTH_SHORT);
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT);
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        });


    }

}