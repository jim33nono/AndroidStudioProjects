package com.example.yesee.jsontogsonbyurl;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.yesee.jsontogsonbyurl.vo.Field;
import com.example.yesee.jsontogsonbyurl.vo.Final;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "TestMainActivity";
    private Context context;
    private Button btn;
    private ListView lv;
    private Final finalValue;
    private List<Field> listField;
    private Ada adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        context = this;
        btn.setOnClickListener(MainActivity.this);

//      第一種寫法，有好幾層去抓array
//        listField = new Final().getResult().getFields();

//      第二種寫法，有好幾層去抓array
//        Result result = new Result();
        listField = new ArrayList<Field>();

        adapter = new Ada(context, listField);
        lv.setAdapter(adapter);

    }

    private void findViews() {
        btn = (Button) findViewById(R.id.btn);
        lv = (ListView) findViewById(R.id.lv);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn:
                new CallUrl("http://data.ntpc.gov.tw/api/v1/rest/datastore/382000000A-000434-001").start();
                break;
        }
    }

    class Ada extends BaseAdapter {
        private Context context;
        private List<Field> listField;

        Ada(Context context, List<Field> listField) {
            this.context = context;
            this.listField = listField;
        }

        @Override
        public int getCount() {
            return listField.size();
        }

        @Override
        public Object getItem(int i) {
            return listField.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            LayoutInflater inflater = getLayoutInflater().from(context);
            view = inflater.inflate(R.layout.row, null);

            final Field field =  listField.get(i);


            TextView tv_id = (TextView) view.findViewById(R.id.tv_id);
            TextView tv_name = (TextView) view.findViewById(R.id.tv_name);

            tv_id.setText(field.getType());
            tv_name.setText(field.getId());



            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                        Field sub = new Field();
                        sub.setOpen(false);
                        sub.setId("");
                        sub.setType("");
                        listField.add(i+1,sub);
                        notifyDataSetChanged();

                }
            });

            return view;
        }
    }

    public class CallUrl extends Thread {
        //OkHttpClient GET A URL 標準okhttpclient get URL 寫法
        OkHttpClient client = new OkHttpClient();
        private String url;
        private Gson gson = new Gson();

        public CallUrl(String url) {
            this.url = url;
        }

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

                adapter.notifyDataSetChanged();
            }
        };

        @Override
        public void run() {
            try {
                listField.clear();
                //gson use method fromJson to object
                String json = run(url);
//                listField = gson.fromJson(json, Final.class).getResult().getFields();
                Final  finalValue = gson.fromJson(json , Final.class);
                listField.addAll(finalValue.getResult().getFields());
//                List<Field>  tmp_list = finalValue.getResult().getFields();
//                listField.addAll(tmp_list);
                Log.i(TAG, listField.size()+"~~~~");
                runOnUiThread(rUi); // run Runnable rUi's run() to change Ui
            } catch (IOException e) {
                e.printStackTrace();
            }

        }


    }
}
