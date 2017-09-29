package com.intfocus.syptemplatev1.adapter

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.intfocus.syptemplatev1.R
import com.intfocus.syptemplatev1.entity.UnitTableEntity
import org.json.JSONObject

/**
 * 表格第一列名字列表适配器
 * Created by zbaoliang on 17-5-15.
 */
class TableNameAdapter(private val ctx: Context, ltdata: List<UnitTableEntity.TableRowEntity>) : BaseAdapter() {
    private var ltdata: List<UnitTableEntity.TableRowEntity>? = null

    private val defauteColor: Int = ctx.resources.getColor(R.color.co3)
    private val hasSubColor: Int = ctx.resources.getColor(R.color.co14)

    init {
        setData(ltdata)
    }

    private fun setData(ltdata: List<UnitTableEntity.TableRowEntity>?) {
        if (ltdata == null)
            return
        this.ltdata = ltdata
    }

    fun updateData(ltdata: List<UnitTableEntity.TableRowEntity>) {
        setData(ltdata)
        notifyDataSetChanged()
    }

    override fun getCount(): Int {
        return if (ltdata == null) 0 else ltdata!!.size
    }

    override fun getItem(position: Int): Any {
        return ltdata!![position].main_data[0]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val viewHolder: ViewHolder
        if (convertView == null) {
            convertView = LayoutInflater.from(ctx).inflate(R.layout.item_table_name, parent, false)
            viewHolder = ViewHolder(convertView)
            convertView!!.tag = viewHolder
        } else {
            viewHolder = convertView.tag as ViewHolder
        }

        val entity = ltdata!![position]

        if (entity.sub_data == "{}") {
            viewHolder.img_dot!!.visibility = View.GONE
            viewHolder.tv_name!!.setTextColor(defauteColor)
        } else {
            viewHolder.tv_name!!.setTextColor(hasSubColor)
            viewHolder.img_dot!!.visibility = View.GONE
        }

        viewHolder.tv_name!!.text = JSONObject(entity.main_data[0]).getString("value")
        viewHolder.tv_name!!.gravity = Gravity.LEFT
        return convertView
    }

    internal class ViewHolder {
        var tv_name: TextView? = null
        var img_dot: ImageView? = null

        constructor(view: View) {
            this.tv_name = view.findViewById(R.id.tv_tableName_value) as TextView
            this.img_dot = view.findViewById(R.id.img_tableName_dot) as ImageView
        }
    }
}
