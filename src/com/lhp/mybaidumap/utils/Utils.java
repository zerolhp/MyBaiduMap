package com.lhp.mybaidumap.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

public class Utils {

	private static Toast toast;
	
	public static void showToast(Context context, String str) {
		toast = Toast.makeText(context, str, Toast.LENGTH_LONG);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}

	
}
