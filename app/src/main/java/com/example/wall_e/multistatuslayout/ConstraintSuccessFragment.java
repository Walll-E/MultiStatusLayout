package com.example.wall_e.multistatuslayout;

import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
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



    private RecyclerView recyclerView;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_constraint_loading;
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
        recyclerView.setAdapter(new CommonAdapter<String>(this.getContext(),R.layout.item,items) {
            @Override
            protected void convert(ViewHolder holder, String o, int position) {
                holder.setText(R.id.tv_name,o);
            }
        });
    }

}
