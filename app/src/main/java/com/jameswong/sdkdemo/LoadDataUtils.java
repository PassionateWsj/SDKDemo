package com.jameswong.sdkdemo;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * ****************************************************
 * author: jameswong
 * created on: 17/09/28 下午2:35
 * e-mail: PassionateWsj@outlook.com
 * name:
 * desc:
 * ****************************************************
 */
public class LoadDataUtils {
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
