package com.example.wall_e.multistatuslayout;


import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class ErrorFragment extends BaseFragment {




    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            statusLayout.showError();
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_error;
    }

    @Override
    protected void initView() {
        statusLayout.showLoading();
        statusLayout.setOnReloadDataListener(() -> {
            statusLayout.showLoading();
            handler.sendEmptyMessageDelayed(1, 2000);
        });
        handler.sendEmptyMessageDelayed(1, 2000);
    }

}
