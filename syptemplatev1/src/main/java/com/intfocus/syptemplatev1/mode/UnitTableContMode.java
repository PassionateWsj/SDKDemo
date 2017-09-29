package com.intfocus.syptemplatev1.mode;

import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.intfocus.syptemplatev1.entity.UnitTableEntity;
import com.intfocus.syptemplatev1.entity.msg.MDetalRootPageRequestResult;
import com.zbl.lib.baseframe.core.AbstractMode;
import com.zbl.lib.baseframe.utils.TimeUtil;


/**
 * 仪表盘-数据处理模块
 * Created by zbaoliang on 17-4-28.
 */
public class UnitTableContMode extends AbstractMode {

    String TAG = UnitTableContMode.class.getSimpleName();

    Context ctx;

    public UnitTableContMode(Context ctx) {
        this.ctx = ctx;
    }

    @Override
    public void requestData() {
    }

    /**
     * 解析数据
     *
     * @param result
     */
    public void analysisData(final String result) {
        Log.i(TAG, "StartAnalysisTime:" + TimeUtil.getNowTime());
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    UnitTableEntity entity = JSON.parseObject(result, UnitTableEntity.class);
                    dataCallback(entity, "onMessageEvent");
                    Log.i(TAG, "EndAnalysisTime:" + TimeUtil.getNowTime());
                } catch (Exception e) {
                    dataCallback(new MDetalRootPageRequestResult(true, 400, null), "onMessageEvent");
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
