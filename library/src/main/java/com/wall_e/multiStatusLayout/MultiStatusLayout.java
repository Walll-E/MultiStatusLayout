package com.wall_e.multiStatusLayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;



/**
 * Created by Wall-E on 2017/4/14.
 */

public class MultiStatusLayout extends RelativeLayout {

    /**
     * 子控件个数
     */
    private int mContentViewCount;

    private Context mContext;
    /**
     * 布局包裹的子控件
     */
    private SparseArray<View> mContentViews;
    /**
     * 默认的layout
     */
    private static final int DEFAULT_LAYOUT = R.layout.default_layout;

    /**
     * 网络错误的layout
     */
    private int mNetErrorLayout = DEFAULT_LAYOUT;

    /**
     * 加载中的layout
     */
    private int mLoadingLayout = DEFAULT_LAYOUT;

    /**
     * 没有数据layout
     */
    private int mEmptyLayout = DEFAULT_LAYOUT;

    /**
     * 数据错误layout
     */
    private int mErrorLayout = DEFAULT_LAYOUT;
    /**
     * 扩充layout
     */
    private int mOtherLayout = DEFAULT_LAYOUT;
    /**
     * 布局中的不隐藏的View的id
     */
    private int mTargetViewId = -999;

    /**
     * 重新加载的监听
     */
    private OnReloadDataListener onReloadDataListener;


    private final RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);


    public MultiStatusLayout(Context context) {
        this(context, null);
    }

    public MultiStatusLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MultiStatusLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.MultiStatusLayout, defStyleAttr, 0);
        mNetErrorLayout = array.getResourceId(R.styleable.MultiStatusLayout_netErrorLayout, mNetErrorLayout);
        mLoadingLayout = array.getResourceId(R.styleable.MultiStatusLayout_loadingLayout, mLoadingLayout);
        mErrorLayout = array.getResourceId(R.styleable.MultiStatusLayout_errorLayout, mErrorLayout);
        mEmptyLayout = array.getResourceId(R.styleable.MultiStatusLayout_emptyLayout, mEmptyLayout);
        mOtherLayout = array.getResourceId(R.styleable.MultiStatusLayout_otherLayout, mOtherLayout);
        mTargetViewId = array.getResourceId(R.styleable.MultiStatusLayout_targetViewId, mTargetViewId);
        array.recycle();
    }


    /**
     * 重写此方法，获取到其中的子控件个数、子控件将其添加至
     * SparseArray mContentViews 中
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        int childCount = mContentViewCount = getChildCount();
        mContentViews = new SparseArray<>();
        //隐藏布局中的子控件并且添加至mContentViews
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            mContentViews.put(i, childView);
        }
    }


    /**
     * 显示扩充布局
     */
    public void showOther() {
        //先从中获取加载中的布局
        View view = mContentViews.get(mContentViewCount + 4);
        //显示加载中的布局
        showView(view, mOtherLayout, mContentViewCount + 4);
    }

    /**
     * 显示此View,如果view==null的时候才去加载这个布局
     *
     * @param view
     * @param layoutId
     * @param index
     */
    private void showView(View view, int layoutId, int index) {
        //如果子控件处于显示状态先隐藏所有的子控件
        hideViews();
        if (view == null) {
            view = inflate(mContext, layoutId, null);
            if (index >= mContentViewCount && mTargetViewId != -999) {
                layoutParams.addRule(RelativeLayout.BELOW, mTargetViewId);
            }
            addView(view, layoutParams);
            mContentViews.put(index, view);
        } else {
            view.setVisibility(VISIBLE);
        }
        //设置网络错误或者数据错误布局的点击事件
        if (index == mContentViewCount + 1 || index == mContentViewCount + 3) {
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onReloadDataListener != null) onReloadDataListener.reloadData();
                }
            });
        }
    }


    /**
     * 显示加载中布局
     */
    public void showLoading() {
        //先从中获取加载中的布局
        View view = mContentViews.get(mContentViewCount);
        //显示加载中的布局
        showView(view, mLoadingLayout, mContentViewCount);
    }

    /**
     * 显示网络错误的布局
     */
    public void showNetError() {
        View view = mContentViews.get(mContentViewCount + 1);
        showView(view, mNetErrorLayout, mContentViewCount + 1);
    }

    /**
     * 列表数据为空时显示此布局
     */
    public void showEmpty() {
        View view = mContentViews.get(mContentViewCount + 2);
        showView(view, mEmptyLayout, mContentViewCount + 2);
    }


    /**
     * 加载数据错误时显示此布局
     */
    public void showError() {
        View view = mContentViews.get(mContentViewCount + 3);
        showView(view, mErrorLayout, mContentViewCount + 3);
    }


    /**
     * 隐藏所有的View
     */
    private void hideViews() {
        for (int i = 0; i < mContentViews.size(); i++) {
            View view = mContentViews.valueAt(i);
            if (mTargetViewId != view.getId() && view.getVisibility() != GONE) {
                view.setVisibility(GONE);
            }
        }
    }


    /**
     * 显示
     */
    public void showContent() {
        for (int i = 0; i < mContentViews.size(); i++) {
            View view = mContentViews.valueAt(i);
            if (i < mContentViewCount) {
                if (view != null) {
                    view.setVisibility(VISIBLE);
                }
            } else {
                if (view != null) view.setVisibility(GONE);
            }
        }
    }

    /**
     * 获取加载布局
     *
     * @return
     */
    public View getLoadingView() {
        View view = mContentViews.get(mContentViewCount);
        if (view == null) {
            //如果加载中布局为null，证明还未加载此布局
            view = inflate(mContext, mLoadingLayout, null);
            mContentViews.put(mContentViewCount, view);
        }
        return view;
    }

    public View getOtherView() {
        View view = mContentViews.get(mContentViewCount + 4);
        if (view == null) {
            //如果加载中布局为null，证明还未加载此布局
            view = inflate(mContext, mOtherLayout, null);
            mContentViews.put(mContentViewCount + 4, view);
        }
        return view;
    }

    /**
     * 获取网络错误时的布局
     *
     * @return
     */
    public View getNetErrorView() {
        View view = mContentViews.get(mContentViewCount + 1);
        if (view == null) {
            view = inflate(mContext, mNetErrorLayout, null);
            addView(view, layoutParams);
            mContentViews.put(mContentViewCount + 1, view);
        }
        return view;
    }

    /**
     * 获取数据为空时的布局
     *
     * @return
     */
    public View getEmptyView() {
        View view = mContentViews.get(mContentViewCount + 2);
        if (view == null) {
            view = inflate(mContext, mEmptyLayout, null);
            addView(view, layoutParams);
            mContentViews.put(mContentViewCount + 2, view);
        }
        return view;
    }

    /**
     * 获取数据加载错误时的布局
     *
     * @return
     */
    public View getErrorView() {
        View view = mContentViews.get(mContentViewCount + 3);
        if (view == null) {
            view = inflate(mContext, mErrorLayout, null);
            addView(view, layoutParams);
            mContentViews.put(mContentViewCount + 3, view);
        }
        return view;
    }


    /**
     * 重新加载数据
     */
    public void setOnReloadDataListener(final OnReloadDataListener onReloadDataListener) {
        this.onReloadDataListener = onReloadDataListener;
    }

    /**
     * 重新加载
     */
    public interface OnReloadDataListener {
        void reloadData();
    }
}
