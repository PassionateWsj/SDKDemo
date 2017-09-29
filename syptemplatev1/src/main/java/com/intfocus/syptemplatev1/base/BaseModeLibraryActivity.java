package com.intfocus.syptemplatev1.base;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.intfocus.syptemplatev1.utils.LoadingUtils;
import com.zbl.lib.baseframe.core.AbstractActivity;
import com.zbl.lib.baseframe.core.ActManager;
import com.zbl.lib.baseframe.core.Subject;

import com.intfocus.syptemplatev1.R;

/**
 * 基类界面
 */
public abstract class BaseModeLibraryActivity<T extends Subject> extends AbstractActivity<T> {
    protected Context ctx;

    /**
     * 布局根View
     */
    protected FrameLayout rootView;

    /**
     * Header的根布局
     */
    protected ViewStub stub_header;

    /**
     * 左边的TextView
     */
    protected TextView tv_left;
    protected TextView tv_title;
    protected LinearLayout topRightLayout;

    protected PopupWindow popupWindow;

    /**
     * 容器
     */
    protected FrameLayout fl_container;
    private Dialog loadingDialog;

    //TODO =====================================界面处理=====================================================

    @Override
    public int setLayoutRes() {
        return 0;
    }

    public boolean isFollowTheme() {
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().getDecorView();
        ctx = this.getApplicationContext();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frame_baseui);

        stub_header = (ViewStub) findViewById(R.id.ll_baseUI_title);

        rootView = (FrameLayout) findViewById(R.id.rootView);
//        progressBar = (ProgressBar) findViewById(R.id.progressBar_baseUI);

        int contentID = setLayoutRes();
        View content = null;
        if (contentID != 0 && contentID != -1) {
            content = LayoutInflater.from(this).inflate(contentID, null);
        }
        fl_container = (FrameLayout) findViewById(R.id.fl_baseUI_container);
        if (content != null) {
            fl_container.addView(content);
        }

        onCreateFinish(savedInstanceState);
    }

    /**
     * 实例化标题栏
     */
    public void initHeader() {
        stub_header.inflate();
        tv_left = (TextView) findViewById(R.id.tv_baseUI_back);
        tv_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBack();
            }
        });
        tv_title = (TextView) findViewById(R.id.tv_baseUI_title);
        topRightLayout = (LinearLayout) findViewById(R.id.ll_baseUI_title_RightView);
        topRightLayout.setVisibility(View.GONE);
    }

    /**
     * 返回键回掉方法
     */
    protected void onBack() {
        ActManager.getActManager().finishActivity();
    }
//
//    /**
//     * 显示菜单
//     *
//     * @param clickView
//     */
//    void showComplaintsPopWindow(View clickView) {
//        View contentView = LayoutInflater.from(this).inflate(R.layout.pop_menu_v2, null);
//        x.view().inject(this, contentView);
//        //设置弹出框的宽度和高度
//        popupWindow = new PopupWindow(contentView,
//                ViewGroup.LayoutParams.WRAP_CONTENT,
//                ViewGroup.LayoutParams.WRAP_CONTENT);
//        popupWindow.setFocusable(true);// 取得焦点
//        //注意  要是点击外部空白处弹框消息  那么必须给弹框设置一个背景色  不然是不起作用的
//        popupWindow.setBackgroundDrawable(new BitmapDrawable());
//        //点击外部消失
//        popupWindow.setOutsideTouchable(true);
//        //设置可以点击
//        popupWindow.setTouchable(true);
//        popupWindow.showAsDropDown(clickView);
//    }

    /**
     * 设置标题栏右边TextView的文字
     *
     * @param src
     */
    public void setACTitle(String src) {
        tv_title.setText(src);
    }

    //TODO -------------------设置返回键状态------------------------------

    /**
     * 设置标题栏右边TextView的图片
     *
     * @param drawable
     */
    public void setLeftTVSrc(int drawable) {
        Drawable nav_up = getResources().getDrawable(drawable);
        nav_up.setBounds(0, 0, nav_up.getMinimumWidth(),
                nav_up.getMinimumHeight());
        tv_left.setCompoundDrawables(nav_up, null, null, null);
    }

    /**
     * 设置标题栏右边TextView的文字
     *
     * @param src
     */
    public void setLeftTVSrc(String src) {
        tv_left.setText(src);
    }

    /**
     * @return 取得标题栏右边的TextView
     */
    public TextView getLeftTV() {
        return tv_left;
    }
    //TODO -------------------设置返回键状态------------------------------

    protected void showDialog(Context context) {
        loadingDialog = LoadingUtils.createLoadingDialog(context, false);
        loadingDialog.show();
    }

    protected void hideLoading() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        rootView.removeAllViews();
    }
}
