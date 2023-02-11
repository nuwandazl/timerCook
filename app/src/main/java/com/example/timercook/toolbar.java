package com.example.timercook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toolbar;

public class toolbar extends AppCompatActivity {
    private ImageButton right_i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toolbar);
        right_i = findViewById(R.id.right_icon);
        right_i.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent active = new Intent(toolbar.this, BelyashiActivity.class);
                startActivity(active);
            }
        });
    }
}