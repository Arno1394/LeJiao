package com.yuqin.lejiao.activity;

import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SimpleCursorAdapter;

import com.yuqin.lejiao.R;
import com.yuqin.lejiao.db.LeJiaoDB;

public class testActivity extends ListActivity {


    private SimpleCursorAdapter adapter;
    private LeJiaoDB leJiaoDB;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        username=bundle.getString("name");

        leJiaoDB=LeJiaoDB.getInstance(this);
    }
}
