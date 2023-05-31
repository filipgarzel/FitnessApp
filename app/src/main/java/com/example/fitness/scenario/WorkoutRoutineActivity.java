package com.example.fitness.scenario;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.fitness.db.MyDatabaseHelper;
import com.example.fitness.R;
import com.example.fitness.adapters.ScenariosAdapter;
import com.example.fitness.base.BaseActivity;

import java.util.ArrayList;

public class WorkoutRoutineActivity extends AppCompatActivity {

    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private EditText workoutName, description;
    private Button saveButton, cancelButton;
    private MyDatabaseHelper dbHelper;
    private ArrayList<String> scenName, scenDesc;
    static ScenariosAdapter myAdapter;
    static RecyclerView recycler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_routine);
        recycler = findViewById(R.id.scenariosRecycler);
        dbHelper = new MyDatabaseHelper(WorkoutRoutineActivity.this);
        scenName = new ArrayList<>();
        scenDesc = new ArrayList<>();

        storeInArrays();
        myAdapter = new ScenariosAdapter(WorkoutRoutineActivity.this, scenName, scenDesc);

        recycler.setAdapter(myAdapter);
        recycler.setLayoutManager(new LinearLayoutManager(WorkoutRoutineActivity.this));


    }

    public void launchBaza(View v) {
        Intent i = new Intent(this, BaseActivity.class);
        startActivity(i);
    }



    public void launcher(View v) {
        //to do, if brak planu to days else to Baza
    }


    public void createNewWorkout(View view) {
        builder = new AlertDialog.Builder(this);
        final View popupView = getLayoutInflater().inflate(R.layout.popup, null);
        workoutName = (EditText) popupView.findViewById(R.id.repsEditTxt);
        description = (EditText) popupView.findViewById(R.id.seriesEditTxt);
        saveButton = (Button) popupView.findViewById(R.id.savePopupBtn);
        cancelButton = (Button) popupView.findViewById(R.id.cancelBtn);

        builder.setView(popupView);
        dialog = builder.create();
        dialog.show();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(WorkoutRoutineActivity.this);
                myDB.addScenario(workoutName.getText().toString().trim(),
                        description.getText().toString().trim(),
                        null, 0, 0);
                finish();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    void storeInArrays() {
        Cursor cursor = dbHelper.injectDataScenarios();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "Brak wyników", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                scenName.add(cursor.getString(0));
                scenDesc.add(cursor.getString(1));

            }
        }
    }

}