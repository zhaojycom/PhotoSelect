package com.zhaojy.selectlibrary.control;

import android.content.Context;

/**
 * 建造者接口
 *
 * @author: zhaojy
 * @data:On 2018/5/17.
 */

public interface Builder {
    Builder setHorizontalSpacing(int horizontalSpacing);

    Builder setVerticalSpacing(int verticalSpacing);

    Builder setSelectedPhotoPath(PhotoSelectBuilder.ISelectedPhotoPath selectedPhotoPath);

    void setPhotoItemHw(int photoItemHw);

    Builder setMultiple(boolean multiple);

    Builder setCropable(boolean cropable);

    Builder setCropWidth(int cropWidth);

    Builder setCropHeight(int cropHeight);

    int getHorizontalSpacing();

    int getVerticalSpacing();

    int getPhotoItemHw();

    boolean getMultiple();

    boolean getCropable();

    int getCropWidth();

    int getCropHeight();

    PhotoSelectBuilder.ISelectedPhotoPath getSelectedPhotoPath();

    void create(Context context);
}
