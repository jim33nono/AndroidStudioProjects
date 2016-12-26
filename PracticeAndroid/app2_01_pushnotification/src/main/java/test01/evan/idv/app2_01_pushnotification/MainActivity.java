package test01.evan.idv.app2_01_pushnotification;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

// 使用 www.parse.com 推播 Notification
// SQLite 實作 SQLiteOpenHelper
// 實作 SQL CRUD 新增 查詢 修改 刪除

public class MainActivity extends AppCompatActivity {
    private Context context;
    private SQLiteDatabase db;
    private ListView listView;
    private SimpleCursorAdapter adapter;
    private ImageView iv_Image; // input.xml 的 ImageViewId
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        listView = (ListView) findViewById(R.id.listView);
        // 設定 SQL dbHelper.class
        DBHelper dbHelper = new DBHelper(context, "food.db", null, 1);
        db = dbHelper.getWritableDatabase(); //會以寫入模式開啟資料庫

        // 如果SQL 裡沒有_id 可以用  idName as _id 來援救
//        Cursor cursor = db.rawQuery("SELECT _id, itemTitle, itemPrice, itemImageId FROM food", null);
        Cursor cursor = db.rawQuery("SELECT _id, itemTitle, itemPrice, itemImage FROM food", null);
        // Toast.makeText(context, cursor.getCount() + "筆", Toast.LENGTH_SHORT).show();

        // 建立適配器
        adapter = new SimpleCursorAdapter(
                context,
                R.layout.row,
                cursor,
//                new String[]{"itemTitle", "itemPrice", "itemImageId"},
                new String[]{"itemTitle", "itemPrice", "itemImage"},
                new int[]{R.id.row_tv_Title, R.id.row_tv_Price, R.id.row_iv_Image},
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER
        ) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                LayoutInflater inflater = LayoutInflater.from(context);
                View v = inflater.inflate(R.layout.row, null);

                TextView row_tv_Title = (TextView) v.findViewById(R.id.row_tv_Title);
                TextView row_tv_Price = (TextView) v.findViewById(R.id.row_tv_Price);
                ImageView row_iv_image = (ImageView) v.findViewById(R.id.row_iv_Image);

                Cursor cur = (Cursor) getItem(position);
                row_tv_Title.setText(cur.getString(1));
                row_tv_Price.setText(cur.getString(2));

                // bytes --> bitmap
                byte[] bytes = cur.getBlob(3);
                // 判斷SQL裡的資料，cur.getBlob(3)為空值，就new 一個為{0}的 byte Array 
                if (bytes == null) {
                    bytes = new byte[]{0};
                }
                bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                row_iv_image.setImageBitmap(bitmap);
                return v;
            }
        };
        listView.setAdapter(adapter);

        // 設定listViewLong監聽器
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cur = (Cursor) parent.getItemAtPosition(position);
                Toast.makeText(context, "刪除成功", Toast.LENGTH_SHORT).show();
                int _id = cur.getInt(0);
                Object[] bindArgs = {_id};
                db.execSQL("DELETE FROM food WHERE _id=?", bindArgs);

                // 更新資料 Method
                reflesh();
                // return true ，不讓其它點擊監聽器干擾
                return true;
            }
        });

        // UpData 更新資料
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cur = (Cursor) parent.getItemAtPosition(position);
                final int _id = cur.getInt(0);
                AlertDialog.Builder upBuilder = new AlertDialog.Builder(context);
                upBuilder.setTitle("修改");

                // 膨脹器
                LayoutInflater inflater = LayoutInflater.from(context);
                View v = inflater.inflate(R.layout.input, null);
                final EditText ed_Title = (EditText) v.findViewById(R.id.ed_Title);
                final EditText ed_Price = (EditText) v.findViewById(R.id.ed_Price);
                iv_Image = (ImageView) v.findViewById(R.id.iv_Image);

                ed_Title.setText(cur.getString(1));
                ed_Price.setText(cur.getString(2));
//                iv_Image.setImageResource(cur.getInt(3));
//                iv_Image.setTag(cur.getInt(3)); // 先把原來的圖片暫存

                // bytes --> bitmap
                byte[] bytes = cur.getBlob(3);
                // 判斷SQL裡的資料，cur.getBlob(3)為空值，就new 一個為{0}的 byte Array 
                if (bytes == null) { 
                    bytes = new byte[]{0};
                }

                bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                iv_Image.setImageBitmap(bitmap);
                iv_Image.setTag(cur.getBlob(3)); // 如果修改沒改變iv_Image，需先暫存，注意格式不是bitmap

                // 圖片點擊事件
                iv_Image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 亂數選擇圖片
//                        int n = new Random().nextInt(Info.imageIds.length);
//                        int imageId = Info.imageIds[n]; // View的暫存口袋
//                        v.setTag(imageId);
//                        ((ImageView) v).setImageResource(imageId);

                        // 照相
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent, 100);

                    }
                });
                upBuilder.setView(v);

                // 確認更新
                upBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Object[] bindArgs = {ed_Title.getText().toString(), ed_Price.getText().toString(), iv_Image.getTag(), _id};
//                        db.execSQL("UPDATE food SET itemTitle=?, itemPrice=?, itemImageId=? WHERE _id=?", bindArgs);
                        db.execSQL("UPDATE food SET itemTitle=?, itemPrice=?, itemImage=? WHERE _id=?", bindArgs);
                        // 更新資料 Method
                        reflesh();
                    }
                });

                upBuilder.setNegativeButton("Cancel", null);
                upBuilder.show();
            }
        });

    }

    // 更新資料 Method
    private void reflesh() {
        // 新增完要重查資料庫得到指標Cursor
//        Cursor cursor = db.rawQuery("SELECT _id, itemTitle, itemPrice, itemImageId FROM food", null);
        Cursor cursor = db.rawQuery("SELECT _id, itemTitle, itemPrice, itemImage FROM food", null);
        // 通知 adapter 改變Cursor
        adapter.changeCursor(cursor);
        // 通知adapter更改
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        menu.add(0, 1, Menu.NONE, "建立資料庫/表");
        menu.add(0, 2, Menu.NONE, "新增");
//        menu.add(0, 3, Menu.NONE, "查詢");
        menu.add(0, 4, Menu.NONE, "刪除全部");

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            Bitmap bitmap = data.getExtras().getParcelable("data");
            iv_Image.setImageBitmap(bitmap);
            iv_Image.setTag(Info.bitmapToByteArray(bitmap));
        } else {
            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
//            // 建立數據庫資料             
//            case 1:
//                建立資料庫 取得寫入資料
//                DBHelper dbHelper = new DBHelper(context, "food.db", null, 1);
//                db = dbHelper.getWritableDatabase();
//                break;
            // InPut 新增資料
            case 2:
//                // "CREATE TABLE food (_id INTEGER PRIMARY KEY, itemTitle TEXT, itemPrice NUMERIC, itemImageId NUMERIC, itemImage BLOB)"
//                // 建立  Object[] 放入對應SQL語法
//                Object[] bindArgs = {"Latte", (new Random().nextInt(50) + 50), 0};
//                // execSQL (String "SQL語法" , Object[] bindArgs)
//                db.execSQL("INSERT INTO food('itemTitle', 'itemPrice', 'itemImageId') VALUES (?, ?, ?)", bindArgs);

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("新增");

                // 膨脹器 編撰 layout
                LayoutInflater inflater = LayoutInflater.from(context);
                View v = inflater.inflate(R.layout.input, null);
                final EditText ed_Title = (EditText) v.findViewById(R.id.ed_Title);
                final EditText ed_Price = (EditText) v.findViewById(R.id.ed_Price);
                iv_Image = (ImageView) v.findViewById(R.id.iv_Image);

                iv_Image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent, 100);

                    }
                });
                builder.setView(v);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Object[] bindArgs = {ed_Title.getText().toString(), ed_Price.getText().toString(), iv_Image.getTag()};
//                        db.execSQL("INSERT INTO food('itemTitle', 'itemPrice', 'itemImageId') VALUES (?, ?, ?)", bindArgs);
                        db.execSQL("INSERT INTO food('itemTitle', 'itemPrice', 'itemImage') VALUES (?, ?, ?)", bindArgs);

                        // 更新資料 Method
                        reflesh();
                    }
                });

                builder.setNegativeButton("Cancel", null);
                builder.show();
                break;
//            // 查詢資料            
//            case 3:
//                // 如果SQL 裡沒有_id 可以用  idname as _id 來緩就
//                Cursor cursor = db.rawQuery("SELECT _id, itemTitle, itemPrice FROM food", null);
//                // Toast.makeText(context, cursor.getCount() + "筆", Toast.LENGTH_SHORT).show();
//
//                adapter = new SimpleCursorAdapter(
//                        context,
//                        android.R.layout.simple_expandable_list_item_2,
//                        cursor,
//                        new String[]{"itemTitle", "itemPrice"},
//                        new int[]{android.R.id.text1, android.R.id.text2},
//                        CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER
//                );
//                listView.setAdapter(adapter);
//
//                break;
            // 刪除全部資料
            case 4:
                AlertDialog.Builder confirm = new AlertDialog.Builder(context);
                confirm.setTitle("刪除資料");
                confirm.setMessage("確定全部刪除?");

                confirm.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.execSQL("DELETE FROM food");
                    }
                });

                confirm.setNegativeButton("Cancel", null);
                confirm.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
