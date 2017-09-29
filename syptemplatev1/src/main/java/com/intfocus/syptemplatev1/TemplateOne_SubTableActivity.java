package com.intfocus.syptemplatev1;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.transition.Slide;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.intfocus.syptemplatev1.entity.DataHolder;
import com.zbl.lib.baseframe.utils.StringUtil;

/**
 * 子Table页面
 */
public class TemplateOne_SubTableActivity extends AppCompatActivity {

    private TextView title;

    private FrameLayout fl_container;
    private String subData;

    private ImageButton imgBtn_Cols;
    public int suRootID;

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modular_two_subtable);
        getSupportActionBar().hide();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setEnterTransition(new Slide().setDuration(500));
            getWindow().setExitTransition(new Slide().setDuration(500));
        }

        title = (TextView) findViewById(R.id.tv_subtable_name);
        fl_container = (FrameLayout) findViewById(R.id.fl_subtable_container);
        imgBtn_Cols = (ImageButton) findViewById(R.id.imgBtn_ColsPopupWindow_bannerInfo);
        imgBtn_Cols.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.finishAfterTransition(TemplateOne_SubTableActivity.this);
            }
        });

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String titel = bundle.getString("Title");
//            subData = bundle.getString("Data");
            subData = DataHolder.getInstance().getData();
            suRootID = bundle.getInt("suRootID");
            title.setText(titel);
            if (StringUtil.isEmpty(subData))
                finish();

            TemplateOne_UnitTablesContModeLibraryFragment toFragment = TemplateOne_UnitTablesContModeLibraryFragment.newInstance(suRootID, subData);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.replace(R.id.fl_subtable_container, toFragment);
            ft.commit();
        }
    }
}
