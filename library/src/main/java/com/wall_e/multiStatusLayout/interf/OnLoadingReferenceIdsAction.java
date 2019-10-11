package com.wall_e.multiStatusLayout.interf;

import android.support.annotation.Nullable;
import android.view.View;

import java.util.List;

public interface OnLoadingReferenceIdsAction extends OnReferenceViewAction{
    void showLoadingAction(@Nullable List<View> referenceViews);
}
