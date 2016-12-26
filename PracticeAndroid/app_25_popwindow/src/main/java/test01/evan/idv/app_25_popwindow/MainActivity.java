package test01.evan.idv.app_25_popwindow;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private Context context;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        textView = (TextView) findViewById(R.id.textView);
    }

    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_activity:
                Intent intent = new Intent(context, PopActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_dialog:  // Dialog 彈窗對話視窗
                final Dialog dialog = new Dialog(context);
                dialog.setTitle("請輸入驗證碼:");
                final EditText editText = new EditText(context);
                editText.setSingleLine(true); // 只能輸入一行 禁止Enter換行

                // 使用鍵盤監聽，判斷使用者按下key == Enter就將dialog.dismiss();
                editText.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        if (event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                            dialog.dismiss();
                        }
                        return false;
                    }
                });
                dialog.setContentView(editText);
                dialog.show();

                // 當對話視窗元件 dismiss 離開有一個監聽事件可以實作
                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        textView.setText(editText.getText().toString());
                    }
                });
                break;
            case R.id.btn_alertDialog: // AlertDialog 使用 builder
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setIcon(android.R.drawable.ic_dialog_info); // 設定icon圖標
                builder.setTitle("Title"); // 標題
                builder.setMessage("Message"); // 內容
                // 最右邊 通常使用確定
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(context, "Is OK Button", Toast.LENGTH_SHORT).show();
                    }
                });
                // 最左邊 通常使用清除
                builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(context, "Is Cancel Button", Toast.LENGTH_SHORT).show();
                    }
                });
                // 中間 通常使用取消
                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(context, "Is NO Button", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
                break;
            case R.id.btn_popWindow: // 自訂義彈窗
                final PopupWindow popupWindow = new PopupWindow(context); // 程式碼先增view
                Button button = new Button(context); // 程式碼先增view
                button.setText("OK");
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss(); // 方法內的類別要適用區域變數，需要在popupWindow區域變數加上final
                    }
                });
                popupWindow.setFocusable(true); // 可被焦點的視窗，與主畫面分離為單獨視窗(可返回鍵)
                popupWindow.setContentView(button);
                popupWindow.setWidth(300);
                popupWindow.setHeight(100);
                popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0); // 設定popupWindow 顯示位置 與x,y偏移
                break;
            case R.id.btn_dialog_Inflater: // 自訂義 dialog

                Dialog dialog2 = new Dialog(context);
                dialog2.setTitle("幸運數字");

                // 程式碼產生 layout
                final EditText et_inflater_dialog = new EditText(context);
                et_inflater_dialog.setHint("");
                et_inflater_dialog.setWidth(300);
                Button btn_inflater_dialog = new Button(context);
                btn_inflater_dialog.setText("重新整理");
                LinearLayout layout = new LinearLayout(context);
                layout.addView(et_inflater_dialog);
                layout.addView(btn_inflater_dialog);

                btn_inflater_dialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int n = new Random().nextInt(101);
                        et_inflater_dialog.setText(String.valueOf(n));
                    }
                });
                dialog2.setContentView(layout);
                dialog2.show();


//                LayoutInflater inflater = LayoutInflater.from(context);
//                View dialogView = inflater.inflate(R.layout.dialog, null);
//                final EditText et_inflater_dialog = (EditText) dialogView.findViewById(R.id.et_inflater_dialog);
//                Button btn_inflater_dialog = (Button) dialogView.findViewById(R.id.btn_inflater_dialog);
//                btn_inflater_dialog.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        int n = new Random().nextInt(101);
//                        et_inflater_dialog.setText(String.valueOf(n));
//                    }
//                });
//                dialog2.setContentView(dialogView);
//                dialog2.show();
                break;


        }

    }
}
