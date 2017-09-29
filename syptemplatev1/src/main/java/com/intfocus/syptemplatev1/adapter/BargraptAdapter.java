package com.intfocus.syptemplatev1.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.intfocus.syptemplatev1.entity.BargraphComparator;

import java.util.LinkedList;

import com.intfocus.syptemplatev1.R;

/**
 * Created by zbaoliang on 17-5-15.
 */
public class BargraptAdapter extends BaseAdapter {
    private Context ctx;
    private LinkedList<BargraphComparator> ltdata;
    private Drawable herearrow;
    private int selectItemIndex = 0;
    private int defauteColor;
    private int selectColor;

    public BargraptAdapter(Context ctx, LinkedList<BargraphComparator> ltdata) {
        this.ctx = ctx;
        setData(ltdata);
        herearrow = ctx.getResources().getDrawable(R.drawable.icon_herearrow);
        herearrow.setBounds(0, 0, herearrow.getMinimumWidth(),
                herearrow.getMinimumHeight());
        defauteColor = ctx.getResources().getColor(R.color.co3);
        selectColor = ctx.getResources().getColor(R.color.co14);
    }

    private void setData(LinkedList<BargraphComparator> ltdata) {
        if (ltdata == null)
            return;
        this.ltdata = ltdata;
    }

    public void updateData(LinkedList<BargraphComparator> ltdata) {
        setData(ltdata);
        selectItemIndex = 0;
        notifyDataSetChanged();
    }

    public void setSelectItem(int index) {
        selectItemIndex = index;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (ltdata == null)
            return 0;
        return ltdata.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(ctx).inflate(R.layout.item_mdrpunit_bargraph, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (selectItemIndex == position) {
            viewHolder.tv_name.setTextColor(selectColor);
            viewHolder.tv_percentage.setTextColor(selectColor);
            viewHolder.img_cursor.setImageResource(R.drawable.icon_herearrow);
        } else {
            viewHolder.tv_name.setTextColor(defauteColor);
            viewHolder.tv_percentage.setTextColor(defauteColor);
            viewHolder.img_cursor.setImageResource(0);
        }

        viewHolder.tv_name.setText(ltdata.get(position).name);
        viewHolder.tv_percentage.setText(ltdata.get(position).data);
        return convertView;
    }

    static class ViewHolder {
        TextView tv_name;
        TextView tv_percentage;
        ImageView img_cursor;

        public ViewHolder(View view) {
            this.tv_name = (TextView) view.findViewById(R.id.tv_name);
            this.tv_percentage = (TextView) view.findViewById(R.id.tv_percentage);
            this.img_cursor = (ImageView) view.findViewById(R.id.img_cursor);
        }
    }
}
