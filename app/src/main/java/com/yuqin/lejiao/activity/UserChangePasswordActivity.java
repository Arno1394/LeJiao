package com.yuqin.lejiao.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.yuqin.lejiao.R;
import com.yuqin.lejiao.model.User_Info;
import com.yuqin.lejiao.servive.UserChangePassword;
import com.yuqin.lejiao.util.Utils;

public class UserChangePasswordActivity extends AppCompatActivity {


    private Button bt_changepassword;
    private EditText et_oldpsd,et_newpsd;
    private User_Info user_info;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_change_password);

        initView();

        //点击确认更改密码事件
        bt_changepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user_info.setPassword(et_oldpsd.getText().toString());
                UserChangePassword userChangePassword=new UserChangePassword();
                String newpsd=et_newpsd.getText().toString();
                if(userChangePassword.UserChangePassword(UserChangePasswordActivity.this,user_info,newpsd)){
                    Utils.showToast(UserChangePasswordActivity.this,"更改成功");
                    finish();
                }else{
                    Utils.showToast(UserChangePasswordActivity.this,"原密码错误");
                    et_oldpsd.setText("");
                }
            }
        });

        //点击返回按钮事件
        findViewById(R.id.bt_return).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    public void initView(){
        et_newpsd=findViewById(R.id.et_newpsd);
        et_oldpsd=findViewById(R.id.et_oldpsd);
        bt_changepassword=findViewById(R.id.bt_changepassword);
        user_info=new User_Info();
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        String name=bundle.getString("name").toString();
        user_info.setUsername(bundle.getString("name").toString());
    }
}
