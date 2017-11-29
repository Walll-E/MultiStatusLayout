package com.example.wall_e.multistatuslayout;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.wall_e.multiStatusLayout.MultiStatusLayout;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

public class SuccessActivity extends AppCompatActivity {

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            statusLayout.showContent();
            setAdapter();
        }
    };
    private MultiStatusLayout statusLayout;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);
        findViewById(R.id.iv_back).setOnClickListener(view -> finish());
        ((TextView) findViewById(R.id.tv_title)).setText("请求成功");
        statusLayout = findViewById(R.id.statusLayout);
        statusLayout.showLoading();
        handler.sendEmptyMessageDelayed(1, 2000);
        recyclerView = findViewById(R.id.recyclerView);
    }

    private void setAdapter(){
        List<String> items = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        for (int i = 0; i < 10; i++) {
            items.add("Wall-E");
        }
        recyclerView.setAdapter(new CommonAdapter<String>(this,R.layout.item,items) {
            @Override
            protected void convert(ViewHolder holder, String o, int position) {
                holder.setText(R.id.tv_name,o);
            }

        });

    }
}
