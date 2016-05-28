package com.lhp.mybaidumap.activity;

import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.CircleOptions;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.TextOptions;
import com.lhp.mybaidumap.R;
import com.lhp.mybaidumap.R.drawable;

import android.view.Menu;
import android.view.MenuItem;

/**
 * OverlayActivity继承自BaseActivity，主要用于显示三种位置指示方式。
 */

public class OverlayActivity extends BaseActivity {

	private int item_id;

	@Override
	public void init() {
		setTitle("三种位置指示方式"); // 设置Activity标题
	}

	/** 添加菜单项 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 1, 0, "圆形指示");
		menu.add(0, 2, 0, "文字指示");
		menu.add(0, 3, 0, "图标指示");
		return true;
	}

	/** 处理菜单项 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		item_id = item.getItemId();
		switch (item_id) {
		case 1: // 圆形指示
			CircleOptions circleOptions = new CircleOptions(); // 圆形覆盖物参数对象
			circleOptions.center(zyPos) // 圆心
					.radius(1000) // 半径
					// .stroke(new Stroke(8, 0x00FF0000)) // 描边：宽度，颜色
					.fillColor(0x2200FF00); // 填充色
			// 向地图对象添加该圆形覆盖物
			baiduMap.addOverlay(circleOptions);
			break;
		case 2: // 文字指示
			TextOptions textOptions = new TextOptions(); // 文本覆盖物参数对象
			textOptions.position(zyPos) // 对应坐标
					.text("张晔家") // 文本
					.fontSize(50) // 字体大小
					.fontColor(0xFFE06666) // 字体颜色
					.bgColor(0x996D9EEB); // 背景颜色
			// .rotate(30) // 旋转角度
			baiduMap.addOverlay(textOptions); // 添加到覆盖层
			break;
		case 3: // 图标指示
			MarkerOptions markerOptions = new MarkerOptions();
			BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.icon_geo);
			markerOptions.position(zyPos) // 位置
						 .title("张晔家") // 标题
						 .icon(icon) // 图标
						 .draggable(true); // 可拖动
			baiduMap.addOverlay(markerOptions);
			break;
		default:
			break;
		}
		return true;
	}

}
