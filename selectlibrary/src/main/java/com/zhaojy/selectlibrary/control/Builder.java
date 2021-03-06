package com.zhaojy.selectlibrary.control;

import android.graphics.drawable.Drawable;
import android.net.Uri;

/**
 * 建造者接口
 *
 * @author: zhaojy
 * @data:On 2018/5/17.
 */

public interface Builder {
    /**
     * 设置照片横向间距
     *
     * @param horizontalSpacing 横向间距
     * @return Builder对象
     */
    Builder setHorizontalSpacing(int horizontalSpacing);

    Builder setVerticalSpacing(int verticalSpacing);

    Builder setSelectedPhotoPath(PhotoSelectBuilder.ISelectedPhotoPath selectedPhotoPath);

    void setPhotoItemHw(int photoItemHw);

    Builder setMultiple(boolean multiple);

    Builder setCropable(boolean cropable);

    Builder setCropWidth(int cropWidth);

    Builder setCropHeight(int cropHeight);

    Builder setShowCamera(boolean showCamera);

    Builder setColumnSum(int columnSum);

    Builder setPhotoUri(Uri photoUri);

    Builder setCropUri(Uri cropUri);

    Builder setPlaceholder(int placeholder);

    Builder setMaxSelected(int maxSelected);

    Builder setTitleBarShape(Drawable titleBarShape);

    Builder setTitleBarColor(int titleBarColor);

    Builder setFooterBarShape(Drawable footerBarShape);

    Builder setFooterBarColor(int footerBarColor);

    Builder setBackIcon(int backIcon);

    Builder setTitle(String title);

    Builder setSelectedColor(int selectedColor);

    Builder setTitleColor(int titleColor);

    Builder setPhotoSortColor(int photoSortColor);

    Builder setSortIcon(int sortIcon);

    int getHorizontalSpacing();

    int getVerticalSpacing();

    int getPhotoItemHw();

    boolean getMultiple();

    boolean getCropable();

    int getCropWidth();

    int getCropHeight();

    boolean getShowCamera();

    int getColumnSum();

    Uri getPhotoUri();

    Uri getCropUri();

    int getPlaceholder();

    int getMaxSelected();

    Drawable getTitleBarShape();

    int getTitleBarColor();

    Drawable getFooterBarShape();

    int getFooterBarColor();

    int getBackIcon();

    String getTitle();

    int getSelectedColor();

    int getTitleColor();

    int getPhotoSortColor();

    int getSortIcon();

    PhotoSelectBuilder.ISelectedPhotoPath getSelectedPhotoPath();

    void create();
}
