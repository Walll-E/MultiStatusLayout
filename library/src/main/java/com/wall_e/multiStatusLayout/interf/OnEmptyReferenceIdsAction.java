package com.wall_e.multiStatusLayout.interf;

import android.support.annotation.Nullable;
import android.view.View;

import java.util.List;

public interface OnEmptyReferenceIdsAction extends OnReferenceViewAction{
    void showEmptyAction(@Nullable List<View> referenceViews);
}
