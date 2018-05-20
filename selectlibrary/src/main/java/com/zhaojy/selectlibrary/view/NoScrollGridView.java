package com.zhaojy.selectlibrary.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.GridView;

/**
 * 无滚动GridView
 *
 * @author: zhaojy
 * @data:On 2018/5/17.
 */

public class NoScrollGridView extends GridView {

    public NoScrollGridView(Context context) {
        super(context);
    }

    public NoScrollGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NoScrollGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override

    public boolean dispatchTouchEvent(MotionEvent ev) {
        //禁止GridView滑动
        return ev.getAction() == MotionEvent.ACTION_MOVE || super.dispatchTouchEvent(ev);
    }

    /**
     * ScrollView嵌套GridView需要重新测量高度，否则会出现显示不全的情况
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(
                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

}
