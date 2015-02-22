package com.example.viewpager;

import java.util.Currency;

import android.R.raw;
import android.content.Context;
import android.os.SystemClock;

/**
 * 计算位移距离的工具类
 * 
 * @author Administrator
 * 
 */
public class MyScroller {

	private int startX;
	private int startY;
	private int distanceX;
	private int distanceY;

	private long startTime;

	private boolean isFinish;

	public MyScroller(Context ctx) {

	}

	/**
	 * 开始移动
	 * 
	 * @param startX
	 *            开始时的x坐标
	 * @param startY
	 *            开始时的y坐标
	 * @param disX
	 *            x方向要移动的距离
	 * @param disY
	 *            y方向要移动的距离
	 */
	public void startScroll(int startX, int startY, int disX, int disY) {
		this.startX = startX;
		this.startY = startY;
		this.distanceX = disX;
		this.distanceY = disY;
		this.startTime = SystemClock.uptimeMillis();
		this.isFinish = false;
	}

	/**
	 * 默认运行的时间 毫秒值
	 */
	private int duration = 500;
	/**
	 * 当前的x值
	 */
	private long currx;

	private long currY;

	/**
	 * 计算一下当前的运行状况 返回值： true 代表还在运行 false 运行结束
	 */
	public boolean computeScrollOffset() {

		if (isFinish) {
			return false;
		} else {
			// 获得所用的时间
			long passtime = SystemClock.uptimeMillis() - startTime;
			// 如果时间HIA在允许 的范围内
			if (passtime < duration) {
				//当前的位置 = 开始的位置 + 移动的距离(距离 = 速度*时间)
				currx = startX + distanceX * passtime / duration;
				currY = startY + distanceY * passtime / duration;

			} else {
				currx = startX + distanceX;
				currY = startY + distanceY;
				isFinish = true;
			}
			// 计算位移
		}
		return true;
	}
	public long getCurrX(){
		return currx;
		
	}
	public void setCurrX(){
	this.currx =currx;
	}
}
