package application.evan.idv.app2_12_map2;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Context context;
    private List<Marker> markers = new ArrayList<>();
    private List<Polyline> polylines = new ArrayList<>();
    private Polyline polyline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        context = this;

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    // 畫線
    private void drawPolyLine() {

        if (markers.size() >= 2) {
            // 沒加這段會殘留之前的線段
            if (polyline != null) polyline.remove();
            PolylineOptions options = new PolylineOptions();
            for (Marker m : markers) {
                options.add(m.getPosition());
            }
            options.width(5);
            options.color(Color.MAGENTA);
            options.zIndex(1);

            polyline = mMap.addPolyline(options);
//            polylines.add(polyline);

        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        // UI 控制
        UiSettings ui = mMap.getUiSettings();
        ui.setZoomControlsEnabled(true);
        ui.setScrollGesturesEnabled(true);
        ui.setZoomGesturesEnabled(true);
        ui.setTiltGesturesEnabled(true);
        ui.setRotateGesturesEnabled(true);

        //
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(final LatLng latLng) {
                if (markers.size() >= 2) {
                    mMap.clear();
                    markers = new ArrayList<>();
                }
                Marker marker = mMap.addMarker(new MarkerOptions()
                        .position(latLng)
                        .draggable(true)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker))
                        .title("點"));
                markers.add(marker);

                // 使用Picasso 來處理即時的街景小圖
                String smallstreetview_url = "https://maps.googleapis.com/maps/api/streetview?size=600x400&location=%s,%s";
                smallstreetview_url = String.format(smallstreetview_url, latLng.latitude, latLng.longitude);

                Picasso.with(context).load(smallstreetview_url).into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        // 建立街景小圖
                        GroundOverlay overlay = mMap.addGroundOverlay(
                                new GroundOverlayOptions()
                                        .image(BitmapDescriptorFactory.fromBitmap(bitmap))
                                        .anchor(0, 1.15f)
                                        .position(latLng, 400, 250)
                        );
                        overlay.setTransparency(0.2f); // 設定透明度 0表示不明 1 表示全透明
                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {

                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                });

                drawPolyLine();
            }
        });

        // 當Marker 被移動的時候
        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {

            }

            @Override
            public void onMarkerDrag(Marker marker) {
//                polylines = new ArrayList<>();
                drawPolyLine();
            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
//                markers.add(marker);
//                polylines = new ArrayList<>();
                drawPolyLine();
            }
        });

        // 泡泡框  點擊事件
        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                // 街景服務
                String path = "google.streetview:cbll=%s,%s&mz=21";
                path = String.format(path, marker.getPosition().latitude, marker.getPosition().longitude);
                Uri uri = Uri.parse(path);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);

            }
        });

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(24.990, 121.311);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 15));
    }
}
