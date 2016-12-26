package application.evan.idv.app2_08_bindservice.services;

import android.widget.TextView;

import java.util.Random;

/**
 * Created by study on 2016/1/22.
 */
public class BMI {

    public double calcBMI(double h, double w) {
        return w / Math.pow(h / 100, 2);
    }

    public void calcBMI(double h, double w, TextView tv) {
        tv.setText(calcBMI(h, w) + "");
    }

    public int getRandom(int x) {
        return new Random().nextInt(x);
    }
}
