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

    /**
     * 是否为多选
     */
    private boolean multiple;

    /**
     * 是否可裁剪
     */
    private boolean cropable;

    /**
     * 裁剪宽度
     */
    private int cropWidth;

    /**
     * 裁剪高度
     */
    private int cropHeight;

    /**
     * 是否显示拍照
     */
    private boolean showCamera;

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
    public Builder setMultiple(boolean multiple) {
        this.multiple = multiple;
        return this;
    }

    @Override
    public Builder setCropable(boolean cropable) {
        this.cropable = cropable;
        return this;
    }

    @Override
    public Builder setCropWidth(int cropWidth) {
        this.cropWidth = cropWidth;
        return this;
    }

    @Override
    public Builder setCropHeight(int cropHeight) {
        this.cropHeight = cropHeight;
        return this;
    }

    @Override
    public Builder setShowCamera(boolean showCamera) {
        this.showCamera = showCamera;
        return this;
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
    public boolean getMultiple() {
        return multiple;
    }

    @Override
    public boolean getCropable() {
        return cropable;
    }

    @Override
    public int getCropWidth() {
        return cropWidth;
    }

    @Override
    public int getCropHeight() {
        return cropHeight;
    }

    @Override
    public boolean getShowCamera() {
        return showCamera;
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
        if (multiple) {
            cropable = false;
            showCamera = false;
        }

    }

    /**
     * 被选择照片路径接口
     */
    public interface ISelectedPhotoPath {
        void selectedResult(List<String> pathList);
    }

}
