package com.tt.rds.app.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.esri.arcgisruntime.geometry.Envelope;
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.geometry.SpatialReferences;
import com.esri.arcgisruntime.layers.ArcGISMapImageLayer;
import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.Basemap;
import com.esri.arcgisruntime.mapping.view.DrawStatus;
import com.esri.arcgisruntime.mapping.view.DrawStatusChangedEvent;
import com.esri.arcgisruntime.mapping.view.DrawStatusChangedListener;
import com.esri.arcgisruntime.mapping.view.Graphic;
import com.esri.arcgisruntime.mapping.view.GraphicsOverlay;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.esri.arcgisruntime.symbology.PictureMarkerSymbol;
import com.github.mikephil.charting.charts.LineChart;
import com.tt.rds.app.R;
import com.tt.rds.app.app.GPSApplication;
import com.tt.rds.app.bean.Picture;
import com.tt.rds.app.bean.PointBridge;
import com.tt.rds.app.bean.PointCulvert;
import com.tt.rds.app.bean.PointFerry;
import com.tt.rds.app.bean.PointMarker;
import com.tt.rds.app.bean.PointSchool;
import com.tt.rds.app.bean.PointSign;
import com.tt.rds.app.bean.PointStandardVillage;
import com.tt.rds.app.bean.PointTown;
import com.tt.rds.app.bean.PointTunnel;
import com.tt.rds.app.bean.PointVillage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alpha Dog on 2017/10/3.
 */

public class DataQueryDetailActivity extends AppCompatActivity {
    private com.esri.arcgisruntime.mapping.view.MapView mapView;
    private android.widget.ProgressBar progressBar;

    private PointBridge mPointBridge;//桥
    private PointCulvert mPointCulvert;//隧道
    private PointFerry mPointFerry;//渡口
    private PointTunnel mPointTunnel;//涵洞
    private PointTown mPointTown;//乡镇
    private PointStandardVillage mPointStandardVillage;//建制村
    private PointVillage mPointVillage;//自然村
    private PointSchool mPointSchool;//学校
    private PointSign mPointSign;//标识标牌
    private PointMarker mPointMarker;//标识点

    private String[] shortAttribute;
    private String[] moreAttribute;
    private boolean isMore = false;
    private String title;
    List<Picture> mPictureList = new ArrayList<>();

    private android.support.design.widget.TabLayout relativelayout;
    private android.widget.TextView tvmore;
    private android.widget.LinearLayout llattribute;
    private android.widget.ViewFlipper welcomeflipper;
    private View welcomdot1;
    private View welcomdot2;
    private View welcomdot3;
    private TextView mTvTitle;
    private android.widget.LinearLayout llimgs;
    private com.github.mikephil.charting.charts.LineChart chart1;
    private com.github.mikephil.charting.charts.LineChart chart2;
    private android.widget.LinearLayout llcharts;
    private List<View> mDotList;
    private int currentPos;
    private int oldPos;
    private static final int AUTO = 0x01;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case AUTO:
                    if (currentPos == mDotList.size() - 1) {
                    } else {
                        showNext();
                        sendAutoMsg();
                    }
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_query_detail);
        initToolBar();
        initView();
        initData();
        initArcgisMap();
        llattribute.setVisibility(View.VISIBLE);
        llimgs.setVisibility(View.GONE);
        relativelayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    llattribute.setVisibility(View.VISIBLE);
                    llimgs.setVisibility(View.GONE);
                }
                if (tab.getPosition() == 1) {
                    llattribute.setVisibility(View.GONE);
                    llimgs.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_data_query_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    private void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataQueryDetailActivity.this.onBackPressed();
            }
        });
    }

    private void initView() {
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        llcharts = (LinearLayout) findViewById(R.id.ll_charts);
        chart2 = (LineChart) findViewById(R.id.chart2);
        chart1 = (LineChart) findViewById(R.id.chart1);
        llimgs = (LinearLayout) findViewById(R.id.ll_imgs);
        welcomdot3 = (View) findViewById(R.id.welcom_dot3);
        welcomdot2 = (View) findViewById(R.id.welcom_dot2);
        welcomdot1 = (View) findViewById(R.id.welcom_dot1);
        welcomeflipper = (ViewFlipper) findViewById(R.id.welcome_flipper);
        llattribute = (LinearLayout) findViewById(R.id.ll_attribute);
        tvmore = (TextView) findViewById(R.id.tv_more);
        relativelayout = (TabLayout) findViewById(R.id.relative_layout);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        mapView = (MapView) findViewById(R.id.mapView);
    }

    private void initArcgisMap() {
        // inflate MapView from layout
        mapView = (MapView) findViewById(R.id.mapView);
        // create a MapImageLayer with dynamically generated map images
        ArcGISMapImageLayer mapImageLayer = new ArcGISMapImageLayer(getResources().getString(R.string.sample_service_url));
//        ArcGISTiledLayer mapTiledLayer= new ArcGISTiledLayer("http://gis.ncgl.cn/arcgis/rest/services/yjcmapQS20160520/MapServer");
        // create an empty map instance
        ArcGISMap map = new ArcGISMap(Basemap.createTopographic());
//        ArcGISMap map = new ArcGISMap();
        // add map image layer as operational layer
        map.getOperationalLayers().add(mapImageLayer);
        // set the map to be displayed in this view
        mapView.setMap(map);

        // create an initial viewpoint using an envelope (of two points, bottom left and top right)
        Envelope envelope = new Envelope(new Point(12993828.5821309, 3706520.00454287, SpatialReferences.getWebMercator()),
                new Point(13098547.3108814, 3773861.02646202, SpatialReferences.getWebMercator()));
        //set viewpoint on mapview
//        mapView.setViewpointGeometryAsync(envelope, 100.0);
        mapView.setViewpointCenterAsync(new Point(12993828.5821309, 3706520.00454287, SpatialReferences.getWebMercator()), 10000);
        mapView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        final GraphicsOverlay mGraphicsOverlay = new GraphicsOverlay();
        BitmapDrawable pinStarBlueDrawable = (BitmapDrawable) ContextCompat.getDrawable(this, R.drawable.point_collect);
        final PictureMarkerSymbol pinStarBlueSymbol = new PictureMarkerSymbol(pinStarBlueDrawable);
        //Optionally set the size, if not set the image will be auto sized based on its size in pixels,
        //its appearance would then differ across devices with different resolutions.
        pinStarBlueSymbol.setHeight(40);
        pinStarBlueSymbol.setWidth(40);
        pinStarBlueSymbol.setOffsetY(11);
        pinStarBlueSymbol.loadAsync();
        //[DocRef: END]
        pinStarBlueSymbol.addDoneLoadingListener(new Runnable() {
            @Override
            public void run() {
                //add a new graphic with the same location as the initial viewpoint
                Point pinStarBluePoint = new Point(12993828.5821309, 3706520.00454287, SpatialReferences.getWebMercator());
                Graphic pinStarBlueGraphic = new Graphic(pinStarBluePoint, pinStarBlueSymbol);
                mGraphicsOverlay.getGraphics().add(pinStarBlueGraphic);
            }
        });
        mapView.getGraphicsOverlays().add(mGraphicsOverlay);

        //[DocRef: Name=Monitor map drawing, Category=Work with maps, Topic=Display a map]
        mapView.addDrawStatusChangedListener(new DrawStatusChangedListener() {
            @Override
            public void drawStatusChanged(DrawStatusChangedEvent drawStatusChangedEvent) {
                if (drawStatusChangedEvent.getDrawStatus() == DrawStatus.IN_PROGRESS) {
                    progressBar.setVisibility(View.VISIBLE);
                } else if (drawStatusChangedEvent.getDrawStatus() == DrawStatus.COMPLETED) {
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }
        });
        //[DocRef: END]

    }

    private void initData() {
//        mPointMarker = GPSApplication.getInstance().getDbService().getPointMarkerDao().loadByRowId(getIntent().getLongExtra("id", 0)
        initBean(getIntent().getLongExtra("typeId", 0));
        initString(getIntent().getLongExtra("typeId", 0));


        mTvTitle.setText(title);
        for (String s : shortAttribute) {
            TextView tv = new TextView(this);
            tv.setText(s);
            tv.setTextSize(15);
            tv.setTextColor(Color.BLACK);
            tv.setPadding(40, 20, 40, 20);
            tv.setGravity(View.TEXT_ALIGNMENT_CENTER);
            tv.setLayoutParams(new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            llattribute.addView(tv, llattribute.getChildCount() - 1);
        }
        for (int i = 0; i < mPointMarker.getTtPoint().getPictures().size(); i++) {
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            Bitmap bitmap = null;
            try {
                File file = new File(mPointMarker.getTtPoint().getPictures().get(i).getPath());
                if (file.exists()) {
                    bitmap = BitmapFactory.decodeFile(mPointMarker.getTtPoint().getPictures().get(i).getPath());
                    imageView.setImageBitmap(bitmap);
                }
            } catch (Exception e) {
                // TODO: handle exception
            }
            welcomeflipper.addView(imageView);
        }
    }

    private void initBean(Long id) {
        switch (id.intValue()) {
            case 0:
                mPointBridge = GPSApplication.getInstance().getDbService().getPointBridgeDao().loadByRowId(getIntent().getLongExtra("id", 0));
                break;
            case 1:
                mPointTunnel = GPSApplication.getInstance().getDbService().getPointTunnelDao().loadByRowId(getIntent().getLongExtra("id", 0));
                break;
            case 2:
                mPointFerry = GPSApplication.getInstance().getDbService().getPointFerryDao().loadByRowId(getIntent().getLongExtra("id", 0));
                break;
            case 3:
                mPointCulvert = GPSApplication.getInstance().getDbService().getPointCulvertDao().loadByRowId(getIntent().getLongExtra("id", 0));
                break;
            case 4:
                mPointTown = GPSApplication.getInstance().getDbService().getPointTownDao().loadByRowId(getIntent().getLongExtra("id", 0));
                break;
            case 5:
                mPointStandardVillage = GPSApplication.getInstance().getDbService().getPointStandardVillageDao().loadByRowId(getIntent().getLongExtra("id", 0));
                break;
            case 6:
                mPointVillage = GPSApplication.getInstance().getDbService().getPointVillageDao().loadByRowId(getIntent().getLongExtra("id", 0));
                break;
            case 7:
                mPointSchool = GPSApplication.getInstance().getDbService().getPointSchoolDao().loadByRowId(getIntent().getLongExtra("id", 0));
                break;
            case 8:
                mPointSign = GPSApplication.getInstance().getDbService().getPointSignDao().loadByRowId(getIntent().getLongExtra("id", 0));
                break;
            case 9:
                mPointMarker = GPSApplication.getInstance().getDbService().getPointMarkerDao().loadByRowId(getIntent().getLongExtra("id", 0));
                break;
        }
    }

    private void initString(Long id) {
        switch (id.intValue()) {
            case 0:
                title=mPointBridge.getName();
                shortAttribute = new String[]{"桥梁名称:   " + mPointBridge.getName(),
                        "桥梁编码:   " + mPointBridge.getCode(),
                        "行政区划:   " + mPointBridge.getTtPoint().getAdminCode(),
                        "桥梁状态: " + mPointBridge.getStatus(),
                        "设计载荷:   " + mPointBridge.getDesignLoading(),
                        "按材料分类:   " + mPointBridge.getMaterialType(),
                        "按跨径分类:   " + mPointBridge.getSpanType(),
                        "是否危桥:   " + (mPointBridge.getDangeable() == 1 ? "是" : "否"),
                        "管养单位:   " + mPointBridge.getManagerOrg(),
                        "桥梁全长:   " + mPointBridge.getLength(),
                        "桥梁全宽:   " + mPointBridge.getWidth(),
                        "桥梁净宽:   " + mPointBridge.getClearWidth(),
                        "跨径总长:   " + mPointBridge.getSpanLength(),
                        "跨径总和:   " + mPointBridge.getSpanCombo(),
                        "建设时间:   " + mPointBridge.getBuildTime(),
                        "所属路线名称:   " + mPointBridge.getTtPoint().getPathName(),
                        "所属路线编码:   " + mPointBridge.getTtPoint().getPathCode(),
                        "路线序列号:   " + mPointBridge.getTtPoint().getSectionNo(),
                        "备注:   " + mPointBridge.getRemark()};
                moreAttribute = shortAttribute;
                mPictureList=mPointBridge.getTtPoint().getPictures();
                break;
            case 1:
                title=mPointTunnel.getName();
                shortAttribute = new String[]{"隧道名称:   " + mPointTunnel.getName(),
                        "隧道编码:   " + mPointTunnel.getCode(),
                        "行政区划:   " + mPointTunnel.getTtPoint().getAdminCode(),
                        "管养单位:   " + mPointTunnel.getManageOrg(),
                        "桥梁全长:   " + mPointTunnel.getLength(),
                        "隧道全宽:   " + mPointTunnel.getWidth(),
                        "隧道净高:   " + mPointTunnel.getHeigth(),
                        "建设年份:   " + mPointTunnel.getBuidYear(),
                        "所属路线编码:   " + mPointTunnel.getTtPoint().getPathCode(),
                        "备注" + mPointTunnel.getRemark()};
                moreAttribute = shortAttribute;
                mPictureList=mPointTunnel.getTtPoint().getPictures();
                break;
            case 2:
                title=mPointFerry.getName();
                shortAttribute = new String[]{"渡口名称:   " + mPointFerry.getName(),
                        "渡口编码:   " + mPointFerry.getCode(),
                        "行政区划:   " + mPointFerry.getTtPoint().getAdminCode(),
                        "管养单位:   " + mPointFerry.getManageOrg(),
                        "是否机动渡口:   "+mPointFerry.getFlexible(),
                        "渡口类型:   "+mPointFerry.getFerryType(),
                        "所属路线编码:   " + mPointFerry.getTtPoint().getPathCode(),
                        "备注" + mPointFerry.getRemark()};
                moreAttribute = shortAttribute;
                mPictureList=mPointFerry.getTtPoint().getPictures();
                break;
            case 3:
                title=mPointCulvert.getName();
                shortAttribute = new String[]{"涵洞名称:   " + mPointCulvert.getName(),
                        "涵洞编码:   " + mPointCulvert.getCode(),
                        "行政区划:   " + mPointCulvert.getTtPoint().getAdminCode(),
                        "涵洞中心柱号:   "+mPointCulvert.getCenterMarkNo(),
                        "涵洞跨径:   " + mPointCulvert.getSpan(),
                        "涵洞净高:   " + mPointCulvert.getHeight(),
                        "涵洞类型:   "+mPointCulvert.getBuildType(),
                        "建造性质:   "+mPointCulvert.getCategory(),
                        "备注:   " + mPointCulvert.getRemark()};
                moreAttribute = shortAttribute;
                mPictureList=mPointCulvert.getTtPoint().getPictures();
                break;
            case 4:
                title=mPointTown.getName();
                shortAttribute = new String[]{"乡镇名称:   " + mPointTown.getName(),
                        "乡镇编码:   " + mPointTown.getCode(),
                        "行政区划:   " + mPointTown.getTtPoint().getAdminCode(),
                        "乡镇人口:   "+mPointTown.getPopulation() ,
                        "所属建制村数量:   "+mPointTown.getStandardVillages() ,
                        "通达现状:   "+mPointTown.getArriveStatus() ,
                        "备注:   "+mPointTown. getRemark()};
                moreAttribute = new String[]{"乡镇名称:   " + mPointTown.getName(),
                        "乡镇编码:   " + mPointTown.getCode(),
                        "行政区划:   " + mPointTown.getTtPoint().getAdminCode(),
                        "乡镇人口:   "+mPointTown.getPopulation() ,
                        "所属建制村数量:   "+mPointTown.getStandardVillages() ,
                        "通达现状:   "+mPointTown.getArriveStatus() ,
                        "通达位置:   "+mPointTown.getArriveLocation(),
                        "通达方向:   "+mPointTown.getArriveDirection() ,
                        "优先通达路线行政等级:   "+mPointTown.getArriveLevel() ,
                        "优先通达路线名称:   "+mPointTown.getArrivePathName() ,
                        "优先通达路线编码:   "+mPointTown.getArrivePathCode() ,
                        "备注:   "+mPointTown. getRemark()};
                mPictureList=mPointTown.getTtPoint().getPictures();
                tvmore.setVisibility(View.VISIBLE);
                tvmore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        llattribute.removeViewsInLayout(0,llattribute.getChildCount()-2);
                        for (String s : moreAttribute) {
                            TextView tv = new TextView(DataQueryDetailActivity.this);
                            tv.setText(s);
                            tv.setTextSize(15);
                            tv.setTextColor(Color.BLACK);
                            tv.setPadding(40, 20, 40, 20);
                            tv.setGravity(View.TEXT_ALIGNMENT_CENTER);
                            tv.setLayoutParams(new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                            llattribute.addView(tv, llattribute.getChildCount() - 1);
                        }
                    }
                });
                break;
            case 5:
                title=mPointStandardVillage.getName();
                shortAttribute = new String[]{"建制村名称:   " + mPointStandardVillage.getName(),
                        "建制村编码:   " + mPointStandardVillage.getCode(),
                        "行政区划:   " + mPointStandardVillage.getTtPoint().getAdminCode(),
                        "乡镇人口:   "+mPointStandardVillage.getPopulation() ,
                        "所属自然村数量:   "+mPointStandardVillage.getVillages() ,
                        "通达现状:   "+mPointStandardVillage.getArriveStatus() ,
                        "备注:   "+mPointStandardVillage. getRemark()};
                moreAttribute = new String[]{"建制村名称:   " + mPointStandardVillage.getName(),
                        "建制村编码:   " + mPointStandardVillage.getCode(),
                        "行政区划:   " + mPointStandardVillage.getTtPoint().getAdminCode(),
                        "乡镇人口:   "+mPointStandardVillage.getPopulation() ,
                        "所属自然村数量:   "+mPointStandardVillage.getVillages() ,
                        "通达现状:   "+mPointStandardVillage.getArriveStatus() ,
                        "通达位置:   "+mPointStandardVillage.getArriveLocation(),
                        "通达方向:   "+mPointStandardVillage.getArriveDirection() ,
                        "优先通达路线行政等级:   "+mPointStandardVillage.getArriveLevel() ,
                        "优先通达路线名称:   "+mPointStandardVillage.getArrivePathName() ,
                        "优先通达路线编码:   "+mPointStandardVillage.getArrivePathCode() ,
                        "备注:   "+mPointStandardVillage. getRemark()};
                mPictureList=mPointStandardVillage.getTtPoint().getPictures();
                tvmore.setVisibility(View.VISIBLE);
                tvmore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        llattribute.removeViewsInLayout(0,llattribute.getChildCount()-2);
                        for (String s : moreAttribute) {
                            TextView tv = new TextView(DataQueryDetailActivity.this);
                            tv.setText(s);
                            tv.setTextSize(15);
                            tv.setTextColor(Color.BLACK);
                            tv.setPadding(40, 20, 40, 20);
                            tv.setGravity(View.TEXT_ALIGNMENT_CENTER);
                            tv.setLayoutParams(new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                            llattribute.addView(tv, llattribute.getChildCount() - 1);
                        }
                    }
                });
                break;
            case 6:
                title=mPointVillage.getName();
                shortAttribute = new String[]{"自然村名称:   " + mPointVillage.getName(),
                        "自然村编码:   " + mPointVillage.getCode(),
                        "人口数:   "+mPointVillage.getPopulation(),
                        "行政区划:   " + mPointVillage.getTtPoint().getAdminCode(),
                        "备注:   "+mPointVillage. getRemark()};
                moreAttribute = shortAttribute;
                mPictureList=mPointVillage.getTtPoint().getPictures();
                break;
            case 7:
                title=mPointSchool.getName();
                shortAttribute = new String[]{"学校名称:   " + mPointSchool.getName(),
                        "学校编码:   " + mPointSchool.getCode(),
                        "建成年份:   " + mPointSchool.getBuildYear(),
                        "学校类别:   " + mPointSchool.getCatergory(),
                        "行政区划:   " + mPointSchool.getTtPoint().getAdminCode(),
                        "备注:   "+mPointSchool. getRemark()};
                moreAttribute = shortAttribute;
                mPictureList=mPointSchool.getTtPoint().getPictures();
                break;
            case 8:
                title=mPointSign.getName();
                shortAttribute = new String[]{"标志标牌名称:   " + mPointSign.getName(),
                        "标志标牌编码:   " + mPointSign.getCode(),
                        "标志标牌类别:   " + mPointSign.getCatergory(),
                        "行政区划:   " + mPointSign.getTtPoint().getAdminCode(),
                        "备注:   "+mPointSign. getRemark()};
                moreAttribute = shortAttribute;
                mPictureList=mPointSign.getTtPoint().getPictures();
                break;
            case 9:
                title=mPointMarker.getName();
                shortAttribute = new String[]{"标识点名称:   " + mPointMarker.getName(),
                        "标识点编码:   " + mPointMarker.getCode(),
                        "标识点类别:   " + mPointMarker.getCatergory(),
                        "行政区划:   " + mPointMarker.getTtPoint().getAdminCode(),
                        "备注:   "+mPointMarker. getRemark()};
                moreAttribute = shortAttribute;
                mPictureList=mPointMarker.getTtPoint().getPictures();
                break;
        }
    }

    @Override
    protected void onPause() {
        mapView.pause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.resume();
    }


    private void showNext() {
        welcomeflipper.setInAnimation(this, R.anim.slide_in_right);
        welcomeflipper.setOutAnimation(this, R.anim.slide_out_left);
        welcomeflipper.showNext();
        setDotsBackgroud();
    }

    private void sendAutoMsg() {
        Message msgs = new Message();
        msgs.what = AUTO;
        mHandler.sendMessageDelayed(msgs, 2000);
    }

    private void setDotsBackgroud() {
        oldPos = currentPos;
        currentPos = welcomeflipper.getDisplayedChild();
        mDotList.get(oldPos).setBackgroundResource(R.drawable.dot_normal);
        mDotList.get(currentPos).setBackgroundResource(R.drawable.dot_focused);
    }
}
