package com.intfocus.syptemplatev1.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * Created by zbaoliang on 17-6-15.
 */
public class RootScrollView extends ScrollView {
    private OnScrollListener onScrollListener;
    private int lastXIntercept;
    private int lastYIntercept;

    public RootScrollView(Context context) {
        super(context);
    }

    public RootScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.onScrollListener = onScrollListener;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (onScrollListener != null) {
            onScrollListener.onScroll(t);
        }
    }

    /**
     * 滚动的回调接口
     */
    public interface OnScrollListener {
        /**
         * 返回ScrollView滑动的Y方向距离
         *
         * @param scrollY
         */
        void onScroll(int scrollY);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        boolean intercepted = false;
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        switch (ev.getAction()) {
            /*如果拦截了Down事件,则子类不会拿到这个事件序列*/
            case MotionEvent.ACTION_DOWN:
                lastXIntercept = x;
                lastYIntercept = y;
                intercepted = false;
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = x - lastXIntercept;
                int deltaY = y - lastYIntercept;
                /*根据条件判断是否拦截该事件*/
                if (Math.abs(deltaX) > Math.abs(deltaY)) {
                    intercepted = true;
                } else {
                    intercepted = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                intercepted = false;
                break;

        }
        lastXIntercept = x;
        lastYIntercept = y;
        requestDisallowInterceptTouchEvent(intercepted);
//        return intercepted;
        return super.onInterceptTouchEvent(ev);
    }
}
