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
    
