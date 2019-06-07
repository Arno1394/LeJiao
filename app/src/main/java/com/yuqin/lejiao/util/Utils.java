package com.yuqin.lejiao.util;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class Utils {

    public static void showToast(Context context,String info){
        Toast.makeText(context, info, Toast.LENGTH_SHORT).show();
    }

    public static void showLog(String test){
        Log.d("yuqin>>>","让开，开始测试");
        Log.d("yuqin>>>",test);
        Log.d("yuqin>>>","OK，测试完成");
    }
}
