package com.tt.rds.app.activity;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
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

/**
 * Created by Alpha Dog on 2017/10/3.
 */

public class DataQueryDetailActivity extends AppCompatActivity {
    private com.esri.arcgisruntime.mapping.view.MapView mapView;
    private android.widget.ProgressBar progressBar;
    private android.support.design.widget.TabLayout relativelayout;
    private android.widget.TableLayout tlattribute;
    private android.widget.TextView tvmore;
    private android.widget.LinearLayout llattribute;
    private android.widget.ViewFlipper welcomeflipper;
    private View welcomdot1;
    private View welcomdot2;
    private View welcomdot3;
    private android.widget.LinearLayout llimgs;
    private com.github.mikephil.charting.charts.LineChart chart1;
    private com.github.mikephil.charting.charts.LineChart chart2;
    private android.widget.LinearLayout llcharts;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_query_detail);
        initToolBar();
        initView();
        initArcgisMap();
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
        tlattribute = (TableLayout) findViewById(R.id.tl_attribute);
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

//        mapView.setOnTouchListener(new DefaultMapViewOnTouchListener(this, mapView) {
//            @Override
//            public boolean  onSingleTapConfirmed(MotionEvent v) {
//                android.graphics.Point screenPoint=new android.graphics.Point(Math.round(v.getX()), Math.round(v.getY()));
//                Point clickPoint = mMapView.screenToLocation(screenPoint);
//                GraphicsOverlay graphicsOverlay_1=new GraphicsOverlay();
//                SimpleMarkerSymbol pointSymbol = new SimpleMarkerSymbol(SimpleMarkerSymbol.Style.DIAMOND, Color.RED, 10);
//                Graphic pointGraphic = new Graphic(clickPoint,pointSymbol);
//                graphicsOverlay_1.getGraphics().add(pointGraphic);
//                mMapView.getGraphicsOverlays().add(graphicsOverlay_1);
//                return true;
//            }
//        });

        // create an initial viewpoint using an envelope (of two points, bottom left and top right)
        Envelope envelope = new Envelope(new Point(12993828.5821309, 3706520.00454287, SpatialReferences.getWebMercator()),
                new Point(13098547.3108814, 3773861.02646202, SpatialReferences.getWebMercator()));
        //set viewpoint on mapview
//        mapView.setViewpointGeometryAsync(envelope, 100.0);
        mapView.setViewpointCenterAsync(new Point(12993828.5821309, 3706520.00454287, SpatialReferences.getWebMercator()),10000);
        mapView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        final GraphicsOverlay mGraphicsOverlay=new GraphicsOverlay();
        BitmapDrawable pinStarBlueDrawable = (BitmapDrawable) ContextCompat.getDrawable(this, R.drawable.point_collect);
        final PictureMarkerSymbol pinStarBlueSymbol = new PictureMarkerSymbol(pinStarBlueDrawable);
        //Optionally set the size, if not set the image will be auto sized based on its size in pixels,
        //its appearance would then differ across devices with different resolutions.
        pinStarBlueSymbol.setHeight(40);
        pinStarBlueSymbol.setWidth(40);
        //Optionally set the offset, to align the base of the symbol aligns with the point geometry
        pinStarBlueSymbol.setOffsetY(
                11); //The image used for the symbol has a transparent buffer around it, so the offset is not simply height/2
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

    @Override
    protected void onPause(){
        mapView.pause();
        super.onPause();
    }

    @Override
    protected void onResume(){
        super.onResume();
        mapView.resume();
    }
}
