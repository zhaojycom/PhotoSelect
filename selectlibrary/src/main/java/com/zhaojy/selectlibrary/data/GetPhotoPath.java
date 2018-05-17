package com.zhaojy.selectlibrary.data;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.database.Cursor;
import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.List;

/**
 * 获取照片路径
 *
 * @author: zhaojy
 * @data:On 2018/5/17.
 */

public class GetPhotoPath {
    private final static String TAG = GetPhotoPath.class.getSimpleName();

    /**
     * 获取SD卡中的全部图片的路径列表
     *
     * @param activity 制定的activity对象
     * @return 本机所有图片路径
     */
    public static List<String> queryGallery(Activity activity) {
        //文件名
        List<String> names = new ArrayList<>();
        //目录
        List<String> descs = new ArrayList<>();
        //图片路径
        List<String> fileNames = new ArrayList<>();

        @SuppressLint("Recycle")
        Cursor cursor = activity.getContentResolver()
                .query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                        , null, null, null, null);

        while (cursor != null && cursor.moveToNext()) {
            //获取图片的名称
            String name = cursor.getString(cursor.getColumnIndex(
                    MediaStore.Images.Media.DISPLAY_NAME));
            //获取图片的生成日期
            byte[] data = cursor.getBlob(cursor.getColumnIndex(
                    MediaStore.Images.Media.DATA));
            //获取图片的详细信息
            String desc = cursor.getString(cursor.getColumnIndex(
                    MediaStore.Images.Media.DESCRIPTION));
            names.add(name);
            descs.add(desc);
            fileNames.add(new String(data, 0, data.length - 1));
        }

        return fileNames;
    }


}