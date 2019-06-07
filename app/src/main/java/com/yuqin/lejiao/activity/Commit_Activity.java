package com.yuqin.lejiao.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.yuqin.lejiao.R;
import com.yuqin.lejiao.model.Msg;
import com.yuqin.lejiao.model.User_Friend;
import com.yuqin.lejiao.servive.MsgAdapter;

import java.util.ArrayList;
import java.util.List;

public class Commit_Activity extends AppCompatActivity {

    private ListView msgListView;
    private EditText inputText;
    private Button send;
    private MsgAdapter adapter;
    private List<Msg> msgList=new ArrayList<Msg>();

    private User_Friend user_friend;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commit_);

        init();
        adapter=new MsgAdapter(Commit_Activity.this,R.layout.msg_item,msgList);
        inputText=findViewById(R.id.input_text);
        send=findViewById(R.id.send);
        msgListView=findViewById(R.id.msg_list_view);
        msgListView.setAdapter(adapter);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content=inputText.getText().toString();
                if(!"".equals(content)){
                    Msg msg=new Msg(content,Msg.TYPE_SENT);
                    msgList.add(msg);
                    //新消息，刷新ListView显示
                    adapter.notifyDataSetChanged();
                   //将ListView定位到最后一行
                    msgListView.setSelection(msgList.size());
                    //清空输入框中的内容
                    inputText.setText("");
                }
            }
        });


        findViewById(R.id.bt_return).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    public void init(){
        adapter=new MsgAdapter(Commit_Activity.this,R.layout.msg_item,msgList);
        inputText=findViewById(R.id.input_text);
        send=findViewById(R.id.send);
        msgListView=findViewById(R.id.msg_list_view);
        msgListView.setAdapter(adapter);
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        user_friend=new User_Friend();
        user_friend.setUser_name(bundle.getString("username").toString());
        user_friend.setFriendname(bundle.getString("friendname").toString());
        Msg msg1=new Msg("Hello,!"+user_friend.getUser_name(),Msg.TYPE_RECEIVED);
        msgList.add(msg1);
        Msg msg2=new Msg("who are you?",Msg.TYPE_SENT);
        msgList.add(msg2);
        Msg msg3=new Msg("My name is "+user_friend.getFriendname()+",Nice to meet you!",Msg.TYPE_RECEIVED);
        msgList.add(msg3);
    }
}
