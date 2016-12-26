package idv.evan.app_02_fresco;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

public class MainActivity extends AppCompatActivity {

// 測試 網路圖片 Fresco 處理.gif檔
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_main);

        Uri uri = Uri.parse("http://45.media.tumblr.com/b821fa9af4ddc32b03d9a3c4d33130ae/tumblr_nmi9j5Oqs81tcuj64o1_400.gif");
        SimpleDraweeView draweeView = (SimpleDraweeView) findViewById(R.id.my_image_view);
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
//                . // other setters
        .build();

        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setAutoPlayAnimations(true)
//                . // other setters
        .build();
        draweeView.setController(controller);
    }
}
