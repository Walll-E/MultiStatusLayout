package com.example.wall_e.multistatuslayout;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wall_e.multiStatusLayout.MultiStatusConstraintLayout;


/**
 * A simple {@link Fragment} subclass.
 */
public class ConstraintOtherFragment extends BaseFragment {


    public ConstraintOtherFragment() {
        // Required empty public constructor
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            statusLayout.showOther();
        }
    };
    MultiStatusConstraintLayout statusLayout;
    private boolean hasLoadFInish;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_constraint_other, container, false);
        statusLayout = view.findViewById(R.id.statusLayout);
        isPrepared = true;
        lazyLoad();
        return view;
    }

    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible || hasLoadFInish)return;
        hasLoadFInish = true;
        statusLayout.showLoading();
        handler.sendEmptyMessageDelayed(1, 2000);
    }
}
