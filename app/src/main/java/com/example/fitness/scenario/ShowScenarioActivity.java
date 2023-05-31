package com.example.fitness.scenario;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fitness.calendar.Days;
import com.example.fitness.db.MyDatabaseHelper;
import com.example.fitness.R;
import com.example.fitness.base.BaseActivity;
import com.example.fitness.main.MainActivity;

import java.util.ArrayList;

public class ShowScenarioActivity extends AppCompatActivity {

    TextView passedName, passedDescription, listOfExercises, calories, timeOfTraining, pickeddays, helper;
    static MyDatabaseHelper myDB;
    Button add, saveBtn, cancelBtn, delete, pick;
    AlertDialog dialog;
    AlertDialog.Builder builder;
    DatePicker simpleDatePicker;
    String id;
    ArrayList<String> exercises;
    ArrayList<Integer> lvl,reps, series;
    ArrayList<Workout> workouts;
    ArrayList<Days> days;
    CalendarView pickInCalendar;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_scenario);
        Intent i = getIntent();
        String passedN = i.getStringExtra("passNameScen");
        Intent intent = getIntent();
        String passedD = intent.getStringExtra("passDescriptionScen");
        exercises = new ArrayList<>();
        lvl = new ArrayList<>();
        reps = new ArrayList<>();
        series = new ArrayList<>();
        myDB = new MyDatabaseHelper(ShowScenarioActivity.this);
        String exercises = "";
        String plan = "";
        int number = 0;
        int time = 0;
        id = getIntent().getStringExtra("scenName");

        pickeddays = findViewById(R.id.days);
        helper = findViewById(R.id.nieUsuwac);

        delete = findViewById(R.id.deleteScenBtn);
        passedName = findViewById(R.id.scenNameTxtView);
        passedDescription = findViewById(R.id.descScenTxtView);
        passedName.setText(passedN);
        passedDescription.setText(passedD);
        listOfExercises = findViewById(R.id.exercisesTxtView);
        listOfExercises.setMovementMethod(new ScrollingMovementMethod());
        calories = findViewById(R.id.caloriesTxtView);
        timeOfTraining = findViewById(R.id.trainingTimeTxtView);
        workouts = myDB.getData(passedN);
        days = myDB.getDate(passedN);
        for (int j = 0; j < workouts.size(); j++) {
            exercises += workouts.get(j).workOutName + ": [" + workouts.get(j).reps + " reps] [" +
                    workouts.get(j).series + " series] \n ";
            number += Math.round(workouts.get(j).level * workouts.get(j).reps * workouts.get(j).series / 50) ;
            time += Math.round(workouts.get(j).reps * workouts.get(j).series / 7);
        }
        for (int k = 0; k < days.size(); k++){
            plan+=days.get(k).date + "\n ";
        }

        listOfExercises.setText(exercises);
        calories.setText(String.valueOf(number));
        timeOfTraining.setText(String.valueOf(time));
        pickeddays.setText(plan);

        add = findViewById(R.id.addExScenBtn);
        pick = findViewById(R.id.pickDayBtn);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShowScenarioActivity.this, BaseActivity.class);
                intent.putExtra("vis", "yes");
                intent.putExtra("scenName", passedName.getText());
                intent.putExtra("scenDescr", passedDescription.getText());

                startActivity(intent);

            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDialogScenario(passedN);
            }
        });

        pick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar(passedN);
            }
        });


    }




    public void confirmDialogScenario(String nm) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        builder.setTitle("Delete this scenario?");
        builder.setMessage("Are you sure you want to delete this scenario?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                myDB.deleteRecordScenario(nm);
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }


    public void calendar(String nm) {
        builder = new AlertDialog.Builder(this);
        View popupView = getLayoutInflater().inflate(R.layout.popup_calendar, null);
        saveBtn = (Button) popupView.findViewById(R.id.saveBtn);
        cancelBtn = (Button) popupView.findViewById(R.id.dismissBtn);
        pickInCalendar = popupView.findViewById(R.id.calendarView1);

        builder.setView(popupView);

        pickInCalendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                String date = (i1 + 1) + "/" + (i2) + "/" + i;
                Toast.makeText(getApplicationContext(), date, Toast.LENGTH_SHORT).show();

                helper.setText(date);
            }
        });
        dialog = builder.create();
        dialog.show();
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDB.confirmDate(helper.getText().toString(), nm);
                Intent i = new Intent(ShowScenarioActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }








    /*
    public void displayList() {
        Cursor cursor = myDB.injectDataScenarios();
        String text = "";
        int number = 0;
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "Brak wyników", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                exercises.add(cursor.getString(0));
            }

        }
    }
    public void displayCalories(){
        Cursor cursor = myDB.injectToList();
        String text="";
        int number=0;
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "Brak wyników", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                lvl.add(cursor.getInt(1));
                reps.add(cursor.getInt(2));
                series.add(cursor.getInt(3));
            }

        }
    }
 */
}

