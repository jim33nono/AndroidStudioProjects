package test01.evan.idv.app_30_animal;

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

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private Context context;
    private TextView tv_animal_colour, tv_animal_kind, tv_cDate;
    private ImageView iv_album_file;
    private String url = "http://data.coa.gov.tw/Service/OpenData/AnimalOpenData.aspx";
    private boolean click = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        tv_animal_colour = (TextView) findViewById(R.id.tv_animal_colour);
        tv_animal_kind = (TextView) findViewById(R.id.tv_animal_kind);
        tv_cDate = (TextView) findViewById(R.id.tv_cDate);
        iv_album_file = (ImageView) findViewById(R.id.iv_album_file);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!click) {
                    click = true;
                    new RunAnimal().start();
                } else {
                    click = false;
                    tv_animal_colour.setText("");
                    tv_animal_kind.setText("");
                    tv_cDate.setText("");
                    iv_album_file.setImageResource(R.mipmap.ic_launcher);
                }
            }
        });
    }


    class RunAnimal extends Thread {
        private String json;
        OkHttpClient client = new OkHttpClient();

        String run(String url) throws IOException {
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            Response response = client.newCall(request).execute();
            return response.body().string();
        }

        RunAnimal() {

        }

        Runnable r = new Runnable() {
            @Override
            public void run() {
                JSONArray jsonArray = null;
                try {
                    jsonArray = new JSONArray(json);
                    String animal_colour = jsonArray.getJSONObject(0).getString("animal_colour"); // color
                    String animal_kind = jsonArray.getJSONObject(0).getString("animal_kind"); // kind
                    String cDate = jsonArray.getJSONObject(0).getString("cDate"); // DATE
                    String album_file = jsonArray.getJSONObject(0).getString("album_file"); // file
                    tv_animal_colour.setText(animal_colour);
                    tv_animal_kind.setText(animal_kind);
                    tv_cDate.setText(cDate);
                    Picasso.with(context).load(album_file).into(iv_album_file);

                } catch (JSONException je) {
                    je.printStackTrace();
                }
            }
        };

        @Override
        public void run() {
            try {
                json = run(url);
            } catch (IOException ie) {
                ie.printStackTrace();
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
