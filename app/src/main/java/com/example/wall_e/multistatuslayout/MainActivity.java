package com.example.wall_e.multistatuslayout;

import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.wall_e.multiStatusLayout.MultiStatusFrameLayout;
import com.wall_e.multiStatusLayout.MultiStatusLinearLayout;
import com.wall_e.multiStatusLayout.interf.OnContentReferenceIdsAction;
import com.wall_e.multiStatusLayout.interf.OnLoadingReferenceIdsAction;

import java.util.List;

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
    private MultiStatusLinearLayout constraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        constraintLayout = findViewById(R.id.statusLayout);
        constraintLayout.showLoading();
        handler.sendEmptyMessageDelayed(1, 2000);
        constraintLayout.setOnContentReferenceIdsAction(referenceViews -> {
            if (referenceViews == null) return;
            for (View view : referenceViews) {
                if (view.getId() == R.id.actionButtonCenter)
                    view.setVisibility(View.GONE);
                else
                    view.setVisibility(View.VISIBLE);
            }
        });
        constraintLayout.setOnLoadingReferenceIdsAction(referenceViews -> {
            if (referenceViews == null) return;
            for (View view : referenceViews) {
                view.setVisibility(View.VISIBLE);
            }
        });
        findViewById(R.id.btn_multiStatusLayout).setOnClickListener(v -> startActivity(new Intent(MainActivity.this, MultiStatusLayoutActivity.class)));
        findViewById(R.id.btn_multiStatusConstraintLayout).setOnClickListener(v -> startActivity(new Intent(MainActivity.this, MultiStatusConstraintLayoutActivity.class)));
        findViewById(R.id.btn_linearLayout).setOnClickListener(v -> startActivity(new Intent(MainActivity.this, MultiStatusLinearLayoutActivity.class)));
    }
}
