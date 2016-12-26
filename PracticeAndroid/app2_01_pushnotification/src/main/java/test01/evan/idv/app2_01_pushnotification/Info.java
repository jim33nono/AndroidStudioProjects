package test01.evan.idv.app2_01_pushnotification;

import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;

/**
 * Created by study on 2016/1/15.
 */
public class Info {

    public static int[] imageIds = {
            R.drawable.coca_cola,
            R.drawable.chicken,
            R.drawable.salad,
            R.drawable.big_mac,
            R.drawable.french_fries,
            R.drawable.apple_pie,
            R.drawable.milkshake,
    };

    public static byte[] bitmapToByteArray(Bitmap bmp) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }
}
