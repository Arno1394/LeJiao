package com.yuqin.lejiao.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class LeJiaoOpenHelper extends SQLiteOpenHelper {

    //User_Info表建表语句
    private static final String CREATE_USER="create table User_Info("
            +"_id Integer primary key autoincrement,"
            +"user_name text Unique,"
            +"salt text ,"
            +"password text)";

    //User_Friend表建表语句
    private static final String CREATE_USER_FRIEND="create table User_Friend("
            +"_id Integer primary key autoincrement,"
            +"user_name text,"
            +"friendId Integer,"
            +"friendname text)";



    //Friend_Request表建表语句
    private static final String CREATE_FRIEND_REQUEST="create table Friend_Request("
            +"_id Integer primary key autoincrement,"
            +"source_user_name text,"
            +"destination_user_name text)";


    public LeJiaoOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_USER);
        sqLiteDatabase.execSQL(CREATE_USER_FRIEND);
        sqLiteDatabase.execSQL(CREATE_FRIEND_REQUEST);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
