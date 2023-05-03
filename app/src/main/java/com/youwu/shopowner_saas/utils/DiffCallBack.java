package com.youwu.shopowner_saas.utils;

import androidx.recyclerview.widget.DiffUtil;

import com.blankj.utilcode.util.AppUtils;
import com.youwu.shopowner_saas.bean.UpDateBean;

import java.util.List;

/**
 * 核心类 用来判断 新旧Item是否相等
 */
public class DiffCallBack extends DiffUtil.Callback {
    private List<UpDateBean> mOldDatas, mNewDatas;

    @Override
    public int getOldListSize() {
        return mOldDatas.size();
    }

    @Override
    public int getNewListSize() {
        return mNewDatas.size();
    }

    /**
     * 被DiffUtil调用，用来判断 两个对象是否是相同的Item。
     * @param oldItemPosition
     * @param newItemPosition
     * @return
     */
    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return mOldDatas.get(oldItemPosition).getName().equals(mNewDatas.get(newItemPosition).getName());
    }

    /**
     * 被DiffUtil调用，用来检查 两个item是否含有相同的数据
     * @param oldItemPosition
     * @param newItemPosition
     * @return
     */
    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return mOldDatas.get(oldItemPosition).equals(mNewDatas.get(newItemPosition));
    }
}