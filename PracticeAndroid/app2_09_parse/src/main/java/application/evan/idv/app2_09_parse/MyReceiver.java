package application.evan.idv.app2_09_parse;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.google.gson.Gson;

/**
 * Created by study on 2016/1/25.
 */
public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
//        Toast.makeText(context,"Hello",Toast.LENGTH_SHORT).show();
        if (intent.getAction().equals("com.parse.push.intent.RECEIVE")) {
//            String json = intent.getExtras().getString("com.parse.Data");
//            Toast.makeText(context,json,Toast.LENGTH_SHORT).show();
            Gson gson = new Gson();
//            MyMessage msg = gson.fromJson(json, MyMessage.class);
            MyMessage msg = gson.fromJson(intent.getExtras().getString("com.parse.Data"), MyMessage.class);
            Toast.makeText(context, msg.alert, Toast.LENGTH_LONG).show();
        }
    }
}
