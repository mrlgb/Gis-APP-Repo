package cn.edu.hfuu.gis.gisapp.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.esri.arcgisruntime.layers.ArcGISMapImageLayer;
import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.view.MapView;

import java.util.List;

import cn.edu.hfuu.gis.gisapp.R;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private BottomSheetBehavior mBottomSheetBehavior;
    private View mBottomSheet;
    private GridView gv;

    private int count = 0;
    private MapView mMapView;

    private Button mSearchButton;
    private BootstrapButton showall_button, hide_button;
    private List<String> collectPointList;
    private ArrayAdapter<String> gridViewArrayAdapter;


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initActivity(Bundle savedInstanceState) {
        super.initActivity(savedInstanceState);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initToolBarAndSearchBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mSearchButton = (Button) findViewById(R.id.bSearch);
        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);

            }
        });


        initArcgisMap();


        //Implement click listeners
        findViewById(R.id.collect_button_main).setOnClickListener(this);
        findViewById(R.id.line_button_main).setOnClickListener(this);
        findViewById(R.id.query_button_main).setOnClickListener(this);
//
        showall_button = (BootstrapButton) findViewById(R.id.del_button);
        hide_button = (BootstrapButton) findViewById(R.id.hide_button);

        mBottomSheet = findViewById(R.id.bottomSheet);

        initBottomSheet();

        gv = (GridView) findViewById(R.id.gv);
        initBottomGridView();
    }


    private void initBottomGridView() {
        // Populate a List from Array elements
        collectPointList = getApp().getCollectPointList();

        // Create a new ArrayAdapter
        gridViewArrayAdapter = new ArrayAdapter<String>
                (this, R.layout.list_item_tag, collectPointList);

        // Data bind GridView with ArrayAdapter (String Array elements)
        gv.setAdapter(gridViewArrayAdapter);

        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the GridView selected/clicked item text
                String selectedItem = parent.getItemAtPosition(position).toString();

                // Display the selected/clicked item text
                Toast.makeText(getApplicationContext(), selectedItem, Toast.LENGTH_SHORT).show();
            }
        });

//        add_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                addItemInGV(collectPointList, gridViewArrayAdapter);
//                mBottomSheetBehavior.setPeekHeight(100 * (collectPointList.size() / 3 + 1));
//                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
//            }
//        });

        showall_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItemInGV(collectPointList, gridViewArrayAdapter);
                mBottomSheetBehavior.setPeekHeight(100 * (collectPointList.size() / 3 + 1));
                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });

        hide_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            }
        });
    }

    private void initBottomSheet() {
        mBottomSheetBehavior = BottomSheetBehavior.from(mBottomSheet);

        //By default set BottomSheet Behavior as Collapsed and Height 0
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        mBottomSheetBehavior.setPeekHeight(0);
        mBottomSheetBehavior.setSkipCollapsed(false);

//
//        //If you want to handle callback of Sheet Behavior you can use below code
        mBottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
//                switch (newState) {
//                    case BottomSheetBehavior.STATE_COLLAPSED:
//                        Log.d(TAG, "State Collapsed");
//                        break;
//                    case BottomSheetBehavior.STATE_DRAGGING:
//                        Log.d(TAG, "State Dragging");
//                        break;
//                    case BottomSheetBehavior.STATE_EXPANDED:
//                        Log.d(TAG, "State Expanded");
//                        break;
//                    case BottomSheetBehavior.STATE_HIDDEN:
//                        Log.d(TAG, "State Hidden");
//                        break;
//                    case BottomSheetBehavior.STATE_SETTLING:
//                        Log.d(TAG, "State Settling");
//                        break;
//                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            }
        });
    }

    private void initArcgisMap() {
        // inflate MapView from layout
        mMapView = (MapView) findViewById(R.id.mapView);
        // create a MapImageLayer with dynamically generated map images
        ArcGISMapImageLayer mapImageLayer = new ArcGISMapImageLayer(getResources().getString(R.string.sample_service_url));
        // create an empty map instance
        ArcGISMap map = new ArcGISMap();
        // add map image layer as operational layer
        map.getOperationalLayers().add(mapImageLayer);
        // set the map to be displayed in this view
        mMapView.setMap(map);
    }

    private void removeItemInGV(List<String> plantsList, ArrayAdapter<String> gridViewArrayAdapter) {
        // Get the second item from ArrayAdapter
        String secondItemText = plantsList.get(1);

        // Remove/delete second item from ArrayAdapter
        // Delete index position 1 item from ArrayAdapter
        // ArrayAdapter is zero based index
        plantsList.remove(1);

        // Update the GridView
        gridViewArrayAdapter.notifyDataSetChanged();

        // Confirm the deletion
        Toast.makeText(getApplicationContext(),
                "Removed : " + secondItemText, Toast.LENGTH_SHORT).show();
    }

    private void addItemInGV(List<String> plantsList, ArrayAdapter<String> gridViewArrayAdapter) {
        count++;
        plantsList.add(plantsList.size(), "hello" + count);

        // Update the GridView
        gridViewArrayAdapter.notifyDataSetChanged();

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.collect_button_main:
                Log.d(TAG, count + "");
                mBottomSheetBehavior.setPeekHeight(200);
                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                break;
//            case R.id.bottom_sheet_collapse:
//                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
//                break;
//            case R.id.bottom_sheet_hide:
//                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
//                break;
//            case R.id.selecnormal_button:
//                mBottomSheetBehavior.setPeekHeight(200);
//                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
////                gridLayout.removeAllViews();
//                break;
//            case R.id.selectall_button:

            //
//                mBottomSheetBehavior.setPeekHeight(100*(plantsList.size()/3+1));
//                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
//                break;
//            case R.id.hideall_button:
//                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
//                break;


        }
    }


    public static int dpToPx(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    public void initToolBarAndSearchBar(Toolbar toolbar) {
        //设置我们的ToolBar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_collectsum) {
            // Handle the camera action
        } else if (id == R.id.nav_pointsetting) {

        } else if (id == R.id.nav_emptystorage) {

        } else if (id == R.id.nav_feedback) {

        } else if (id == R.id.nav_switchuser) {

        } else if (id == R.id.nav_about) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.resume();
    }
}
