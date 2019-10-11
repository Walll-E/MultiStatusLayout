package com.wall_e.multiStatusLayout.interf;

import android.support.annotation.Nullable;
import android.view.View;

import java.util.List;

public interface OnNetErrorReferenceIdsAction extends OnReferenceViewAction{
    void showNetErrorAction(@Nullable List<View> referenceViews);
}
