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

import com.zhaojy.selectlibrary.R;
import com.zhaojy.selectlibrary.adapter.PhotoAdapter;
import com.zhaojy.selectlibrary.data.GetPhotoPath;
import com.zhaojy.selectlibrary.util.PathUtil;
import com.zhaojy.selectlibrary.util.PermissionUtils;
import com.zhaojy.selectlibrary.util.StatusBarUtil;

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

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.back) {
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
                getPhotoPath();
            } else {

            }
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
        StatusBarUtil.setStatusBarColor(this, getResources().getColor(R.color.theme));
        findViewById();
        //申请权限
        applyPermission();
    }

    private void findViewById() {
        photoGridView = findViewById(R.id.photoGridView);
    }

    private void getPhotoPath() {
        List<String> path = GetPhotoPath.queryGallery(this);
        Map<String, List<String>> map = PathUtil.getPathSort(path);

        PhotoAdapter pictureAdapter = new PhotoAdapter(this, path);
        photoGridView.setAdapter(pictureAdapter);
        
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
            getPhotoPath();
        }
    }

}