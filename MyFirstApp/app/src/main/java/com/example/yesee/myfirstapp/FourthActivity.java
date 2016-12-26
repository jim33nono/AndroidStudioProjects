package com.example.yesee.myfirstapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.yesee.myfirstapp.vo.DeviceInfo;
import com.example.yesee.myfirstapp.vo.DeviceListResponse;
import com.example.yesee.myfirstapp.vo.User;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FourthActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "FourthActivity";
    private Context context;
    private Button btn4, btn5;
    private User user;
    private ListView lv;
//    private List<Aqx> aqxList;
    private List<DeviceInfo> deviceInfoList;
    private Ada adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fourth);

        context = this;
        findViews();

        Bundle bundle = getIntent().getExtras();
//        String st = bundle.getString("name");
        user = bundle.getParcelable("userInfo");
        user.setName("It's changed");

        btn4.setOnClickListener(FourthActivity.this);
        btn5.setOnClickListener(FourthActivity.this);
//
//        aqxList = new ArrayList<Aqx>();
//        adapter = new Ada(context, aqxList);

//        deviceInfoList = new ArrayList<DeviceInfo>();
//        adapter = new Ada(context, deviceInfoList);
//
//        lv.setAdapter(adapter);



       //  new CallUrl("http://opendata2.epa.gov.tw/AQX.json").start();

    }

    private void findViews (){
        btn4 = (Button) findViewById(R.id.btn4);
        btn5 = (Button) findViewById(R.id.btn5);
        lv = (ListView) findViewById(R.id.lv);
    }

    @Override
    public void onClick(View view) {
//        Intent intent = new Intent(this, MainActivity.class);

        switch (view.getId()) {
            case R.id.btn4:
                Intent intent = new Intent();
                intent.setClass(this, ThirdActivity.class);
                intent.putExtra("userInfo", (Parcelable) user);

                // onActivityResult setResult function
                setResult(RESULT_OK, intent);
                finish();
                break;
            case R.id.btn5:
                test();
                break;

        }
    }

    public class CallUrl extends Thread{
        private String url;
        private Gson gson = new Gson();

        public CallUrl(String url){
            this.url = url;
        }
        //OkHttpClient GET A URL 標準okhttpclient get URL 寫法
        OkHttpClient client = new OkHttpClient();
        String run(String url) throws IOException {
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            Response response = client.newCall(request).execute();
            return response.body().string(); //json string type
        }


        Runnable rUi = new Runnable() {
            @Override
            public void run() {
                // update Ui
//                Log.i(TAG,  String.valueOf(aqxList.size()));
                Log.i(TAG, deviceInfoList.size()+"");

                adapter.notifyDataSetChanged();


            }
        };

        @Override
        public void run() {
            try {
                //gson use method fromJson to object
//                String json = run(url);
                String json = "{\"err\":{\"code\":\"0\",\"msg\":\"Operation Successfully Completed\"},\"status\":\"ok\",\"total_count\":2,\"limit\":10,\"total_pages\":1,\"current_page\":1,\"log_count\":2,\"gw_dev_id\":\"IIC3LIGHT‐0000‐‐‐‐‐‐\",\"group_id\":5,\"device_info_list\":[{\"dev_listid\":23,\"dev_id\":\"IIL5DM8NS‐0001010101\",\"dev_name\":\"DemoRoom 燈控制器\",\"dev_category\":\"LIGHT\",\"dev_ext_type\":\"L5\",\"manu_code\":\"II\",\"enable_status\":\"0\"},{\"dev_listid\":28,\"dev_id\":\"IIL5DM8NS‐0001010102\",\"dev_name\":\"DemoRoom 燈控制器2\",\"dev_category\":\"LIGHT\",\"dev_ext_type\":\"L5\",\"manu_code\":\"II\",\"enable_status\":\"1\"}]}";
                Log.i(TAG, json);
//                AqxRsp aqxRsp = gson.fromJson(json, DeviceListResponse.class); //gson
                deviceInfoList = gson.fromJson(json, DeviceListResponse.class).getDevice_info_list();
                Log.i(TAG, deviceInfoList.size()+"");

                // DeviceListResponse deviceListResponse = gson.fromJson(json, DeviceListResponse.class); //gson
//                deviceInfoList = deviceListResponse.getDevice_info_list();
//                List<Aqx> tmp_list = new ArrayList<Aqx>();
//                tmp_list = aqxRsp.getAqxList();
//
//                for (Aqx a: tmp_list) {
//                    if (tmp_list.indexOf(a) == 10){
//                        break;
//                    }
//                    aqxList.add(a);
//                }
                runOnUiThread(rUi); // run Runnable rUi's run() to change Ui
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //設定 adapter
    class Ada extends BaseAdapter {
        private Context context;
//        private List<Aqx> aqxList;
        private List<DeviceInfo> deviceInfoList;

        Ada(Context context, List<DeviceInfo> deviceInfoList) {
            this.context = context;
            this.deviceInfoList = deviceInfoList;
        }

        @Override
        public int getCount() {
            return deviceInfoList.size();
        }

        @Override
        public Object getItem(int i) {
            return deviceInfoList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            // View
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.aqx_row, null);
            // Data
//            Aqx aqx = aqxList.get(i);
            DeviceInfo deviceInfo = deviceInfoList.get(i);
            Log.i(TAG, new Gson().toJson(deviceInfo));

            // findViews
            TextView tv_co = (TextView) view.findViewById(R.id.tv_co);
            TextView tv_county = (TextView) view.findViewById(R.id.tv_county);
            TextView tv_fpmi = (TextView) view.findViewById(R.id.tv_fpmi);
            TextView tv_publishtime = (TextView) view.findViewById(R.id.tv_publishtime);

            // setText
            tv_co.setText(deviceInfo.getDev_id());
            tv_county.setText(deviceInfo.getDev_name());
            tv_fpmi.setText(deviceInfo.getEnable_status());
            tv_publishtime.setText(deviceInfo.getDev_category());

            return view;
        }
    }


    private  void test (){
//
//        deviceInfoList = new ArrayList<DeviceInfo>();

        String json = "{\"err\":{\"code\":\"0\",\"msg\":\"Operation Successfully Completed\"},\"status\":\"ok\",\"total_count\":2,\"limit\":10,\"total_pages\":1,\"current_page\":1,\"log_count\":2,\"gw_dev_id\":\"IIC3LIGHT‐0000‐‐‐‐‐‐\",\"group_id\":5,\"device_info_list\":[{\"dev_listid\":23,\"dev_id\":\"IIL5DM8NS‐0001010101\",\"dev_name\":\"DemoRoom 燈控制器\",\"dev_category\":\"LIGHT\",\"dev_ext_type\":\"L5\",\"manu_code\":\"II\",\"enable_status\":\"0\"},{\"dev_listid\":28,\"dev_id\":\"IIL5DM8NS‐0001010102\",\"dev_name\":\"DemoRoom 燈控制器2\",\"dev_category\":\"LIGHT\",\"dev_ext_type\":\"L5\",\"manu_code\":\"II\",\"enable_status\":\"1\"}]}";
        List<DeviceInfo>  deviceInfoList = new Gson().fromJson(json, DeviceListResponse.class).getDevice_info_list();
        adapter = new Ada(context , deviceInfoList);

        lv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
