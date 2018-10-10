package com.example.wall_e.multistatuslayout;

import android.support.v4.app.Fragment;

public abstract class BaseFragment extends Fragment {

    protected boolean isVisible;
    protected boolean isPrepared;
    /**
     * 在这里实现Fragment数据的缓加载.
     *
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }


    /**
     * 可见
     */
    protected void onVisible() {
        lazyLoad();
    }

    /**
     * 懒加载
     */
    protected abstract void lazyLoad();

    /**
     * 不可见
     */
    protected void onInvisible() {
    }

}
