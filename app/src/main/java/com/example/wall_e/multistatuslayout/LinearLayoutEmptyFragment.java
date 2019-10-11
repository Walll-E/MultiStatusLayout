package com.example.wall_e.multistatuslayout;


import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class LinearLayoutEmptyFragment extends BaseFragment {


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            statusLayout.showEmpty();
        }
    };


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_linear_layout_empty;
    }

    @Override
    protected void initView() {
        statusLayout.showLoading();
        handler.sendEmptyMessageDelayed(1, 2000);
    }


}
