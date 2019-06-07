package com.yuqin.lejiao.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.yuqin.lejiao.model.FriendRequest;
import com.yuqin.lejiao.model.User_Friend;
import com.yuqin.lejiao.model.User_Info;
import com.yuqin.lejiao.util.Utils;

import java.util.ArrayList;
import java.util.List;

public class LeJiaoDB {

    //数据库名
    public static final String DB_NAME="LeJiao";
    //数据库版本
    public static final int VERSION=1;

    private  static LeJiaoDB leJiaoDB;

    private SQLiteDatabase db;
    private SQLiteDatabase dr;

    //将构造方法私有化
    private LeJiaoDB(Context context){
        LeJiaoOpenHelper dbHelper=new LeJiaoOpenHelper(context,DB_NAME,null,VERSION);
        db=dbHelper.getWritableDatabase();
        dr=dbHelper.getReadableDatabase();
    }
    //获取LeJiaoDB的实例
    public synchronized static LeJiaoDB getInstance(Context context){
        if(leJiaoDB==null){
            leJiaoDB=new LeJiaoDB(context);
        }
        return leJiaoDB;
    }

    //将User_Info实例存储到数据库
    public long saveUserInfo(User_Info user_info){
        long i=0;
        if(user_info!=null){
            ContentValues contentValues=new ContentValues();
            contentValues.put("user_name",user_info.getUsername());
            contentValues.put("password",user_info.getPassword());
            contentValues.put("salt", user_info.getSalt());
            i=db.insert("User_Info",null,contentValues);
        }
        return i;
    }
    //根据用户名从数据库查询数据
    public User_Info selectUserByName(String name){
        User_Info u1=new User_Info();
        Cursor cursor=db.query("User_Info",null,"user_name=?",new String[]{name+""},null,null,null);
        if(cursor.moveToFirst()){
            u1.setUsername(name);
           u1.setPassword(cursor.getString(cursor.getColumnIndex("password")));
           u1.setSalt(cursor.getString(cursor.getColumnIndex("salt")));
        }
        return u1;
    }
    //根据用户名从数据库查询是否存在用户
    public Boolean selectUserByName2(User_Info user_info){
        String name=user_info.getUsername();
        String password=user_info.getPassword();
        Cursor cursor=db.query("User_Info",null,"user_name=?",new String[]{name+""},null,null,null);
        if(cursor.moveToFirst()){
            return true;
        }
        return false;
    }
    //从数据库读取所有用户信息
    public List<User_Info> loadAllUser(){
        List<User_Info> list=new ArrayList<User_Info>();
        Cursor cursor=db.query("User_Info",null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do{
                User_Info user_info=new User_Info();
                user_info.setId(cursor.getInt(cursor.getColumnIndex("id")));
                user_info.setUsername(cursor.getString(cursor.getColumnIndex("user_name")));
                user_info.setPassword(cursor.getString(cursor.getColumnIndex("password")));
                list.add(user_info);
            }while (cursor.moveToNext());
        }
        if(cursor!=null){
            cursor.close();
        }
        return list;
    }
    //更新数据库用户信息
    public void UpdateUserInfo(User_Info user_info){
        ContentValues contentValues=new ContentValues();
        contentValues.put("password",user_info.getPassword());
        db.update("User_Info",contentValues,"user_name=?",new String[]{user_info.getUsername()+""});
    }

    //将User_Friend实例存储到数据库
    public long saveFriend(User_Friend user_friend){
        long i=0;
        if(user_friend!=null){
            ContentValues contentValues=new ContentValues();
            contentValues.put("user_name",user_friend.getUser_name());
            contentValues.put("friendname",user_friend.getFriendname());
            i=db.insert("User_Friend",null,contentValues);
        }
        return i;
    }
    //从数据库读取用户所有好友信息
    public List<User_Friend> loadAllFriend(){
        List<User_Friend> list=new ArrayList<User_Friend>();
        Cursor cursor=db.query("User_Friend",null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do {
                User_Friend user_friend=new User_Friend();
                user_friend.setId(cursor.getInt(cursor.getColumnIndex("id")));
                user_friend.setUser_name(cursor.getString(cursor.getColumnIndex("user_name")));
                user_friend.setFriendId(cursor.getInt(cursor.getColumnIndex("friendId")));
                user_friend.setFriendname(cursor.getString(cursor.getColumnIndex("friendname")));
                list.add(user_friend);
            }while (cursor.moveToNext());
        }
        if(cursor!=null){
            cursor.close();
        }
        return list;
    }
    //根据用户名查找所有好友
    public Cursor selectFriendByName(String username){
        Cursor c=db.query("User_Friend",null,"user_name=?",new String[]{username+""},null,null,null);
        return c;
    }

    //将Friend_Request实例存储到数据库
    public long saveFriendRequest(FriendRequest friendRequest){
        long i=0;
        if(friendRequest!=null){
            ContentValues contentValues=new ContentValues();
            contentValues.put("source_user_name",friendRequest.getSourcename());
            contentValues.put("destination_user_name",friendRequest.getDestination());
            i=db.insert("Friend_Request",null,contentValues);
        }
        return i;
    }
    //根据目标好友查找好友请求信息
    public Cursor selectRequestByName(String username){
        Cursor cursor=db.query("Friend_Request",null,"destination_user_name=?",new String[]{username+""},null,null,null);
        if(cursor.moveToFirst()){
            return cursor;
        }
        if(cursor!=null){
            cursor.close();
        }
        return cursor;
    }
    //根据ID删除好友请求
    public long deleteRequest(int id){
        long i=db.delete("Friend_Request","_id=?",new String[]{id+""});
        return i;
    }
    //根据ID查找好友请求信息
    public FriendRequest selectFriendRequestById(int id){
        FriendRequest friendRequest=new FriendRequest();
        Cursor c=db.query("Friend_Request",null,"_id=?",new String[]{id+""},null,null,null);
        if(c.moveToFirst()){
            friendRequest.setDestination(c.getString(c.getColumnIndex("destination_user_name")));
            friendRequest.setSourcename(c.getString(c.getColumnIndex("source_user_name")));
        }
        if(c!=null){
            c.close();
        }
        return friendRequest;
    }

}
