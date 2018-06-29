package com.chrisahn.popularmovies.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.chrisahn.popularmovies.R;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_container, new MainFragment(), "MAINFRAGMENT_TAG").commit();
    }
}
