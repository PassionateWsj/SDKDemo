package com.intfocus.syptemplatev1.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.v4.util.ArrayMap;
import android.support.v4.util.SimpleArrayMap;
import android.view.View;

import com.intfocus.syptemplatev1.constant.Colors;
import com.intfocus.syptemplatev1.utils.DisplayUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.R.attr.value;

/**
 * Created by zbaoliang on 17-5-21.
 */
public class TableValueView extends View {

    private Paint dividerPaint;
    private Paint textPaint;
    private int[] colors = Colors.INSTANCE.getColorsRGY();

    public int dividerHeight = 1;
    public int textSize = 14;
    public int textColor = 0x73737373;
    public int dividerColor = 0x73737373;

    public void setItemHeight(int itemHeight) {
        this.itemHeight = itemHeight;
    }

    public void setHeaderLenghts(ArrayList<Integer> headerLengths) {
        this.headerLenghts.clear();
        this.headerLenghts.addAll(headerLengths);
    }

    public void setTableValues(ArrayMap<Integer, String[]> lables) {
        this.tableValues.clear();
        tableValues.putAll((SimpleArrayMap<? extends Integer, ? extends String[]>) lables);
    }

    public void setDividerHeight(int dividerHeight) {
        this.dividerHeight = dividerHeight;
        dividerPaint.setStrokeWidth(dividerHeight);
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
        textPaint.setColor(textColor);
        invalidate();
    }

    public void setDividerColor(int dividerColor) {
        this.dividerColor = dividerColor;
        dividerPaint.setColor(dividerColor);
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
        textPaint.setTextSize(textSize);
    }

    public TableValueView(Context context) {
        super(context);
        init();
    }

    private void init() {
        dividerPaint = new Paint();
        dividerPaint.setStyle(Paint.Style.FILL);
        dividerPaint.setAntiAlias(true);
        dividerPaint.setDither(true);
        dividerPaint.setColor(dividerColor);
        dividerPaint.setStrokeWidth(1f);

        textPaint = new Paint(dividerPaint);
        textPaint.setTextSize(DisplayUtil.dip2px(getContext(), textSize));
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        textPaint.setColor(textColor);
        textPaint.setStrokeWidth(8);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int totalWidth = 0;
        for (Integer headerLenght : headerLenghts) {
            totalWidth += headerLenght;
        }
        setMeasuredDimension(totalWidth, tableValues.size() * itemHeight);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        initXYAxesTVCenter();
    }

    /**
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        try {
            canvas.drawColor(Color.WHITE);
            int nlSize = tableValues.size();
            float endX = getWidth();
            for (int i = 0; i < nlSize; i++) {
                float startY = itemHeight * (i + dividerHeight / 2) + (i + dividerHeight / 2);
                canvas.drawLine(0, startY, endX, startY, dividerPaint);
            }

            for (int i = 0; i < nlSize; i++) {
                String[] tabrowValues = tableValues.get(i);
                int headerSize = headerLenghts.size();
                Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
                float y = (int) (YAxesCenterPoint.get(i) - fontMetrics.top / 2 - fontMetrics.bottom / 2);
                for (int n = 0; n < headerSize; n++) {
                    float x = XAxesCenterPoint.get(n);
                    JSONObject rowData = new JSONObject(tabrowValues[n + 1]);
                    if (!rowData.getString("color").equals("-1")) {
                        textPaint.setColor(colors[Integer.parseInt(rowData.getString("color"))]);
                    }
                    String value = rowData.getString("value");
                    if (value.contains(".") && !value.contains("%")) {
                        value = (value + "00").substring(0, value.indexOf(".") + 3);
                    } else if (value.contains("%")) {
                        value.replace("%", "");
                        value = (value + "00").substring(0, value.indexOf(".") + 3) + "%";
                    }
                    canvas.drawText(value, x, y, textPaint);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int itemHeight;
    public ArrayList<Integer> headerLenghts = new ArrayList<>();
    public ArrayMap<Integer, String[]> tableValues = new ArrayMap<>();

    /**
     * X轴文本中心点
     */
    private ArrayMap<Integer, Float> XAxesCenterPoint = new ArrayMap<>();

    /**
     * Y轴文本中心点
     */
    private ArrayMap<Integer, Float> YAxesCenterPoint = new ArrayMap<>();

    /**
     * 计算XY轴中心点坐标
     */
    private void initXYAxesTVCenter() {
        XAxesCenterPoint.clear();
        YAxesCenterPoint.clear();

        //TODO 计算文本对应的X轴中心点
        int hlSize = headerLenghts.size();
        float upxPointLs = 0;
        for (int i = 0; i < hlSize; i++) {
            float width = upxPointLs + headerLenghts.get(i);

            float centerPwidth;
            float currValue = (float) (headerLenghts.get(i) / 2);
            if (i == 0)
                centerPwidth = currValue;
            else
                centerPwidth = width - currValue;
            XAxesCenterPoint.put(i, centerPwidth);

            upxPointLs = width;
        }

        //TODO 计算文本对应的Y轴中心点
        int nlSize = tableValues.size();
        float itemHeight2 = (float) (itemHeight / 2);
        for (int i = 0; i < nlSize; i++) {
            float height;
            height = itemHeight * (i + 1);
            float centerPHeight = height - itemHeight2;
            YAxesCenterPoint.put(i, centerPHeight);
        }
    }
}
