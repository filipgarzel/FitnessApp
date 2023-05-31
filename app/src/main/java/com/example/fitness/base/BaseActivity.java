package com.example.fitness.base;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.fitness.adapters.AdapterForRecycler;
import com.example.fitness.db.MyDatabaseHelper;
import com.example.fitness.adapters.MyListAdapter;
import com.example.fitness.R;

import java.util.ArrayList;
import java.util.Objects;

public class BaseActivity extends AppCompatActivity{

    ListView listView;

    String[] maintitle ={
            "Chest","Back","Shoulders",
            "Triceps","Biceps",
            "Legs","ABS"
    };
    Integer[] imgid={
            R.drawable.chest,R.drawable.back,
            R.drawable.shoulder,R.drawable.triceps,
            R.drawable.biceps, R.drawable.leg,R.drawable.abs
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        String test = getIntent().getStringExtra("vis");
        String passName = getIntent().getStringExtra("scenName");
        String passDescr = getIntent().getStringExtra("scenDescr");


        MyListAdapter adapter= new MyListAdapter(this, maintitle, imgid);

        listView = (ListView)findViewById(R.id.CategoriesListView);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent();

                if(position == 0) {
                    intent.setClass(BaseActivity.this, ChestActivity.class);
                    intent.putExtra("category", "Chest");
                    intent.putExtra("passName", passName);
                    intent.putExtra("passDescription", passDescr);
                    if(Objects.equals(test, "yes")){
                        intent.putExtra("pass", "yup");
                    }

                    startActivity(intent);
                }
                if(position == 1) {
                    intent.setClass(BaseActivity.this, BackActivity.class);
                    intent.putExtra("category", "Back");
                    intent.putExtra("passName", passName);
                    intent.putExtra("passDescription", passDescr);
                    if(Objects.equals(test, "yes")){
                        intent.putExtra("pass", "yup");
                    }
                    startActivity(intent);

                }
                if(position == 2) {
                    intent.setClass(BaseActivity.this, ShouldersActivity.class);
                    intent.putExtra("category", "Shoulders");
                    intent.putExtra("passName", passName);
                    intent.putExtra("passDescription", passDescr);
                    if(Objects.equals(test, "yes")){
                        intent.putExtra("pass", "yup");
                    }
                    startActivity(intent);

                }
                if(position == 3) {
                    intent.setClass(BaseActivity.this, BicepsActivity.class);
                    intent.putExtra("category", "Biceps");
                    intent.putExtra("passName", passName);
                    intent.putExtra("passDescription", passDescr);
                    if(Objects.equals(test, "yes")){
                        intent.putExtra("pass", "yup");
                    }
                    startActivity(intent);

                }
                if(position == 4) {
                    intent.setClass(BaseActivity.this, TricepsActivity.class);
                    intent.putExtra("category", "Triceps");
                    intent.putExtra("passName", passName);
                    intent.putExtra("passDescription", passDescr);
                    if(Objects.equals(test, "yes")){
                        intent.putExtra("pass", "yup");
                    }
                    startActivity(intent);

                }
                if(position == 5) {
                    intent.setClass(BaseActivity.this, LegsActivity.class);
                    intent.putExtra("category", "Legs");
                    intent.putExtra("passName", passName);
                    intent.putExtra("passDescription", passDescr);
                    if(Objects.equals(test, "yes")){
                        intent.putExtra("pass", "yup");
                    }
                    startActivity(intent);

                }
                if(position == 6) {
                    intent.setClass(BaseActivity.this, ABSActivity.class);
                    intent.putExtra("category", "ABS");
                    intent.putExtra("passName", passName);
                    intent.putExtra("passDescription", passDescr);
                    if(Objects.equals(test, "yes")){
                        intent.putExtra("pass", "yup");
                    }
                    startActivity(intent);

                }
            }
        });

    }


    public void launchDodaj (View v){
        Intent i = new Intent(this, AddExerciseActivity.class);
        startActivity(i);
    }



}