package com.example.wall_e.multistatuslayout;


import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class OtherFragment extends BaseFragment {




    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            statusLayout.showOther();
        }
    };


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_other;
    }

    @Override
    protected void initView() {
        statusLayout.showLoading();
        handler.sendEmptyMessageDelayed(1, 2000);
    }


}
