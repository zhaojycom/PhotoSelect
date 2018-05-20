package com.zhaojy.selectlibrary.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Environment;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * bitmap工具类
 *
 * @author: zhaojy
 * @data:On 2018/5/17.
 */

public class BitmapUtils {
    private final static String TAG = BitmapUtils.class.getSimpleName();

    /**
     * 采样率压缩  按照图片宽高自动计算缩放比，图片质量有保障
     *
     * @param filePath  设置宽高并不是设置图片实际宽高，而是根据宽高自动计算缩放比，压缩后图片不会变形，宽高会根据计算的缩放比同时缩放，
     *                  宽高建议都设置300   设置300后图片大小为100-200KB，图片质量能接受；设置为400到500，图片大小为500-600kb，上传偏大，可自行设置
     * @param reqHeight
     * @param reqWidth
     * @return
     */
    public static Bitmap getSmallBitmap(String filePath, int reqHeight, int reqWidth) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        //计算图片的缩放值
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        options.inSampleSize = inSampleSize;
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }

    /**
     * 压缩bitmap
     *
     * @param bitmap
     * @param quality
     * @return
     */
    public static Bitmap compressBitmap(Bitmap bitmap, int quality) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, out);
        return bitmap;
    }

    /**
     * 这个可以压缩到指定的宽，但是图片大小可能达不到预期，图片本身较小的可以使用，图片较大的建议使用上一个压缩方式
     * 根据自定义宽度设置图片大小，高度自适应  0不压缩
     *
     * @param path
     * @param width
     * @return
     */
    public static Bitmap createScaledBitemap(String path, int width) {
        Bitmap bit = BitmapFactory.decodeFile(path);
        //得到图片宽
        int bitWidth = bit.getWidth();
        //计算宽度缩放比例
        float scaleWidth = ((float) width) / ((float) bitWidth);
        if (width == 0) {
            return bit;
        } else {
            //根据宽度缩放比例设置高度
            int height = (int) (bit.getHeight() * scaleWidth);
            Bitmap bitmap = Bitmap.createScaledBitmap(bit, width, height, true);
            return bitmap;
        }
    }

    /**
     * 判断是否有sd卡
     *
     * @return
     */
    public static boolean isStorage() {
        boolean isstorage = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        return isstorage;
    }

    /**
     * 把Bimtmap转成Base64，用于上传图片到服务器，一般是先压缩然后转成Base64，在上传
     */
    public static String getBitmapStrBase64(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] bytes = baos.toByteArray();
        return Base64.encodeToString(bytes, Base64.NO_WRAP);
    }

    public static String bitmapToBase64(Bitmap bitmap) {

        String result = null;
        ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                baos.flush();
                baos.close();
                byte[] bitmapBytes = baos.toByteArray();
                result = Base64.encodeToString(bitmapBytes, Base64.NO_WRAP);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 把Base64转换成Bitmap
     */
    public static Bitmap getBitmapFromBase64(String iconBase64) {
        byte[] bitmapArray = Base64.decode(iconBase64, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
    }

    /**
     * @param imagePath
     * @param maxWidth
     * @param maxHeight
     * @param isDeleteFile
     * @return
     */
    public static Bitmap getImageThumbnail(String imagePath, int maxWidth,
                                           int maxHeight, boolean isDeleteFile) {
        Bitmap bitmap = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        bitmap = BitmapFactory.decodeFile(imagePath, options);
        options.inJustDecodeBounds = false;
        options.inSampleSize = calculateInSampleSize(options, maxWidth, maxHeight);
        FileInputStream inputStream = null;
        File file = new File(imagePath);
        try {
            if (file != null && file.exists()) {
                inputStream = new FileInputStream(file);
                bitmap = BitmapFactory.decodeStream(inputStream, null, options);
                if (isDeleteFile) {
                    file.delete();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return bitmap;
        }
    }

    /**
     * 计算像素压缩的缩放比例
     *
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    private static int calculateInSampleSize(BitmapFactory.Options options,
                                             int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        // int inSampleSize = 1;
        float temp = 0f;
        if (height > reqHeight || width > reqWidth) {
            if (width > height) {
                temp = (float) width / (float) reqWidth;
            } else {
                temp = (float) height / (float) reqHeight;
            }
        }

        for (float i = 0f; ; i++) {
            if (i > temp) {
                return (int) i;
            }
        }

    }

    /**
     * 按正方形裁切图片
     *
     * @param bitmap
     * @return
     */
    public static Bitmap cropWithSquare(Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }
        // 得到图片的宽，高
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        // 裁切后所取的正方形区域边长
        int wh = w > h ? h : w;
        // 基于原图，取正方形左上角x坐标
        int retX = w > h ? (w - h) / 2 : 0;
        int retY = w > h ? 0 : (h - w) / 2;

        Bitmap bmp = Bitmap.createBitmap(bitmap, retX, retY, wh, wh, null, false);
        if (!bitmap.equals(bmp) && !bitmap.isRecycled()) {
            bitmap.recycle();
        }

        // 下面这句是关键
        return bmp;
    }

    /**
     * 按长方形裁切图片
     *
     * @param bitmap
     * @return
     */
    public static Bitmap cropWithRect(Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }

        // 得到图片的宽，高
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        int nw, nh, retX, retY;
        if (w > h) {
            nw = h / 2;
            nh = h;
            retX = (w - nw) / 2;
            retY = 0;
        } else {
            nw = w / 2;
            nh = w;
            retX = w / 4;
            retY = (h - w) / 2;
        }

        // 下面这句是关键
        Bitmap bmp = Bitmap.createBitmap(bitmap, retX, retY, nw, nh, null,
                false);
        if (!bitmap.equals(bmp) && !bitmap.isRecycled()) {
            bitmap.recycle();
        }

        return bmp;
    }

    /**
     * 图片的缩放方法
     *
     * @param orgBitmap ：源图片资源
     * @param newWidth  ：缩放后宽度
     * @param newHeight ：缩放后高度
     * @return
     */
    public static Bitmap getZoomImage(Bitmap orgBitmap, double newWidth, double newHeight) {
        if (null == orgBitmap) {
            return null;
        }
        if (orgBitmap.isRecycled()) {
            return null;
        }
        if (newWidth <= 0 || newHeight <= 0) {
            return null;
        }

        // 获取图片的宽和高
        float width = orgBitmap.getWidth();
        float height = orgBitmap.getHeight();
        // 创建操作图片的matrix对象
        Matrix matrix = new Matrix();
        // 计算宽高缩放率
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 缩放图片动作
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap bitmap = Bitmap.createBitmap(orgBitmap, 0, 0,
                (int) width, (int) height, matrix, true);

        if (!orgBitmap.isRecycled()) {
            orgBitmap.recycle();
        }
        return bitmap;
    }

}
