package com.lhp.mybaidumap.activity;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.lhp.mybaidumap.R;
import com.lhp.mybaidumap.R.drawable;

import android.view.Menu;
import android.view.MenuItem;

/**
 * LocationActivity继承自BaseActivity，主要用于展示三种定位方式。
 */

public class LocationActivity extends BaseActivity {

	public LocationClient mLocationClient = null; // 定位客户端对象
	public BDLocationListener myListener = new MyLocationListener(); // 自定义百度定位信息监听器对象
	private int item_id; // 菜单项ID

	/** 自定义百度定位信息监听器 */
	public class MyLocationListener implements BDLocationListener {

		/** 接收并处理定位结果 */
		@Override
		public void onReceiveLocation(BDLocation location) {
			if (location != null) {
				MyLocationData.Builder builder = new MyLocationData.Builder();
				builder.accuracy(location.getRadius()); // 设置精度
				builder.direction(location.getDirection()); // 设置方向
				builder.latitude(location.getLatitude()); // 设置纬度
				builder.longitude(location.getLongitude()); // 设置经度
				MyLocationData locationData = builder.build();
				baiduMap.setMyLocationData(locationData); // 将定位数据显示到地图上
			}
		}
	}

	/** Activity的初始化操作 */
	@Override
	public void init() {
		setTitle("三种定位方式"); // 设置标题
		mLocationClient = new LocationClient(getApplicationContext()); // 声明LocationClient类
		mLocationClient.registerLocationListener(myListener); // 注册监听函数
		setLocationOptions(); // 设置定位选项
		baiduMap.setMyLocationEnabled(true); // 开启定位图层
		setMyLocationConfigeration(MyLocationConfiguration.LocationMode.COMPASS); // 定位图层默认采用罗盘态
		mLocationClient.start(); // 开始定位
	}

	/** 创建菜单项 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 1, 0, "罗盘态");
		menu.add(0, 2, 0, "跟随态");
		menu.add(0, 3, 0, "普通态");
		return true;
	}

	/** 处理菜单项 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		item_id = item.getItemId();
		switch (item_id) {
		case 1: // 罗盘态：显示定位方向圈，保持定位图标始终在地图中心。
			setMyLocationConfigeration(MyLocationConfiguration.LocationMode.COMPASS);
			break;
		case 2: // 跟随态：保持定位图标始终在地图中心
			setMyLocationConfigeration(MyLocationConfiguration.LocationMode.FOLLOWING);
			break;
		case 3: // 普通态： 更新定位数据时不对地图做任何操作
			setMyLocationConfigeration(MyLocationConfiguration.LocationMode.NORMAL);
			break;
		}
		return true;
	}

	/** 根据不同的定位模式配置定位信息 */
	private void setMyLocationConfigeration(MyLocationConfiguration.LocationMode mode) {
		// 设置允许显示方向
		boolean enableDirection = true;
		// 导入自定义的定位图标
		BitmapDescriptor customMarker = BitmapDescriptorFactory.fromResource(R.drawable.icon_geo);
		// 定义定位配置信息对象
		MyLocationConfiguration config = new MyLocationConfiguration(mode, enableDirection, customMarker);
		// 将定位配置信息添加到地图管理器中去
		baiduMap.setMyLocationConfigeration(config);
	}

	private void setLocationOptions() {
		// 创建定位客户端选项对象
		LocationClientOption option = new LocationClientOption();
		// 设置高精度定位模式
		option.setLocationMode(LocationMode.Hight_Accuracy); // 定位模式：高精度、低功耗、仅设备（默认高精度）
		// 设置返回的定位结果坐标系
		option.setCoorType("bd09ll");
		// 设置发起定位请求的事件间隔
		int span = 5000;
		option.setScanSpan(span);
		// 设置需要地址信息
		option.setIsNeedAddress(true);
		// 设置使用GPS
		option.setOpenGps(true);
		// 设置当GPS有效时按照1S1次频率输出GPS结果
		option.setLocationNotify(true);
		// 设置需要位置语义化结果，如：“在北京天安门附近”。
		option.setIsNeedLocationDescribe(true);
		// 设置需要POI结果
		option.setIsNeedLocationPoiList(true);
		// 设置Service stop时不杀死这个进程（定位SDK的内部是一个Service，且在一个独立进程中。）
		option.setIgnoreKillProcess(false);
		// 设置不收集CRASH信息
		option.SetIgnoreCacheException(false);
		// 设置不需要过滤GPS仿真结果
		option.setEnableSimulateGps(false);
		// 将选项对象添加到定位客户端对象中去
		mLocationClient.setLocOption(option);
	}

	/** 由于定位的底层是由Service完成的，所以我们需要在Activity销毁时停止服务。 */
	@Override
	protected void onDestroy() {
		mLocationClient.stop(); // 停止定位
		super.onDestroy();
	}

}
