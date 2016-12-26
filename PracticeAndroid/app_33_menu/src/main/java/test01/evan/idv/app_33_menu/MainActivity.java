package test01.evan.idv.app_33_menu;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Context context;
    private TextView tv_food, tv_drink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        tv_food = (TextView) findViewById(R.id.tv_food);
        tv_drink = (TextView) findViewById(R.id.tv_drink);
        registerForContextMenu(tv_food);
        registerForContextMenu(tv_drink);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        switch (v.getId()) {
            case R.id.tv_food:
                menu.add(0, 100, Menu.NONE, "麥香魚");
                menu.add(0, 200, Menu.NONE, "薯條");
                break;
            case R.id.tv_drink:
                menu.add(1, 300, Menu.NONE, "冰淇淋");
                menu.add(1, 400, Menu.NONE, "可樂");
                break;
        }

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getGroupId()) {
            case 0:
                switch (item.getItemId()) {
                    case 100:
                        tv_food.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, R.drawable.fish);
                        break;
                    case 200:
                        tv_food.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, R.drawable.french_fries_big);
                        break;
                }
                tv_food.setText(item.getTitle());
                break;
            case 1:
                switch (item.getItemId()) {
                    case 300:
                        tv_drink.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, R.drawable.ice_cream);
                        break;
                    case 400:
                        tv_drink.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, R.drawable.coca_cola);
                        break;
                }
                tv_drink.setText(item.getTitle());
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // MenuItem.SHOW_AS_ACTION_IF_ROOM  如果有空間就顯示
        // MenuItem.SHOW_AS_ACTION_ALWAYS   總是顯示
        // MenuItem.SHOW_AS_ACTION_NEVER    不顯示
        // MenuItem.SHOW_AS_ACTION_WITH_TEXT 只顯示文字
        // MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW
        // 動態產生Menu
        menu.add(0, 100, Menu.NONE, "新增").setIcon(android.R.drawable.ic_menu_add).setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW); // 設定menu的icon圖標
        menu.add(0, 200, Menu.NONE, "修改").setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM); // 有足夠空間就顯示，避免溢排
        menu.add(0, 300, Menu.NONE, "刪除").setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

        // submenu
        SubMenu submenu = menu.addSubMenu(0, 400, Menu.NONE, "查詢");
        submenu.add(0, 401, Menu.NONE, "價格");
        submenu.add(0, 401, Menu.NONE, "種類");


        SubMenu submenus = submenu.addSubMenu("顏色");
        submenus.add("紅色");
        submenus.add("黑色");
        submenus.add("白色");
        submenus.add("藍色");
        submenus.add("橘色");


        menu.add(0, 999, Menu.NONE, "離開").setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS); //總是顯示
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Toast.makeText(MainActivity.this, item.getGroupId() + "," + item.getItemId() + "," + item.getTitle(), Toast.LENGTH_SHORT).show();
        switch (item.getItemId()) {
            case 100:
                break;
            case 200:
                break;
            case 300:
                break;
            case 400:
                break;
            case 999:
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
