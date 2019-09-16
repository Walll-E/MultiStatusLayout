package com.wall_e.multiStatusLayout;

import android.view.View;

import com.wall_e.multiStatusLayout.interf.OnContentReferenceIdsAction;
import com.wall_e.multiStatusLayout.interf.OnEmptyReferenceIdsAction;
import com.wall_e.multiStatusLayout.interf.OnErrorReferenceIdsAction;
import com.wall_e.multiStatusLayout.interf.OnLoadingReferenceIdsAction;
import com.wall_e.multiStatusLayout.interf.OnNetErrorReferenceIdsAction;
import com.wall_e.multiStatusLayout.interf.OnOtherReferenceIdsAction;

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

    void setNetErrorReloadViewId(int netErrorReloadViewId);

    void setErrorReloadViewId(int errorReloadViewId);

    void setOnContentReferenceIdsAction(OnContentReferenceIdsAction onContentReferenceIdsAction);

    void setOnOtherReferenceIdsAction(OnOtherReferenceIdsAction onOtherReferenceIdsAction);

    void setOnEmptyReferenceIdsAction(OnEmptyReferenceIdsAction onEmptyReferenceIdsAction);

    void setOnErrorReferenceIdsAction(OnErrorReferenceIdsAction onErrorReferenceIdsAction);

    void setOnNetErrorReferenceIdsAction(OnNetErrorReferenceIdsAction onNetErrorReferenceIdsAction);

    void setOnLoadingReferenceIdsAction(OnLoadingReferenceIdsAction onLoadingReferenceIdsAction);

}
