package com.wall_e.multiStatusLayout.constraint;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.view.View;
import android.view.ViewParent;

import com.wall_e.multiStatusLayout.annotation.ViewConstraintProvider;

/**
 * ConstraintLayout下View和TargetViewId的依赖关系
 */
public class ConstraintLayoutConstraintProvider implements ViewConstraintProvider {

    @Override
    public void addViewBlewTargetView(@NonNull View view, int targetViewId, @NonNull ViewParent parent) {
        if (parent instanceof ConstraintLayout) {
            ConstraintLayout constraintLayout = (ConstraintLayout) parent;
            ConstraintSet constraintSet = new ConstraintSet();
            constraintLayout.addView(view);
            constraintSet.clone(constraintLayout);
            constraintSet.constrainWidth(view.getId(), ConstraintLayout.LayoutParams.MATCH_PARENT);
            constraintSet.constrainHeight(view.getId(), ConstraintLayout.LayoutParams.MATCH_PARENT);
            constraintSet.connect(view.getId(), ConstraintSet.TOP, targetViewId == View.NO_ID ? ConstraintSet.PARENT_ID : targetViewId, ConstraintSet.TOP);
            constraintSet.connect(view.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM);
            constraintSet.connect(view.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START);
            constraintSet.connect(view.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END);
            constraintSet.applyTo(constraintLayout);
        }
    }
}
