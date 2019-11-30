package com.example.ggd;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {


    LocationManager locationManager;
    ArrayList<StationInfo.Station> stations;

    RecyclerView recyclerView;
    MyAdapter adapter;

    Button cheerBtn, tipBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cheerBtn = findViewById(R.id.cheerBtn);
        tipBtn = findViewById(R.id.tipBtn);

        cheerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CheerActivity.class);
                startActivity(intent);
            }
        });

        tipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MainActivity.this, TipActivity.class);
                startActivity(intent);
            }
        });
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }

        final SwipeRefreshLayout mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_layout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getLocation();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
        //getLocation();

        adapter = new MyAdapter(new ArrayList<String>(), this.getApplicationContext());
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        getLocation();
    }

    void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        double lat = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER).getLatitude();
        double lon = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER).getLongitude();

        requestToDaum(lon,lat);
    }


    void requestToDaum(double lon, double lat) {

        NetworkHelper.getInstance().GetCoordinate(lon,lat).enqueue(new Callback<CoordinateTM>() {
            @Override
            public void onResponse(Call<CoordinateTM> call, Response<CoordinateTM> response) {
                List<CoordinateTM.Document> doc = response.body().getDocuments();
                if (doc.size() > 0 ) {
                    double x = doc.get(0).getX();
                    double y = doc.get(0).getY();
                    Log.e("TM", doc.get(0).getX() + " / " + doc.get(0).getY());
                    requestToSeoul(x,y);
                }
            }

            @Override
            public void onFailure(Call<CoordinateTM> call, Throwable t) {
                Log.e("error",t.getLocalizedMessage());
            }
        });
    }

    void requestToSeoul(double x, double y) {
        NetworkHelper.getSeoulInstance().GetStationInfo(0,10,x,y).enqueue(new Callback<StationInfo>() {
            @Override
            public void onResponse(Call<StationInfo> call, Response<StationInfo> response) {
                stations = response.body().stationList;
                List<String> stationNames = new ArrayList<>();
                for (StationInfo.Station i : stations) {
                    stationNames.add(i.statnNm);
                    Log.e(i.statnNm,i.subwayNm);
                }
                List<String> list2 = Arrays.asList(getString(R.string.subways).split(","));

                unioning(stationNames,list2);

            }

            @Override
            public void onFailure(Call<StationInfo> call, Throwable t) {
                Log.e("error",t.getLocalizedMessage());
            }
        });
    }


    void unioning(List<String> list1, List<String> list2) {
        list1.retainAll(list2);

        //Toast.makeText(this, list1.toString(), Toast.LENGTH_SHORT).show();
        if (list1.size() > 0) {
            //Intent intent = new Intent(MainActivity.this, SeekActivity.class);
            //intent.putExtra("station", list1.get(0));
            adapter.setList(list1);
            adapter.notifyDataSetChanged();
            //startActivity(intent);
        } else {
            Toast.makeText(this, "가까이 있는 10 정거장 안에 화장실이 없어요!", Toast.LENGTH_SHORT).show();
        }
    }

}