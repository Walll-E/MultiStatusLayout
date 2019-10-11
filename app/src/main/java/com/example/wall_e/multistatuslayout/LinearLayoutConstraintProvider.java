package com.example.wall_e.multistatuslayout;

import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.LinearLayout;

import com.wall_e.multiStatusLayout.annotation.ViewConstraintProvider;

public class LinearLayoutConstraintProvider implements ViewConstraintProvider {

    @Override
    public void addViewBlewTargetView(View view, int targetViewId, ViewParent parent) {
        if (parent instanceof LinearLayout){
            LinearLayout linearLayout = (LinearLayout) parent;
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParams.gravity = Gravity.CENTER;
            linearLayout.addView(view, layoutParams);
        }
    }
}
