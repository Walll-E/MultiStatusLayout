package com.example.wall_e.multistatuslayout;

import android.widget.RadioButton;
import android.widget.RadioGroup;

public class MultiStatusConstraintLayoutActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_multi_status_constraint_layout;
    }

    @Override
    protected void initView() {
        RadioGroup rgp = findViewById(R.id.rgp);
        ((RadioButton) rgp.getChildAt(0)).setChecked(true);
        initFragment();
        rgp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
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


    private void initFragment() {
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
        showFragment(0);
    }
}
