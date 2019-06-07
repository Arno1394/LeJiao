package com.yuqin.lejiao.servive;

import android.content.Context;

import com.yuqin.lejiao.db.LeJiaoDB;
import com.yuqin.lejiao.model.User_Info;
import java.util.*;
import java.security.MessageDigest;

public class UserRegister {
    public void UserRegister(Context context, User_Info user_info){
        //随机生成salt
        String rs=getRandomString();
        String s=rs+user_info.getPassword();
        //将salt和用户输入的密码拼接之后进行MD5加密，存入数据库
        user_info.setPassword(MD5Endode(s));
        user_info.setSalt(rs);
        LeJiaoDB jiaoDB=LeJiaoDB.getInstance(context);
        jiaoDB.saveUserInfo(user_info);
    }

    // length用户要求产生字符串的长度
    public  String getRandomString() {
        int length = 20;
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
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
