package com.example.viewpager;

import java.util.Currency;

import android.R.raw;
import android.content.Context;
import android.os.SystemClock;

/**
 * ����λ�ƾ���Ĺ�����
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
	 * ��ʼ�ƶ�
	 * 
	 * @param startX
	 *            ��ʼʱ��x����
	 * @param startY
	 *            ��ʼʱ��y����
	 * @param disX
	 *            x����Ҫ�ƶ��ľ���
	 * @param disY
	 *            y����Ҫ�ƶ��ľ���
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
	 * Ĭ�����е�ʱ�� ����ֵ
	 */
	private int duration = 500;
	/**
	 * ��ǰ��xֵ
	 */
	private long currx;

	private long currY;

	/**
	 * ����һ�µ�ǰ������״�� ����ֵ�� true ���������� false ���н���
	 */
	public boolean computeScrollOffset() {

		if (isFinish) {
			return false;
		} else {
			// ������õ�ʱ��
			long passtime = SystemClock.uptimeMillis() - startTime;
			// ���ʱ��HIA������ �ķ�Χ��
			if (passtime < duration) {
				//��ǰ��λ�� = ��ʼ��λ�� + �ƶ��ľ���(���� = �ٶ�*ʱ��)
				currx = startX + distanceX * passtime / duration;
				currY = startY + distanceY * passtime / duration;

			} else {
				currx = startX + distanceX;
				currY = startY + distanceY;
				isFinish = true;
			}
			// ����λ��
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
