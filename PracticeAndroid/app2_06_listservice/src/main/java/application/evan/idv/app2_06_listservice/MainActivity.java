package application.evan.idv.app2_06_listservice;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;
// 取得目前運行的服務
public class MainActivity extends AppCompatActivity {
    private TextView textView;
    private Context context;
    private ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textView);
        context = this;
//        listView = (ListView) findViewById(R.id.listView);


        ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> list = manager.getRunningServices(100);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            private boolean colorflag;

            @Override
            public void onClick(View view) {
                ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
                List<ActivityManager.RunningServiceInfo> list = manager.getRunningServices(100);
                StringBuilder sb = new StringBuilder();
                // html 顯示
                for (ActivityManager.RunningServiceInfo info : list) {
//                    colorflag = !colorflag;
                    String name = info.service.getClassName();
                    String html = "<font color=%s>" + name + "</font> <br>";
//                    html = String.format(html, colorflag ? "black" : "red");
                    html = String.format(html, (colorflag = !colorflag) ? "black" : "red");
//                    sb.append(name + "/n");
                    sb.append(html);
                }
//                textView.setText(sb.toString());
                textView.setText(Html.fromHtml(sb.toString()));
            }
        });
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
