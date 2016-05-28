package com.lhp.mybaidumap.activity;

import java.util.List;

import com.baidu.mapapi.overlayutil.DrivingRouteOverlay;
import com.baidu.mapapi.overlayutil.TransitRouteOverlay;
import com.baidu.mapapi.overlayutil.WalkingRouteOverlay;
import com.baidu.mapapi.search.route.DrivingRouteLine;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteLine;
import com.baidu.mapapi.search.route.TransitRoutePlanOption;
import com.baidu.mapapi.search.route.TransitRoutePlanOption.TransitPolicy;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteLine;
import com.baidu.mapapi.search.route.WalkingRoutePlanOption;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.lhp.mybaidumap.utils.Utils;

import android.view.Menu;
import android.view.MenuItem;

/**
 * RouteActivity继承自BaseActivity，并实现OnGetRoutePlanResultListener。
 * 主要用于展示三种路线推荐方式。
 */

public class RouteActivity extends BaseActivity implements OnGetRoutePlanResultListener {

	private RoutePlanSearch routePlanSearch;
	private int item_id;

	@Override
	public void init() {
		setTitle("三种路线推荐方式");
		routePlanSearch = RoutePlanSearch.newInstance();
		routePlanSearch.setOnGetRoutePlanResultListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 1, 0, "行车路线推荐");
		menu.add(0, 2, 0, "换乘路线推荐");
		menu.add(0, 3, 0, "步行路线推荐");
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		item_id = item.getItemId();
		switch (item_id) {
		case 1: // 行车路线推荐
			baiduMap.clear(); // 清空覆盖物
			DrivingRoutePlanOption drivingOptions = new DrivingRoutePlanOption();
			drivingOptions.from(PlanNode.withCityNameAndPlaceName("南京", "洪蓝"));
			drivingOptions.to(PlanNode.withCityNameAndPlaceName("南京", "新街口"));
			routePlanSearch.drivingSearch(drivingOptions); // 回调onGetDrivingRouteResult()
			break;
		case 2: // 换乘路线推荐
			baiduMap.clear();
			TransitRoutePlanOption transitOptions = new TransitRoutePlanOption();
			transitOptions.city("南京"); // 所在城市
			transitOptions.policy(TransitPolicy.EBUS_TIME_FIRST); // 换乘策略：时间优先
			transitOptions.from(PlanNode.withCityNameAndPlaceName("南京", "小行"));
			transitOptions.to(PlanNode.withCityNameAndPlaceName("南京", "新街口"));
			routePlanSearch.transitSearch(transitOptions); // 回调onGetTransitRouteResult()
			break;
		case 3: // 步行路线推荐
			baiduMap.clear();
			WalkingRoutePlanOption walkOptions = new WalkingRoutePlanOption();
			walkOptions.from(PlanNode.withLocation(zyPos));
			walkOptions.to(PlanNode.withCityNameAndPlaceName("南京", "洪蓝"));
			routePlanSearch.walkingSearch(walkOptions); // 回调onGetWalkingRouteResult()
			break;
		default:
			break;
		}
		return true;
	}

	/** 获取行车路线 */
	@Override
	public void onGetDrivingRouteResult(DrivingRouteResult result) {
		DrivingRouteOverlay overlay = new DrivingRouteOverlay(baiduMap); // 创建行车路线覆盖物
		baiduMap.setOnMarkerClickListener(overlay); // 使该覆盖物可以响应点击事件
		List<DrivingRouteLine> routes = result.getRouteLines(); // 获取行车路线集合
		if (routes == null) { // null处理
			Utils.showToast(getApplicationContext(), "未获取路线");
		} else {
			overlay.setData(routes.get(0)); // 最优行车路线在最前面
			overlay.addToMap(); // 将覆盖物添加到Map
			overlay.zoomToSpan(); // 在屏幕范围内显示整体结果
		}
	}

	/** 获取换乘路线 */
	@Override
	public void onGetTransitRouteResult(TransitRouteResult result) {
		TransitRouteOverlay overlay = new TransitRouteOverlay(baiduMap);
		baiduMap.setOnMarkerClickListener(overlay); // 使该覆盖物可以响应点击事件
		List<TransitRouteLine> routes = result.getRouteLines(); // 获取行车路线集合
		if (routes == null) { 
			Utils.showToast(getApplicationContext(), "未获取路线");
		} else {
			overlay.setData(routes.get(0)); // 最优行车路线在最前面
			overlay.addToMap(); // 将覆盖物添加到Map
			overlay.zoomToSpan(); // 在屏幕范围内显示整体结果
		}
	}

	/** 获取步行路线 */
	@Override
	public void onGetWalkingRouteResult(WalkingRouteResult result) {
		WalkingRouteOverlay overlay = new WalkingRouteOverlay(baiduMap);
		baiduMap.setOnMarkerClickListener(overlay); // 使该覆盖物可以响应点击事件
		List<WalkingRouteLine> routes = result.getRouteLines(); // 获取行车路线集合
		if (routes == null) {
			Utils.showToast(getApplicationContext(), "未获取路线");
		} else {
			overlay.setData(routes.get(0)); // 最优行车路线在最前面
			overlay.addToMap(); // 将覆盖物添加到Map
			overlay.zoomToSpan(); // 在屏幕范围内显示整体结果
		}
	}

}
