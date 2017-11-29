package com.example.wall_e.multistatuslayout;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_content).setOnClickListener(view -> startActivity(new Intent(this, SuccessActivity.class)));
        findViewById(R.id.btn_empty).setOnClickListener(view -> startActivity(new Intent(this, EmptyActivity.class)));
        findViewById(R.id.btn_error).setOnClickListener(view -> startActivity(new Intent(this, ErrorActivity.class)));
        findViewById(R.id.btn_netError).setOnClickListener(view -> startActivity(new Intent(this, NetErrorActivity.class)));
    }
}
