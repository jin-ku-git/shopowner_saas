package com.youwu.shopowner_saas.ui.marker;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.TypedValue;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.MPPointF;
import com.youwu.shopowner_saas.R;
import com.youwu.shopowner_saas.utils_view.DividerItemDecorations;


import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import me.goldze.mvvmhabit.utils.KLog;

public class PeopleDetailsMarkerView extends MarkerView {
    private final int DEFAULT_INDICATOR_COLOR = 0xff0091FF;//指示器默认的颜色
    private final int ARROW_HEIGHT = dp2px(10); // 箭头的高度
    private final int ARROW_WIDTH = dp2px(15); // 箭头的宽度
    private final float ARROW_OFFSET = dp2px(2);//箭头偏移量
    private final float BG_CORNER = dp2px(10);//背景圆角
    private Bitmap bitmapForDot;//选中点图片
    private int bitmapWidth;//点宽
    private int bitmapHeight;//点高

    private TextView tvDate;

    private RecyclerView recyclerView;
    private ValueFormatter valueFormatter;
    DecimalFormat df = new DecimalFormat("0.00");

    private PopupAdapter adapter;
    private List<String> list=new ArrayList<>();

    List<String> Datelist= new ArrayList<>();
    private Context context;
    public PeopleDetailsMarkerView(Context context, ValueFormatter valueFormatter, List<String> Datelist) {
        super(context, R.layout.layout_markview);
        this.valueFormatter = valueFormatter;
        this.context=context;
        this.Datelist=Datelist;
        tvDate = findViewById(R.id.tv_date);

        recyclerView = findViewById(R.id.recyclerView);
        //图片自行替换
        bitmapForDot = getBitmap(context, R.drawable.ic_brightness_curve_point);
        bitmapWidth = bitmapForDot.getWidth();
        bitmapHeight = bitmapForDot.getHeight();
    }


    private static Bitmap getBitmap(Context context,int vectorDrawableId) {
        Bitmap bitmap=null;
        if (Build.VERSION.SDK_INT>Build.VERSION_CODES.LOLLIPOP){
            Drawable vectorDrawable = context.getDrawable(vectorDrawableId);
            bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(),
                    vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            vectorDrawable.draw(canvas);
        }else {
            bitmap = BitmapFactory.decodeResource(context.getResources(), vectorDrawableId);
        }
        return bitmap;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        Chart chart = getChartView();
        if (chart instanceof LineChart) {
            LineData lineData = ((LineChart) chart).getLineData();
            //获取到图表中的所有曲线
            List<ILineDataSet> dataSetList = lineData.getDataSets();

            list.clear();
            for (int i = 0; i < dataSetList.size(); i++) {
                LineDataSet dataSet = (LineDataSet) dataSetList.get(i);
                //获取到曲线的所有在Y轴的数据集合，根据当前X轴的位置 来获取对应的Y轴值
                float y = dataSet.getValues().get((int) e.getX()).getY();

                list.add(dataSet.getLabel() + "：" + subZeroAndDot(df.format(y )) + "人");

            }
            initRecyclerView();


//            tvDate.setText(valueFormatter.getFormattedValue(e.getX())+"天");
            tvDate.setText(Datelist.get((int) e.getX()));
        }else if (chart instanceof BarChart){
            BarData lineData = ((BarChart) chart).getBarData();
            //获取到图表中的所有曲线
            List<IBarDataSet> dataSetList = lineData.getDataSets();

            list.clear();

            for (int i = 0; i < dataSetList.size(); i++) {
                BarDataSet dataSet = (BarDataSet) dataSetList.get(i);
                //获取到曲线的所有在Y轴的数据集合，根据当前X轴的位置 来获取对应的Y轴值
                float y = dataSet.getValues().get((int) e.getX()).getY();
                float[] YVals = dataSet.getValues().get((int) e.getX()).getYVals();
                String[] name = dataSet.getStackLabels();
                list.add("全部：" + subZeroAndDot(df.format(y )) + "人");
                KLog.d("YVals.长度："+YVals.length);
                KLog.d("name.长度："+name.length);
                for (int j=0;j<name.length;j++){
                    list.add(name[j]+"：" + subZeroAndDot(YVals[j]+"") + "人");
                }


            }
            initRecyclerView();

            tvDate.setText(Datelist.get((int) e.getX()));
        }
        super.refreshContent(e, highlight);
    }

    private void initRecyclerView() {
        //创建adapter
        adapter = new PopupAdapter(context, list);
        //给RecyclerView设置adapter
        recyclerView.setAdapter(adapter);
        //设置layoutManager,可以设置显示效果，是线性布局、grid布局，还是瀑布流布局

        //参数是：上下文、列表方向（横向还是纵向）、是否倒叙
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL));
        //设置item的分割线
        if (recyclerView.getItemDecorationCount()==0) {
            recyclerView.addItemDecoration(new DividerItemDecorations(context, DividerItemDecorations.VERTICAL));
        }
    }

    @Override
    public MPPointF getOffset() {
        return new MPPointF(-(getWidth() / 2), -getHeight());
    }
    /**
     * 去除小数点后多余的0
     *
     * @param s
     * @return
     */
    public static String subZeroAndDot(String s) {
        if (s.indexOf(".") > 0) {
            s = s.replaceAll("0+?$", "");//去掉多余的0
            s = s.replaceAll("[.]$", "");//如最后一位是.则去掉
        }
        return s;
    }


    @Override
    public void draw(Canvas canvas, float posX, float posY) {
        Chart chart = getChartView();
        if (chart == null) {
            super.draw(canvas, posX, posY);
            return;
        }

        //指示器背景画笔
        Paint bgPaint = new Paint();
        bgPaint.setStyle(Paint.Style.FILL);
        bgPaint.setAntiAlias(true);
        bgPaint.setColor(DEFAULT_INDICATOR_COLOR);
        //剪头画笔
        Paint arrowPaint = new Paint();
        arrowPaint.setStyle(Paint.Style.FILL);
        arrowPaint.setAntiAlias(true);
        arrowPaint.setColor(DEFAULT_INDICATOR_COLOR);

        float width = getWidth();
        float height = getHeight();

        int saveId = canvas.save();
        //移动画布到点并绘制点
        canvas.translate(posX, posY);
        canvas.drawBitmap(bitmapForDot, -bitmapWidth / 2f , -bitmapHeight / 2f ,null);

        //画指示器
        Path path = new Path();
        RectF bRectF;
        if (posY < height + ARROW_HEIGHT + bitmapHeight / 2f) {//处理超过上边界
            //移动画布并绘制三角形和背景
            canvas.translate(0, height + ARROW_HEIGHT + bitmapHeight / 2f);
            if (posX > chart.getWidth() - (width/2f)) {//超过右边界
                canvas.translate( -(width/2 - (chart.getWidth() - posX)), 0);
                /*************绘制三角形  超过上边界 / 超过右边界**/
                path.moveTo(width/2 - (chart.getWidth() - posX) - ARROW_OFFSET, -(height + ARROW_HEIGHT + ARROW_OFFSET));
                path.lineTo(ARROW_WIDTH / 2f, -(height - BG_CORNER));
                path.lineTo(- ARROW_WIDTH / 2f, -(height - BG_CORNER));
                path.moveTo(width/2 - (chart.getWidth() - posX) - ARROW_OFFSET, -(height + ARROW_HEIGHT + ARROW_OFFSET));

            }else{
                if (posX > width / 2f) {//在图表中间
                    path.moveTo(0, -(height + ARROW_HEIGHT));
                    path.lineTo(ARROW_WIDTH / 2f, -(height - BG_CORNER));
                    path.lineTo(- ARROW_WIDTH / 2f, -(height - BG_CORNER));
                    path.lineTo(0, -(height + ARROW_HEIGHT));
                } else {//超过左边界
                    canvas.translate(width/2f - posX, 0);
                    /*************绘制三角形 超过上边界 /  超过左边界**/
                    path.moveTo(-(width/2f - posX) - ARROW_OFFSET, -(height + ARROW_HEIGHT + ARROW_OFFSET));
                    path.lineTo(ARROW_WIDTH / 2f, -(height - BG_CORNER));
                    path.lineTo(- ARROW_WIDTH / 2f, -(height - BG_CORNER));
                    path.moveTo(-(width/2f - posX) - ARROW_OFFSET, -(height + ARROW_HEIGHT  + ARROW_OFFSET));

                }
            }

            bRectF=new RectF(-width / 2, -height, width / 2, 0);
            canvas.drawPath(path, arrowPaint);
            canvas.drawRoundRect(bRectF, BG_CORNER, BG_CORNER, bgPaint);
            canvas.translate(-width / 2f, -height);
        } else {//没有超过上边界
            //移动画布并绘制三角形和背景
            canvas.translate(0, -height - ARROW_HEIGHT - bitmapHeight / 2f);
            if (posX < width/2f)  {//超过左边界  平移view
                canvas.translate( width/2f - posX, 0);

                /*************绘制三角形 超过下边界 /  超过左边界**/
                path.moveTo(-(width/2f - posX) + ARROW_OFFSET, height + ARROW_HEIGHT + ARROW_OFFSET);
                path.lineTo(ARROW_WIDTH / 2f, height - BG_CORNER);
                path.lineTo(- ARROW_WIDTH / 2f, height - BG_CORNER);
                path.moveTo(-(width/2f - posX) + ARROW_OFFSET, height + ARROW_HEIGHT + ARROW_OFFSET);
            }else{
                if (posX > chart.getWidth() - (width/2f))  {//超过右边界  绘制三角
                    /*************绘制三角形 超过下边界 /  超过右边界**/
                    canvas.translate( -(width/2 - (chart.getWidth() - posX)), 0);
                    path.moveTo(width/2 - (chart.getWidth() - posX) + ARROW_OFFSET, height + ARROW_HEIGHT + ARROW_OFFSET);
                    path.lineTo(ARROW_WIDTH / 2f, height - BG_CORNER);
                    path.lineTo(- ARROW_WIDTH / 2f, height - BG_CORNER);
                    path.moveTo(width/2 - (chart.getWidth() - posX) + ARROW_OFFSET, height + ARROW_HEIGHT + ARROW_OFFSET);

                }else{
                    path.moveTo(0, height + ARROW_HEIGHT);
                    path.lineTo(ARROW_WIDTH / 2f, height - BG_CORNER);
                    path.lineTo(- ARROW_WIDTH / 2f, height - BG_CORNER);
                    path.moveTo(0, height + ARROW_HEIGHT);
                }

            }

            bRectF=new RectF(-width / 2, 0, width / 2, height);

            canvas.drawPath(path, arrowPaint);
            canvas.drawRoundRect(bRectF, BG_CORNER, BG_CORNER, bgPaint);
            canvas.translate(-width / 2f, 0);
        }
        draw(canvas);
        canvas.restoreToCount(saveId);
    }

    private int dp2px(int dpValues) {
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dpValues,
                getResources().getDisplayMetrics());
    }
}