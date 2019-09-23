package com.example.wall_e.multistatuslayout;

import android.support.constraint.ConstraintLayout;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.wall_e.multiStatusLayout.annotation.MultiStatus;
import com.wall_e.multiStatusLayout.constraint.ConstraintLayoutConstraintProvider;
import com.wall_e.multiStatusLayout.constraint.RelativeLayoutConstraintProvider;


@MultiStatus(value = {RelativeLayout.class,ConstraintLayout.class, FrameLayout.class},
provider = {RelativeLayoutConstraintProvider.class, ConstraintLayoutConstraintProvider.class,FrameLayoutConstraintProvider.class})
public class MultiStatusInit {
}
