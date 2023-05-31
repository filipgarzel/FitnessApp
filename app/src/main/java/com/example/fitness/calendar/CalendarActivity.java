package com.example.fitness.calendar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fitness.R;
import com.example.fitness.db.MyDatabaseHelper;
import com.example.fitness.scenario.ShowScenarioActivity;

import java.util.ArrayList;

public class CalendarActivity extends AppCompatActivity {

    CalendarView cal;
    TextView schedule;
    MyDatabaseHelper myDB;
    ArrayList<Days> days;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ready_workouts);
        myDB = new MyDatabaseHelper(CalendarActivity.this);
        cal = findViewById(R.id.calendarView2);
        schedule = findViewById(R.id.schedule);
        days = myDB.returnAll();
        String summary = "";
        cal.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                String date = (i1 + 1) + "/" + i2 + "/" + i;
                Toast.makeText(CalendarActivity.this, date, Toast.LENGTH_SHORT).show();
            }
        });
        for(int i = 0; i< days.size(); i++){
            summary+= "Dnia: " + days.get(i).date + ", Trening: " + days.get(i).name + "\n";
        }
        schedule.setText(summary);

    }
}