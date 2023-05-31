package com.example.fitness.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.example.fitness.R;
import com.example.fitness.base.BaseActivity;
import com.example.fitness.calendar.CalendarActivity;
import com.example.fitness.scenario.WorkoutRoutineActivity;

public class MainActivity extends AppCompatActivity {
    Button b1,b2,b3;
    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = findViewById(R.id.webView);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://www.budujmase.pl/trening/trening-techniczny-technika-cwiczen-na-silowni.html");
        b1 = findViewById(R.id.baseBtn);
        b2 = findViewById(R.id.calendarBtn);
        b3 = findViewById(R.id.scenarioBtn);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, BaseActivity.class);
                startActivity(i);
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, CalendarActivity.class);
                startActivity(i);
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, WorkoutRoutineActivity.class);
                startActivity(i);
            }
        });
    }
}

