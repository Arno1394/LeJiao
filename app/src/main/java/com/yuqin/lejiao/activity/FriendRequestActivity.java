package com.yuqin.lejiao.activity;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.yuqin.lejiao.R;
import com.yuqin.lejiao.db.LeJiaoDB;
import com.yuqin.lejiao.model.FriendRequest;
import com.yuqin.lejiao.model.User_Friend;
import com.yuqin.lejiao.model.User_Info;
import com.yuqin.lejiao.util.Utils;

import java.util.List;

public class FriendRequestActivity extends ListActivity {


    //适配器
    private SimpleCursorAdapter Adapter;
    private EditText meditText;
    private Button mbutton;
    private String username,user_name;

    private LeJiaoDB leJiaoDB;
    private User_Info user_info;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_request);

        initView();

        //将请求添加好友的请求从数据库查询出来
        leJiaoDB=LeJiaoDB.getInstance(this);

        Adapter=new SimpleCursorAdapter(this,R.layout.list_friendrequest_item,null,new String[]{"source_user_name"},new int[]{R.id.tv_fr_list});
        setListAdapter(Adapter);

        refreshListView();


        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Cursor c=Adapter.getCursor();

                final int itemid=c.getInt(c.getColumnIndex("_id"));
                new AlertDialog.Builder(FriendRequestActivity.this).setTitle("提醒").setMessage("同意好友请求?").setNegativeButton("拒绝", new DialogInterface.OnClickListener() {
                    //点击拒绝好友请求事件
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //删除好友请求信息
                        leJiaoDB.deleteRequest(itemid);
                        refreshListView();
                    }
                }).setPositiveButton("同意", new DialogInterface.OnClickListener() {
                    //点击同意好友请求事件
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FriendRequest fr=leJiaoDB.selectFriendRequestById(itemid);
                        //互相添加好友
                        User_Friend uf=new User_Friend();
                        User_Friend fu=new User_Friend();
                        fu.setUser_name(fr.getSourcename());
                        fu.setFriendname(fr.getDestination());
                        uf.setUser_name(fr.getDestination());
                        uf.setFriendname(fr.getSourcename());
                        leJiaoDB.saveFriend(uf);
                        leJiaoDB.saveFriend(fu);
                        leJiaoDB.deleteRequest(itemid);
                        refreshListView();
                        Utils.showToast(FriendRequestActivity.this,"好友添加成功");
                    }
                }).show();
            }
        });

        //搜索好友添加好友请求
        mbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user_name=meditText.getText().toString();
                user_info=new User_Info();
                user_info.setUsername(user_name);
                //搜索的用户存在
                if(leJiaoDB.selectUserByName2(user_info)){
                    User_Info u1=leJiaoDB.selectUserByName(user_name);
                    new AlertDialog.Builder(FriendRequestActivity.this).setTitle("提醒").setMessage("您确定要向"+user_name+"发送好友请求吗？").setNegativeButton("取消",null).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            FriendRequest friendRequest=new FriendRequest();
                            friendRequest.setSourcename(username);
                            friendRequest.setDestination(user_name);
                            leJiaoDB.saveFriendRequest(friendRequest);
                            Utils.showToast(FriendRequestActivity.this,"发送成功");
                        }
                    }).show();
                }else{
                    Utils.showToast(FriendRequestActivity.this,"搜索的用户不存在");
                    meditText.setText("");
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



    private void refreshListView(){
        Cursor cursor=leJiaoDB.selectRequestByName(username);
        Adapter.changeCursor(cursor);
    }

    private void initView(){
        mbutton=findViewById(R.id.bt_serchfriend);
        meditText=findViewById(R.id.et_findfirend);
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        username=bundle.getString("name");
    }
}
