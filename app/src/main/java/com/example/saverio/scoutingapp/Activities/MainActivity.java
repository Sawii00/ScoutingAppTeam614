package com.example.saverio.scoutingapp.Activities;

import android.content.Intent;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.saverio.scoutingapp.R;

import Database.DatabaseHandler;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private DatabaseHandler db;
    private SimpleCursorAdapter dataAdapter;
    private static final String[]paths = {"Gears", "Climbed", "RP"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHandler(this);
        Spinner spinner = (Spinner)findViewById(R.id.spinner);
        spinner.bringToFront();
        ArrayAdapter<String>adapter = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_spinner_item, paths);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        displayListViewSorted(paths[0]);



    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch (i){



            case 0:
                String type = "Gears";
                displayListViewSorted(type);

                break;

            case 1:
                String type1 = "Climbed";
                displayListViewSorted(type1);

                break;
            case 2:
                String type2 = "RP";
                displayListViewSorted(type2);

                break;



        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    private void displayListView(){

        Cursor cursor = db.fetchAllTeams();


        String[] columns = new String[] {

                DatabaseHandler.KEY_TEAMNUMBER,
                DatabaseHandler.KEY_GEARSPLACED_TOT,
                DatabaseHandler.KEY_CLIMBATTEMPTS_TOT,
                DatabaseHandler.KEY_CLIMBED_TOT
        };

        int[] to = new int[] {
                R.id.textview_teamnumber,
                R.id.textview_gearsplaced,
                R.id.textview_attempts,
                R.id.textview_climbed
        };
        dataAdapter = new SimpleCursorAdapter(
                this, R.layout.team_list_layout,
                cursor,
                columns,
                to,
                0);

        ListView listView = (ListView) findViewById(R.id.teamList);
        // Assign adapter to ListView
        listView.setAdapter(dataAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

            }
        });




    }


    private void displayListViewSorted(String type){

        Cursor cursor = db.sortData(type);

        String[] columns = new String[] {

                DatabaseHandler.KEY_TEAMNUMBER,
                DatabaseHandler.KEY_GEARSPLACED_TOT,
                DatabaseHandler.KEY_CLIMBATTEMPTS_TOT,
                DatabaseHandler.KEY_CLIMBED_TOT,
                DatabaseHandler.KEY_AVGGEARS,
                DatabaseHandler.KEY_AVGCLIMBS
        };

        int[] to = new int[] {
                R.id.textview_teamnumber,
                R.id.textview_gearsplaced,
                R.id.textview_attempts,
                R.id.textview_climbed,
                R.id.textview_avggears,
                R.id.textview_avgclimbs
        };
        dataAdapter = new SimpleCursorAdapter(
                this, R.layout.team_list_layout,
                cursor,
                columns,
                to,
                0);

        ListView listView = (ListView) findViewById(R.id.teamList);
        // Assign adapter to ListView
        listView.setAdapter(dataAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Cursor cur = (Cursor)dataAdapter.getItem(position);
                cur.moveToPosition(position);
                int id_ = cur.getInt(cur.getColumnIndexOrThrow("_id"));




                Intent intent = new Intent(MainActivity.this, SingleTeamActivity.class);
                intent.putExtra("teamNumber",id_);
                startActivity(intent);



            }
        });


    }





        @Override
        public boolean onCreateOptionsMenu (Menu menu){
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu, menu);
            return true;
        }

        @Override
        protected void onPause() {
            super.onPause();
            finish();
        }

        @Override
        public boolean onOptionsItemSelected (MenuItem item){
            int id = item.getItemId();
            switch (id) {

                case R.id.list_menu:


                    break;

                case R.id.insert_menu:

                    Intent intent = new Intent(MainActivity.this, InsertActivity.class);
                    startActivity(intent);

                    break;
                case R.id.delete_menu:

                    Intent intent2 = new Intent(MainActivity.this, DeleteActivity.class);
                    startActivity(intent2);

                    break;
                case R.id.edit_menu:

                    Intent intent3 = new Intent(MainActivity.this, EditActivity.class);
                    startActivity(intent3);

                    break;


            }
            return false;
        }


}
