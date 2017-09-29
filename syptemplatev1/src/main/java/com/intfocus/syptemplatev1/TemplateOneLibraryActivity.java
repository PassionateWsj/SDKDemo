package com.intfocus.syptemplatev1;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.intfocus.syptemplatev1.base.BaseModeLibraryActivity;
import com.intfocus.syptemplatev1.base.BaseModeLibraryFragment;
import com.intfocus.syptemplatev1.entity.msg.EventRefreshTableRect;
import com.intfocus.syptemplatev1.entity.msg.MDetalActRequestResult;
import com.intfocus.syptemplatev1.mode.MeterDetalActMode;
import com.intfocus.syptemplatev1.utils.DisplayUtil;
import com.intfocus.syptemplatev1.view.RootScrollView;
import com.zbl.lib.baseframe.core.Subject;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 模块一页面
 */
public class TemplateOneLibraryActivity extends BaseModeLibraryActivity<MeterDetalActMode> {
    private String TAG = TemplateOneLibraryActivity.class.getSimpleName();
    private static final String fragmentTag = "android:switcher:" + R.layout.actvity_meter_detal + ":";

    public RootScrollView rScrollView;

    public FrameLayout suspendContainer;
    public int suspendRootId;
    public ViewStub actionbar;

    private FragmentManager fm;
    private FragmentTransaction ft;
    public static int lastCheckId;
    private BaseModeLibraryFragment currFragment;
    private BaseModeLibraryFragment toFragment;
    private String currentFtName;

    private String itemsString;
    private String bannerName;

    private FrameLayout fl_titleContainer;

    private RadioGroup radioGroup;
    private RootTableCheckedChangeListener rootTableListener;

    private TextView tv_single_title;

    private Context mContext;
    /**
     * 数据实体
     */
    private MDetalActRequestResult entity;

    @Override
    public int setLayoutRes() {
        return R.layout.actvity_meter_detal;
    }

    @Override
    public Subject setSubject() {
        EventBus.getDefault().register(this);
        return new MeterDetalActMode(ctx);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onCreateFinish(Bundle bundle) {
        initHeader();
        actionbar = stub_header;
        getSupportActionBar().hide();
        fm = getSupportFragmentManager();
        rScrollView = (RootScrollView) findViewById(R.id.rootScrollView);
        suspendContainer = (FrameLayout) findViewById(R.id.fl_mdetal_top_suspend_container);
        fl_titleContainer = (FrameLayout) findViewById(R.id.fl_mdetal_title_container);

        mContext = this;
        init();
    }

    private void init() {
        Intent intent = getIntent();
        itemsString = intent.getStringExtra("itemsString");
        bannerName = intent.getStringExtra("bannerName");
        rootTableListener = new RootTableCheckedChangeListener();
        setACTitle("标题");
        showDialog(mContext);
        getModel().requestData();
    }

    /**
     * 切换页面的重载，优化了fragment的切换
     */
    public void switchFragment(int checkId) {
        lastCheckId = checkId;
        currentFtName = fragmentTag + checkId;
        toFragment = (BaseModeLibraryFragment) getSupportFragmentManager().findFragmentByTag(currentFtName);

        if (currFragment != null && currFragment == toFragment)
            return;

        if (toFragment == null)
            toFragment = TemplateOne_RootPageModeLibraryFragment.newInstance(checkId, entity.datas.data.get(checkId).parts);

        FragmentTransaction ft = fm.beginTransaction();
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.replace(R.id.fl_mdetal_cont_container, toFragment, currentFtName).commitAllowingStateLoss();
        currFragment = toFragment;
        EventBus.getDefault().post(new EventRefreshTableRect(checkId));
    }

    /**
     * 图表点击事件统一处理方法
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MDetalActRequestResult entity) {
        this.entity = entity;
        setACTitle(bannerName);
        if (entity != null) {
            int dataSize = entity.datas.data.size();
            if (dataSize > 1) {    // 多个根页签
                View scroll_title = LayoutInflater.from(ctx)
                        .inflate(R.layout.item_mdetal_scroll_title, null);
                fl_titleContainer.addView(scroll_title);
                radioGroup = (RadioGroup) scroll_title.findViewById(R.id.radioGroup);

                for (int i = 0; i < dataSize; i++) {
                    RadioButton rbtn = new RadioButton(this);
                    RadioGroup.LayoutParams params_rb = new RadioGroup.LayoutParams(
                            RadioGroup.LayoutParams.WRAP_CONTENT,
                            DisplayUtil.dip2px(ctx, 25f));
                    params_rb.setMargins(50, 0, 0, 0);

                    rbtn.setTag(i);
                    rbtn.setPadding(DisplayUtil.dip2px(ctx, 15f), 0, DisplayUtil.dip2px(ctx, 15f), 0);
                    Bitmap a = null;
                    rbtn.setButtonDrawable(new BitmapDrawable(a));
                    rbtn.setBackgroundResource(R.drawable.selector_mdetal_act_rbtn);
                    rbtn.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.font_medium));
                    ColorStateList colorStateList = getResources().getColorStateList(R.color.color_mdetal_act_rbtn);
                    rbtn.setTextColor(colorStateList);
                    rbtn.setText(entity.datas.data.get(i).title);
                    radioGroup.addView(rbtn, params_rb);
                    rbtn.setOnCheckedChangeListener(rootTableListener);
                    if (i == 0)
                        rbtn.setChecked(true);
                }
            } else if (dataSize == 1) {    // 只有一个根页签
                fl_titleContainer.setVisibility(View.GONE);
                switchFragment(0);
            }
        } else
            Toast.makeText(ctx, "数据实体为空", Toast.LENGTH_SHORT).show();
        hideLoading();
    }

    class RootTableCheckedChangeListener implements RadioButton.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                int tag = (Integer) buttonView.getTag();
                switchFragment(tag);
            }
        }
    }

    /**
     * 刷新
     * @param v
     */
    public void refresh(View v) {
        getModel().requestData(itemsString);
    }
}
