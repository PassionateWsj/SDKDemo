package com.intfocus.syptemplatev1.entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 仪表盘实体对象
 * Created by zbaoliang on 17-4-28.
 */
public class UnitTableEntity implements Serializable {
    /**
     * 仪表盘标题
     */
    public String[] head;

    public ArrayList<TableRowEntity> data;

    public class TableRowEntity implements Serializable {
        public String[] main_data;
        public String sub_data;
    }
}
