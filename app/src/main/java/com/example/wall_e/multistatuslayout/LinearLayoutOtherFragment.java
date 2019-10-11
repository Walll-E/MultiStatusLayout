package com.example.wall_e.multistatuslayout;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class LinearLayoutOtherFragment extends BaseFragment {


    public LinearLayoutOtherFragment() {
        // Required empty public constructor
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            statusLayout.showOther();
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_linear_layout_other;
    }

    @Override
    protected void initView() {
        statusLayout.showLoading();
        handler.sendEmptyMessageDelayed(1, 2000);
    }

}
