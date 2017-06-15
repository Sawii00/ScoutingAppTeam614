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

public class EditActivity extends AppCompatActivity{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        final EditText teamNumber = (EditText)findViewById(R.id.team_number_edit);
        final EditText gears = (EditText)findViewById(R.id.gears_edit);
        final CheckBox attempts = (CheckBox) findViewById(R.id.attempts_edit);
        final CheckBox climbs = (CheckBox) findViewById(R.id.climbs_edit);
        final CheckBox nothing = (CheckBox) findViewById(R.id.nothing_edit);
        final EditText nmatch = (EditText)findViewById(R.id.match_edit);

        final Button edit = (Button)findViewById(R.id.confirmEdit);
        final DatabaseHandler db = new DatabaseHandler(this);





        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String _teamnumber = teamNumber.getText().toString();
                String _gears = gears.getText().toString();
                String _nmatch = nmatch.getText().toString();

                boolean checkedClimbed = climbs.isChecked();
                boolean checkedAttempts = attempts.isChecked();
                boolean checkedNothing = nothing.isChecked();

                if (_teamnumber.matches("") || _gears.matches("")||_nmatch.matches("")){

                    Toast.makeText(EditActivity.this, "Complete all the fields", Toast.LENGTH_SHORT).show();




                }else{
                    if (isInteger(_teamnumber) && isInteger(_gears) && isInteger(_nmatch)){

                        if(checkedClimbed == true) {
                            if(checkedAttempts == true || checkedNothing == true){

                                Toast.makeText(EditActivity.this, "Can't be all checked", Toast.LENGTH_SHORT).show();


                            }else{

                                db.editTeam(Integer.parseInt(_teamnumber),Integer.parseInt(_gears),0,1,Integer.parseInt(_nmatch));
                                Toast.makeText(EditActivity.this, "team edited", Toast.LENGTH_SHORT).show();
                                teamNumber.setText("");
                                gears.setText("");
                                nmatch.setText("");
                                attempts.setChecked(false);
                                climbs.setChecked(false);
                                nothing.setChecked(false);

                            }


                        }else if(checkedAttempts == true){
                            if(checkedClimbed == true||checkedNothing == true){


                                Toast.makeText(EditActivity.this, "Can't be all checked", Toast.LENGTH_SHORT).show();
                            }else{

                                db.editTeam(Integer.parseInt(_teamnumber),Integer.parseInt(_gears),1,0,Integer.parseInt(_nmatch));
                                Toast.makeText(EditActivity.this, "team edited", Toast.LENGTH_SHORT).show();
                                teamNumber.setText("");
                                gears.setText("");
                                nmatch.setText("");
                                attempts.setChecked(false);
                                climbs.setChecked(false);
                                nothing.setChecked(false);


                            }



                        }
                        else if(checkedNothing == true){
                            if(checkedClimbed == true||checkedAttempts == true){


                                Toast.makeText(EditActivity.this, "Can't be all checked", Toast.LENGTH_SHORT).show();
                            }else{

                                db.editTeam(Integer.parseInt(_teamnumber),Integer.parseInt(_gears),0,0,Integer.parseInt(_nmatch));
                                Toast.makeText(EditActivity.this, "team edited", Toast.LENGTH_SHORT).show();
                                teamNumber.setText("");
                                gears.setText("");
                                nmatch.setText("");
                                attempts.setChecked(false);
                                climbs.setChecked(false);
                                nothing.setChecked(false);


                            }



                        }else{
                            Toast.makeText(EditActivity.this, "Check one box", Toast.LENGTH_SHORT).show();

                        }

                    }else{

                        Toast.makeText(EditActivity.this, "Only numbers accepted", Toast.LENGTH_SHORT).show();

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
                Intent intent = new Intent(EditActivity.this, MainActivity.class);
                startActivity(intent);

                break;

            case R.id.insert_menu:

                Intent intent2 = new Intent(EditActivity.this, InsertActivity.class);
                startActivity(intent2);

                break;


            case R.id.delete_menu:

                Intent intent3 = new Intent(EditActivity.this, DeleteActivity.class);
                startActivity(intent3);

                break;

            case R.id.edit_menu:
                break;



        }
        return false;
    }
}
