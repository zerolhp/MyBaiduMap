package com.lhp.mybaidumap.activity;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.baidu.mapapi.overlayutil.PoiOverlay;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiBoundSearchOption;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.lhp.mybaidumap.utils.Utils;

import android.view.Menu;
import android.view.MenuItem;

/**
 * 	SearchActivity继承自BaseActivity，并实现OnGetRoutePlanResultListener。
 * 	主要用于展示三种地图搜索方式。
 */

public class SearchActivity extends BaseActivity implements OnGetPoiSearchResultListener {

	protected PoiSearch poiSearch;
	private int item_id;

	@Override
	public void init() {
		setTitle("三种地图搜索方式");
		// 创建搜索对象
		poiSearch = PoiSearch.newInstance(); // 单例模式
		// 为搜索对象设置获取位置信息的监听器
		poiSearch.setOnGetPoiSearchResultListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 1, 0, "指定坐标区域搜索");
		menu.add(0, 2, 0, "指定城市（南京）搜索");
		menu.add(0, 3, 0, "指定附件距离(5KM)搜索");
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		item_id = item.getItemId();
		switch (item_id) {
		case 1: // 在指定坐标区域内搜索
			PoiBoundSearchOption boundOptions = new PoiBoundSearchOption();
			// 坐标区域有一个西南角和一个东北角确定
			LatLngBounds bounds = new LatLngBounds.Builder().include(new LatLng(31.4250, 118.8078))
					.include(new LatLng(31.8250, 119.2078)).build();
			// 往参数中添加搜索区域
			boundOptions.bound(bounds);
			// 往参数中添加搜索内容
			boundOptions.keyword("中国银行");
			// 调用searchInBound()方法来触发onGetPoiResult()
			poiSearch.searchInBound(boundOptions);
			break;
		case 2: // 城市（南京）搜索
			PoiCitySearchOption cityOptions = new PoiCitySearchOption();
			cityOptions.city("南京");
			cityOptions.keyword("中国银行");
			cityOptions.pageCapacity(10); // 一页显示10条数据
			// cityParams.pageNum(pageNum); // 获取第几页的数据
			poiSearch.searchInCity(cityOptions);
			break;
		case 3: // 附件5KM搜索
			PoiNearbySearchOption nearbyOptions = new PoiNearbySearchOption();
			nearbyOptions.location(zyPos); 
			nearbyOptions.radius(5000);
			nearbyOptions.keyword("中国银行");
			poiSearch.searchNearby(nearbyOptions);
			break;
		default:
			break;
		}
		return true;
	}

	/** 获取POI兴趣点的信息并显示 */
	@Override
	public void onGetPoiResult(PoiResult result) {
		// 异常提示
		if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
			Utils.showToast(this, "未搜索到结果");
		}
		// 创建位置覆盖物及其点击事件处理
		PoiOverlay poiOverlay = new PoiOverlay(baiduMap) {
			@Override
			public boolean onPoiClick(int index) {
				// 获取位置信息
				PoiInfo poiInfo = getPoiResult().getAllPoi().get(index);
				// 显示地名和地址
				Utils.showToast(getApplicationContext(), poiInfo.name + "\n" + poiInfo.address);
				return true;
			}
		};
		baiduMap.setOnMarkerClickListener(poiOverlay); // 设置地图覆盖物的点击监听事件
		poiOverlay.setData(result); // 往位置覆盖物对象添加数据
		poiOverlay.addToMap(); // 将位置覆盖物添加到Map中去
		poiOverlay.zoomToSpan(); // 将位置覆盖物的信息在一个屏幕内显示
	}

	/** 获取POI兴趣点的详细信息 */
	@Override
	public void onGetPoiDetailResult(PoiDetailResult arg0) {
		// TODO Auto-generated method stub
	}

}
