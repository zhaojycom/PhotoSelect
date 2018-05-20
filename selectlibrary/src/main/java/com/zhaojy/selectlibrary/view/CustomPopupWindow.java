package com.zhaojy.selectlibrary.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.zhaojy.selectlibrary.R;

/**
 * @author: zhaojy
 * @data:On 2018/5/18.
 */

public class CustomPopupWindow extends PopupWindow implements View.OnClickListener {
    private final static String TAG = CustomPopupWindow.class.getSimpleName();

    private View mPopView;
    private OnItemClickListener mListener;

    public CustomPopupWindow(Context context) {
        super(context);
        init(context);
        setPopupWindow();
    }

    /**
     * 初始化
     *
     * @param context
     */
    private void init(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        //绑定布局
        mPopView = inflater.inflate(R.layout.popwindow_layout, null);
    }

    /**
     * 设置窗口的相关属性
     */
    @SuppressLint("InlinedApi")
    private void setPopupWindow() {
        // 设置View
        this.setContentView(mPopView);
        // 设置弹出窗口的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        // 设置弹出窗口的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置弹出窗口可
        this.setFocusable(true);
        // 设置动画
        this.setAnimationStyle(R.style.mypopwindow_anim_style);
        // 设置背景透明
        this.setBackgroundDrawable(new ColorDrawable(0x00000000));
        // 如果触摸位置在窗口外面则销毁
        mPopView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int height = mPopView.findViewById(R.id.recyclerView).getTop();
                int y = (int) event.getY();

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });
    }

    /**
     * 定义一个接口，公布出去 在Activity中操作按钮的单击事件
     */
    public interface OnItemClickListener {
        void setOnItemClick(View v);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    @Override
    public void onClick(View v) {
        if (mListener != null) {
            mListener.setOnItemClick(v);
        }
    }

}