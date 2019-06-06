package com.example.wall_e.multistatuslayout;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wall_e.multiStatusLayout.MultiStatusLayout;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoadingFragment extends BaseFragment {


    public LoadingFragment() {
        // Required empty public constructor
    }


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            statusLayout.showContent();
            setAdapter();
        }
    };


    private RecyclerView recyclerView;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_loading;
    }

    @Override
    protected void initView() {
        recyclerView = mView.findViewById(R.id.recyclerView);
        statusLayout.showLoading();
        handler.sendEmptyMessageDelayed(1, 2000);
    }

    private void setAdapter() {
        List<String> items = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            items.add("Wall-E");
        }
        recyclerView.setAdapter(new CommonAdapter<String>(this.getContext(), R.layout.item, items) {
            @Override
            protected void convert(ViewHolder holder, String o, int position) {
                holder.setText(R.id.tv_name, o);
            }
        });
    }
}
