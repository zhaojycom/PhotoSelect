package com.zhaojy.selectlibrary.control;

import android.content.Context;
import android.content.Intent;

import com.zhaojy.selectlibrary.view.PhotoSelectActivity;

/**
 * @author: zhaojy
 * @data:On 2018/5/22.
 */

public class Director {
    private Context context;
    private static Builder builder;

    public static Builder getBuilder() {
        return builder;
    }

    public Director(Context context, Builder temp) {
        this.context = context;
        builder = temp;
    }

    public void create() {
        Intent intent = new Intent(context, PhotoSelectActivity.class);
        context.startActivity(intent);
    }

}
