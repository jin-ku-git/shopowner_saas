package com.youwu.shopowner_saas.ui.goods_operate;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;
import com.luck.picture.lib.style.PictureCropParameterStyle;
import com.luck.picture.lib.style.PictureParameterStyle;
import com.luck.picture.lib.style.PictureWindowAnimationStyle;
import com.luck.picture.lib.tools.SdkVersionUtils;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.youwu.shopowner_saas.BR;
import com.youwu.shopowner_saas.R;
import com.youwu.shopowner_saas.app.AppViewModelFactory;
import com.youwu.shopowner_saas.databinding.ActivityGroupManageBinding;
import com.youwu.shopowner_saas.databinding.ActivityNewCommodityBinding;
import com.youwu.shopowner_saas.image.FullyGridLayoutManager;
import com.youwu.shopowner_saas.image.GlideEngine;
import com.youwu.shopowner_saas.toast.RxToast;
import com.youwu.shopowner_saas.ui.goods_operate.adapter.NewImageAdapter;
import com.youwu.shopowner_saas.ui.goods_operate.bean.ImageBean;
import com.youwu.shopowner_saas.ui.group.GroupManageViewModel;
import com.youwu.shopowner_saas.ui.group.NewGroupActivity;
import com.youwu.shopowner_saas.ui.group.adapter.GroupManageAdapter;
import com.youwu.shopowner_saas.utils_view.DividerItemDecorations;
import com.youwu.shopowner_saas.utils_view.StatusBarUtil;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.utils.KLog;

/**
 * 新建商品页面
 * 2023/03/06
 */
public class NewCommodityActivity extends BaseActivity<ActivityNewCommodityBinding, NewCommodityViewModel> {


    //相册
    private int maxSelectNum = 5;//图片最大选择数量
    private PictureCropParameterStyle mCropParameterStyle;
    private List<LocalMedia> selectList = new ArrayList<>();
    private NewImageAdapter adapter;
    private PictureParameterStyle mPictureParameterStyle;
    private int chooseMode = PictureMimeType.ofImage();
    private int themeId;
    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_new_commodity;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public NewCommodityViewModel initViewModel() {
        //使用自定义的ViewModelFactory来创建ViewModel，如果不重写该方法，则默认会调用LoginViewModel(@NonNull Application application)构造方法
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(NewCommodityViewModel.class);
    }

    @Override
    public void initViewObservable() {

    }

    @Override
    public void initData() {
        super.initData();

        StatusBarUtil.setRootViewFitsSystemWindows(this, true);
        //修改状态栏是状态栏透明
        StatusBarUtil.setTransparentForWindow(this);
        StatusBarUtil.setDarkMode(this);//使状态栏字体变为白色
        themeId = R.style.picture_default_style;


        initViews();
        getWeChatStyle();
        initWidget();
    }


    protected void initViews() {
        mPictureParameterStyle = new PictureParameterStyle();
        mCropParameterStyle = new PictureCropParameterStyle(
                ContextCompat.getColor(getBaseContext(), R.color.white),
                ContextCompat.getColor(getBaseContext(), R.color.white),
                ContextCompat.getColor(getBaseContext(), R.color.white),
                mPictureParameterStyle.isChangeStatusBarFontColor);


    }
    private void initWidget() {
        FullyGridLayoutManager manager = new FullyGridLayoutManager(this, 4,
                GridLayoutManager.VERTICAL, false);
        binding.recycler.setLayoutManager(manager);
        adapter = new NewImageAdapter(this, onAddPicClickListener);
        adapter.setList(selectList);
        adapter.setSelectMax(maxSelectNum);
        binding.recycler.setAdapter(adapter);
        adapter.setOnItemClickListener((v, position) -> {
            List<LocalMedia> selectList = adapter.getData();
            if (selectList.size() > 0) {
                LocalMedia media = selectList.get(position);
                String mimeType = media.getMimeType();
                int mediaType = PictureMimeType.getMimeType(mimeType);
                switch (mediaType) {
                    case PictureConfig.TYPE_VIDEO:
                        // 预览视频
                        PictureSelector.create(NewCommodityActivity.this).externalPictureVideo(media.getPath());
                        break;
                    case PictureConfig.TYPE_AUDIO:
                        // 预览音频
                        PictureSelector.create(NewCommodityActivity.this).externalPictureAudio(media.getPath());
                        break;
                    default:
                        // 预览图片 可自定长按保存路径
                        PictureWindowAnimationStyle animationStyle =
                                new PictureWindowAnimationStyle();
                        animationStyle.activityPreviewEnterAnimation = R.anim.picture_anim_up_in;
                        animationStyle.activityPreviewExitAnimation = R.anim.picture_anim_down_out;
                        PictureSelector.create(NewCommodityActivity.this)
                                .openGallery(chooseMode)// 全部.PictureMimeType.ofAll()、图片.ofImage
                                // ()、视频.ofVideo()、音频.ofAudio()
                                .imageEngine(GlideEngine.createGlideEngine())// 外部传入图片加载引擎，必传项
                                .theme(themeId)// 主题样式设置 具体参考 values/styles   用法：R.style.picture
                                // .white.style v2.3.3后 建议使用setPictureStyle()动态方式
                                .setPictureStyle(mPictureParameterStyle)// 动态自定义相册主题
                                //.setPictureWindowAnimationStyle(animationStyle)// 自定义页面启动动画
                                .isNotPreviewDownload(false)// 预览图片长按是否可以下载
                                .openExternalPreview(position, selectList);

                        break;
                }
            }
        });
    }

    private NewImageAdapter.onAddPicClickListener onAddPicClickListener =
            new NewImageAdapter.onAddPicClickListener() {

                @SuppressLint("CheckResult")
                @Override
                public void onAddPicClick() {
                    //获取写的权限
                    RxPermissions rxPermission = new RxPermissions(NewCommodityActivity.this);
                    rxPermission.requestEach(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            .subscribe(new Consumer<Permission>() {
                                @Override
                                public void accept(Permission permission) {
                                    if (permission.granted) {// 用户已经同意该权限
                                        //第一种方式，弹出选择和拍照的dialog
//                                showPop();

                                        //第二种方式，直接进入相册，但是 是有拍照得按钮的
                                        showAlbum();
                                    } else {
                                        RxToast.normal("拒绝");
                                    }
                                }
                            });
                }
            };

    private void showAlbum() {
        // 进入相册 以下是例子：不需要的api可以不写
        PictureSelector.create(NewCommodityActivity.this)
                .openGallery(chooseMode)// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()
                // 、音频.ofAudio()
                .imageEngine(GlideEngine.createGlideEngine())// 外部传入图片加载引擎，必传项
                .theme(themeId)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style v2.3
                // .3后 建议使用setPictureStyle()动态方式
                .isWeChatStyle(true)// 是否开启微信图片选择风格
//                .isUseCustomCamera(true)// 是否使用自定义相机
//                .setLanguage(language)// 设置语言，默认中文
//                .isPageStrategy(cbPage.isChecked())// 是否开启分页策略 & 每页多少条；默认开启
                .setPictureStyle(mPictureParameterStyle)// 动态自定义相册主题
                .setPictureCropStyle(mCropParameterStyle)// 动态自定义裁剪主题
                .isWithVideoImage(true)// 图片和视频是否可以同选,只在ofAll模式下有效
                .isMaxSelectEnabledMask(true)// 选择数到了最大阀值列表是否启用蒙层效果
                .maxSelectNum(maxSelectNum)// 最大图片选择数量
                .minSelectNum(1)// 最小选择数量
                .maxVideoSelectNum(1) // 视频最大选择数量
                .imageSpanCount(4)// 每行显示个数
                .isReturnEmpty(false)// 未选择数据时点击按钮是否可以返回
                .closeAndroidQChangeWH(true)//如果图片有旋转角度则对换宽高,默认为true
                .closeAndroidQChangeVideoWH(!SdkVersionUtils.checkedAndroid_Q())//
                // 如果视频有旋转角度则对换宽高,默认为false
                .setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)//
                // 设置相册Activity方向，不设置默认使用系统
                .isSingleDirectReturn(true)// 单选模式下是否直接返回，PictureConfig.SINGLE模式下有效
                .isPreviewImage(true)// 是否可预览图片
                .isPreviewVideo(true)// 是否可预览视频
                //.querySpecifiedFormatSuffix(PictureMimeType.ofJPEG())// 查询指定后缀格式资源
                .isEnablePreviewAudio(true) // 是否可播放音频
                .isCamera(false)// 是否显示拍照按钮
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                .imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg,Android Q使用PictureMimeType
                // .PNG_Q
                .isEnableCrop(false)// 是否裁剪
                .isCompress(true)// 是否压缩
                .synOrAsy(false)//同步true或异步false 压缩 默认同步
                .withAspectRatio(1, 1)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                .freeStyleCropEnabled(false)// 裁剪框是否可拖拽
                .circleDimmedLayer(false)// 是否圆形裁剪
                .showCropFrame(false)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
                .showCropGrid(false)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
                .isOpenClickSound(false)// 是否开启点击声音
                .selectionData(adapter.getData())// 是否传入已选图片
                .cutOutQuality(90)// 裁剪输出质量 默认100
                .minimumCompressSize(100)// 小于多少kb的图片不压缩
                .forResult(new MyResultCallback(adapter));
    }
    /**
     * 返回结果回调
     */
    private class MyResultCallback implements OnResultCallbackListener<LocalMedia> {
        private WeakReference<NewImageAdapter> mAdapterWeakReference;

        public MyResultCallback(NewImageAdapter adapter) {
            super();
            this.mAdapterWeakReference = new WeakReference<>(adapter);
        }

        /**
         * return LocalMedia result
         *
         * @param result
         */
        @Override
        public void onResult(List<LocalMedia> result) {

            for (LocalMedia media : result) {
                KLog.i( "是否压缩:" + media.isCompressed());
                KLog.i( "压缩:" + media.getCompressPath());
                KLog.i( "原图:" + media.getPath());
                KLog.i( "是否裁剪:" + media.isCut());
                KLog.i( "裁剪:" + media.getCutPath());
                KLog.i( "是否开启原图:" + media.isOriginal());
                KLog.i( "原图路径:" + media.getOriginalPath());
                KLog.i( "Android Q 特有Path:" + media.getAndroidQToPath());
                KLog.i( "宽高: " + media.getWidth() + "x" + media.getHeight());
                KLog.i( "Size: " + media.getSize());
                ImageBean medias = new ImageBean();
                medias.setImage(media.getCompressPath());
//                medias.setPath(media.getPath());
//                results.add(medias);

//                imageFile = getFileByUrl(media.getPath());

                // TODO 可以通过PictureSelectorExternalUtils.getExifInterface();
                //  方法获取一些额外的资源信息，如旋转角度、经纬度等信息
            }


            if (mAdapterWeakReference.get() != null) {
                mAdapterWeakReference.get().setList(result);
                mAdapterWeakReference.get().notifyDataSetChanged();
            }
        }

        /**
         * Cancel
         */
        @Override
        public void onCancel() {

        }
    }

    private void getWeChatStyle() {
        // 相册主题
        mPictureParameterStyle = new PictureParameterStyle();
        // 是否改变状态栏字体颜色(黑白切换)
        mPictureParameterStyle.isChangeStatusBarFontColor = false;
        // 是否开启右下角已完成(0/9)风格
        mPictureParameterStyle.isOpenCompletedNumStyle = false;
        // 是否开启类似QQ相册带数字选择风格
        mPictureParameterStyle.isOpenCheckNumStyle = true;
        // 状态栏背景色
        mPictureParameterStyle.pictureStatusBarColor = Color.parseColor("#393a3e");
        // 相册列表标题栏背景色
        mPictureParameterStyle.pictureTitleBarBackgroundColor = Color.parseColor("#393a3e");
        // 相册父容器背景色
        mPictureParameterStyle.pictureContainerBackgroundColor =
                ContextCompat.getColor(getBaseContext(), R.color.black);
        // 相册列表标题栏右侧上拉箭头
        mPictureParameterStyle.pictureTitleUpResId = R.drawable.picture_icon_wechat_up;
        // 相册列表标题栏右侧下拉箭头
        mPictureParameterStyle.pictureTitleDownResId = R.drawable.picture_icon_wechat_down;
        // 相册文件夹列表选中圆点
        mPictureParameterStyle.pictureFolderCheckedDotStyle = R.drawable.picture_orange_oval;
        // 相册返回箭头
        mPictureParameterStyle.pictureLeftBackIcon = R.drawable.picture_icon_close;
        // 标题栏字体颜色
        mPictureParameterStyle.pictureTitleTextColor = ContextCompat.getColor(getBaseContext(),
                R.color.picture_color_white);
        // 相册右侧按钮字体颜色  废弃 改用.pictureRightDefaultTextColor和.pictureRightDefaultTextColor
        mPictureParameterStyle.pictureCancelTextColor = ContextCompat.getColor(getBaseContext(),
                R.color.picture_color_53575e);
        // 相册右侧按钮字体默认颜色
        mPictureParameterStyle.pictureRightDefaultTextColor =
                ContextCompat.getColor(getBaseContext(), R.color.picture_color_53575e);
        // 相册右侧按可点击字体颜色,只针对isWeChatStyle 为true时有效果
        mPictureParameterStyle.pictureRightSelectedTextColor =
                ContextCompat.getColor(getBaseContext(), R.color.picture_color_white);
        // 相册右侧按钮背景样式,只针对isWeChatStyle 为true时有效果
        mPictureParameterStyle.pictureUnCompleteBackgroundStyle =
                R.drawable.picture_send_button_default_bg;
        // 相册右侧按钮可点击背景样式,只针对isWeChatStyle 为true时有效果
        mPictureParameterStyle.pictureCompleteBackgroundStyle = R.drawable.picture_send_button_bg;
        // 选择相册目录背景样式
        mPictureParameterStyle.pictureAlbumStyle = R.drawable.picture_new_item_select_bg;
        // 相册列表勾选图片样式
        mPictureParameterStyle.pictureCheckedStyle = R.drawable.picture_wechat_num_selector;
        // 相册标题背景样式 ,只针对isWeChatStyle 为true时有效果
        mPictureParameterStyle.pictureWeChatTitleBackgroundStyle = R.drawable.picture_album_bg;
        // 微信样式 预览右下角样式 ,只针对isWeChatStyle 为true时有效果
        mPictureParameterStyle.pictureWeChatChooseStyle = R.drawable.picture_wechat_select_cb;
        // 相册返回箭头 ,只针对isWeChatStyle 为true时有效果
        mPictureParameterStyle.pictureWeChatLeftBackStyle = R.drawable.picture_icon_back;
        // 相册列表底部背景色
        mPictureParameterStyle.pictureBottomBgColor = ContextCompat.getColor(getBaseContext(),
                R.color.picture_color_grey);
        // 已选数量圆点背景样式
        mPictureParameterStyle.pictureCheckNumBgStyle = R.drawable.picture_num_oval;
        // 相册列表底下预览文字色值(预览按钮可点击时的色值)
        mPictureParameterStyle.picturePreviewTextColor = ContextCompat.getColor(getBaseContext(),
                R.color.picture_color_white);
        // 相册列表底下不可预览文字色值(预览按钮不可点击时的色值)
        mPictureParameterStyle.pictureUnPreviewTextColor =
                ContextCompat.getColor(getBaseContext(), R.color.picture_color_9b);
        // 相册列表已完成色值(已完成 可点击色值)
        mPictureParameterStyle.pictureCompleteTextColor = ContextCompat.getColor(getBaseContext()
                , R.color.picture_color_white);
        // 相册列表未完成色值(请选择 不可点击色值)
        mPictureParameterStyle.pictureUnCompleteTextColor =
                ContextCompat.getColor(getBaseContext(), R.color.picture_color_53575e);
        // 预览界面底部背景色
        mPictureParameterStyle.picturePreviewBottomBgColor =
                ContextCompat.getColor(getBaseContext(), R.color.picture_color_half_grey);
        // 外部预览界面删除按钮样式
        mPictureParameterStyle.pictureExternalPreviewDeleteStyle = R.drawable.picture_icon_delete;
        // 原图按钮勾选样式  需设置.isOriginalImageControl(true); 才有效
        mPictureParameterStyle.pictureOriginalControlStyle =
                R.drawable.picture_original_wechat_checkbox;
        // 原图文字颜色 需设置.isOriginalImageControl(true); 才有效
        mPictureParameterStyle.pictureOriginalFontColor = ContextCompat.getColor(getBaseContext()
                , R.color.white);
        // 外部预览界面是否显示删除按钮
        mPictureParameterStyle.pictureExternalPreviewGonePreviewDelete = true;
        // 设置NavBar Color SDK Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP有效
        mPictureParameterStyle.pictureNavBarColor = Color.parseColor("#393a3e");


        // 裁剪主题
        mCropParameterStyle = new PictureCropParameterStyle(
                ContextCompat.getColor(getBaseContext(), R.color.app_color_grey),
                ContextCompat.getColor(getBaseContext(), R.color.app_color_grey),
                Color.parseColor("#393a3e"),
                ContextCompat.getColor(getBaseContext(), R.color.white),
                mPictureParameterStyle.isChangeStatusBarFontColor);
    }

}

