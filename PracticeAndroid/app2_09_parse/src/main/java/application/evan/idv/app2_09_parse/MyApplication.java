package application.evan.idv.app2_09_parse;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.ParsePush;

/**
 * Created by study on 2016/1/25.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(this, "Gb0LcCDZsH2Y4tKgTccn1lFyqRox74npORcPDosv", "tyYxpsmfAEPgKfUE2L1UJutYLi5BmIJUL1dy9wEl");
        // 可設定接收群組`,配合java專案ParseClient
        // 也可以做設定讓使用者選擇群組
        ParsePush.subscribeInBackground("demo");
        ParseInstallation.getCurrentInstallation().saveInBackground();
    }
}
