package com.gashe.app_subactivities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SubActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        Intent intent = new Intent();
        intent.putExtra("NAME", "Pepa Pig");
        setResult(8, intent);
        finish();

    }
}
