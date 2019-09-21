package com.wall_e.multiStatusLayout.annotation;

import android.view.View;
import android.view.ViewParent;

/**
 * 约束View的提供者，主要提供实现View和TargetViewId之间的依赖关系
 * 比如设置View在TargetViewId对应的View下方
 */
public interface ViewConstraintProvider {
    /**
     * 提供实现View和TargetViewId之间的依赖关系
     * @param view 和targetViewId有依赖关系（View：目前最多提供LoadingView，NetErrorView,ErrorView,EmptyView,OtherView）
     * @param targetViewId 和View有依赖关系
     * @param parent 父view
     */
    void addViewBlewTargetView(View view,  int targetViewId, ViewParent parent);
}
