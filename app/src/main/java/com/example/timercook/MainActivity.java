package com.example.timercook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private RecipesFragment fragment_recipes = new RecipesFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment_recipes).commit();
    }
    public void spis_click (View view){
        Intent active = new Intent(MainActivity.this,BelyashiActivity.class);
        startActivity(active);
    }
}