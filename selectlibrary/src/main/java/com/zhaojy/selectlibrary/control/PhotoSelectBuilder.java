package com.zhaojy.selectlibrary.control;

import android.content.Context;
import android.content.Intent;

import com.zhaojy.selectlibrary.util.DisplayUtil;
import com.zhaojy.selectlibrary.view.PhotoSelectActivity;

import java.util.List;

/**
 * 具体建造者
 *
 * @author: zhaojy
 * @data:On 2018/5/17.
 */

public class PhotoSelectBuilder implements Builder {
    private static PhotoSelectBuilder photoSelectBuilder = new PhotoSelectBuilder();

    /**
     * GridView横向间距
     */
    private int horizontalSpacing;

    /**
     * GridView纵向间距
     */
    private int verticalSpacing;

    /**
     * 照片的宽高
     */
    private int photoItemHw;

    /**
     * 被选择照片路径接口
     */
    private ISelectedPhotoPath selectedPhotoPath;

    private PhotoSelectBuilder() {

    }

    public static PhotoSelectBuilder getInstance() {
        return photoSelectBuilder;
    }

    @Override
    public Builder setHorizontalSpacing(int horizontalSpacing) {
        this.horizontalSpacing = horizontalSpacing;
        return this;
    }

    @Override
    public Builder setVerticalSpacing(int verticalSpacing) {
        this.verticalSpacing = verticalSpacing;
        return this;
    }

    @Override
    public Builder setSelectedPhotoPath(ISelectedPhotoPath selectedPhotoPath) {
        this.selectedPhotoPath = selectedPhotoPath;
        return this;
    }

    @Override
    public void setPhotoItemHw(int photoItemHw) {
        this.photoItemHw = photoItemHw;
    }

    @Override
    public int getHorizontalSpacing() {
        return horizontalSpacing;
    }

    @Override
    public int getVerticalSpacing() {
        return verticalSpacing;
    }

    @Override
    public int getPhotoItemHw() {
        return photoItemHw;
    }


    @Override
    public ISelectedPhotoPath getSelectedPhotoPath() {
        return selectedPhotoPath;
    }

    @Override
    public void create(Context context) {
        //设置默认值
        setDefault(context);
        Intent intent = new Intent(context, PhotoSelectActivity.class);
        context.startActivity(intent);
    }

    /**
     * 设置默认值
     *
     * @param context 上下文
     */
    private void setDefault(Context context) {
        if (horizontalSpacing == 0) {
            setHorizontalSpacing(DisplayUtil.dip2px(context, 4));
        }
        if (verticalSpacing == 0) {
            setVerticalSpacing(DisplayUtil.dip2px(context, 4));
        }


    }

    /**
     * 被选择照片路径接口
     */
    public interface ISelectedPhotoPath {
        void selectedResult(List<String> pathList);
    }

}
