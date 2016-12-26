package test01.evan.idv.app_24_camera;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

// 相機 + 外部儲存 + 分享
public class MainActivity extends AppCompatActivity {
    private Context context;
    private ImageView imageView;
    private Uri uri;
    private EditText editText;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        editText = (EditText) findViewById(R.id.editText);
        imageView = (ImageView) findViewById(R.id.imageView);

        // 判斷外部app send in 分享進來的內容
        if (getIntent() != null && getIntent().getExtras() != null) {
            String uri = getIntent().getExtras().get(Intent.EXTRA_STREAM).toString();
            imageView.setImageURI(Uri.parse(uri));
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 多媒體，捕獲圖像動作(相機拍照)
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                // 檔案路徑(系統外部儲存路徑)
                // File file = new File(Environment.getExternalStorageDirectory(), "photo.jpg");
                // 轉資源路徑URI
//                uri = Uri.fromFile(file);
//                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                startActivityForResult(intent, 200);

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

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);

        switch (id) {
            case R.id.action_settings:
                // 分享文字
                sendIntent.putExtra(Intent.EXTRA_TEXT, editText.getText().toString());
                sendIntent.setType("text/plain");
//                startActivity(sendIntent);
                // 可以自訂義信息
                break;
            case R.id.action_settings2:
                // 分享圖片
                sendIntent.putExtra(Intent.EXTRA_STREAM, getImageUri(context, bitmap));
                sendIntent.setType("image/jpeg");
                break;
//            case R.id.action_settings3:
//                // 多件分享
//                ArrayList<Uri> imageUris = new ArrayList<Uri>();
//                imageUris.add(getImageUri(context, bitmap)); // Add your image URIs here
//                imageUris.add(getImageUri(context, bitmap));
//                Intent shareIntent = new Intent();
//                sendIntent.setAction(Intent.ACTION_SEND_MULTIPLE);
//                sendIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageUris);
//                sendIntent.setType("image/*");
//                startActivity(Intent.createChooser(shareIntent, "Share images to.."));
//                break;
        }
        startActivity(Intent.createChooser(sendIntent, "你要分享到哪的App"));

//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    // Bitmap 轉成 格式
    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(
                inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            bitmap = data.getExtras().getParcelable("data");
//            imageView.setImageURI(uri);
            imageView.setImageBitmap(bitmap);
            Toast.makeText(context, "Size: " + ((double) bitmap.getByteCount() / 1024), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
        }

    }
}
