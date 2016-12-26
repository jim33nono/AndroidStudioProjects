package idv.evan.app_02_picasso;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

// 網路圖片處裡 Picasso
public class MainActivity extends AppCompatActivity {
    private ImageView imageView;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
    }

    private void findViews() {
        context = this;
        imageView = (ImageView) findViewById(R.id.imageView);
        Picasso.with(context).load("http://i.imgur.com/DvpvklR.png").into(imageView);
    }
}
