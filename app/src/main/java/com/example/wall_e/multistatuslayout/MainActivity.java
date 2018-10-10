package com.example.wall_e.multistatuslayout;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_multiStatusLayout).setOnClickListener(v -> startActivity(new Intent(MainActivity.this,MultiStatusLayoutActivity.class)));
        findViewById(R.id.btn_multiStatusConstraintLayout).setOnClickListener(v -> startActivity(new Intent(MainActivity.this,MultiStatusConstraintLayoutActivity.class)));
    }
}
