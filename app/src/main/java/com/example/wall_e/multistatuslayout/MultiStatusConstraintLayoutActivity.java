package com.example.wall_e.multistatuslayout;

import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.wall_e.multiStatusLayout.MultiStatusConstraintLayout;
import com.wall_e.multiStatusLayout.MultiStatusLayout;

import java.util.ArrayList;
import java.util.List;

public class MultiStatusConstraintLayoutActivity extends AppCompatActivity {




    private List<Fragment> fragments = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_status_constraint_layout);
        RadioGroup rgp = findViewById(R.id.rgp);
        ((RadioButton)rgp.getChildAt(0)).setChecked(true);
        initFragment();
        rgp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb_other:
                        showFragment(0);
                        break;
                    case R.id.rb_loading:
                        showFragment(1);
                        break;
                    case R.id.rb_netError:
                        showFragment(2);
                        break;
                    case R.id.rb_empty:
                        showFragment(3);
                        break;
                    case R.id.rb_error:
                        showFragment(4);
                        break;
                    default:
                        break;
                }
            }
        });
    }


        /**
         * 显示Fragment
         *
         * @param currentIndex
         */
        private void showFragment(int currentIndex) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            for (int i = 0; i < fragments.size(); i++) {
                if (i == currentIndex) {
                    continue;
                }
                Fragment fragment = fragments.get(i);
                transaction.hide(fragment);
            }
            transaction.show(fragments.get(currentIndex)).commitAllowingStateLoss();
            fragments.get(currentIndex).setUserVisibleHint(true);
        }


    private void initFragment(){

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        ConstraintOtherFragment otherFragment = new ConstraintOtherFragment();
        ConstraintSuccessFragment successFragment = new ConstraintSuccessFragment();
        ConstraintNetErrorFragment netErrorFragment = new ConstraintNetErrorFragment();
        ConstraintEmptyFragment emptyFragment = new ConstraintEmptyFragment();
        ConstraintErrorFragment errorFragment = new ConstraintErrorFragment();
        fragments.add(otherFragment);
        fragments.add(successFragment);
        fragments.add(netErrorFragment);
        fragments.add(emptyFragment);
        fragments.add(errorFragment);
        transaction.add(R.id.ll,otherFragment)
                .add(R.id.ll,successFragment)
                .add(R.id.ll,netErrorFragment)
                .add(R.id.ll,emptyFragment)
                .add(R.id.ll,errorFragment)
                .hide(successFragment)
                .hide(netErrorFragment)
                .hide(emptyFragment)
                .hide(errorFragment)
                .commitAllowingStateLoss();
        otherFragment.setUserVisibleHint(true);
    }
}
