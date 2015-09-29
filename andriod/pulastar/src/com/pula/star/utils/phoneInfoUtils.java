package com.pula.star.utils;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.res.Configuration;
import android.util.DisplayMetrics;



public class phoneInfoUtils {
	/**
	 * 判断是否是手机
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isPhone(Activity context) {
		return ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) < Configuration.SCREENLAYOUT_SIZE_LARGE) || getPhysicalSize(context) < 6;
	}

	/**
	 * 获取物理尺寸
	 * 
	 * @param context
	 * @return
	 */
	public static double getPhysicalSize(Activity context) {
		DisplayMetrics dm = getScreenSize(context);
		double diagonalPixels = Math.sqrt(Math.pow(dm.widthPixels, 2) + Math.pow(dm.heightPixels, 2));
		return diagonalPixels / (160 * dm.density);
	}

	/**
	 * 获取屏幕尺寸
	 * 
	 * @param context
	 * @return
	 */
	public static DisplayMetrics getScreenSize(Activity context) {
		DisplayMetrics dm = new DisplayMetrics();
		context.getWindowManager().getDefaultDisplay().getMetrics(dm);
		return dm;
	}

	
}
