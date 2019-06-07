package com.yuqin.lejiao.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.yuqin.lejiao.R;
import com.yuqin.lejiao.db.LeJiaoDB;
import com.yuqin.lejiao.model.User_Info;
import com.yuqin.lejiao.servive.UserRegister;
import com.yuqin.lejiao.util.Utils;

public class RegisterActivity extends AppCompatActivity {

    private EditText et_name,et_password;
    private Button bt_register,bt_return;

    private ProgressDialog progressDialog;
    private LeJiaoDB leJiaoDB;
    private User_Info user_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();

        //注册按钮监听事件
        bt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userRegister();
            }
        });

        //取消按钮监听事件
        bt_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    //初始化控件
    public void initView(){
        et_name=findViewById(R.id.et_re_name);
        et_password=findViewById(R.id.et_re_password);
        bt_register=findViewById(R.id.register);
        bt_return=findViewById(R.id.bt_Cancle);
    }

    //点击注册按钮响应事件
    public void userRegister(){
        String name=et_name.getText().toString();
        String password=et_password.getText().toString();
        user_info=new User_Info();
        user_info.setUsername(name);
        user_info.setPassword(password);
        leJiaoDB=LeJiaoDB.getInstance(this);
        if(leJiaoDB.selectUserByName2(user_info)){
            Utils.showToast(RegisterActivity.this,"注册失败，用户名已经被使用");
            et_name.setText("");
        }else{
            UserRegister userRegister=new UserRegister();
            userRegister.UserRegister(RegisterActivity.this,user_info);
            Utils.showToast(RegisterActivity.this,"注册成功");
            Intent intent=new Intent();
            intent.putExtra("data_return",user_info.getUsername());
            setResult(RESULT_OK,intent);
            finish();
        }
    }
}
