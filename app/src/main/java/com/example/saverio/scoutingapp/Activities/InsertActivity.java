package com.example.saverio.scoutingapp.Activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.saverio.scoutingapp.R;

import Database.DatabaseHandler;
import Database.Team;

public class InsertActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        final EditText teamNumber = (EditText)findViewById(R.id.team_number);
        final EditText gearsPlaced = (EditText)findViewById(R.id.gears_placed);
        final CheckBox attempts = (CheckBox) findViewById(R.id.climb_attempt);
        final CheckBox climbed = (CheckBox) findViewById(R.id.climbed);
        final CheckBox nothing = (CheckBox) findViewById(R.id.nothing);
        final Button save = (Button)findViewById(R.id.confirmInsertion);
        final DatabaseHandler db = new DatabaseHandler(this);


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String _teamNumber = teamNumber.getText().toString();
                String _gearsPlaced = gearsPlaced.getText().toString();

                boolean checkedClimbed = climbed.isChecked();
                boolean checkedAttempts = attempts.isChecked();
                boolean checkedNothing = nothing.isChecked();


                    if (_teamNumber.matches("") || _gearsPlaced.matches("")) {

                        Toast.makeText(InsertActivity.this, "Complete all the fields", Toast.LENGTH_SHORT).show();

                    } else {
                        if (isInteger(_teamNumber) && isInteger(_gearsPlaced)) {


                            if(checkedClimbed == true) {
                                if(checkedAttempts == true || checkedNothing == true){

                                    Toast.makeText(InsertActivity.this, "Can't be all checked", Toast.LENGTH_SHORT).show();


                                }else{

                                    db.addTeam(new Team(Integer.parseInt(_teamNumber), Integer.parseInt(_gearsPlaced), 0, 1));
                                    Toast.makeText(InsertActivity.this, "team added", Toast.LENGTH_SHORT).show();
                                    teamNumber.setText("");
                                    gearsPlaced.setText("");
                                    attempts.setChecked(false);
                                    climbed.setChecked(false);
                                    nothing.setChecked(false);

                                }


                            }else if(checkedAttempts == true){
                                if(checkedClimbed == true||checkedNothing == true){


                                    Toast.makeText(InsertActivity.this, "Can't be all checked", Toast.LENGTH_SHORT).show();
                                }else{

                                    db.addTeam(new Team(Integer.parseInt(_teamNumber), Integer.parseInt(_gearsPlaced), 1, 0));
                                    Toast.makeText(InsertActivity.this, "team added", Toast.LENGTH_SHORT).show();
                                    teamNumber.setText("");
                                    gearsPlaced.setText("");
                                    attempts.setChecked(false);
                                    climbed.setChecked(false);
                                    nothing.setChecked(false);


                                }



                            }
                            else if(checkedNothing == true){
                                if(checkedClimbed == true||checkedAttempts == true){


                                    Toast.makeText(InsertActivity.this, "Can't be all checked", Toast.LENGTH_SHORT).show();
                                }else{

                                    db.addTeam(new Team(Integer.parseInt(_teamNumber), Integer.parseInt(_gearsPlaced), 0, 0));
                                    Toast.makeText(InsertActivity.this, "team added", Toast.LENGTH_SHORT).show();
                                    teamNumber.setText("");
                                    gearsPlaced.setText("");
                                    attempts.setChecked(false);
                                    climbed.setChecked(false);
                                    nothing.setChecked(false);


                                }



                            }else{
                                Toast.makeText(InsertActivity.this, "Check one box", Toast.LENGTH_SHORT).show();

                            }



                        }else{

                            Toast.makeText(InsertActivity.this, "Only numbers are accepted", Toast.LENGTH_SHORT).show();

                        }
                    }

        }

    });
    }
    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch(NumberFormatException e) {
            return false;
        } catch(NullPointerException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){

            case R.id.list_menu:
                Intent intent = new Intent(InsertActivity.this, MainActivity.class);
                startActivity(intent);

                break;

            case R.id.insert_menu:


                break;

            case R.id.delete_menu:

                Intent intent2 = new Intent(InsertActivity.this, DeleteActivity.class);
                startActivity(intent2);

                break;

            case R.id.edit_menu:

                Intent intent3 = new Intent(InsertActivity.this, EditActivity.class);
                startActivity(intent3);

                break;

        }
        return false;
    }

}