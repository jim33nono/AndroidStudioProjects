package test01.evan.idv.app_32_jsoup;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

// JSOUP
public class MainActivity extends AppCompatActivity {
    private Context context;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        textView= (TextView) findViewById(R.id.textView);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new RunJSOUP().start();
            }
        });
    }

    class RunJSOUP extends Thread {
        Document doc;

        RunJSOUP() {
        }

        Runnable r = new Runnable() {
            @Override
            public void run() {
//                Toast.makeText(context, doc.toString(), Toast.LENGTH_SHORT).show();

                String data = doc.select("td[nowrap]").text().trim();
                String[] dataArray = data.split(" ");
                textView.setText(dataArray[3]);

            }
        };

        @Override
        public void run() {
            try {
                doc = Jsoup.connect("https://tw.stock.yahoo.com/q/q?s=2330").get();
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
