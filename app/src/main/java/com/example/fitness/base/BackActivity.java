package com.example.fitness.base;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitness.adapters.AdapterForRecycler;
import com.example.fitness.db.MyDatabaseHelper;
import com.example.fitness.R;

import java.util.ArrayList;

public class BackActivity extends AppCompatActivity {

    static RecyclerView recyclerView;
    MyDatabaseHelper myDB;
    ArrayList<String> name, description, category;
    ArrayList<Integer> percent;
    static AdapterForRecycler adapter;
    Button deleteButton1;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_back);

        Intent i = getIntent();
        String cat = i.getStringExtra("category");
        recyclerView = findViewById(R.id.BackrecycleView);
        myDB = new MyDatabaseHelper(this);
        name = new ArrayList<>();
        description = new ArrayList<>();
        category = new ArrayList<>();
        percent = new ArrayList<>();

        id = getIntent().getStringExtra("passName");

        storeInArrays(cat);
        adapter = new AdapterForRecycler(BackActivity.this, name, description, category, percent, getIntent());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(BackActivity.this));
        deleteButton1 = (Button)findViewById(R.id.deleteBtn);


    }



    void storeInArrays(String injected) {
        Cursor cursor = myDB.injectData(injected);
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "Brak wyników", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                name.add(cursor.getString(1));
                description.add(cursor.getString(2));
                category.add(cursor.getString(3));
                percent.add(cursor.getInt(4));
            }
        }
    }


}


