package com.wall_e.multiStatusLayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.IntRange;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v4.util.ArrayMap;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;

import com.wall_e.multiStatusLayout.R.id;
import com.wall_e.multiStatusLayout.interf.OnContentReferenceIdsAction;
import com.wall_e.multiStatusLayout.interf.OnEmptyReferenceIdsAction;
import com.wall_e.multiStatusLayout.interf.OnErrorReferenceIdsAction;
import com.wall_e.multiStatusLayout.interf.OnLoadingReferenceIdsAction;
import com.wall_e.multiStatusLayout.interf.OnNetErrorReferenceIdsAction;
import com.wall_e.multiStatusLayout.interf.OnOtherReferenceIdsAction;

import android.widget.RelativeLayout;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class MultiStatusHelper {

    private static final String TAG = "MultiStatusHelper";

    private Context mContext;
    private static final int OTHER_TYPE = 0;
    private static final int LOADING_TYPE = 1;
    private static final int NET_ERROR_TYPE = 2;
    private static final int EMPTY_TYPE = 3;
    private static final int ERROR_TYPE = 4;
    private static final int CONTENT_TYPE = 5;

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
    private int mTargetViewId = View.NO_ID;
    /**
     * 服务端错误重试按钮id
     */
    private int mErrorReloadViewId = View.NO_ID;
    /**
     * 是否已经添加加载数据失败重试点击事件
     */
    private boolean isAddErrorClickEvent;
    /**
     * 网络错误重试按钮id
     */
    private int mNetErrorReloadViewId = View.NO_ID;
    /**
     * 是否已经添加网络错误重试点击事件
     */
    private boolean isAddNetErrorClickEvent;

    /**
     * 重新加载的监听
     */
    private OnReloadDataListener onReloadDataListener;
    /**
     * Content下ReferenceIds的状态监听
     */
    private OnContentReferenceIdsAction mOnContentReferenceIdsAction;
    /**
     * Error下ReferenceIds的状态监听
     */
    private OnErrorReferenceIdsAction mOnErrorReferenceIdsAction;
    /**
     * NetError下ReferenceIds的状态监听
     */
    private OnNetErrorReferenceIdsAction mOnNetErrorReferenceIdsAction;
    /**
     * Empty下ReferenceIds的状态监听
     */
    private OnEmptyReferenceIdsAction mOnEmptyReferenceIdsAction;
    /**
     * Loading下ReferenceIds的状态监听
     */
    private OnLoadingReferenceIdsAction mOnLoadingReferenceIdsAction;
    /**
     * Other下ReferenceIds的状态监听
     */
    private OnOtherReferenceIdsAction mOnOtherReferenceIdsAction;

    /**
     * 用于记录当前显示的type
     */
    private int mViewType = -1;

    /**
     * 存储每一个类型的引用id集合
     */
    private Map<Integer,List<Integer>> mReferenceIds;

    /**
     * 真正的父类
     */
    private ViewGroup mParent;
    /**
     * 类型对应的索引
     */
    private SparseIntArray mRealIndex = new SparseIntArray(5);

    public MultiStatusHelper(Context context, AttributeSet attrs, int defStyleAttr, ViewGroup viewGroup) {
        mContext = context;
        mParent = viewGroup;
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.MultiStatusLayout, defStyleAttr, 0);
        mNetErrorLayout = array.getResourceId(R.styleable.MultiStatusLayout_netErrorLayout, mNetErrorLayout);
        mLoadingLayout = array.getResourceId(R.styleable.MultiStatusLayout_loadingLayout, mLoadingLayout);
        mErrorLayout = array.getResourceId(R.styleable.MultiStatusLayout_errorLayout, mErrorLayout);
        mEmptyLayout = array.getResourceId(R.styleable.MultiStatusLayout_emptyLayout, mEmptyLayout);
        mOtherLayout = array.getResourceId(R.styleable.MultiStatusLayout_otherLayout, mOtherLayout);
        mTargetViewId = array.getResourceId(R.styleable.MultiStatusLayout_targetViewId, mTargetViewId);
        mNetErrorReloadViewId = array.getResourceId(R.styleable.MultiStatusLayout_netErrorReloadViewId, mNetErrorReloadViewId);
        mErrorReloadViewId = array.getResourceId(R.styleable.MultiStatusLayout_errorReloadViewId, mErrorReloadViewId);
        setIds(array.getString(R.styleable.MultiStatusLayout_contentReferenceIds),CONTENT_TYPE);
        setIds(array.getString(R.styleable.MultiStatusLayout_emptyReferenceIds),EMPTY_TYPE);
        setIds(array.getString(R.styleable.MultiStatusLayout_errorReferenceIds),ERROR_TYPE);
        setIds(array.getString(R.styleable.MultiStatusLayout_netErrorReferenceIds),NET_ERROR_TYPE);
        setIds(array.getString(R.styleable.MultiStatusLayout_loadingReferenceIds),LOADING_TYPE);
        setIds(array.getString(R.styleable.MultiStatusLayout_otherReferenceIds),OTHER_TYPE);
        array.recycle();
    }


    private void setIds(String referenceIds,int type) {
        if (referenceIds == null) return;
        int begin = 0;
        while (true) {
            int end = referenceIds.indexOf(",", begin);
            if (end == -1) {
                addId(referenceIds.substring(begin),type);
                return;
            }
            addId(referenceIds.substring(begin, end),type);
            begin = end + 1;
        }
    }

    private void addId(String idString,int type) {
        if (idString == null || mContext == null) return;
        idString = idString.trim();
        int tag = 0;
        try {
            Class res = id.class;
            Field field = res.getField(idString);
            tag = field.getInt(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (tag == 0) {
            tag = mContext.getResources().getIdentifier(idString, "id", mContext.getPackageName());
        }
        if (tag == 0) {
            Log.d(TAG, "xml中配置的referenceIds并不能被解析，当前的Id:" + idString);
            return;
        }
        if (mReferenceIds == null) {
            mReferenceIds = new ArrayMap<>();
        }
        if (mReferenceIds.containsKey(type)){
            List<Integer> list = mReferenceIds.get(type);
            if (list!=null && !list.contains(tag)){
                list.add(tag);
            }
        }else {
            List<Integer> list = new ArrayList<>();
            list.add(tag);
            mReferenceIds.put(type,list);
        }
    }


    /**
     *
     * @param type 0:Other下面限制的ids
     *             1:Loading下面限制的ids
     *             2:NetError下面限制的ids
     *             3:Empty下面限制的ids
     *             4:Error下面限制的ids
     *             5:Content下面限制的ids
     * @return 返回相对应下的ids
     */
    @Nullable
    public List<Integer> getReferenceIds(@IntRange(from = 0,to = 5) int type) {
        if (mReferenceIds==null)return null;
        return mReferenceIds.get(type);
    }


    /**
     * 显示此View,如果view==null的时候才去加载这个布局
     *
     * @param layoutId 布局索引
     * @param type    存放view容器中的索引
     */
    private void showView(int layoutId, int type) {
        //如果子控件处于显示状态先隐藏所有的子控件
        hideViews(type);
        View view = inflateAndAddViewInLayout(type, layoutId);
        if (view.getVisibility()!= VISIBLE){
            view.setVisibility(VISIBLE);
        }
        //设置网络错误或者数据错误布局的点击事件
        switch (type) {
            case NET_ERROR_TYPE://网络错误
                if (!isAddNetErrorClickEvent) {
                    setReloadClick(view, mNetErrorReloadViewId);
                    isAddNetErrorClickEvent = true;
                }
                break;
            case ERROR_TYPE://数据加载失败
                if (!isAddErrorClickEvent) {
                    setReloadClick(view, mErrorReloadViewId);
                    isAddErrorClickEvent = true;
                }
                break;
        }
    }

    /**
     * 加载相应状态的布局，并且添加至ViewGroup中
     *
     * @param index       存放布局容器的索引
     * @param layoutResId 布局资源id
     * @return 返回相应状态的布局
     */
    private View inflateAndAddViewInLayout(int index, int layoutResId) {
        int realIndex = mRealIndex.get(index, -1);
        View view;
        if (realIndex==-1){
            view = ViewGroup.inflate(mContext, layoutResId, null);
            addViewBlewTargetView(view);
            mRealIndex.put(index,mParent.getChildCount()-1);
        }else {
            view = mParent.getChildAt(realIndex);
        }
        return view;
    }




    private void setReloadClick(View view, int viewId) {
        View clickView = view.findViewById(viewId);
        if (clickView == null) {
            clickView = view;
        }
        clickView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onReloadDataListener != null) onReloadDataListener.reloadData();
            }
        });
    }

    /**
     * 在View id 为mTargetViewId 下方添加其他状态的view
     *
     * @param view 不同状态下的view
     */
    private void addViewBlewTargetView(View view) {
        if (mParent instanceof ConstraintLayout) {
            ConstraintLayout constraintLayout = (ConstraintLayout) mParent;
            ConstraintSet constraintSet = new ConstraintSet();
            constraintLayout.addView(view);
            constraintSet.clone(constraintLayout);
            constraintSet.constrainWidth(view.getId(), ConstraintLayout.LayoutParams.MATCH_PARENT);
            constraintSet.constrainHeight(view.getId(), ConstraintLayout.LayoutParams.MATCH_PARENT);
            constraintSet.connect(view.getId(), ConstraintSet.TOP, mTargetViewId == View.NO_ID ? ConstraintSet.PARENT_ID : mTargetViewId, ConstraintSet.TOP);
            constraintSet.connect(view.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM);
            constraintSet.connect(view.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START);
            constraintSet.connect(view.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END);
            constraintSet.applyTo(constraintLayout);
        } else if (mParent instanceof RelativeLayout) {
            RelativeLayout relativeLayout = (RelativeLayout) mParent;
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            if (mTargetViewId != View.NO_ID) {
                layoutParams.addRule(RelativeLayout.BELOW, mTargetViewId);
            }
            relativeLayout.addView(view, layoutParams);
        }
    }






    /**
     * 显示扩充布局
     */
    public void showOther() {
        if (mViewType == OTHER_TYPE)
            return;
        mViewType = OTHER_TYPE;
        //显示加载中的布局
        showView(mOtherLayout, OTHER_TYPE);
    }

    /**
     * 显示加载中布局
     */
    public void showLoading() {
        if (mViewType == LOADING_TYPE)
            return;
        mViewType = LOADING_TYPE;
        //显示加载中的布局
        showView(mLoadingLayout, LOADING_TYPE);
    }

    /**
     * 显示网络错误的布局
     */
    public void showNetError() {
        if (mViewType == NET_ERROR_TYPE)
            return;
        mViewType = NET_ERROR_TYPE;
        showView(mNetErrorLayout, NET_ERROR_TYPE);
    }

    /**
     * 列表数据为空时显示此布局
     */
    public void showEmpty() {
        if (mViewType == EMPTY_TYPE)
            return;
        mViewType = EMPTY_TYPE;
        showView(mEmptyLayout, EMPTY_TYPE);
    }


    /**
     * 加载数据错误时显示此布局
     */
    public void showError() {
        if (mViewType == ERROR_TYPE)
            return;
        mViewType = ERROR_TYPE;
        showView(mErrorLayout, ERROR_TYPE);
    }


    /**
     * 按需隐藏相关的View
     * @param type
     */
    private void hideViews(int type) {
        int count = mParent.getChildCount();
        List<Integer> referenceIds = null;
        if (mReferenceIds!=null){
            referenceIds = mReferenceIds.get(type);
        }
        int realIndex = mRealIndex.get(type, -1);
        type = isCollectionEmpty(referenceIds)?-1:type;
        List<View> views;
        switch (type){
            case OTHER_TYPE:
                views = accordingToTypeShow(realIndex,referenceIds);
                if (mOnOtherReferenceIdsAction!=null){
                    mOnOtherReferenceIdsAction.showOtherAction(views);
                }
                break;
            case LOADING_TYPE:
                views = accordingToTypeShow(realIndex,referenceIds);
                if (mOnLoadingReferenceIdsAction!=null){
                    mOnLoadingReferenceIdsAction.showLoadingAction(views);
                }
                break;
            case EMPTY_TYPE:
                views = accordingToTypeShow(realIndex,referenceIds);
                if (mOnEmptyReferenceIdsAction!=null){
                    mOnEmptyReferenceIdsAction.showEmptyAction(views);
                }
                break;
            case ERROR_TYPE:
                views = accordingToTypeShow(realIndex, referenceIds);
                if (mOnErrorReferenceIdsAction!=null){
                    mOnErrorReferenceIdsAction.showErrorAction(views);
                }
                break;
            case NET_ERROR_TYPE:
                views = accordingToTypeShow(realIndex, referenceIds);
                if (mOnNetErrorReferenceIdsAction!=null){
                    mOnNetErrorReferenceIdsAction.showNetErrorAction(views);
                }
                break;
            default:
                for (int i = 0; i < count; i++) {
                    if (i==realIndex)continue;
                    View view = mParent.getChildAt(i);
                    if (mTargetViewId != view.getId()
                            && view.getVisibility() != GONE
                            && !(view instanceof ViewStub)
                            ) {
                        view.setVisibility(GONE);
                    }
                }
                break;
        }
    }

    private List<View> accordingToTypeShow(int realIndex,List<Integer> referenceIds){
        List<View> views = new ArrayList<>();
        int childCount = mParent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            if (i==realIndex)continue;
            View view = mParent.getChildAt(i);
            int id = view.getId();
            if (referenceIds.contains(id)){
                views.add(view);
                continue;
            }
            if (mTargetViewId != view.getId()
                    && view.getVisibility() != GONE
                    && !(view instanceof ViewStub)) {
                view.setVisibility(GONE);
            }
        }
        return views;
    }

    private boolean isCollectionEmpty(Collection e){
        return e==null || e.isEmpty();
    }


    /**
     * 显示
     */
    public void showContent() {
        if (mViewType == CONTENT_TYPE)
            return;
        mViewType = CONTENT_TYPE;
        int count = mParent.getChildCount();
        int size = mRealIndex.size();
        count-=size;
        List<Integer> contentIds = null;
        if (mReferenceIds!=null){
            contentIds = mReferenceIds.get(CONTENT_TYPE);
        }
        boolean hasContentAction = mOnContentReferenceIdsAction!=null;
        for (int i = 0; i < count; i++) {
            View view = mParent.getChildAt(i);
            if (!(view instanceof ViewStub)) {
                view.setVisibility(VISIBLE);
            }
        }
        for (int i = 0; i <size; i++) {
            mParent.getChildAt(mRealIndex.valueAt(i)).setVisibility(GONE);
        }
        if (hasContentAction){
            mOnContentReferenceIdsAction.showContentAction(contentIds);
        }
    }


    /**
     * 获取扩展布局
     *
     * @return 扩展布局
     */
    public View getOtherView() {
        return inflateAndAddViewInLayout( OTHER_TYPE, mOtherLayout);
    }

    /**
     * 获取加载布局
     *
     * @return 加载布局
     */
    public View getLoadingView() {
        return inflateAndAddViewInLayout(LOADING_TYPE, mLoadingLayout);
    }

    /**
     * 获取网络错误时的布局
     *
     * @return 网络错误时的布局
     */
    public View getNetErrorView() {
        return inflateAndAddViewInLayout(NET_ERROR_TYPE, mNetErrorLayout);
    }

    /**
     * 获取数据为空时的布局
     *
     * @return 数据为空时的布局
     */
    public View getEmptyView() {
        return inflateAndAddViewInLayout(EMPTY_TYPE, mEmptyLayout);
    }

    /**
     * 获取数据加载错误时的布局
     *
     * @return 数据加载错误时的布局
     */
    public View getErrorView() {
        return inflateAndAddViewInLayout(ERROR_TYPE, mErrorLayout);
    }

    /**
     * 设置扩展布局的view
     *
     * @param layoutResId 布局资源id
     */
    public void setOtherView(int layoutResId) {
        mOtherLayout = layoutResId;
        inflateAndAddViewInLayout(OTHER_TYPE, layoutResId);
    }

    /**
     * 设置扩展布局的view
     *
     * @param layoutResId 布局资源id
     */
    public void setLoadingView(int layoutResId) {
        mLoadingLayout = layoutResId;
        inflateAndAddViewInLayout(LOADING_TYPE, layoutResId);
    }

    /**
     * 设置扩展布局的view
     *
     * @param layoutResId 布局资源id
     */
    public void setNetErrorView(int layoutResId) {
        mNetErrorLayout = layoutResId;
        inflateAndAddViewInLayout(NET_ERROR_TYPE, layoutResId);
    }

    /**
     * 设置扩展布局的view
     *
     * @param layoutResId 布局资源id
     */
    public void setEmptyView(int layoutResId) {
        mEmptyLayout = layoutResId;
        inflateAndAddViewInLayout(EMPTY_TYPE, layoutResId);
    }

    /**
     * 设置扩展布局的view
     *
     * @param layoutResId 布局资源id
     */
    public void setErrorView(int layoutResId) {
        mErrorLayout = layoutResId;
        inflateAndAddViewInLayout(ERROR_TYPE, layoutResId);
    }

    public int getTargetViewId() {
        return mTargetViewId;
    }

    /**
     * 设置不隐藏的view id
     *
     * @param targetViewId 不隐藏的view id
     */
    public void setTargetViewId(int targetViewId) {
        mTargetViewId = targetViewId;
    }


    /**
     * 获取当前显示的布局
     * 1：other
     * 2：loading
     * 3：net_error
     * 4：empty
     * 5：error
     * 6：content
     *
     * @return
     */
    public int getShowViewType() {
        return mViewType;
    }


    /**
     * 重新加载数据
     */
    public void setOnReloadDataListener(final OnReloadDataListener onReloadDataListener) {
        this.onReloadDataListener = onReloadDataListener;
    }

    public void setOnContentReferenceIdsAction(OnContentReferenceIdsAction onContentReferenceIdsAction){
        this.mOnContentReferenceIdsAction = onContentReferenceIdsAction;
    }

    public void setOnOtherReferenceIdsAction(OnOtherReferenceIdsAction onOtherReferenceIdsAction){
        this.mOnOtherReferenceIdsAction = onOtherReferenceIdsAction;
    }

    public void setOnEmptyReferenceIdsAction(OnEmptyReferenceIdsAction onEmptyReferenceIdsAction){
        this.mOnEmptyReferenceIdsAction = onEmptyReferenceIdsAction;
    }

    public void setOnErrorReferenceIdsAction(OnErrorReferenceIdsAction onErrorReferenceIdsAction){
        this.mOnErrorReferenceIdsAction = onErrorReferenceIdsAction;
    }

    public void setOnNetErrorReferenceIdsAction(OnNetErrorReferenceIdsAction onNetErrorReferenceIdsAction){
        this.mOnNetErrorReferenceIdsAction = onNetErrorReferenceIdsAction;
    }

    public void setOnLoadingReferenceIdsAction(OnLoadingReferenceIdsAction onLoadingReferenceIdsAction){
        this.mOnLoadingReferenceIdsAction = onLoadingReferenceIdsAction;
    }
}
