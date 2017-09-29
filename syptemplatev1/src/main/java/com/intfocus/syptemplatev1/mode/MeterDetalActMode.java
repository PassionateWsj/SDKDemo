package com.intfocus.syptemplatev1.mode;

import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSONReader;
import com.intfocus.syptemplatev1.entity.MererDetalEntity;
import com.intfocus.syptemplatev1.entity.msg.MDetalActRequestResult;
import com.zbl.lib.baseframe.core.AbstractMode;
import com.zbl.lib.baseframe.utils.TimeUtil;

import org.greenrobot.eventbus.EventBus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;

/**
 * 仪表盘-数据处理模块
 * Created by zbaoliang on 17-4-28.
 */
public class MeterDetalActMode extends AbstractMode {

    String TAG = MeterDetalActMode.class.getSimpleName();
    String itemsString;

    Context ctx;

    MererDetalEntity entity;

    public MeterDetalActMode(Context ctx) {
        this.ctx = ctx;
    }

    public void requestData(String itemsString) {
        this.itemsString = itemsString;
        requestData();
    }

    @Override
    public void requestData() {
        entity = null;

        new Thread(new Runnable() {
            @Override
            public void run() {
                itemsString=getJsonData(ctx);
                StringReader stringReader = new StringReader(itemsString);
                Log.i(TAG, "analysisDataReaderTime1:" + TimeUtil.getNowTime());
                JSONReader reader = new JSONReader(stringReader);
                reader.startArray();
                reader.startObject();

                entity = new MererDetalEntity();
                entity.data = new ArrayList<>();
                Log.i(TAG, "analysisDataReaderTime2:" + TimeUtil.getNowTime());

                while (reader.hasNext()) {
                    String key = reader.readString();
                    switch (key) {
                        case "name":
                            String name = reader.readObject().toString();
                            entity.name = name;
                            Log.i(TAG, "name:" + TimeUtil.getNowTime());
                            break;

                        case "data":
                            Log.i(TAG, "dataStart:" + TimeUtil.getNowTime());
                            reader.startArray();
                            while (reader.hasNext()) {
                                reader.startObject();
                                MererDetalEntity.PageData data = new MererDetalEntity.PageData();
                                while (reader.hasNext()) {
                                    String dataKey = reader.readString();
                                    switch (dataKey) {
                                        case "parts":
                                            String parts = reader.readObject().toString();
                                            data.parts = parts;
                                            break;

                                        case "title":
                                            String title = reader.readObject().toString();
                                            data.title = title;
                                            break;
                                    }
                                }
                                reader.endObject();
                                entity.data.add(data);
                            }
                            reader.endArray();
                            Log.i(TAG, "dataEnd:" + TimeUtil.getNowTime());
                            break;
                    }
                }
                reader.endObject();
                reader.endArray();
                EventBus.getDefault().post(new MDetalActRequestResult(true, 200, entity));
                Log.i(TAG, "analysisDataEndTime:" + TimeUtil.getNowTime());
            }
        }).start();
    }

    private String getJsonData(Context context) {
        InputStream is = null;
        BufferedReader reader = null;
        StringBuilder sb = null;
        try {
//            is = getResources().getAssets().open("kpi_detaldata.json");
            is = context.getResources().getAssets().open("temple1.json");
            reader = new BufferedReader(new InputStreamReader(is));
            sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
