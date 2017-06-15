package com.example.saverio.scoutingapp.Activities;


import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.saverio.scoutingapp.R;

import Database.DatabaseHandler;

public class DeleteActivity extends AppCompatActivity{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

        final EditText teamNumber = (EditText)findViewById(R.id.deleteteam_edittext);
        final Button delete = (Button)findViewById(R.id.confirmDeletion);
        final DatabaseHandler db = new DatabaseHandler(this);


        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String _teamNumber = teamNumber.getText().toString();

                if(_teamNumber.matches("")){

                    Toast.makeText(DeleteActivity.this, "Insert a team number", Toast.LENGTH_SHORT).show();


                }else{
                    if(isInteger(_teamNumber)) {
                        db.deleteTeam(Integer.parseInt(_teamNumber));
                        Toast.makeText(DeleteActivity.this, "Deleted team: "+_teamNumber, Toast.LENGTH_SHORT).show();
                        teamNumber.setText("");



                    }else{
                        Toast.makeText(DeleteActivity.this, "Only numbers", Toast.LENGTH_SHORT).show();

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
                Intent intent = new Intent(DeleteActivity.this, MainActivity.class);
                startActivity(intent);

                break;

            case R.id.insert_menu:

                Intent intent2 = new Intent(DeleteActivity.this, InsertActivity.class);
                startActivity(intent2);

                break;


            case R.id.delete_menu:
                break;

            case R.id.edit_menu:

                Intent intent3 = new Intent(DeleteActivity.this, EditActivity.class);
                startActivity(intent3);

                break;



        }
        return false;
    }
}
