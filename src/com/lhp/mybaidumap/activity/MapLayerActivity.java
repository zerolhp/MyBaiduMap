package com.lhp.mybaidumap.activity;

import com.baidu.mapapi.map.BaiduMap;
import com.lhp.mybaidumap.utils.Utils;

import android.view.Menu;
import android.view.MenuItem;

/**
 * MapLayerActivity继承自BaseActivity，主要用于展示三种地图图层。
 */

public class MapLayerActivity extends BaseActivity {

	private int item_id;

	@Override
	public void init() {
		setTitle("切换地图的三种图层"); // 设置Activity标题
	}

	/** 添加菜单项 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 1, 0, "普通地图");
		menu.add(0, 2, 0, "卫星地图");
		menu.add(0, 3, 0, "交通地图");
		return true;
	}

	/** 处理菜单项 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		item_id = item.getItemId();
		switch (item_id) {
		case 1:
			baiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL); // 设置地图的种类为普通
			baiduMap.setTrafficEnabled(false); // 不显示交通图层
			Utils.showToast(this, "普通图层");
			break;
		case 2:
			baiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE); // 设置地图的种类为卫星
			baiduMap.setTrafficEnabled(false); // 不显示交通图层
			Utils.showToast(this, "卫星图层");
			break;
		case 3:
			baiduMap.setTrafficEnabled(true); // 显示交通图层
			Utils.showToast(this, "交通图层");
			break;
		default:
			break;
		}
		return true;
	}
}
