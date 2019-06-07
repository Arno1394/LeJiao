package com.yuqin.lejiao.servive;

import android.content.Context;

import com.yuqin.lejiao.db.LeJiaoDB;
import com.yuqin.lejiao.model.User_Info;

import java.security.MessageDigest;

public class UserLogin {
    public boolean userLogin(Context context, User_Info user_info){
        LeJiaoDB leJiaoDB=LeJiaoDB.getInstance(context);
        //根据用户名得到数据库实体
        User_Info u1=leJiaoDB.selectUserByName(user_info.getUsername());
        //将数据库存储的salt和yoghurt输入的密码拼接
        String s=u1.getSalt()+user_info.getPassword();
        //将拼接的字符串进行MD5加密，与数据局库存储的密码进行比较
        if(MD5Endode(s).equals(u1.getPassword())){
            return true;
        }else{
            return false;
        }
    }

    // 得到MD5加密结果
    public String MD5Endode(String plainText) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0) {
                    i += 256;
                }
                if (i < 16) {
                    buf.append("0");
                }
                buf.append(Integer.toHexString(i));
            }
            return buf.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
