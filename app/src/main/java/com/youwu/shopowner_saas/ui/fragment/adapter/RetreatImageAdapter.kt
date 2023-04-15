package com.youwu.shopowner_saas.ui.fragment.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.interfaces.OnSrcViewUpdateListener

import com.lxj.xpopup.interfaces.XPopupImageLoader
import com.lxj.xpopup.util.SmartGlideImageLoader
import com.youwu.shopowner_saas.R
import com.youwu.shopowner_saas.ui.fragment.bean.SaleBillBean
import com.youwu.shopowner_saas.ui.fragment.bean.XXCOrderBean
import com.youwu.shopowner_saas.utils_view.CustomRoundAngleImageView


import java.io.File
import java.util.*


/**
 * 查看图片适配器
 * @author qdafengzi
 */
class RetreatImageAdapter(private val mContext: Context, private val mList: List<XXCOrderBean.OrderDetailsBean>?, private var mPosition: Int) : RecyclerView.Adapter<RetreatImageAdapter.ViewHolder>() {

    var list = ArrayList<Any>()

    /**
     * 新增方法
     *
     * @param position 位置
     */
    fun setPosition(position: Int) {
        mPosition = position
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_image_80, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        list.add(mList!![position].goods_thumb)

        holder.goods_name.setText(mList!![position].goods_name)

        Glide.with(mContext).load(mList!![position].goods_thumb).placeholder(R.mipmap.loading).into(holder.sp_image)


        holder.sp_image.setOnClickListener {
            XPopup.Builder(holder.itemView.context).asImageViewer(holder.sp_image, position, list,
                    OnSrcViewUpdateListener { popupView, position ->

                        popupView.updateSrcView(holder.sp_image)

                    }, SmartGlideImageLoader( R.mipmap.loading))
                    .show()

            if(mreasonListener!=null){
                mreasonListener!!.onReason()
            }

        }

    }




    override fun getItemCount(): Int {
        return mList?.size ?: 0
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {


        var sp_image: CustomRoundAngleImageView

        var goods_name:TextView



        init {

            sp_image = view.findViewById(R.id.sp_image)
            goods_name = view.findViewById(R.id.goods_name)


//            ButterKnife.inject(this, view)

        }
    }

    //回调
    interface OnReasonListener {
        fun onReason()
    }

    fun setOnReasonListener(listener: OnReasonListener?) {
        mreasonListener = listener
    }

    private var mreasonListener: OnReasonListener? = null

    /**
     * 设置item的监听事件的接口
     */
    interface OnItemClickListener {
        /**
         * 接口中的点击每一项的实现方法，参数自己定义
         *
         * @param view 点击的item的视图
         * @param data 点击的item的数据
         */
        fun OnItemClick(view: View?, data: SaleBillBean.OrderDetailsBean?)
    }

    //需要外部访问，所以需要设置set方法，方便调用

    private var onItemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener?) {
        this.onItemClickListener = onItemClickListener
    }


}

