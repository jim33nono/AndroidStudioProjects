package test01.evan.idv.app_22_chat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

// 畫面切換，須設定manifests
// intent.setClass 顯性指定 從這裡到 >> 哪裡
// 隱性 intent.setAction("唯一名稱")
// Bundle(菜籃)，包裹內容
public class MainActivity extends AppCompatActivity {
    private Context context;
    private EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        editText = (EditText) findViewById(R.id.editText);

        editText.getText().toString();


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(); // 建立 意圖
                // 顯性指定到，(這裡  >> manifests的activity的name)
                intent.setClass(context,LoginActivity.class);
                // 隱性 intent.setAction("唯一名稱")
//                intent.setAction("com.help");
                // 隱含式  相機
//                intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);

                // 建立 Bundle(菜籃)
                Bundle bundle = new Bundle();
                // 包裹內容 以key,value 放式
                bundle.putString("message", editText.getText().toString());
                // 掛載
                intent.putExtras(bundle);

                startActivity(intent); // 開始動作
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
