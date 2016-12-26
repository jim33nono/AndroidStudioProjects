package test01.evan.idv.app_36_progressbar;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private Context context;
    private ProgressBar progressBar;
    private ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        imageView = (ImageView) findViewById(R.id.imageView);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setMax(100);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new RunProgressBar().start();
            }
        });

    }

    class RunProgressBar extends Thread {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Runnable r = new Runnable() {
            @Override
            public void run() {
                int progress = progressBar.getProgress() + 1;
                progressBar.setProgress(progress);
            }
        };

        Runnable r2 = new Runnable() {
            @Override
            public void run() {
                Bitmap bitMap = BitmapFactory.decodeByteArray(baos.toByteArray(), 0, baos.toByteArray().length);
                imageView.setImageBitmap(bitMap);
                progressBar.setProgress(0);
            }
        };

        @Override
        public void run() {
            try {
                URL url = new URL("http://i.imgur.com/DvpvklR.png");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                int size = conn.getContentLength();
                progressBar.setMax(size);
                InputStream is = conn.getInputStream();
                byte[] buffer = new byte[1];

                while (is.read(buffer) != -1) {
                    baos.write(buffer);
                    runOnUiThread(r);
                }
                runOnUiThread(r2);
            } catch (Exception e) {
                e.printStackTrace();
            }
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
