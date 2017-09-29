package com.intfocus.syptemplatev1.entity;

/**
 * Created by liuruilin on 2017/8/8.
 */

public class DataHolder {
    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    private static final DataHolder holder = new DataHolder();

    public static DataHolder getInstance() {
        return holder;
    }
}
