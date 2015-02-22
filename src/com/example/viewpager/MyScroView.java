package com.example.viewpager;

import android.R.integer;
import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

public class MyScroView extends ViewGroup {

	private Context ctx;

	/**
	 * 判断是否为快速滑动
	 */
	private boolean isFling = true;

	public MyScroView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.ctx = context;
		initView();
	}

	private void initView() {

		msMyScroller = new Scroller(ctx);
		detector = new GestureDetector(ctx, new OnGestureListener() {

			@Override
			public boolean onSingleTapUp(MotionEvent e) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public void onShowPress(MotionEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			/**
			 * 响应手指在屏幕上的滑动事件
			 */
			public boolean onScroll(MotionEvent e1, MotionEvent e2,
					float distanceX, float distanceY) {
				// 移动屏幕
				System.out.println("distanceX" + distanceX);
				/**
				 * 移动当前view的内容 移动一段距离 disx x方向移动的距离 为正是，图片向左移动，为负时，图片向右移动 disy
				 * y方向移动的距离
				 */
				scrollBy((int) distanceX, 0);

				/**
				 * 将当前视图的基准点移动的到某个点 坐标点 x 水平方向x坐标 y 竖直方向y坐标
				 * 
				 * scrollTo(x, y);
				 */
				return false;
			}

			@Override
			public void onLongPress(MotionEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			/**
			 * 发生快速滑动时的回调
			 */
			public boolean onFling(MotionEvent e1, MotionEvent e2,
					float velocityX, float velocityY) {
				isFling = true;
				if (velocityX > 0 && currId > 0) {// 快速向右滑动

					currId--;
				} else if (velocityX < 0 && currId < getChildCount() - 1) {
					// 快速向左滑动
					currId++;
				}
				moveToDest(currId);
				return false;
			}

			@Override
			public boolean onDown(MotionEvent e) {
				// TODO Auto-generated method stub
				return false;
			}
		});
	}

	@Override
	/**
	 * 计算控件的大小
	 * 作为Viewgroup还有一个责任，计算子view的大小
	 */
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		for (int i = 0; i < getChildCount(); i++) {
			View v = getChildAt(i);
			v.measure(widthMeasureSpec, heightMeasureSpec);
			
//			v.getMeasuredWidth()测量view大小
		}
	}

	@Override
	/**
	 * 对子view进行布局，确定子view的位置
	 * changed 若为true，说明布局发生了变化
	 * l\t\r\b是指当前viewgroup在其父View中的位置
	 * 
	 */
	protected void onLayout(boolean changed, int l, int t, int r, int b) {

		for (int i = 0; i < getChildCount(); i++) {

			View view = getChildAt(i);// 依次取得下标为i的子view
			/**
			 * 父view会根据子view的需求，和自身的情况，来综合确定子view的位置(区分他的大小)
			 * 
			 */
			// 指定子view的位置,左，上，右。下,在viewGrond坐标系中的位置
			view.layout(0 + i * getWidth(), 0, getWidth() + i * getWidth(),
					getHeight());
//			view.getWidth();//得到view的真实的大小
		}
	}

	/**
	 * 手势识别工具类
	 */
	private GestureDetector detector;
	/**
	 * 当前的id值 显示在屏幕上的ImageView的下标
	 */
	private int currId = 0;
	/*
	 * down事件的坐标
	 */

	private int firstX = 0;

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		super.onTouchEvent(event);

		detector.onTouchEvent(event);

		// 添加自己的事件解析
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			firstX = (int) event.getX();

			break;
		case MotionEvent.ACTION_MOVE:

			break;
		case MotionEvent.ACTION_UP:
			if (!isFling) {// 在没有发生快速滑动的时候，才执行按位置判断currid

				// 当手指从按下到离开的距离>屏幕的一半
				int nextid = 0;
				if (event.getX() - firstX > getWidth() / 2) {// 手指向右滑动，超过屏幕的1/2，当前的currId-1
					nextid = currId - 1;
				} else if (firstX - event.getX() > getWidth() / 2) {// 手指向坐标滑。超过屏幕的1/2，当前的currId+1
					nextid = currId + 1;
				} else {
					nextid = currId;
				}

				moveToDest(nextid);
				// scrollTo(0, 0);//一松手，就回到原点
			}
			isFling = false;
			break;
		}
		return true;
	}

	/**
	 * 计算位移的工具类
	 */
	// private MyScroller msMyScroller;
	private Scroller msMyScroller;

	/**
	 * 移动到指定的屏幕上
	 * 
	 * @param nextid
	 *            屏幕的下标
	 */
	public void moveToDest(int nextid) {

		/**
		 * 对nextId进行判断，确保是在合理的范围 即nextId >=0 && next <=getChindCount() -1
		 */
		// 确保currid大于等于0
		currId = (nextid >= 0) ? nextid : 0;
		// 确保currId <= getChildCount()-1
		currId = (nextid <= getChildCount() - 1) ? nextid
				: (getChildCount() - 1);

		// 瞬间移动
		// scrollTo(currId * getWidth(), 0);

		// 瞬间移动，触发listener事件
		if (pChangeListener != null) {
			pChangeListener.moveToDest(currId);
		}

		int distance = currId * getWidth() - getScrollX();// 最终的位置 -
															// 现在的位置，等于要移动的距离
															// msMyScroller.startScroll(getScrollX(),
															// 0, distance, 0);
		msMyScroller.startScroll(getScrollX(), 0, distance, 0,
				Math.abs(distance));
		/**
		 * 刷新当前view,onDraw方法的执行
		 */
		invalidate();
	}

	/**
	 * invalidate(),会导致computeScroll（）这个方法执行
	 */
	@Override
	public void computeScroll() {
		if (msMyScroller.computeScrollOffset()) {
			int newX = (int) msMyScroller.getCurrX();
			System.out.println("newx" + newX);
			scrollTo(newX, 0);
			invalidate();
		}
	}

	public MypageChangeListener getpChangeListener() {
		return pChangeListener;
	}

	public void setpChangeListener(MypageChangeListener pChangeListener) {
		this.pChangeListener = pChangeListener;
	}

	private MypageChangeListener pChangeListener;

	/**
	 * 页面改变时的监听接口
	 * 
	 * @author Administrator
	 * 
	 */
	public interface MypageChangeListener {

		void moveToDest(int currid);
	}
}
