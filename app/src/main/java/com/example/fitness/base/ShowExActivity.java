package com.example.fitness.base;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.fitness.db.MyDatabaseHelper;
import com.example.fitness.R;
import com.example.fitness.main.MainActivity;

import java.util.Objects;

public class ShowExActivity extends AppCompatActivity {

    TextView passedName, passedDescription;
    Button deleteButton1, add;
    String id;
    MyDatabaseHelper myDB;
    private EditText nrOfReps, nrOfSeries;
    private Button saveButton, cancelButton;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_ex);
        add = findViewById(R.id.addBtn);
        Intent i = getIntent();
        String name11 = i.getStringExtra("passName");
        Intent intent = getIntent();
        String name112 = intent.getStringExtra("passDescription");
        Intent in = getIntent();
        String test = in.getStringExtra("give");
        String test1 = in.getStringExtra("passN");
        String test2 = in.getStringExtra("passD");

        if(Objects.equals(test, "go")){
            add.setVisibility(View.VISIBLE);
        }else{
            add.setVisibility(View.INVISIBLE);
        }



        passedName = findViewById(R.id.nameOfExTxtView);
        passedDescription = findViewById(R.id.descShowExTxtView);

        passedName.setText(name11);
        passedDescription.setText(name112);

        id = getIntent().getStringExtra("passName");

        deleteButton1 = findViewById(R.id.deleteBtn);
        myDB = new MyDatabaseHelper(this);



        deleteButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDialog();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                repsPopup(test1, test2, name11);
            }
        });
    }
    public void confirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        Intent intent = new Intent(this, BaseActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        builder.setTitle("Delete this exercise?");
        builder.setMessage("Are you sure you want to delete this exercise?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                myDB.deleteRecord(id);
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

    public void repsPopup(String name, String description, String exercise){
        builder = new AlertDialog.Builder(this);
        final View popupView = getLayoutInflater().inflate(R.layout.reps_popup, null);
        nrOfReps = popupView.findViewById(R.id.repsEditTxt);
        nrOfSeries =  popupView.findViewById(R.id.seriesEditTxt);
        saveButton = (Button) popupView.findViewById(R.id.savePopupBtn);
        cancelButton = (Button) popupView.findViewById(R.id.cancelBtn);

        //int repsPop = (int)Integer.parseInt(nrOfReps.getText().toString());
        //int seriesPop= (int)Integer.parseInt(nrOfSeries.getText().toString());

        builder.setView(popupView);
        dialog = builder.create();
        dialog.show();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int repsPop = (int)Integer.parseInt(nrOfReps.getText().toString());
                int seriesPop= (int)Integer.parseInt(nrOfSeries.getText().toString());
                addExercise(name, description, exercise, repsPop, seriesPop);
                Intent intent = new Intent(ShowExActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    public void addExercise(String name1, String Description1, String exercise1, int reps, int series){
        myDB.addScenario(name1, Description1, exercise1, reps, series);
    }


}