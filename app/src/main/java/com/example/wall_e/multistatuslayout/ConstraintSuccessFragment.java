package com.example.wall_e.multistatuslayout;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wall_e.multiStatusLayout.MultiStatusConstraintLayout;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

public class ConstraintSuccessFragment extends BaseFragment {


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            statusLayout.showContent();
            setAdapter();
        }
    };



    private MultiStatusConstraintLayout statusLayout;
    private RecyclerView recyclerView;
    private boolean hasLoadFinish;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_constraint_loading, container, false);
        statusLayout = view.findViewById(R.id.statusLayout);
        recyclerView = view.findViewById(R.id.recyclerView);
        isPrepared = true;
        lazyLoad();
        return view;
    }


    private void setAdapter() {
        List<String> items = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            items.add("Wall-E");
        }
        recyclerView.setAdapter(new CommonAdapter<String>(this.getContext(),R.layout.item,items) {
            @Override
            protected void convert(ViewHolder holder, String o, int position) {
                holder.setText(R.id.tv_name,o);
            }
        });
    }

    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible || hasLoadFinish)return;
        hasLoadFinish = true;
        statusLayout.showLoading();
        handler.sendEmptyMessageDelayed(1, 2000);
    }
}
