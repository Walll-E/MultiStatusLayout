package com.example.wall_e.multistatuslayout;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.wall_e.multiStatusLayout.MultiStatusLayout;

public class ErrorActivity extends AppCompatActivity {

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            statusLayout.showError();
        }
    };
    private MultiStatusLayout statusLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error);
        findViewById(R.id.iv_back).setOnClickListener(view -> finish());
        ((TextView) findViewById(R.id.tv_title)).setText("服务器错误");
        statusLayout = findViewById(R.id.statusLayout);
        statusLayout.showLoading();
        handler.sendEmptyMessageDelayed(1, 2000);
        statusLayout.setOnReloadDataListener(() -> {
            statusLayout.showLoading();
            handler.sendEmptyMessageDelayed(1, 2000);
        });
    }
}
