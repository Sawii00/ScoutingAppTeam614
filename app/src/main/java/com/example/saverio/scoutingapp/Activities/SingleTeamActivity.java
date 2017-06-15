package com.example.saverio.scoutingapp.Activities;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;


import com.example.saverio.scoutingapp.R;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import Database.DatabaseHandler;


public class SingleTeamActivity extends AppCompatActivity{

    DatabaseHandler database;
    SQLiteDatabase db;

    int teamNumber;

    DataPoint[] dataset;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singleteam);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        database = new DatabaseHandler(this);


        Bundle data = getIntent().getExtras();
        teamNumber = data.getInt("teamNumber");

        db = database.getReadableDatabase();


        graphGears();
        graphClimbs();


        //TO DO: MAKE THE AXIS MIN AND MAX FIXED NUMBERS.
        //TO DO: ZOOM IN/OUT



    }

    public void graphGears() {

        GraphView graph = (GraphView) findViewById(R.id.graph_gear);

        Cursor cursor = db.rawQuery("SELECT * FROM "+ DatabaseHandler.TABLE_MATCH+" WHERE "+DatabaseHandler.KEY_TEAMNUMBER_MATCH + " = "+teamNumber,null);
        Cursor cursor1 = db.rawQuery("SELECT MAX(match) AS matches_max FROM "+DatabaseHandler.TABLE_MATCH+" WHERE "+DatabaseHandler.KEY_TEAMNUMBER_MATCH+" = "+teamNumber,null);
        cursor1.moveToFirst();
        int max_match = cursor1.getInt(cursor1.getColumnIndex("matches_max"));


        dataset = new DataPoint[max_match];

        int counter = 0;


        while (cursor.moveToNext()) {
            int gears = cursor.getInt(1);
            int match = cursor.getInt(4);

            dataset[counter] = new DataPoint(match,gears);
            counter++;
        }

        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dataset);
        series.setDrawDataPoints(true);
        series.setDataPointsRadius(6);

        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(0);
        graph.getViewport().setMaxY(6);

        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(1);
        graph.getViewport().setMaxX(12);

        graph.getGridLabelRenderer().setNumVerticalLabels(7);
        graph.getGridLabelRenderer().setNumHorizontalLabels(12);



        graph.getViewport().setScrollable(false); // enables horizontal scrolling

        graph.setTitle("Gears");
        graph.setTitleTextSize(40f);





        graph.addSeries(series);


    }



    public void graphClimbs() {

        GraphView graph = (GraphView) findViewById(R.id.graph_climb);

        Cursor cursor = db.rawQuery("SELECT * FROM "+ DatabaseHandler.TABLE_MATCH+" WHERE "+DatabaseHandler.KEY_TEAMNUMBER_MATCH + " = "+teamNumber,null);
        Cursor cursor1 = db.rawQuery("SELECT MAX(match) AS matches_max FROM "+DatabaseHandler.TABLE_MATCH+" WHERE "+DatabaseHandler.KEY_TEAMNUMBER_MATCH+" = "+teamNumber,null);
        cursor1.moveToFirst();
        int max_match = cursor1.getInt(cursor1.getColumnIndex("matches_max"));


        dataset = new DataPoint[max_match];

        int counter = 0;

        while (cursor.moveToNext()) {
            int gears = cursor.getInt(3);
            int match = cursor.getInt(4);

            dataset[counter] = new DataPoint(match,gears);
            counter++;
        }

        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dataset);
        series.setDrawDataPoints(true);
        series.setDataPointsRadius(6);

        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(0);
        graph.getViewport().setMaxY(1);

        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(1);
        graph.getViewport().setMaxX(12);

        graph.getGridLabelRenderer().setNumVerticalLabels(2);
        graph.getGridLabelRenderer().setNumHorizontalLabels(12);



        graph.getViewport().setScrollable(false); // enables horizontal scrolling

        graph.setTitle("Climbs");
        graph.setTitleTextSize(40f);





        graph.addSeries(series);


    }


    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                Intent intent = new Intent(SingleTeamActivity.this, MainActivity.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

}
