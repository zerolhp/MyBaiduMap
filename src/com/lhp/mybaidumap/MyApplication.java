package com.lhp.mybaidumap;

import com.baidu.mapapi.SDKInitializer;

import android.app.Application;

/**
 * MyApplication类继承自Application，主要用于初始化百度地图SDK。
 */

public class MyApplication extends Application {

	/** 百度SDK的初始化需要在Application onCreate()时进行 */
	@Override
	public void onCreate() {
		super.onCreate();
		SDKInitializer.initialize(getApplicationContext());
	}

}
