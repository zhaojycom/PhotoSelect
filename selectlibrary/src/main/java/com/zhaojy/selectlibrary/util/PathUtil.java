package com.zhaojy.selectlibrary.util;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 路径工具类
 *
 * @author: zhaojy
 * @data:On 2018/5/17.
 */

public class PathUtil {
    private final static String TAG = PathUtil.class.getSimpleName();

    /**
     * 获取路径分类map集合
     *
     * @param pathList 照片绝对路径集合
     * @return 路径分类map集合 key为目录名，value为该目录下所有的照片路径
     */
    public static Map<String, List<String>> getPathSort(List<String> pathList) {
        Map<String, List<String>> sortMap = new HashMap<>();
        //文件名集合
        List<String> nameList = new ArrayList<>();
        //文件上一级目录集合
        List<String> descList = new ArrayList<>();

        //获取所有文件的文件名已经上一级目录名
        for (String path : pathList) {
            try {
                //分割绝对路径获取文件名以及文件的上一级目录名
                String[] temp = path.split("/");
                int len = temp.length;
                nameList.add(temp[len - 1]);
                descList.add(temp[len - 2]);
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
        }

        //计数器
        int count = 0;
        //遍历文件上一级目录名集合
        for (String desc : descList) {
            List<String> sort = null;
            if (sortMap.containsKey(desc)) {
                //如果包含该文件名的map
                sort = sortMap.get(desc);
            } else {
                //否则重新new一个List
                sort = new ArrayList<>();
                sortMap.put(desc, sort);
            }

            sort.add(pathList.get(count++));
        }

        return sortMap;
    }

}
