package com.zhaojy.selectlibrary.util;

import android.content.Context;
import android.os.Build;
import android.util.TypedValue;
import android.view.View;

import java.util.List;

/**
 * 水波纹效果工具
 *
 * @author: zhaojy
 * @data:On 2018/5/19.
 */

public class RippleUtil {
    /**
     * 无边界水波纹效果
     *
     * @param context 上下文
     * @param list    需要设置水波纹效果的view集合
     */
    public static void rippleBorderless(Context context, List<View> list) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            TypedValue tv = new TypedValue();
            //从这里可以明显看到是从theme中提取属性值的！
            context.getTheme().resolveAttribute(android.R.attr.selectableItemBackgroundBorderless, tv, true);
            int resId = tv.resourceId;

            for (int i = 0; i < list.size(); i++) {
                View view = list.get(i);
                view.setBackgroundResource(resId);
            }
        }
    }

    /**
     * 无边界水波纹效果
     *
     * @param context 上下文
     * @param view    需要设置水波纹效果的view
     */
    public static void rippleBorderless(Context context, View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            TypedValue tv = new TypedValue();
            //从这里可以明显看到是从theme中提取属性值的！
            context.getTheme().resolveAttribute(android.R.attr.selectableItemBackgroundBorderless, tv, true);
            int resId = tv.resourceId;

            view.setBackgroundResource(resId);
        }
    }

    /**
     * 有边界水波纹效果
     *
     * @param context 上下文
     * @param list    需要设置水波纹效果的view集合
     */
    public static void rippleEffect(Context context, List<View> list) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            TypedValue tv = new TypedValue();
            //从这里可以明显看到是从theme中提取属性值的！
            context.getTheme().resolveAttribute(android.R.attr.selectableItemBackground,
                    tv, true);
            int resId = tv.resourceId;

            for (int i = 0; i < list.size(); i++) {
                View view = list.get(i);
                view.setBackgroundResource(resId);
            }

        }
    }

    /**
     * 有边界水波纹效果
     *
     * @param context 上下文
     * @param view    需要设置水波纹效果的view
     */
    public static void rippleEffect(Context context, View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            TypedValue tv = new TypedValue();
            //从这里可以明显看到是从theme中提取属性值的！
            context.getTheme().resolveAttribute(android.R.attr.selectableItemBackground,
                    tv, true);
            int resId = tv.resourceId;

            view.setBackgroundResource(resId);
        }
    }

}
