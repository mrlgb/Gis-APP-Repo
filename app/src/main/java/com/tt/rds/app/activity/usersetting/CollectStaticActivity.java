package com.tt.rds.app.activity.usersetting;

import android.graphics.Color;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.MediaController;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.tt.rds.app.R;

import java.util.ArrayList;
import java.util.List;

public class CollectStaticActivity extends AppCompatActivity
    implements RadioGroup.OnCheckedChangeListener{

    RadioGroup rg;
    TextView tv_area,tv_time,tv_speed,tv_length;
    BarChart bchart;
    List<BarEntry> barEntries;
    String weekTime,monthTime,yearTime,allTime;//总时长
    double weekSpeed,monthSpeed,yearSpeed,allSpeed;//平均速度
    double weekData,monthData,yearData,allData;//本周、本月、本年、全部总长度
    float[] weekDetailDatas,monthDetailDatas,yearDetailDatas,allDetailDatas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect_static);

        initToolBar();
        initViews();

    }

    private void initToolBar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CollectStaticActivity.this.onBackPressed();

            }
        });
    }

    private void initViews(){
        tv_area=(TextView)findViewById(R.id.cs_tv_area);
        tv_time=(TextView)findViewById(R.id.cs_tv_time);
        tv_speed=(TextView)findViewById(R.id.cs_tv_speed);
        tv_length=(TextView)findViewById(R.id.cs_tv_length);

        bchart=(BarChart)findViewById(R.id.cs_chart);

        rg=(RadioGroup)findViewById(R.id.cs_rg);
        rg.setOnCheckedChangeListener(this);

        barEntries = new ArrayList<>();
       //chart style
        XAxis xAxis = bchart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setEnabled(true);
        xAxis.setDrawAxisLine(true);
        xAxis.setDrawGridLines(false);
        xAxis.setTextSize(12f);
        xAxis.setAxisLineColor(R.color.grey);
        bchart.getAxisLeft().setAxisLineColor(R.color.grey);

        bchart.getAxisRight().setEnabled(false);
        bchart.getAxisLeft().setDrawGridLines(false);
        bchart.getAxisRight().setDrawGridLines(false);
        bchart.setDescription("");
        bchart.setDrawGridBackground(false);

        Legend legent=bchart.getLegend();
        legent.setEnabled(false);

        setWeekChartData();
        bchart.invalidate();

        weekData=234;
        monthData=645;
        yearData=4500;
        allData=8700;

        weekTime="4:46:35";
        monthTime="45:46:35";
        yearTime="116:46:35";
        allTime="216:46:35";

        weekSpeed=40;
        monthSpeed=40;
        yearSpeed=45;
        allSpeed=45;

    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
        switch (i){
            case R.id.cs_rb_week:
                tv_area.setText("本周");
                tv_length.setText(weekData+"");
                tv_time.setText(weekTime);
                tv_speed.setText(weekSpeed+"km/h");
                setWeekChartData();
                bchart.invalidate();
                break;
            case R.id.cs_rb_month:
                tv_area.setText("本月");
                tv_length.setText(monthData+"");
                tv_time.setText(monthTime);
                tv_speed.setText(monthSpeed+"km/h");
                setMonthChartData();
                bchart.invalidate();
                break;
            case R.id.cs_rb_year:
                tv_area.setText("本年");
                tv_length.setText(yearData+"");
                tv_time.setText(yearTime+"");
                tv_speed.setText(yearSpeed+"km/h");
                setYearChartData();
                bchart.invalidate();
                break;
            case R.id.cs_rb_all:
                tv_area.setText("我的采集");
                tv_length.setText(allData+"");
                tv_time.setText(allTime);
                tv_speed.setText(allSpeed+"km/h");
                setAllChartData();
                bchart.invalidate();
                break;
        }

    }

    private void setWeekChartData(){
        //chart data
        barEntries.clear();
        //TODO 获得本周统计数据
        weekDetailDatas=new float[]{
                64.96f,40.73f,85.56f,0,0,0,0
        };
        String[] axisDesc_week = new String[]{
                "一","二","三","四","五","六","七"
        };
        for(int i=0;i<weekDetailDatas.length;i++){
            barEntries.add(new BarEntry(weekDetailDatas[i],i));
        }
        List<String> axisDesc = new ArrayList<>();
        for(int i=0;i<axisDesc_week.length;i++){
            axisDesc.add(axisDesc_week[i]);
        }

        BarDataSet barDataSet = new BarDataSet(barEntries,"week");
        barDataSet.setBarSpacePercent(40f);
        barDataSet.setValueTextSize(11f);
        BarData barData = new BarData(axisDesc,barDataSet);
        bchart.setData(barData);
    }

    private void setMonthChartData(){
        //chart data
        barEntries.clear();
        //TODO 获得本月统计数据
        monthDetailDatas=new float[]{
                53.96f,24.73f,85.56f,0,12f,0,0,
                64.96f,40.73f,75.56f,0,0,0,0,
                47.96f,90.73f,45.56f,0,10f,0,0,
                59.96f,40.73f,85.56f,0,0,31f,0,12f,11f,
        };
        for(int i=0;i<monthDetailDatas.length;i++){
            barEntries.add(new BarEntry(monthDetailDatas[i],i));
        }
        List<String> axisDesc = new ArrayList<>();
        for(int i=0;i<monthDetailDatas.length;i++){
            axisDesc.add((i+1)+"");
        }

        BarDataSet barDataSet = new BarDataSet(barEntries,"month");
        barDataSet.setBarSpacePercent(10f);
        barDataSet.setDrawValues(false);
        BarData barData = new BarData(axisDesc,barDataSet);
        bchart.setData(barData);
    }

    private void setYearChartData(){
        //chart data
        barEntries.clear();
        //TODO 获得本年统计数据
        yearDetailDatas=new float[]{
                453.96f,624.773f,485.56f,524f,621f,321f,213f,
                464.96f,640.73f,275.56f,453.96f,275.56f
        };
        for(int i=0;i<yearDetailDatas.length;i++){
            barEntries.add(new BarEntry(yearDetailDatas[i],i));
        }
        List<String> axisDesc = new ArrayList<>();
        for(int i=0;i<yearDetailDatas.length;i++){
            axisDesc.add((i+1)+"");
        }

        BarDataSet barDataSet = new BarDataSet(barEntries,"year");
        barDataSet.setBarSpacePercent(40f);
        barDataSet.setDrawValues(false);
        BarData barData = new BarData(axisDesc,barDataSet);
        bchart.setData(barData);
    }

    private void setAllChartData(){
        //chart data
        barEntries.clear();
        allDetailDatas=new float[]{
                4300f,5200f,
        };
        for(int i=0;i<allDetailDatas.length;i++){
            barEntries.add(new BarEntry(allDetailDatas[i],i));
        }
        List<String> axisDesc = new ArrayList<>();
        axisDesc.add("2016");
        axisDesc.add("2017");

        BarDataSet barDataSet = new BarDataSet(barEntries,"all");
        barDataSet.setBarSpacePercent(60f);
        barDataSet.setValueTextSize(11f);
        BarData barData = new BarData(axisDesc,barDataSet);
        bchart.setData(barData);
    }

    class StaticThread extends Thread{
        @Override
        public void run() {
            //TODO 获取详细数据：
            // 包括本周每天，本月每天，本年每月，每年数据
            //TODO 获取汇总数据：
            // 本周总公里、总时长、平均速度；本月总公里、总时长、平均速度；
            // 本年总公里、总时长、平均速度；全部总公里、总时长、平均速度；

        }
    }
}
