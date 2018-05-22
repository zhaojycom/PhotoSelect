package com.zhaojy.selectlibrary.control;

import android.content.Context;
import android.net.Uri;

import com.zhaojy.selectlibrary.R;
import com.zhaojy.selectlibrary.util.DisplayUtil;

import java.util.List;

/**
 * 具体建造者
 *
 * @author: zhaojy
 * @data:On 2018/5/17.
 */

public class PhotoSelectBuilder implements Builder {
    private Context context;
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

    /**
     * 照片摆放列数。默认为3
     */
    private int columnSum = 3;

    /**
     * 照片Uri
     */
    private Uri photoUri;

    /**
     * 裁剪Uri
     */
    private Uri cropUri;

    /**
     * 占位图
     */
    private int placeholder;

    /**
     * 最大选择数量
     */
    private int maxSelected;

    /**
     * 标题栏shape
     */
    private int titleBarShape;

    /**
     * 底部栏shpae
     */
    private int footerBarShape;

    /**
     * 返回图标
     */
    private int backIcon;

    /**
     * 标题栏文字
     */
    private String title;

    /**
     * 选中背景颜色
     */
    private int selectedColor;

    /**
     * 标题颜色
     */
    private int titleColor;

    /**
     * 照片分类文字颜色
     */
    private int photoSortColor;

    public PhotoSelectBuilder(Context context) {
        this.context = context;

        //设置默认参数
        setHorizontalSpacing(DisplayUtil.dip2px(context, 4));
        setVerticalSpacing(DisplayUtil.dip2px(context, 4));
        setBackIcon(R.mipmap.back);
        setTitle(context.getResources().getString(R.string.selectPhoto));
        setTitleBarShape(R.drawable.titlebar_shpae);
        setFooterBarShape(context.getResources().getColor(R.color.white));

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
    public Builder setColumnSum(int columnSum) {
        this.columnSum = columnSum;
        return this;
    }

    @Override
    public Builder setPhotoUri(Uri photoUri) {
        this.photoUri = photoUri;
        return this;
    }

    @Override
    public Builder setCropUri(Uri cropUri) {
        this.cropUri = cropUri;
        return this;
    }

    @Override
    public Builder setPlaceholder(int placeholder) {
        this.placeholder = placeholder;
        return this;
    }

    @Override
    public Builder setMaxSelected(int maxSelected) {
        this.maxSelected = maxSelected;
        return this;
    }

    @Override
    public Builder setTitleBarShape(int titleBarShape) {
        this.titleBarShape = titleBarShape;
        return this;
    }

    @Override
    public Builder setFooterBarShape(int footerBarShape) {
        this.footerBarShape = footerBarShape;
        return this;
    }

    @Override
    public Builder setBackIcon(int backIcon) {
        this.backIcon = backIcon;
        return this;
    }

    @Override
    public Builder setTitle(String title) {
        this.title = title;
        return this;
    }

    @Override
    public Builder setSelectedColor(int selectedColor) {
        this.selectedColor = selectedColor;
        return this;
    }

    @Override
    public Builder setTitleColor(int titleColor) {
        this.titleColor = titleColor;
        return this;
    }

    @Override
    public Builder setPhotoSortColor(int photoSortColor) {
        this.photoSortColor = photoSortColor;
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
    public int getColumnSum() {
        return columnSum;
    }

    @Override
    public Uri getPhotoUri() {
        return photoUri;
    }

    @Override
    public Uri getCropUri() {
        return cropUri;
    }

    @Override
    public int getPlaceholder() {
        return placeholder;
    }

    @Override
    public int getMaxSelected() {
        return maxSelected;
    }

    @Override
    public int getTitleBarShape() {
        return titleBarShape;
    }

    @Override
    public int getFooterBarShape() {
        return footerBarShape;
    }

    @Override
    public int getBackIcon() {
        return backIcon;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public int getSelectedColor() {
        return selectedColor;
    }

    @Override
    public int getTitleColor() {
        return titleColor;
    }

    @Override
    public int getPhotoSortColor() {
        return photoSortColor;
    }

    @Override
    public ISelectedPhotoPath getSelectedPhotoPath() {
        return selectedPhotoPath;
    }

    @Override
    public void create() {
        if (multiple) {
            cropable = false;
            showCamera = false;
        }

        Director director = new Director(context, this);
        director.create();
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
