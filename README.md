# PhotoSelect
This is a photo selection framework.

1、导入依赖

allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}

dependencies {
	implementation 'com.github.User:Repo:Tag'
}

2、配置权限
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.CAMERA" />

3、注册Activity
<activity android:name="com.zhaojy.selectlibrary.view.PhotoSelectActivity" />

4、简单使用

PhotoSelectBuilder photoSelectBuilder = new PhotoSelectBuilder(
                MainActivity.this);
        photoSelectBuilder
                //设置是否可多选
                .setMultiple(true)
                //最大多选数量
                .setMaxSelected(9)
                //是否可裁剪，仅支持单选模式下
                .setCropable(true)
                //裁剪宽度
                .setCropWidth(600)
                //裁剪高度
                .setCropHeight(600)
                //是否显示照相功能，仅支持单选模式
                .setShowCamera(true)
                //每行显示照片数量
                .setColumnSum(3)
                //照片横向间距
                .setHorizontalSpacing(DisplayUtil.dip2px(context, 2))
                //照片纵向间距
                .setVerticalSpacing(DisplayUtil.dip2px(context, 2))
                //占位底图
                .setPlaceholder(R.mipmap.placeholder)
                //框架title
                .setTitle("照片选择")
                //选择成功回调接口
                .setSelectedPhotoPath(new PhotoSelectBuilder.ISelectedPhotoPath() {
                    @Override
                    public void selectedResult(List<String> pathList) {
                        for (String path : pathList) {
                            Log.e(TAG, path);
                        }
                    }
                });

        //设置照片裁剪存放Uri
        File fileCropUri = new File(Environment
                .getExternalStorageDirectory().getPath() + "/crop_photo.jpg");
        photoSelectBuilder.setCropUri(Uri.fromFile(fileCropUri));

        //设置拍照存放Uri
        File takePhotoFile = new File(Environment
                .getExternalStorageDirectory().getPath() + "/take_photo.jpg");
        photoSelectBuilder.setPhotoUri(Uri.fromFile(takePhotoFile));

        //创建
        photoSelectBuilder.create();
