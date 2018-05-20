package com.zhaojy.selectlibrary.view;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhaojy.selectlibrary.R;
import com.zhaojy.selectlibrary.adapter.PhotoAdapter;
import com.zhaojy.selectlibrary.bean.PhotoSortBean;
import com.zhaojy.selectlibrary.control.PhotoSelectBuilder;
import com.zhaojy.selectlibrary.data.GetPhotoPath;
import com.zhaojy.selectlibrary.util.PathUtil;
import com.zhaojy.selectlibrary.util.PermissionUtils;
import com.zhaojy.selectlibrary.util.PhotoSortPopUtil;
import com.zhaojy.selectlibrary.util.ScreenUtils;
import com.zhaojy.selectlibrary.util.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author: zhaojy
 * @data:On 2018/5/17.
 */

public class PhotoSelectActivity extends AppCompatActivity implements View.OnClickListener {
    private final static String TAG = PhotoSelectActivity.class.getSimpleName();
    private final int PERMISSION_REQUEST_CODE = 10000;

    private GridView photoGridView;
    private PhotoAdapter photoAdapter;

    /**
     * 照片分类弹框工具
     */
    private PhotoSortPopUtil sortPopUtil;

    /**
     * 最大选中数量
     */
    private final static int MAX_SELECTED = 9;

    /**
     * 所有照片路径集合
     */
    private List<String> pathList;

    /**
     * 适配器照片路径集合
     */
    private List<String> adapterPathList;
    /**
     * 所有照片分类map集合
     */
    private Map<String, List<String>> sortMap;

    private LinearLayout photoSort;
    private TextView selected;
    private TextView sort;

    /**
     * 照片选择器构建对象
     */
    private PhotoSelectBuilder builder = PhotoSelectBuilder.getInstance();

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.back) {
            this.finish();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            boolean isAllGranted = true;
            //判断是否所有的权限都已经授予了
            for (int grant : grantResults) {
                if (grant != PackageManager.PERMISSION_GRANTED) {
                    isAllGranted = false;
                    break;
                }
            }

            if (isAllGranted) {
                //获得了所有的权限
                control();
            } else {

            }
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if (hasFocus) {

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.photo_select_layout);

        init();
    }

    private void init() {
        //设置状态栏透明
        StatusBarUtil.setStatusBarColor(this, getResources().getColor(R.color.titleBg));
        findViewById();
        //申请权限
        applyPermission();
    }

    private void findViewById() {
        photoGridView = findViewById(R.id.photoGridView);
        photoSort = findViewById(R.id.photoSort);
        selected = findViewById(R.id.selected);
        sort = findViewById(R.id.sort);
    }

    /**
     * 控制
     */
    private void control() {
        //获取所有照片的路径
        getPhotoPath();
        //设置GridView
        setPhotoGridView();
        //设置GridView适配器
        setPhotoAdapter();
        //设置照片分类弹窗
        setPopupWindow();
        //设置监听器
        setListener();
        //初始化选中情况
        initSelected();
    }

    /**
     * 获取所有照片的路径
     */
    private void getPhotoPath() {
        pathList = GetPhotoPath.queryGallery(this);
        //获取照片的分类信息
        sortMap = PathUtil.getPathSort(pathList);
    }

    private void setPhotoGridView() {
        photoGridView.setHorizontalSpacing(builder.getHorizontalSpacing());
        photoGridView.setVerticalSpacing(builder.getVerticalSpacing());
        //获取屏幕宽度
        int screenWidth = ScreenUtils.getScreenWidth(this);
        photoGridView.setColumnWidth(screenWidth / 3);
        //设置照片宽高
        builder.setPhotoItemHw((screenWidth - 2 * builder.getHorizontalSpacing()) / 3);
    }

    /**
     * 设置GridView适配器
     */
    private void setPhotoAdapter() {
        adapterPathList = new ArrayList<>();
        adapterPathList.addAll(pathList);
        photoAdapter = new PhotoAdapter(this, adapterPathList, MAX_SELECTED
                , new PhotoAdapter.ISelected() {
            @Override
            public void selected(int selectedSum) {
                selected.setText("完成(" + selectedSum + "/" + MAX_SELECTED + ")");
            }
        });
        photoGridView.setAdapter(photoAdapter);
    }

    /**
     * 设置PopupWindow
     */
    public void setPopupWindow() {
        sortPopUtil = new PhotoSortPopUtil();
        final List<PhotoSortBean> data = new ArrayList<>();
        PhotoSortBean photoSortBean = new PhotoSortBean();
        photoSortBean.setTitle("所有照片");
        photoSortBean.setPath("/sdcard");
        if (pathList.size() > 0) {
            photoSortBean.setIconPath(pathList.get(0));
        }
        photoSortBean.setSum(pathList.size());
        data.add(photoSortBean);

        for (String key : sortMap.keySet()) {
            PhotoSortBean psb = new PhotoSortBean();
            psb.setTitle(key);
            psb.setSum(sortMap.get(key).size());
            psb.setIconPath(sortMap.get(key).get(0));
            String[] temp = sortMap.get(key).get(0).split("/");
            String str = "";
            for (int i = 0; i < temp.length - 1; i++) {
                if (i != 0) {
                    str += "/";
                }
                str += temp[i];
            }
            psb.setPath(str);
            data.add(psb);
        }
        sortPopUtil.setClickListener(new PhotoSortPopUtil.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                adapterPathList.clear();
                if (position == 0) {
                    adapterPathList.addAll(pathList);
                } else {
                    adapterPathList.addAll(sortMap.get(data.get(position).getTitle()));
                }

                photoAdapter.notifyDataSetChanged();

                sortPopUtil.dismiss();

                sort.setText(data.get(position).getTitle());
            }
        });
        sortPopUtil.createSpinner(this, data);
    }

    /**
     * 设置监听器
     */
    private void setListener() {
        photoSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sortPopUtil.showSpinner();
            }
        });

        selected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<String> path = photoAdapter.getSelectedList();
                builder.getSelectedPhotoPath().selectedResult(path);
            }
        });
    }

    /**
     * 初始化选中情况
     */
    private void initSelected() {
        selected.setText("完成(0/" + MAX_SELECTED + ")");
    }

    /**
     * 申请权限
     */
    private void applyPermission() {
        //判断权限是否申请
        boolean isAllGranted = false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            isAllGranted = PermissionUtils.checkPermissionAllGranted(
                    new String[]{
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    }, this
            );
        }
        if (!isAllGranted) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                ActivityCompat.requestPermissions(
                        this,
                        new String[]{
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE
                        },
                        PERMISSION_REQUEST_CODE
                );
            }
        } else {
            //获得全部权限
            control();
        }
    }

}
