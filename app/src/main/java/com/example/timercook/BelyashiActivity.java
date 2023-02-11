package com.example.timercook;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class BelyashiActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_belyashi);

        ListView listView = (ListView)findViewById(R.id.list_view);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Ingredienty,
                android.R.layout.simple_list_item_1);
        listView.setAdapter(adapter);
    }
}