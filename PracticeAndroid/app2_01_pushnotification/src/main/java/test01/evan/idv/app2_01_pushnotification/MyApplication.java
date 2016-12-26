package test01.evan.idv.app2_01_pushnotification;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseInstallation;

/**
 * Created by study on 2016/1/13.
 */

// 可讓App關閉還能接收到推播信息
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // www.parse.com >> Setting  >> Keys ("Application ID" , "Client Key")
        Parse.initialize(this, "Gb0LcCDZsH2Y4tKgTccn1lFyqRox74npORcPDosv", "tyYxpsmfAEPgKfUE2L1UJutYLi5BmIJUL1dy9wEl");
        ParseInstallation.getCurrentInstallation().saveInBackground();
    }
}
