package com.wall_e.multiStatusLayout.interf;

import android.support.annotation.Nullable;
import android.view.View;

import java.util.List;

public interface OnContentReferenceIdsAction extends OnReferenceViewAction{
    void showContentAction(@Nullable List<View> referenceViews);
}
