package com.yuqin.lejiao.activity;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.SimpleCursorAdapter;

import com.yuqin.lejiao.R;
import com.yuqin.lejiao.db.LeJiaoDB;
import com.yuqin.lejiao.model.User_Friend;
import com.yuqin.lejiao.util.Utils;

public class FriendListActivity extends ListActivity {

    private SimpleCursorAdapter adapter;
    private String username;
    private LeJiaoDB leJiaoDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_list);

        //初始化界面
        initView();
        leJiaoDB=LeJiaoDB.getInstance(this);

        //从数据库读取好友列表
        adapter=new SimpleCursorAdapter(this,R.layout.friend_list,null,new String[]{"friendname"},new int[]{R.id.tv_friend_list});
        setListAdapter(adapter);

        refreshView();

        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Cursor c=adapter.getCursor();
                Bundle bundle=new Bundle();
                bundle.putString("username",username);
                bundle.putString("friendname",c.getString(c.getColumnIndex("friendname")));
                Intent intent=new Intent(FriendListActivity.this,Commit_Activity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        //返回主界面
        findViewById(R.id.bt_return).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    //初始化
    private void initView(){
        Intent i1=getIntent();
        Bundle bundle=i1.getExtras();
        username=bundle.getString("name").toString();
    }

    //刷新
    private void refreshView(){
        Cursor cursor=leJiaoDB.selectFriendByName(username);
        adapter.changeCursor(cursor);
    }
}
