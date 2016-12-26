package com.example.yesee.myfirstapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.yesee.myfirstapp.vo.User;

import java.util.ArrayList;
import java.util.List;

public class ThirdActivity extends AppCompatActivity {

    private static final String TAG = "ThirdActivity";
    private static final int  FOURTH_CODE = 112;
    private Context context;
    private ListView lv;
    private List<User> userList;
    private Ada adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        context = this;

        lv = (ListView) findViewById(R.id.lv);
        userList = new ArrayList<User>();
//        test ();

        // adapter 接集合參數 list、set、map 等種類
        adapter = new Ada(context, userList);

        lv.setAdapter(adapter);

//        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//
//            }
//        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                test();
                adapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            switch (requestCode) {
                case  FOURTH_CODE:
//                    String name = data.getStringExtra("name");
                    Bundle bundle = data.getExtras();
                    User user = bundle.getParcelable("userInfo");
                    Log.i(TAG, user.getIndex() + "");
                    userList.get(user.getIndex()).setName(user.getName());
                    adapter.notifyDataSetChanged();
                    break;
            }

        }

    }

    //      設定一個btn，等待回傳的值
//    private void btnClick(){
//        test();
//        adapter.notifyDataSetChanged();
//    }


    //
    class Ada extends BaseAdapter {
        private Context context;
        private List<User> userList;

        Ada(Context context, List<User> userList) {
            this.context = context;
            this.userList = userList;
        }

        @Override
        public int getCount() {
            return userList.size();
        }

        @Override
        public Object getItem(int i) {
            return userList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            // View
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.row, null);
            // Data
            User user = userList.get(i);

            // findViews
            TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
            TextView tv_phone = (TextView) view.findViewById(R.id.tv_phone);
            TextView tv_email = (TextView) view.findViewById(R.id.tv_email);
            TextView tv_address = (TextView) view.findViewById(R.id.tv_address);
            tv_name.setText(user.getName());
            tv_phone.setText(user.getPhone());
            tv_email.setText(user.getEmail());
            tv_address.setText(user.getAddress());

            //設計按一個view的Click功能
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.setClass(context, FourthActivity.class);
                    userList.get(i).setIndex(i);
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("userInfo" ,userList.get(i));
//                bundle.putSerializable();

                    intent.putExtras(bundle);
//                intent.putExtra("user", (Parcelable) user);
//                intent.putExtra("name","123");
                    startActivityForResult(intent , FOURTH_CODE);
//                    startActivity(intent);
                }
            });

//          finish(); go back
//          Handler 查子執行緒的功能
            return view;
        }
    }

    private void test() {
        User user1 ;
        for(int i = 0; i<10; i++){
            user1 = new User();
            user1.setName("Dennis" + i);
            user1.setPhone("0932245845" + i);
            user1.setEmail("dennis@gmail.com" + i);
            user1.setAddress("台北市中山區"+ i);
            user1.setPassword("221454" + i);
            userList.add(user1);
        }
    }


}
