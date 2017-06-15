package Database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private SQLiteDatabase db = this.getWritableDatabase();


    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "scoutingManager";

    public static final String TABLE_TEAMS = "Teams";
    public static final String TABLE_MATCH = "Match";

    //ROBOTS TABLE

    public static final String KEY_TEAMNUMBER = "_id";
    public static final String KEY_GEARSPLACED_TOT = "gears_placed_tot";
    public static final String KEY_CLIMBATTEMPTS_TOT = "climbed_attempts_tot";
    public static final String KEY_CLIMBED_TOT = "climbed_tot";
    public static final String KEY_MATCH_TOT = "match_tot";
    public static final String KEY_RANKINGPOINTS_TOT = "rp";
    public static final String KEY_AVGGEARS = "avg_gears";
    public static final String KEY_AVGCLIMBS = "avg_climbs";

    //MATCH TABLE
    public static final String KEY_TEAMNUMBER_MATCH = "teamNumber";
    public static final String KEY_GEARSPLACED = "gears_placed";
    public static final String KEY_CLIMBATTEMPTS = "climbed_attempts";
    public static final String KEY_CLIMBED = "climbed";
    public static final String KEY_MATCH = "match";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TEAMS_TABLE = "CREATE TABLE " + TABLE_TEAMS +
                "(" + KEY_TEAMNUMBER + " INTEGER," +
                KEY_GEARSPLACED_TOT + " INTEGER," +
                KEY_CLIMBATTEMPTS_TOT + " INTEGER," +
                KEY_CLIMBED_TOT + " INTEGER," +
                KEY_MATCH_TOT + " INTEGER," +
                KEY_RANKINGPOINTS_TOT + " INTEGER," +
                KEY_AVGGEARS + " REAL," +
                KEY_AVGCLIMBS + " REAL" + ")";
        db.execSQL(CREATE_TEAMS_TABLE);
        String CREATE_MATCH_TABLE = "CREATE TABLE " + TABLE_MATCH +
                "(" + KEY_TEAMNUMBER_MATCH + " INTEGER," +
                KEY_GEARSPLACED + " INTEGER," +
                KEY_CLIMBATTEMPTS + " INTEGER," +
                KEY_CLIMBED + " INTEGER," +
                KEY_MATCH + " INTEGER" + ")";
        db.execSQL(CREATE_MATCH_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TEAMS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MATCH);

        // Create tables again
        onCreate(db);
    }

    public void setTeamsTable(int teamNumber) {

        Cursor cursor_gears = db.rawQuery("SELECT SUM(gears_placed)" + " AS gears FROM " + TABLE_MATCH + " WHERE " + KEY_TEAMNUMBER_MATCH + " = " + teamNumber, null);
        Cursor cursor_climbs = db.rawQuery("SELECT SUM(climbed)" + " AS totClimbs FROM " + TABLE_MATCH + " WHERE " + KEY_TEAMNUMBER_MATCH + " = " + teamNumber, null);
        Cursor cursor_climbs_attempts = db.rawQuery("SELECT SUM(climbed_attempts)" + " AS totAttempts FROM " + TABLE_MATCH + " WHERE " + KEY_TEAMNUMBER_MATCH + " = " + teamNumber, null);
        Cursor cursor_matches = db.rawQuery("SELECT MAX(match) AS matches_max FROM " + TABLE_MATCH + " WHERE " + KEY_TEAMNUMBER_MATCH + " = " + teamNumber, null);

        Cursor cursor_avggears = db.rawQuery("SELECT AVG(gears_placed)" + " AS avggears FROM " + TABLE_MATCH + " WHERE " + KEY_TEAMNUMBER_MATCH + " = " + teamNumber, null);
        Cursor cursor_avgclimbs = db.rawQuery("SELECT AVG(climbed)" + " AS avgclimbs FROM " + TABLE_MATCH + " WHERE " + KEY_TEAMNUMBER_MATCH + " = " + teamNumber, null);

        //TO BE HANDLED: WHEN THERE ARE NO TEAMS

        cursor_gears.moveToFirst();
        cursor_climbs.moveToFirst();
        cursor_climbs_attempts.moveToFirst();
        cursor_matches.moveToFirst();
        cursor_avggears.moveToFirst();
        cursor_avgclimbs.moveToFirst();

        int gears = cursor_gears.getInt(cursor_gears.getColumnIndex("gears"));
        int attempts = cursor_climbs_attempts.getInt(cursor_climbs_attempts.getColumnIndex("totAttempts"));
        int climbs = cursor_climbs.getInt(cursor_climbs.getColumnIndex("totClimbs"));
        int matches = cursor_matches.getInt(cursor_matches.getColumnIndex("matches_max"));

        /*DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.CEILING);*/



        double avggears = cursor_avggears.getDouble(cursor_avggears.getColumnIndex("avggears"));
        double avgclimbs = cursor_avgclimbs.getDouble(cursor_avgclimbs.getColumnIndex("avgclimbs"));

        double avgears_result = (double) Math.round(avggears * 100d) / 100d;
        double avclimbs_result = (double) Math.round(avgclimbs * 100d) / 100d;

        /*Log.d("PIPPOBAUDO",""+a1);
        Log.d("PIPPOBAUDO",""+a2);
        Log.d("PIPPOBAUDO",""+a3);
        Log.d("PIPPOBAUDO",""+a4);*/

        //Log.d("SETTEAMSTABLE", ""+gears);
        int rp = gears * 15 + climbs * 5 + attempts * 3;

        ContentValues values = new ContentValues();
        values.put(KEY_TEAMNUMBER, teamNumber);
        values.put(KEY_GEARSPLACED_TOT, gears);
        values.put(KEY_CLIMBATTEMPTS_TOT, attempts);
        values.put(KEY_CLIMBED_TOT, climbs);
        values.put(KEY_MATCH_TOT, matches);
        values.put(KEY_RANKINGPOINTS_TOT, rp);
        values.put(KEY_AVGGEARS, avgears_result);
        values.put(KEY_AVGCLIMBS, avclimbs_result);

        // Inserting Row


        if (matches == 1) {

            db.insert(TABLE_TEAMS, null, values);


        } else {

            db.update(TABLE_TEAMS, values, "_id=" + teamNumber, null);


        }


    }

    public void debugDatabase() {

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_TEAMS, null);

        String colteamn = cursor.getColumnName(0);
        String colgear = cursor.getColumnName(1);
        String colclimattempt = cursor.getColumnName(2);
        String colclimb = cursor.getColumnName(3);
        String colmatch = cursor.getColumnName(4);
        String colrp = cursor.getColumnName(5);

        Log.d("DebugColName: ", colteamn);
        Log.d("DebugColName: ", colgear);
        Log.d("DebugColName: ", colclimattempt);
        Log.d("DebugColName: ", colclimb);
        Log.d("DebugColName: ", colmatch);
        Log.d("DebugColName: ", colrp);

        while (cursor.moveToNext()) {
            int teamNumberVal = cursor.getInt(0);
            int gearsVal = cursor.getInt(1);
            int attemptVal = cursor.getInt(2);
            int climbVal = cursor.getInt(3);
            int matchVal = cursor.getInt(4);
            int rpVal = cursor.getInt(5);

            Log.d("DebugVal: ", "" + teamNumberVal);
            Log.d("DebugVal: ", "" + gearsVal);
            Log.d("DebugVal: ", "" + attemptVal);
            Log.d("DebugVal: ", "" + climbVal);
            Log.d("DebugVal: ", "" + matchVal);
            Log.d("DebugVal: ", "" + rpVal);
        }


    }


    // Adding new team
    public void addTeam(Team team) {

        Cursor cursor = db.rawQuery("SELECT MAX(match) AS max FROM " + TABLE_MATCH + " WHERE " + KEY_TEAMNUMBER_MATCH + " = " + team.getTeamNumber(), null);

        //int pippo = cursor.getInt(0);

        if (isCursorEmpty(cursor)) {
            ContentValues values = new ContentValues();

            values.put(KEY_TEAMNUMBER_MATCH, team.getTeamNumber());
            values.put(KEY_GEARSPLACED, team.getGearsPlaced());
            values.put(KEY_CLIMBATTEMPTS, team.getClimbAttempts());
            values.put(KEY_CLIMBED, team.getClimbed());
            values.put(KEY_MATCH, 1);

            // Inserting Row
            db.insert(TABLE_MATCH, null, values);
        } else {
            cursor.moveToFirst();
            ContentValues values = new ContentValues();

            values.put(KEY_TEAMNUMBER_MATCH, team.getTeamNumber());
            values.put(KEY_GEARSPLACED, team.getGearsPlaced());
            values.put(KEY_CLIMBATTEMPTS, team.getClimbAttempts());
            values.put(KEY_CLIMBED, team.getClimbed());
            values.put(KEY_MATCH, cursor.getInt(cursor.getColumnIndex("max")) + 1);

            //CHECK THIS INDEX FOR THE MATCH... NOT SURE.

            int pippo = cursor.getInt(cursor.getColumnIndex("max")) + 1;
            Log.d("asdasd", "" + pippo);

            // Inserting Row
            db.insert(TABLE_MATCH, null, values);
        }

        setTeamsTable(team.getTeamNumber());


    }

    public boolean isCursorEmpty(Cursor cursor) {
        if (!cursor.moveToFirst() || cursor.getCount() == 0) return true;
        return false;
    }


    // Getting single contact
    public Team getTeam(String teamNumber) {

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_TEAMS + " WHERE " + KEY_TEAMNUMBER + " = " + teamNumber, null);
        if (cursor != null)
            cursor.moveToFirst();

        Team team = new Team(cursor.getInt(0),
                cursor.getInt(1), cursor.getInt(2), cursor.getInt(3));
        // return contact
        return team;
    }




    //---deletes a particular title---
    public boolean deleteTeam(int teamNumber) {

        return db.delete(TABLE_TEAMS, KEY_TEAMNUMBER + "=" + teamNumber, null) > 0 && db.delete(TABLE_MATCH, KEY_TEAMNUMBER_MATCH + "=" + teamNumber, null) > 0;
    }


    public void editTeam(int teamNumber,  int gearsPlaced,  int attempts, int climbs, int matchN) {


             ContentValues contentValues = new ContentValues();

             contentValues.put(KEY_TEAMNUMBER_MATCH, teamNumber);
             contentValues.put(KEY_GEARSPLACED, gearsPlaced);
             contentValues.put(KEY_CLIMBATTEMPTS, attempts);
             contentValues.put(KEY_CLIMBED, climbs);
             contentValues.put(KEY_MATCH, matchN);

             db.update(TABLE_MATCH, contentValues,"teamNumber="+teamNumber+" AND match="+matchN, null);

            setTeamsTable(teamNumber);




    }
        /*
            public void editTeam(int team, int gears, int attempts, int climbs, int matches){
                SQLiteDatabase db = this.getWritableDatabase();
                ContentValues values = new ContentValues();

                int rankingPoints = gears*5 + climbs*15 + attempts*3;


                values.put(KEY_GEARSPLACED, gears);
                values.put(KEY_CLIMBATTEMPTS, attempts);
                values.put(KEY_CLIMBED, climbs);
                values.put(KEY_MATCH, matches);
                values.put(KEY_RANKINGPOINTS, rankingPoints);

                db.update(TABLE_TEAMS,values,"_id="+team,null);



            }
            */

    public Cursor fetchAllTeams() {

        Cursor mCursor = db.rawQuery("SELECT * FROM " + TABLE_TEAMS, null);

        /*mCursor.move(4);
        Log.d("FETCH",""+mCursor.getInt(0));
        Log.d("FETCH",""+mCursor.getInt(1));
        Log.d("FETCH",""+mCursor.getInt(2));
        Log.d("FETCH",""+mCursor.getInt(3));
        Log.d("FETCH",""+mCursor.getInt(4));*/

        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public Cursor sortData(String type) {
        Cursor cursor = db.rawQuery("", null);

        if (type == "Gears") {
            cursor = db.query(TABLE_TEAMS, new String[] {
                    KEY_TEAMNUMBER,
                    KEY_GEARSPLACED_TOT,
                    KEY_CLIMBATTEMPTS_TOT,
                    KEY_CLIMBED_TOT,
                    KEY_AVGGEARS,
                    KEY_AVGCLIMBS
            }, null, null, null, null, KEY_GEARSPLACED_TOT + " DESC");
            if (cursor != null) {
                cursor.moveToFirst();
            }

        } else if (type == "Climbed") {

            cursor = db.query(TABLE_TEAMS, new String[] {
                    KEY_TEAMNUMBER,
                    KEY_GEARSPLACED_TOT,
                    KEY_CLIMBATTEMPTS_TOT,
                    KEY_CLIMBED_TOT,
                    KEY_AVGGEARS,
                    KEY_AVGCLIMBS
            }, null, null, null, null, KEY_CLIMBED_TOT + " DESC");
            if (cursor != null) {
                cursor.moveToFirst();
            }


        } else if (type == "RP") {

            cursor = db.query(TABLE_TEAMS, new String[] {
                    KEY_TEAMNUMBER,
                    KEY_GEARSPLACED_TOT,
                    KEY_CLIMBATTEMPTS_TOT,
                    KEY_CLIMBED_TOT,
                    KEY_AVGGEARS,
                    KEY_AVGCLIMBS
            }, null, null, null, null, KEY_RANKINGPOINTS_TOT + " DESC");
            if (cursor != null) {
                cursor.moveToFirst();
            }


        } else {
            //PASS
        }

        return cursor;

    }

}