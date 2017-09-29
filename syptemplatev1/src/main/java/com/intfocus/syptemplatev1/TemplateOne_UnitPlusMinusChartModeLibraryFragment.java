package com.intfocus.syptemplatev1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.intfocus.syptemplatev1.adapter.BargraptAdapter;
import com.intfocus.syptemplatev1.base.BaseModeLibraryFragment;
import com.intfocus.syptemplatev1.entity.BargraphComparator;
import com.intfocus.syptemplatev1.entity.MDRPUnitBargraph;
import com.intfocus.syptemplatev1.utils.BargraphDataComparator;
import com.intfocus.syptemplatev1.utils.PinyinUtil;
import com.intfocus.syptemplatev1.view.NotScrollListView;
import com.intfocus.syptemplatev1.view.PlusMinusChart;
import com.intfocus.syptemplatev1.view.SortCheckBox;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

/**
 * 正负图表模块
 */
public class TemplateOne_UnitPlusMinusChartModeLibraryFragment extends BaseModeLibraryFragment implements AdapterView.OnItemClickListener {
    private static final String ARG_PARAM1 = "param1";
    public static String mCurrentParam;
    private View rootView;

    private NotScrollListView lv;
    private BargraptAdapter adapter;
    private FrameLayout fl_container;

    private SortCheckBox cbox_name;
    private SortCheckBox cbox_percentage;

    private PlusMinusChart pmChart;

    private String mParam1;
    private MDRPUnitBargraph entityData;
    private LinkedList<BargraphComparator> lt_data;
    private BargraphNameComparator nameComparator;
    private BargraphDataComparator dataComparator;

    public static TemplateOne_UnitPlusMinusChartModeLibraryFragment newInstance(String param) {
        TemplateOne_UnitPlusMinusChartModeLibraryFragment fragment = new TemplateOne_UnitPlusMinusChartModeLibraryFragment();
        mCurrentParam = param;
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
            rootView = inflater.inflate(R.layout.fragment_mdrpunit_plus_minus_chart, container, false);
            lv = (NotScrollListView) rootView.findViewById(R.id.lv_MDRPUnit_PlusMinusChart);
            fl_container = (FrameLayout) rootView.findViewById(R.id.fl_MDRPUnit_PlusMinusChart_container);
            cbox_name = (SortCheckBox) rootView.findViewById(R.id.cbox_name);
            cbox_percentage = (SortCheckBox) rootView.findViewById(R.id.cbox_percentage);
            initView();
            bindData();
        }


        cbox_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cbox_percentage.reset();
                if (cbox_name.getCheckedState() == SortCheckBox.CheckedState.sort_noneicon)
                    Collections.sort(lt_data, nameComparator);
                else
                    Collections.reverse(lt_data);

                adapter.updateData(lt_data);
                ArrayList<String> chartData = new ArrayList<>();
                for (BargraphComparator bargraphComparator : lt_data) {
                    chartData.add(bargraphComparator.data);
                }
                pmChart.updateData(lt_data);
            }
        });

        cbox_percentage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cbox_name.reset();
                if (cbox_percentage.getCheckedState() == SortCheckBox.CheckedState.sort_noneicon)
                    Collections.sort(lt_data, dataComparator);
                else
                    Collections.reverse(lt_data);

                adapter.updateData(lt_data);
                ArrayList<String> chartData = new ArrayList<>();
                for (BargraphComparator bargraphComparator : lt_data) {
                    chartData.add(bargraphComparator.data);
                }
                pmChart.updateData(lt_data);
            }
        });

        return rootView;
    }

    private void initView() {
        lt_data = new LinkedList<>();
        nameComparator = new BargraphNameComparator();
        dataComparator = new BargraphDataComparator();

        adapter = new BargraptAdapter(ctx, null);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(this);
    }

    private void bindData() {
        lt_data.clear();
        entityData = JSON.parseObject(mParam1, MDRPUnitBargraph.class);
        String[] data_name = entityData.xAxis.data;
        ArrayList<MDRPUnitBargraph.Series.Data> data_value = entityData.series.data;
        for (int i = 0; i < data_name.length; i++) {
            String name = data_name[i];
            String value = data_value.get(i).value;
            int color = data_value.get(i).color;
            lt_data.add(new BargraphComparator(name, value, color));
        }

        cbox_percentage.setText(entityData.series.name);
        cbox_name.setText(entityData.xAxis.name);
        adapter.updateData(lt_data);

        //设置图表数据
        pmChart = new PlusMinusChart(ctx);
        pmChart.setDefauteolor(getResources().getColor(R.color.co9));
        pmChart.setDataValues(lt_data);
        fl_container.addView(pmChart);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        adapter.setSelectItem(position);
        String xValue = entityData.xAxis.data[position];
        Toast.makeText(ctx, xValue, Toast.LENGTH_SHORT).show();
    }

    class BargraphNameComparator implements Comparator<BargraphComparator> {

        public int compare(BargraphComparator o1, BargraphComparator o2) {
            String str1 = PinyinUtil.getPingYin(o1.name);
            String str2 = PinyinUtil.getPingYin(o2.name);
            int flag = str1.compareTo(str2);
            return flag;
        }
    }

}
