package com.example.wall_e.multistatuslayout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseActivity extends AppCompatActivity {

    private int mCurrentIndex = -1;

    protected List<Fragment> fragments = new ArrayList<>();

    protected abstract int getLayoutId();

    protected abstract void initView();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        initView();
    }


    protected void showFragment(int index) {
        if (index == mCurrentIndex || index >= fragments.size()) return;
        Fragment fragment = fragments.get(index);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (mCurrentIndex!=-1){
            transaction.hide(fragments.get(mCurrentIndex));
        }
        if (!fragment.isAdded()) {
            transaction.add(R.id.ll, fragment);
        }
        fragment.setUserVisibleHint(true);
        transaction.show(fragment);
        transaction.commitAllowingStateLoss();
        mCurrentIndex = index;
    }
}
