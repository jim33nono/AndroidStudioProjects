package test01.evan.idv.app2_01_pushnotification;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by study on 2016/1/13.
 */
public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 建立資料表
        db.execSQL("CREATE TABLE food (_id INTEGER PRIMARY KEY, itemTitle TEXT, itemPrice NUMERIC, itemImageId NUMERIC, itemImage BLOB)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 先刪除
        db.execSQL("DROP TABLE IF EXISTS food");
        // 在建立
        db.execSQL("CREATE TABLE food (_id INTEGER PRIMARY KEY, itemTitle TEXT, itemPrice NUMERIC, itemImageId NUMERIC, itemImage BLOB)");
    }
}
