package com.intfocus.syptemplatev1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.intfocus.syptemplatev1.base.BaseModeLibraryFragment;
import com.intfocus.syptemplatev1.entity.MDRPUnitSingleValue;
import com.intfocus.syptemplatev1.view.RateCursor;

import java.text.DecimalFormat;

/**
 * 单值组件
 */
public class TemplateOne_UnitSingleValueModeLibraryFragment extends BaseModeLibraryFragment {
    private static final String ARG_PARAM1 = "SingleValueParam";
    public static String mCurrentParam;
    private String mParam1;
    private int showCount = 0;

    private View rootView;

    private TextView tv_d1;
    private TextView tv_d1name;
    private TextView tv_d2;
    private TextView tv_d2name;
    private TextView tv_rate;

    RateCursor rateCursor;

    int[] coCursor;

    private String diffValue;
    private String diffRate;
    private float mainValue;

    public TemplateOne_UnitSingleValueModeLibraryFragment() {
    }

    public static TemplateOne_UnitSingleValueModeLibraryFragment newInstance(String param1) {
        TemplateOne_UnitSingleValueModeLibraryFragment fragment = new TemplateOne_UnitSingleValueModeLibraryFragment();
        mCurrentParam = param1;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mParam1 = mCurrentParam;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_singlevalue, container, false);
            tv_d1 = (TextView) rootView.findViewById(R.id.tv_singlevalue_d1);
            tv_d1name = (TextView) rootView.findViewById(R.id.tv_singlevalue_d1name);
            tv_d2 = (TextView) rootView.findViewById(R.id.tv_singlevalue_d2);
            tv_d2name = (TextView) rootView.findViewById(R.id.tv_singlevalue_d2name);
            tv_rate = (TextView) rootView.findViewById(R.id.tv_singlevalue_ratio);
            rateCursor = (RateCursor) rootView.findViewById(R.id.img_singlevalue_ratiocursor);
            bindData();
        }


        tv_rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (showCount) {
                    case 0:
                        tv_rate.setText(diffValue);
                        break;

                    case 1:
                        tv_rate.setText(diffRate);
                        break;

                    case 2:
                        tv_rate.setText(mainValue + "");
                        showCount = -1;
                        break;

                    default:
                        tv_rate.setText(mainValue + "");
                        break;
                }

                showCount++;
            }
        });
        return rootView;
    }

    private void bindData() {
        coCursor = getResources().getIntArray(R.array.co_cursor);
        DecimalFormat df = new DecimalFormat("###,###.##");
        MDRPUnitSingleValue valueData = JSON.parseObject(mParam1, MDRPUnitSingleValue.class);
        int state = valueData.state.color;
        int color = coCursor[state];

        tv_d1name.setText(valueData.main_data.name);
        tv_d2name.setText(valueData.sub_data.name);
        mainValue = Float.parseFloat(valueData.main_data.data.replace("%", ""));
        tv_d1.setText(df.format(mainValue));
        float subdata = Float.parseFloat(valueData.sub_data.data.replace("%", ""));
        tv_d2.setText(df.format(subdata));

        tv_d1.setTextColor(color);
        tv_rate.setTextColor(color);
        float rate = (mainValue - subdata) / subdata;
        float diff = mainValue - subdata;
        diffValue = df.format(diff);
        diffRate = new DecimalFormat(".##%").format(rate);
        tv_rate.setText(mainValue + "");

        float absmv = Math.abs(rate);
        boolean isPlus;
//        int cursorIndex;
        if (absmv <= 0.1f) {
//            cursorIndex = 1;
            if (rate > 0)
                isPlus = false;
            else
                isPlus = true;
        } else if (rate < -0.1f) {
//            cursorIndex = 0;
            isPlus = false;
        } else {
//            cursorIndex = 2;
            isPlus = true;
        }
        rateCursor.setCursorState(state, isPlus);
    }
}
