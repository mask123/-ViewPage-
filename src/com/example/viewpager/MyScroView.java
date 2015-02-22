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
	 * �ж��Ƿ�Ϊ���ٻ���
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
			 * ��Ӧ��ָ����Ļ�ϵĻ����¼�
			 */
			public boolean onScroll(MotionEvent e1, MotionEvent e2,
					float distanceX, float distanceY) {
				// �ƶ���Ļ
				System.out.println("distanceX" + distanceX);
				/**
				 * �ƶ���ǰview������ �ƶ�һ�ξ��� disx x�����ƶ��ľ��� Ϊ���ǣ�ͼƬ�����ƶ���Ϊ��ʱ��ͼƬ�����ƶ� disy
				 * y�����ƶ��ľ���
				 */
				scrollBy((int) distanceX, 0);

				/**
				 * ����ǰ��ͼ�Ļ�׼���ƶ��ĵ�ĳ���� ����� x ˮƽ����x���� y ��ֱ����y����
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
			 * �������ٻ���ʱ�Ļص�
			 */
			public boolean onFling(MotionEvent e1, MotionEvent e2,
					float velocityX, float velocityY) {
				isFling = true;
				if (velocityX > 0 && currId > 0) {// �������һ���

					currId--;
				} else if (velocityX < 0 && currId < getChildCount() - 1) {
					// �������󻬶�
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
	 * ����ؼ��Ĵ�С
	 * ��ΪViewgroup����һ�����Σ�������view�Ĵ�С
	 */
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		for (int i = 0; i < getChildCount(); i++) {
			View v = getChildAt(i);
			v.measure(widthMeasureSpec, heightMeasureSpec);
			
//			v.getMeasuredWidth()����view��С
		}
	}

	@Override
	/**
	 * ����view���в��֣�ȷ����view��λ��
	 * changed ��Ϊtrue��˵�����ַ����˱仯
	 * l\t\r\b��ָ��ǰviewgroup���丸View�е�λ��
	 * 
	 */
	protected void onLayout(boolean changed, int l, int t, int r, int b) {

		for (int i = 0; i < getChildCount(); i++) {

			View view = getChildAt(i);// ����ȡ���±�Ϊi����view
			/**
			 * ��view�������view�����󣬺��������������ۺ�ȷ����view��λ��(�������Ĵ�С)
			 * 
			 */
			// ָ����view��λ��,���ϣ��ҡ���,��viewGrond����ϵ�е�λ��
			view.layout(0 + i * getWidth(), 0, getWidth() + i * getWidth(),
					getHeight());
//			view.getWidth();//�õ�view����ʵ�Ĵ�С
		}
	}

	/**
	 * ����ʶ�𹤾���
	 */
	private GestureDetector detector;
	/**
	 * ��ǰ��idֵ ��ʾ����Ļ�ϵ�ImageView���±�
	 */
	private int currId = 0;
	/*
	 * down�¼�������
	 */

	private int firstX = 0;

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		super.onTouchEvent(event);

		detector.onTouchEvent(event);

		// ����Լ����¼�����
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			firstX = (int) event.getX();

			break;
		case MotionEvent.ACTION_MOVE:

			break;
		case MotionEvent.ACTION_UP:
			if (!isFling) {// ��û�з������ٻ�����ʱ�򣬲�ִ�а�λ���ж�currid

				// ����ָ�Ӱ��µ��뿪�ľ���>��Ļ��һ��
				int nextid = 0;
				if (event.getX() - firstX > getWidth() / 2) {// ��ָ���һ�����������Ļ��1/2����ǰ��currId-1
					nextid = currId - 1;
				} else if (firstX - event.getX() > getWidth() / 2) {// ��ָ�����껬��������Ļ��1/2����ǰ��currId+1
					nextid = currId + 1;
				} else {
					nextid = currId;
				}

				moveToDest(nextid);
				// scrollTo(0, 0);//һ���֣��ͻص�ԭ��
			}
			isFling = false;
			break;
		}
		return true;
	}

	/**
	 * ����λ�ƵĹ�����
	 */
	// private MyScroller msMyScroller;
	private Scroller msMyScroller;

	/**
	 * �ƶ���ָ������Ļ��
	 * 
	 * @param nextid
	 *            ��Ļ���±�
	 */
	public void moveToDest(int nextid) {

		/**
		 * ��nextId�����жϣ�ȷ�����ں���ķ�Χ ��nextId >=0 && next <=getChindCount() -1
		 */
		// ȷ��currid���ڵ���0
		currId = (nextid >= 0) ? nextid : 0;
		// ȷ��currId <= getChildCount()-1
		currId = (nextid <= getChildCount() - 1) ? nextid
				: (getChildCount() - 1);

		// ˲���ƶ�
		// scrollTo(currId * getWidth(), 0);

		// ˲���ƶ�������listener�¼�
		if (pChangeListener != null) {
			pChangeListener.moveToDest(currId);
		}

		int distance = currId * getWidth() - getScrollX();// ���յ�λ�� -
															// ���ڵ�λ�ã�����Ҫ�ƶ��ľ���
															// msMyScroller.startScroll(getScrollX(),
															// 0, distance, 0);
		msMyScroller.startScroll(getScrollX(), 0, distance, 0,
				Math.abs(distance));
		/**
		 * ˢ�µ�ǰview,onDraw������ִ��
		 */
		invalidate();
	}

	/**
	 * invalidate(),�ᵼ��computeScroll�����������ִ��
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
	 * ҳ��ı�ʱ�ļ����ӿ�
	 * 
	 * @author Administrator
	 * 
	 */
	public interface MypageChangeListener {

		void moveToDest(int currid);
	}
}
