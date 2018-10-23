package com.wall_e.multiStatusLayout;

import android.view.View;

public interface MultiStatusEvent {

    void showOther();

    void showLoading();

    void showEmpty();

    void showContent();

    void showError();

    void showNetError();

    View getOtherView();

    View getLoadingView();

    View getNetErrorView();

    View getEmptyView();

    View getErrorView();

    void setOtherView(int layoutResId);

    void setLoadingView(int layoutResId);

    void setNetErrorView(int layoutResId);

    void setEmptyView(int layoutResId);

    void setErrorView(int layoutResId);

    void setTargetViewId(int targetViewId);

    int getTargetViewId();

    int getShowViewType();

    void setOnReloadDataListener(final OnReloadDataListener onReloadDataListener);

}
