package com.lhp.mybaidumap;

import com.baidu.mapapi.SDKInitializer;
import com.lhp.mybaidumap.activity.HelloBaiduMapActivity;
import com.lhp.mybaidumap.activity.LocationActivity;
import com.lhp.mybaidumap.activity.MapLayerActivity;
import com.lhp.mybaidumap.activity.OverlayActivity;
import com.lhp.mybaidumap.activity.RouteActivity;
import com.lhp.mybaidumap.activity.SearchActivity;
import com.lhp.mybaidumap.utils.Utils;

import android.app.ListActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * MainActivity继承自ListActivity，用于展示各功能模块。
 */

public class MainActivity extends ListActivity {

	// 广播
	private BroadcastReceiver receiver;
	// ListView的数据源：类集合
	private ClassAndName[] datas = { 
			new ClassAndName(HelloBaiduMapActivity.class, "体验百度地图的基本功能"),
			new ClassAndName(MapLayerActivity.class, "切换地图的三种图层"), 
			new ClassAndName(OverlayActivity.class, "三种位置指示方式"),
			new ClassAndName(SearchActivity.class, "三种地图搜索方式"), 
			new ClassAndName(RouteActivity.class, "三种路线推荐方式"),
			new ClassAndName(LocationActivity.class, "三种定位方式") };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		registerSDKCheckReceiver(); // 接收SDK的内部广播，判断网络和KEY是否会出现问题。
		setTitle("功能模块"); // 设置Activity标题
		// 设置ListView的适配器
		ArrayAdapter<ClassAndName> adapter = new ArrayAdapter<MainActivity.ClassAndName>(this,
				android.R.layout.simple_list_item_1, datas);
		setListAdapter(adapter);
	}

	/** 接收SDK的内部广播，判断网络和KEY是否会出现问题。 */
	private void registerSDKCheckReceiver() {
		// 先定义一个IntentFilter，只接收指定Action的Intent。
		IntentFilter filter = new IntentFilter();
		// 添加Action
		filter.addAction(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR); // 网络错误
		filter.addAction(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR); // KEY错误
		// 再定义一个BroadcastReceiver，根据Intent中不同的Action作出不同的处理。
		receiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				String action = intent.getAction();
				if (SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR.equals(action)) {
					Utils.showToast(getApplicationContext(), "网络连接错误");
				} else if (SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR.equals(action)) {
					Utils.showToast(getApplicationContext(), "KEY验证失败");
				}
			}
		};
		// 调用registerReceiver()方法向系统注册广播
		registerReceiver(receiver, filter); // 立即准备unregisterReceiver()
	}

	@Override
	protected void onDestroy() {
		unregisterReceiver(receiver); // 解除广播的注册
		super.onDestroy();
	}

	/** ListActivity自带点击事件监听方法，无需自定义监听器。 */
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		ClassAndName data = datas[position]; // 获取对应的数据
		startActivity(new Intent(this, data.cls)); // 跳转Activity
	}

	/** 实体类 */
	class ClassAndName {
		public Class<?> cls; // 类对象
		public String name;

		/** 构造方法 */
		public ClassAndName(Class<?> cls, String name) {
			this.cls = cls;
			this.name = name;
		}

		/** 打印类时自动调用toString()方法 */
		@Override
		public String toString() {
			return name;
		}
	}

}
