package com.zhaojy.selectlibrary.view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhaojy.selectlibrary.R;
import com.zhaojy.selectlibrary.adapter.PhotoAdapter;
import com.zhaojy.selectlibrary.bean.PhotoSortBean;
import com.zhaojy.selectlibrary.control.Director;
import com.zhaojy.selectlibrary.control.PhotoSelectBuilder;
import com.zhaojy.selectlibrary.data.GetPhotoPath;
import com.zhaojy.selectlibrary.util.PathUtil;
import com.zhaojy.selectlibrary.util.PermissionUtils;
import com.zhaojy.selectlibrary.util.PhotoSortPopUtil;
import com.zhaojy.selectlibrary.util.PhotoUtils;
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
    private RelativeLayout titleBar;
    private ImageView back;
    private LinearLayout footerBar;
    private TextView title;

    private PhotoAdapter photoAdapter;

    /**
     * 照片分类弹框工具
     */
    private PhotoSortPopUtil sortPopUtil;

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
    private PhotoSelectBuilder builder = (PhotoSelectBuilder) Director.getBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.photo_select_layout);

        init();
    }

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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PhotoUtils.CODE_CAMERA_REQUEST:
                    //拍照
                    if (builder.getCropable()) {
                        //裁剪
                        PhotoUtils.cropImageUri(PhotoSelectActivity.this,
                                builder.getPhotoUri(), builder.getCropUri(),
                                builder.getCropWidth(), builder.getCropHeight()
                                , builder.getCropWidth(), builder.getCropHeight()
                                , PhotoUtils.CODE_CROP_REQUEST);
                    } else {
                        List<String> path = new ArrayList<>();
                        path.add(PhotoUtils.getPath(this, builder.getPhotoUri()));
                        builder.getSelectedPhotoPath().selectedResult(path);

                        this.finish();
                    }
                    break;
                case PhotoUtils.CODE_CROP_REQUEST:
                    //裁剪
                    List<String> path = new ArrayList<>();
                    path.add(PhotoUtils.getPath(this, builder.getCropUri()));
                    builder.getSelectedPhotoPath().selectedResult(path);

                    this.finish();
                    break;
                default:
                    break;
            }

        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if (hasFocus) {

        }
    }

    private void init() {
        //设置状态栏透明
        StatusBarUtil.setStatusBarColor(this, builder.getTitleBarShape());
        findViewById();
        //申请权限
        applyPermission();
    }

    private void findViewById() {
        photoGridView = findViewById(R.id.photoGridView);
        photoSort = findViewById(R.id.photoSort);
        selected = findViewById(R.id.selected);
        sort = findViewById(R.id.sort);
        titleBar = findViewById(R.id.titleBar);
        title = findViewById(R.id.title);
        back = findViewById(R.id.back);
        footerBar = findViewById(R.id.footerBar);

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
        //初始化样式
        initStyle();
    }

    /**
     * 获取所有照片的路径
     */
    private void getPhotoPath() {
        pathList = GetPhotoPath.queryGallery(this);
        //获取照片的分类信息
        sortMap = PathUtil.getPathSort(pathList);

        //如果设置为拍照则加入
        if (builder.getShowCamera()) {
            pathList.add(0, "拍照");
        }

    }

    /**
     * 设置照片GridView属性
     */
    private void setPhotoGridView() {
        photoGridView.setHorizontalSpacing(builder.getHorizontalSpacing());
        photoGridView.setVerticalSpacing(builder.getVerticalSpacing());
        photoGridView.setNumColumns(builder.getColumnSum());

        //获取屏幕宽度
        int screenWidth = ScreenUtils.getScreenWidth(this);
        photoGridView.setColumnWidth(screenWidth / builder.getColumnSum());
        //设置照片宽高
        builder.setPhotoItemHw((screenWidth - (builder.getColumnSum() - 1)
                * builder.getHorizontalSpacing()) / builder.getColumnSum());
    }

    /**
     * 设置GridView适配器
     */
    private void setPhotoAdapter() {
        adapterPathList = new ArrayList<>();
        adapterPathList.addAll(pathList);
        photoAdapter = new PhotoAdapter(this, adapterPathList);
        photoAdapter.setSelected(new PhotoAdapter.ISelected() {
            @Override
            public void selected(int selectedSum) {
                selected.setText("完成(" + selectedSum + "/" + builder.getMaxSelected() + ")");
            }
        });
        //单选监听接口
        photoAdapter.setSingleSelected(new PhotoAdapter.ISingleSelected() {
            @Override
            public void selected() {
                singleResult();
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
        if (builder.getShowCamera()) {
            //如果显示拍照则需要减去一张
            photoSortBean.setSum(pathList.size() - 1);
        }
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

                //滚动到顶部
                photoGridView.smoothScrollToPosition(0);
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
                //将获取到的图片地址集合发给请求方
                List<String> path = photoAdapter.getSelectedList();
                builder.getSelectedPhotoPath().selectedResult(path);

                PhotoSelectActivity.this.finish();
            }
        });
    }

    /**
     * 初始化选中情况
     */
    private void initSelected() {
        if (!builder.getMultiple()) {
            //如果不是多选隐藏
            selected.setVisibility(View.GONE);
        }
        selected.setText("完成(0/" + builder.getMaxSelected() + ")");
    }

    /**
     * 初始化样式
     */
    private void initStyle() {
        back.setImageResource(builder.getBackIcon());
        title.setText(builder.getTitle());
        footerBar.setBackgroundColor(builder.getFooterBarShape());
        title.setTextColor(builder.getTitleColor());
        titleBar.setBackgroundColor(builder.getTitleBarShape());
        sort.setTextColor(builder.getPhotoSortColor());
    }

    /**
     * 单选结果处理
     */
    private void singleResult() {
        //将获取到的图片地址集合发给请求方
        List<String> path = photoAdapter.getSelectedList();

        if (builder.getCropable()) {
            //裁剪
            Uri uri = PhotoUtils.getUri(PhotoSelectActivity.this, path.get(0));

            PhotoUtils.cropImageUri(PhotoSelectActivity.this, uri, builder.getCropUri(),
                    builder.getCropWidth(), builder.getCropHeight()
                    , builder.getCropWidth(), builder.getCropHeight()
                    , PhotoUtils.CODE_CROP_REQUEST);

        } else {
            //不需要裁剪直接返回
            builder.getSelectedPhotoPath().selectedResult(path);
            this.finish();
        }

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
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.CAMERA
                    }, this
            );
        }
        if (!isAllGranted) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                ActivityCompat.requestPermissions(
                        this,
                        new String[]{
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.CAMERA
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
