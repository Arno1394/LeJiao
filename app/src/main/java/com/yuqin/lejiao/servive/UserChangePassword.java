package com.yuqin.lejiao.servive;

import android.content.Context;

import com.yuqin.lejiao.db.LeJiaoDB;
import com.yuqin.lejiao.model.User_Info;
import com.yuqin.lejiao.util.Utils;

import java.security.MessageDigest;
import java.util.Random;

public class UserChangePassword {

    public  boolean UserChangePassword(Context context, User_Info user_info,String newpsd){
        //判断旧密码是否正确
        LeJiaoDB leJiaoDB=LeJiaoDB.getInstance(context);
        User_Info u1=leJiaoDB.selectUserByName(user_info.getUsername());
        //根据新密码和从数据库去的salt生成新的密码
        String s=u1.getSalt()+user_info.getPassword();
        if(MD5Endode(s).equals(u1.getPassword())){
            s=u1.getSalt()+newpsd;
            u1.setPassword(MD5Endode(s));
            leJiaoDB.UpdateUserInfo(u1);
            return true;
        }else{
            return false;
        }
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
