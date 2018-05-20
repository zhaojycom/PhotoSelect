package com.zhaojy.selectlibrary.bean;

/**
 * @author: zhaojy
 * @data:On 2018/5/18.
 */

public class PhotoSortBean {
    private String title;
    private int sum;
    private String path;
    private String iconPath;

    public String getIconPath() {
        return iconPath == null ? "" : iconPath;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

    public String getPath() {
        return path == null ? "" : path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public String getTitle() {
        return title == null ? "" : title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
