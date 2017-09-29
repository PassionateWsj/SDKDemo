package com.intfocus.syptemplatev1.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.HorizontalScrollView;

/**
 * 表格用水平滑动ScrollView
 * Created by zbaoliang on 17-5-16.
 */
public class TableHorizontalScrollView extends HorizontalScrollView {

    View mView;

    public TableHorizontalScrollView(Context context, AttributeSet attrs,
                                     int defStyle) {
        super(context, attrs, defStyle);
    }

    public TableHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TableHorizontalScrollView(Context context) {
        super(context);
    }

    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mView != null) {
            mView.scrollTo(l, t);
        }
    }

    /**
     * 设置关联View
     *
     * @param view
     */
    public void setScrollView(View view) {
        mView = view;
    }
}
