package com.yuqin.lejiao.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.yuqin.lejiao.R;
import com.yuqin.lejiao.model.User_Info;
import com.yuqin.lejiao.util.Utils;

public class IndexActivity extends AppCompatActivity {

    private ImageButton bt_self,bt_addFriend,bt_friend,bt_setting;
    private User_Info user_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        //初始化控件
        initView();
        //点击个人中心事件
        bt_self.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1=new Intent(IndexActivity.this,UserSelfActivity.class);
                Bundle bundle1=new Bundle();
                bundle1.putString("name",user_info.getUsername());
                bundle1.putString("password",user_info.getPassword());
                i1.putExtras(bundle1);
                startActivity(i1);
            }
        });
        //点击设置事件
        bt_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i2=new Intent(IndexActivity.this,SettingActivity.class);
                startActivity(i2);
            }
        });
        //点击联系人事件
        bt_friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i3=new Intent(IndexActivity.this,FriendListActivity.class);
                Bundle bundle3=new Bundle();
                bundle3.putString("name",user_info.getUsername());
                bundle3.putString("password",user_info.getPassword());
                i3.putExtras(bundle3);
                startActivity(i3);
            }
        });
        //点击添加联系人事件
        bt_addFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i4=new Intent(IndexActivity.this,FriendRequestActivity.class);
                Bundle bundle4=new Bundle();
                bundle4.putString("name",user_info.getUsername());
                bundle4.putString("password",user_info.getPassword());
                i4.putExtras(bundle4);
                startActivity(i4);
            }
        });

        //点击搜索图标事件
        findViewById(R.id.bt_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i5=new Intent(IndexActivity.this, WebviewActivity.class);
                startActivity(i5);
            }
        });
    }

    public void initView(){
       bt_self=findViewById(R.id.bt_gerenzhongxin);
       bt_addFriend=findViewById(R.id.bt_tianjialianxiren);
       bt_friend=findViewById(R.id.bt_lianxiren);
       bt_setting=findViewById(R.id.bt_shezhi);
       //得到从登陆界面传过来的用户数据
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        user_info=new User_Info();
        user_info.setUsername(bundle.getString("name").toString());
        user_info.setPassword(bundle.getString("password").toString());
    }
}
