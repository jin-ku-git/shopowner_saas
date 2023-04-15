package com.youwu.shopowner_saas.ui.goods_operate.adapter;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * @author：luck
 * @date：2020-01-13 17:58
 * @describe：长按事件
 */
public interface OnItemLongClickListener {
    void onItemLongClick(RecyclerView.ViewHolder holder, int position, View v);
}
