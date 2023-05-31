package com.example.fitness.base;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.fitness.db.MyDatabaseHelper;
import com.example.fitness.R;

public class AddExerciseActivity extends AppCompatActivity {

    EditText name_input, description_input, level_input;
    Spinner category;
    Button add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exercises);

        name_input = findViewById(R.id.nameOfExAddEditTxt);
        description_input = findViewById(R.id.descAddExEditTxt);
        level_input = findViewById(R.id.rpeEditTxt);
        category = findViewById(R.id.spinnerAddEx);
        add = findViewById(R.id.addExBtn);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(AddExerciseActivity.this);
                myDB.addEx(name_input.getText().toString().trim(),
                        description_input.getText().toString().trim(),
                        category.getSelectedItem().toString().trim(),
                        Integer.valueOf(level_input.getText().toString().trim()));
                finish();

                }
        });

    }

}