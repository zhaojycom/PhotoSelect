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

    int getHorizontalSpacing();

    int getVerticalSpacing();

    int getPhotoItemHw();

    PhotoSelectBuilder.ISelectedPhotoPath getSelectedPhotoPath();

    void create(Context context);
}
