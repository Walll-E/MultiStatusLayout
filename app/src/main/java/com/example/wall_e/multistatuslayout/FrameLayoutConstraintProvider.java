package com.example.wall_e.multistatuslayout;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;

import com.wall_e.multiStatusLayout.annotation.ViewConstraintProvider;


/**
 * 根据自己的业务需求定制Loading、Error、netError等布局与targetViewId之间的关系
 */
public class FrameLayoutConstraintProvider implements ViewConstraintProvider {
    @Override
    public void addViewBlewTargetView(View view, int targetViewId, ViewParent parent) {
        if (parent instanceof FrameLayout) {
            FrameLayout frameLayout = (FrameLayout) parent;
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            if (targetViewId != View.NO_ID) {
                layoutParams.topMargin = dip2px(view.getContext(), 50);//标题栏高度
            }
            frameLayout.addView(view, layoutParams);
        }
    }


    public int dip2px(Context context, float dpValue) {
        return (int) (0.5F + dpValue * context.getResources().getDisplayMetrics().density);
    }
}
