package com.example.wall_e.multistatuslayout;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.wall_e.multiStatusLayout.MultiStatusConstraintLayout;

/**
 * 请移步布局文件看看TargetViewId用法
 */
public class MainActivity extends AppCompatActivity {

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            constraintLayout.showContent();
        }
    };
    private MultiStatusConstraintLayout constraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        constraintLayout = findViewById(R.id.statusLayout);
        constraintLayout.showLoading();
        handler.sendEmptyMessageDelayed(1, 2000);
        findViewById(R.id.btn_multiStatusLayout).setOnClickListener(v -> startActivity(new Intent(MainActivity.this,MultiStatusLayoutActivity.class)));
        findViewById(R.id.btn_multiStatusConstraintLayout).setOnClickListener(v -> startActivity(new Intent(MainActivity.this,MultiStatusConstraintLayoutActivity.class)));
    }
}
