package com.zhaojy.selectlibrary.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.zhaojy.selectlibrary.R;
import com.zhaojy.selectlibrary.control.PhotoSelectBuilder;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: zhaojy
 * @data:On 2018/5/17.
 */

public class PhotoAdapter extends BaseAdapter {
    private static final String TAG = PhotoAdapter.class.getSimpleName();
    private Context context;

    private List<String> pathList;

    /**
     * 记录照片选中状态
     */
    private Map<String, Boolean> map;

    private List<String> selectedList;

    /**
     * 选中的数量
     */
    private int selectedSum;
    /**
     * 最大选中数量
     */
    private int maxSelected;

    /**
     * 选择接口
     */
    private ISelected selected;

    /**
     * 照片选择器构建对象
     */
    private PhotoSelectBuilder builder = PhotoSelectBuilder.getInstance();

    public PhotoAdapter(Context context, List<String> pathList, int maxSelected, ISelected selected) {
        this.context = context;
        this.pathList = pathList;
        this.maxSelected = maxSelected;
        this.selected = selected;

        selectedList = new ArrayList<>();
        initMap();
    }

    @SuppressLint("UseSparseArrays")
    private void initMap() {
        map = new HashMap<>();
        for (int i = 0; i < pathList.size(); i++) {
            map.put(pathList.get(i), false);
        }
    }

    @Override
    public int getCount() {
        return pathList == null ? 0 : pathList.size();
    }

    @Override
    public Object getItem(int position) {
        return pathList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        // 获得容器
        convertView = LayoutInflater.from(this.context).inflate(R.layout.photo_item, null);
        ViewHolder viewHolder = new ViewHolder(convertView);

        WeakReference<ImageView> imageViewReference = new WeakReference<>(viewHolder.photo);
        //加载图片
        Glide.with(context)
                .load(new File(pathList.get(position)))
                .asBitmap()
                .centerCrop()
                .placeholder(R.mipmap.placeholder)
                .into(imageViewReference.get());

        //如果之前该位置已经选中，进行选中状态设置
        if (map.get(pathList.get(position))) {
            setSelected(viewHolder);
        }

        final ViewHolder finalViewHolder = viewHolder;
        viewHolder.photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int visible = finalViewHolder.cover.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE;

                if (visible == View.VISIBLE) {
                    if (selectedSum == maxSelected) {
                        Toast.makeText(context, context.getResources().getString(R.string.tip)
                                , Toast.LENGTH_SHORT).show();
                        return;
                    }
                    map.put(pathList.get(position), true);
                    selectedList.add(pathList.get(position));
                    selectedSum++;
                } else {
                    map.put(pathList.get(position), false);
                    for (int i = 0; i < selectedList.size(); i++) {
                        if (selectedList.get(i).equals(pathList.get(position))) {
                            selectedList.remove(i);
                            break;
                        }
                    }
                    selectedSum--;
                }

                finalViewHolder.cover.setVisibility(visible);
                finalViewHolder.selected.setVisibility(visible);

                selected.selected(selectedSum);
            }
        });

        return convertView;
    }

    class ViewHolder {
        ImageView photo;
        LinearLayout selected;
        LinearLayout cover;

        public ViewHolder(View view) {
            // 初始化组件
            photo = view.findViewById(R.id.photo);
            cover = view.findViewById(R.id.cover);
            selected = view.findViewById(R.id.selected);

            //初始化设置
            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) photo.getLayoutParams();
            lp.height = builder.getPhotoItemHw();
            photo.setLayoutParams(lp);

            RelativeLayout.LayoutParams lp2 = (RelativeLayout.LayoutParams) cover.getLayoutParams();
            lp2.height = builder.getPhotoItemHw();
            cover.setLayoutParams(lp2);
        }
    }

    /**
     * 设置为选中状态
     */
    private void setSelected(ViewHolder holder) {
        holder.cover.setVisibility(View.VISIBLE);
        holder.selected.setVisibility(View.VISIBLE);
    }

    public interface ISelected {
        void selected(int selectedSum);
    }

    /**
     * 获取选中的图片路径集合
     *
     * @return
     */
    public List<String> getSelectedList() {
        return selectedList;
    }


}