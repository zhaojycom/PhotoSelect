package com.zhaojy.selectlibrary.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zhaojy.selectlibrary.R;
import com.zhaojy.selectlibrary.bean.PhotoSortBean;
import com.zhaojy.selectlibrary.control.Director;
import com.zhaojy.selectlibrary.control.PhotoSelectBuilder;
import com.zhaojy.selectlibrary.view.CustomPopupWindow;

import java.io.File;
import java.util.List;

/**
 * @author: zhaojy
 * @data:On 2018/5/18.
 */

public class PhotoSortPopUtil {
    private final static String TAG = PhotoSortPopUtil.class.getSimpleName();

    private Activity activity;

    /**
     * 弹窗
     */
    private CustomPopupWindow popupWindow;

    /**
     * 下拉列表RecyclerView
     */
    private RecyclerView dropDown;

    /**
     * 数据集合
     */
    private List<PhotoSortBean> data;

    /**
     * 多选情况下，标记当前位置是否选中
     */
    public boolean[] selectedArr;

    private OnItemClickListener clickListener;

    /**
     * 照片选择器构建对象
     */
    private PhotoSelectBuilder builder = (PhotoSelectBuilder) Director.getBuilder();

    public void setClickListener(OnItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    /**
     * 显示下拉列表
     */
    public void showSpinner() {
        try {
            popupWindow.showAtLocation(activity.findViewById(R.id.titleBar)
                    , Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }

    }

    /**
     * 创建下拉框
     *
     * @param activity 上下文
     * @param data     数据
     */
    public void createSpinner(Activity activity, List<PhotoSortBean> data) {
        this.activity = activity;
        this.data = data;

        View contentView = LayoutInflater.from(activity)
                .inflate(R.layout.popwindow_layout, null);

        selectedArr = new boolean[data.size()];

        popupWindow = new CustomPopupWindow(activity);
        popupWindow.setContentView(contentView);

        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(true);

        int screenHeigh = activity.getResources().getDisplayMetrics().heightPixels;
        popupWindow.setHeight(Math.round(screenHeigh * 0.618f));

        setSortList(contentView);
    }

    /**
     * 设置分类列表
     *
     * @param view
     */
    private void setSortList(View view) {
        dropDown = view.findViewById(R.id.recyclerView);

        DropDownAdapter adapter = new DropDownAdapter(data, activity);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                clickListener.onItemClick(view, position);
            }
        });
        dropDown.setLayoutManager(new LinearLayoutManager(activity));
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        dropDown.setHasFixedSize(true);
        dropDown.setAdapter(adapter);
    }

    private class DropDownAdapter extends RecyclerView.Adapter<DropDownAdapter.ViewHolder> implements
            View.OnClickListener {

        private List<PhotoSortBean> data;
        private Context context;

        private OnItemClickListener mOnItemClickListener = null;

        public DropDownAdapter(List<PhotoSortBean> data, Context context) {
            this.data = data;
            this.context = context;
        }

        @Override
        public DropDownAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.sort_item, parent,
                    false);
            DropDownAdapter.ViewHolder viewHolder = new DropDownAdapter.ViewHolder(view);
            view.setOnClickListener(this);

            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final DropDownAdapter.ViewHolder holder, final int position) {
            //将position保存在itemView的Tag中，以便点击时进行获取
            holder.itemView.setTag(position);
            PhotoSortBean psb = data.get(position);
            holder.title.setText(psb.getTitle());
            holder.sum.setText(psb.getSum() + "张");
            holder.path.setText(psb.getPath());
            //加载图片
            Glide.with(context)
                    .load(new File(psb.getIconPath()))
                    .asBitmap()
                    .centerCrop()
                    .placeholder(builder.getPlaceholder())
                    .into(holder.icon);

            //设置水波纹效果
            RippleUtil.rippleEffect(activity, holder.itemView);
        }

        @Override
        public int getItemCount() {

            return data.size();
        }

        @Override
        public void onClick(View view) {
            if (mOnItemClickListener != null) {
                //注意这里使用getTag方法获取position
                mOnItemClickListener.onItemClick(view, (Integer) view.getTag());
            }
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView title;
            ImageView icon;
            TextView sum;
            TextView path;

            private ViewHolder(View view) {
                super(view);
                title = view.findViewById(R.id.title);
                icon = view.findViewById(R.id.icon);
                sum = view.findViewById(R.id.sum);
                path = view.findViewById(R.id.path);
            }
        }

        public void setOnItemClickListener(OnItemClickListener listener) {
            this.mOnItemClickListener = listener;
        }

    }

    public void dismiss() {
        popupWindow.dismiss();
    }

    /**
     * item事件监听接口
     */
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

}
