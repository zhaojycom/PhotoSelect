package com.zhaojy.selectlibrary.util;

import android.app.Activity;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;

/**
 * 状态栏工具类
 *
 * @author: zhaojy
 * @data:On 2018/5/17.
 */

public class StatusBarUtil {

    /**
     * 设置状态栏颜色
     *
     * @param activity activity对象
     * @param color    需要设置的颜色
     */
    public static void setStatusBarColor(Activity activity, int color) {
        Window window = activity.getWindow();
        //设置状态栏颜色
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //取消设置透明状态栏,使 ContentView 内容不再覆盖状态栏
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(color);
        }
    }

    /**
     * 设置状态栏透明
     *
     * @param activity activity对象
     */
    public static void setStatusBarTransparent(Activity activity) {
        Window window = activity.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

}
