package com.yuqin.lejiao.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yuqin.lejiao.R;
import com.yuqin.lejiao.model.User_Info;

public class UserSelfActivity extends AppCompatActivity {

    private TextView tv_name;
    private Button bt_return,bt_changepsd;
    private User_Info user_info;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_self);

        initView();

        //点击更改密码事件
        bt_changepsd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(UserSelfActivity.this, UserChangePasswordActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("name",user_info.getUsername());
                bundle.putString("password",user_info.getPassword());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        //点击返回按钮事件
        bt_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //点击退出登录按钮
        findViewById(R.id.bt_tui).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i2=new Intent(UserSelfActivity.this,MainActivity.class);
                startActivity(i2);
            }
        });

    }

    //初始化控件
    public  void initView(){
        tv_name=findViewById(R.id.tv_name);
        bt_return=findViewById(R.id.bt_return);
        bt_changepsd=findViewById(R.id.bt_changepsd);
        Bundle bundle=new Bundle();
        Intent i1=getIntent();
        bundle=i1.getExtras();
        user_info=new User_Info();
        user_info.setUsername(bundle.getString("name").toString());
        user_info.setPassword(bundle.getString("password").toString());
        tv_name.setText(user_info.getUsername());
    }
}
