package com.wall_e.multiStatusLayout.constraint;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.RelativeLayout;

import com.wall_e.multiStatusLayout.annotation.ViewConstraintProvider;

/**
 * RelativeLayout下View和TargetViewId的依赖关系
 */
public class RelativeLayoutConstraintProvider implements ViewConstraintProvider {

    @Override
    public void addViewBlewTargetView(@NonNull View view, int targetViewId, @NonNull ViewParent parent) {
        if (parent instanceof RelativeLayout) {
            RelativeLayout relativeLayout = (RelativeLayout) parent;
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            if (targetViewId != View.NO_ID) {
                layoutParams.addRule(RelativeLayout.BELOW, targetViewId);
            }
            relativeLayout.addView(view, layoutParams);
        }
    }
}
