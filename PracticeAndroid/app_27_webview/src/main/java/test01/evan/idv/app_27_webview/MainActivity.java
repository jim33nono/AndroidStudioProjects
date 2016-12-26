package test01.evan.idv.app_27_webview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class MainActivity extends AppCompatActivity {
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView = (WebView) findViewById(R.id.webView);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.requestFocus();
//        webView.loadUrl("http://192.168.1.11:8084/MyRandom/");

        webView.loadUrl("file:///android_asset/index.html"); // 離線版
    }
}
