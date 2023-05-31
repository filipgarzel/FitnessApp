package com.example.fitness.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.fitness.calendar.Days;
import com.example.fitness.scenario.Workout;

import java.util.ArrayList;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String dbName = "Exercises.db";
    private static final int versionOfDB = 6;
    private static final String tableName = "Exercises";
    private static final String table1Name = "Scenarios";
    private static final String table2Name = "Dates";

    private static final String ExIdColumn = "_id";
    private static final String NameOfExColumn = "Nazwa";
    private static final String DescriptionColumn = "Opis";
    private static final String CategoryColumn = "Kategoria";
    private static final String PercentColumn = "Poziom_trudności";
    private static final String NameofScenario = "Nazwa_scenariuszu";
    private static final String NameofExInScenario = "Nazwa_cwiczenia_w_scenariuszu";
    private static final String DescOfScenario = "Opis_scenariusza";
    private static final String Reps = "Powtórzenia";
    private static final String Series = "Serie";
    private static final String Day = "Dzień";
    ArrayList<Workout> workout = new ArrayList<>();
    ArrayList<Days> plan = new ArrayList<>();


    public MyDatabaseHelper(@Nullable Context context ) {
        super(context, dbName, null, versionOfDB);
        this.context=context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE IF NOT EXISTS " + tableName + " (" +
                ExIdColumn + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                NameOfExColumn + " TEXT, " + DescriptionColumn +
                " TEXT, " + CategoryColumn + " TEXT, " +
                PercentColumn + " INTEGER) ;";
        String query1 = "CREATE TABLE " + table1Name + " (" +
                NameofScenario + " TEXT, " + DescOfScenario +
                " TEXT, " + NameofExInScenario + " TEXT, " + Reps + " INTEGER, " +
                Series + " INTEGER) ;";
        String query2 = "CREATE TABLE IF NOT EXISTS " + table2Name + " (" +
                Day + " TEXT, " + NameofScenario +
                " TEXT) ;";
        db.execSQL(query);
        db.execSQL(query1);
        db.execSQL(query2);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + tableName);
        db.execSQL("DROP TABLE IF EXISTS " + table1Name);
        db.execSQL("DROP TABLE IF EXISTS " + table2Name);
        onCreate(db);
    }

    //Exercises part
    public void addEx(String cv_name, String cv_description, String cv_category, int cv_level){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(NameOfExColumn, cv_name);
        cv.put(DescriptionColumn, cv_description);
        cv.put(CategoryColumn, cv_category);
        cv.put(PercentColumn, cv_level);
        long result = db.insert(tableName, null, cv);
        if(result == -1){
            Toast.makeText(context, "Nie udało się dodać ćwiczenia", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Dodano nowe ćwiczenie", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }


    public Cursor injectData(String inject_category){
        String query = "SELECT * FROM " + tableName + " WHERE " +
                CategoryColumn + "=" + "\"" + inject_category + "\"";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }


    public void deleteRecord(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        //db.delete(dbName, "Nazwa=?", new String[]{row_id});
        db.execSQL("DELETE FROM " + tableName+ " WHERE "+NameOfExColumn+"="+ "\"" + row_id+"\"");
        Toast.makeText(context, "Usunięto ćwiczenie", Toast.LENGTH_SHORT).show();

        db.close();
    }


    //Scenarios part
    public void addScenario(String name, String descr, String exercise, int reps, int series){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(NameofScenario, name);
        cv.put(DescOfScenario, descr);
        cv.put(NameofExInScenario, exercise);
        cv.put(Reps, reps);
        cv.put(Series, series);

        long result = db.insert(table1Name, null, cv);
        if(result == -1){
            Toast.makeText(context, "Nie udało się dodać scenariusza", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Dodano nowy scenariusz", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }


    public Cursor injectDataScenarios(){
        String query = "SELECT Scenarios.Nazwa_scenariuszu, " +
                "Scenarios.Opis_scenariusza" +
                " FROM " + table1Name + " WHERE Scenarios.Serie = 0 ";
        //+ ", " + tableName +
          //      " WHERE Exercises.Nazwa = Scenarios.Nazwa_cwiczenia_w_scenariuszu" ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }


    public void deleteRecordScenario(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + table1Name+ " WHERE "+NameofScenario+"="+ "\"" + row_id+"\"");
        Toast.makeText(context, "Usunięto scenariusz", Toast.LENGTH_SHORT).show();

        db.close();
    }


    public ArrayList<Workout> getData(String name){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT Exercises.Nazwa, Exercises.Poziom_trudności, Scenarios.Powtórzenia, " +
                "Scenarios.Serie "
                + " FROM " +
                tableName + ", " + table1Name + " WHERE Scenarios.Nazwa_scenariuszu = " +
                "\"" + name + "\"" + " AND Exercises.Nazwa = Scenarios.Nazwa_cwiczenia_w_scenariuszu";
        Cursor cursor = db.rawQuery(query, null);
        while(cursor.moveToNext()){
            Workout ex = new Workout(cursor.getString(0), cursor.getInt(1),
                    cursor.getInt(2), cursor.getInt(3));
            workout.add(ex);
        }
        return workout;
    }

    public void confirmDate(String date, String name){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Day, date);
        cv.put(NameofScenario, name);
        long result = db.insert(table2Name, null, cv);
        if(result == -1){
            Toast.makeText(context, "Nie udało się dodać do planu", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Dodano do planu", Toast.LENGTH_SHORT).show();
        }
        db.close();

    }

    public ArrayList<Days> getDate(String name){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " +
                table2Name  + " WHERE " + NameofScenario + " = " + "\"" + name + "\"";
        Cursor cursor = db.rawQuery(query, null);
        while(cursor.moveToNext()){
            Days days = new Days(cursor.getString(0), cursor.getString(1));
            plan.add(days);
        }
        return plan;
    }

    public ArrayList<Days> returnAll(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " +
                table2Name;
        Cursor cursor = db.rawQuery(query, null);
        while(cursor.moveToNext()){
            Days days = new Days(cursor.getString(0), cursor.getString(1));
            plan.add(days);
        }
        return plan;
    }

    /*
    Cursor injectToList(){
        String query = "SELECT Exercises.Nazwa, Exercises.Poziom_trudności, Scenarios.Reps, Scenarios.Series "
                + " FROM " +
                tableName + ", " + table1Name ;
                //+ " WHERE Exercises.Nazwa = Scenarios.Nazwa_cwiczenia_w_scenariuszu";
        //+ ", " + tableName +
        //      " WHERE Exercises.Nazwa = Scenarios.Nazwa_cwiczenia_w_scenariuszu" ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }
    */

}
