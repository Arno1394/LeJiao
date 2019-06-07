package com.yuqin.lejiao.activity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.yuqin.lejiao.R;
import com.yuqin.lejiao.db.LeJiaoDB;
import com.yuqin.lejiao.model.User_Info;
import com.yuqin.lejiao.servive.UserLogin;
import com.yuqin.lejiao.util.Utils;


public class MainActivity extends AppCompatActivity {

    private EditText et_name,et_password;
    private Button bt_login,bt_register;

    private LeJiaoDB leJiaoDB;
    SQLiteDatabase dbRead;
    private User_Info user_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();


        //点击登录
        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLogin();
            }
        });

        //点击新用户注册事件
        bt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,RegisterActivity.class);
                startActivityForResult(intent,1);
            }
        });
    }

    //初始化控件
    public void initView(){
        et_name=findViewById(R.id.et_name);
        et_password=findViewById(R.id.et_password);
        bt_login=findViewById(R.id.bt_login);
        bt_register=findViewById(R.id.bt_register);
    }

    //处理注册界面返回的信息
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case 1:
                if(requestCode==RESULT_OK){
                    String name=data.getStringExtra("data_return");
                    et_name.setText(name);
                }
                break;
            default:
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    //用户点击登录按钮对应事件
    public void userLogin(){
        String username=et_name.getText().toString();
        String password=et_password.getText().toString();

        user_info=new User_Info();
        user_info.setUsername(username);
        user_info.setPassword(password);

        if(TextUtils.isEmpty(username)){
            Utils.showToast(MainActivity.this,"用户名不能为空");
            return ;
        }
        if(TextUtils.isEmpty(password)){
            Utils.showToast(MainActivity.this,"密码不能为空");
            return ;
        }
        UserLogin userLogin=new UserLogin();
        if(userLogin.userLogin(MainActivity.this,user_info)){
            Bundle bundle=new Bundle();
            bundle.putString("name",username);
            bundle.putString("password",password);
            Intent intent2=new Intent(MainActivity.this,IndexActivity.class);
            intent2.putExtras(bundle);
            startActivity(intent2);
        }else{
            Utils.showToast(MainActivity.this,"登录失败，用户名或者密码错误");
        }

    }
}
