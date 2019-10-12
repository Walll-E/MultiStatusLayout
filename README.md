# MultiStatusConstraintLayout、MultiStatusLayout
可自由的加载多种状态的视图，并且只有要显示的时候才会去加载相应的布局文件。这样无需加载使用不到的布局文件，提升了页面加载的性能。
分别继承自`ConstraintLayout`、`Relativelayout`可以作为最外层的布局，支持在布局文件中指定不同状态下的布局。如果感兴趣，可以下载Demo具体看用法

### Gradle集成
```
allprojects {
        repositories {
            ...
            maven { url 'https://jitpack.io' }
        }
    }
    
    
dependencies {
        implementation 'com.github.Walll-E.MultiStatusLayout:library:1.0.6'
        annotationProcessor 'com.github.Walll-E.MultiStatusLayout:compiler:1.0.6'
    }
```
### 1.0.6 版本相较于 1.0.5 版本有如下升级：
- 如果判断`NetErrorView`、`LoadingView`、`EmptyView`等有背景，调用`showLoading`、`showEmpty`等方法时，不会隐藏其他控件（继承自`LinearLayout`、`GridLayout`除外），会将这几种类型的`View`直接添加进父布局
- 优化部分代码逻辑
### 1.0.5 版本相较于 1.0.4 版本有如下升级：
- 增加`showContent`等方法调用时，contentReferenceIds等引用的控件id，不受其控制
- 原来只支持`RelativeLayout`、`ConstraintLayout`这两种系统提供的控件，现在支持自定义扩展更多`ViewGroup`
- 注解`MultiStatus`现在包含`value`、`provider`两种属性
- 抽象出`ViewConstraintProvider`类，自定义的类只需实现`ViewConstraintProvider`接口即可
- 实现`ViewConstraintProvider`接口的类，如果是全局配置，只需要放在注解`MultiStatus`中的`provider`属性中

### 项目配置

项目中定义一个类，在类顶部添加`MultiStatus`,注意value，和provider中的类必须顺序一致，如果不一致，项目运行起来可能报错或显示有问题。
下面示例代码中的`RelativeLayoutConstraintProvider`、`ConstraintLayoutConstraintProvider`由本项目提供，其中`FrameLayoutConstraintProvider`是demo中自定义的。如下定义完成之后，在AndroidStudio中点击build Project 按钮进行一次编译，即可生成相应的layout，生成的layout开头均为MultiStatus。
```
@MultiStatus(value = {RelativeLayout.class,ConstraintLayout.class, FrameLayout.class},
provider = {RelativeLayoutConstraintProvider.class, ConstraintLayoutConstraintProvider.class,FrameLayoutConstraintProvider.class})
public class MultiStatusInit {
}
```
### 布局文件使用
```
<?xml version="1.0" encoding="utf-8"?>
<com.wall_e.multiStatusLayout.MultiStatusFrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/statusLayout"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    app:contentReferenceIds="actionButtonCenter,actionButtonRight,actionButtonLeft"
    app:loadingLayout="@layout/common_loading"
    app:loadingReferenceIds="actionButtonCenter"
    app:targetViewId="@+id/rl_title"
    tools:context="com.example.wall_e.multistatuslayout.MainActivity">

    <Button
        android:id="@+id/btn_multiStatusLayout"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginTop="200dp"
        android:background="@color/colorAccent"
        android:text="MultiStatusLayout"
        android:textColor="#fff" />

    <Button
        android:id="@+id/btn_multiStatusConstraintLayout"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginTop="250dp"
        android:background="@color/colorPrimary"
        android:text="MultiStatusConstraintLayout"
        android:textColor="#fff" />

    <TextView
        android:id="@+id/tv_title"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="center_horizontal"
        android:layout_marginTop="150dp"
        android:text="请注意二级页面的布局文件用法"
        android:textColor="@color/colorAccent"
        android:textSize="22dp" />

    <Button
        android:id="@+id/actionButtonRight"
        android:layout_width="70dp"
        android:text="Right"
        android:textColor="#fff"
        android:layout_height="70dp"
        android:layout_gravity="bottom|right"
        android:layout_marginBottom="30dp"
        android:layout_marginRight="30dp"
        android:background="@drawable/right"
        app:srcCompat="@drawable/ic_launcher_foreground" />


    <Button
        android:id="@+id/actionButtonCenter"
        android:layout_width="70dp"
        android:layout_height="70dp"
        android:text="Center"
        android:textColor="#fff"
        android:layout_gravity="bottom|center_horizontal"
        android:layout_marginBottom="30dp"
        android:background="@drawable/center"
        />

    <Button
        android:id="@+id/actionButtonLeft"
        android:layout_width="70dp"
        android:layout_height="70dp"
        android:text="Left"
        android:textColor="#fff"
        android:layout_gravity="bottom"
        android:layout_marginBottom="30dp"
        android:layout_marginLeft="30dp"
        android:background="@drawable/left"
        />

    <include layout="@layout/title" />


</com.wall_e.multiStatusLayout.MultiStatusFrameLayout>


```
### 代码中使用
```
statusLayout = (MultiStatusLayout) findViewById(R.id.status_layout);
        statusLayout.showContent();
        statusLayout.showNetError();
        statusLayout.showEmpty();
        statusLayout.showLoading();
        statusLayout.showError();
        statusLayout.showOther();
```

### 重要提示：`MultiStatusConstraintLayout`在布局文件中的每一个子View都要添加id且必须在loadingLayout、emptyLayout、otherLayout、netErrorLayout、errorLayout的布局文件中的根`ViewGroup`下添加id，不然会抛异常`throw new RuntimeException("All children of ConstraintLayout must have ids to use ConstraintSet");`



### 1.0.5 版本新增xml属性如下
- `contentReferenceIds`：控件之间的`id`用英文‘,’分隔。`showContent()`被调用时，contentReferenceIds,不受其控制
- `emptyReferenceIds`：控件之间的`id`用英文‘,’分隔。`showEmpty()`被调用时，emptyReferenceIds,不受其控制
- `errorReferenceIds`：控件之间的`id`用英文‘,’分隔。`showError()`被调用时，errorReferenceIds,不受其控制
- `netErrorReferenceIds`：控件之间的`id`用英文‘,’分隔。`showNetError()`被调用时，netErrorReferenceIds,不受其控制
- `otherReferenceIds`：控件之间的`id`用英文‘,’分隔。`showOther()`被调用时，otherReferenceIds,不受其控制
- `loadingReferenceIds`：控件之间的`id`用英文‘,’分隔。`showLoading()`被调用时，loadingReferenceIds,不受其控制

### 1.0.4 版本新增xml属性如下
- `targetViewId`：表示在任何情况下都不隐藏的View的id
- `netErrorReloadViewId`：表示网络错误布局显示，重试View的id，如果不设置，重试事件的监听直接设置在netErrorLayout上
- `errorReloadViewId`：表示数据错误，重试View的id，如果不设置，重试事件的监听直接设置在errorLayout上

### 属性列表
|属性名称   |  说明 |
|:-----:|:-------:|
|loadingLayout  | 加载中的布局 |
|emptyLayout | 数据为空时的布局|
|netErrorLayout | 网络错误时的布局 |
|errorLayout | 加载失败时的布局 |
|otherLayout | 扩充的布局 |
|targetViewId | 子控件中任何时候都显示的控件id |
|netErrorReloadViewId | 网络错误重试按钮id |
|errorReloadViewId | 加载失败重试按钮id |
|contentReferenceIds | showContent()调用后，contentReferenceIds不受其控制;id之间的间隔英文',' |
|emptyReferenceIds | showEmpty()调用后，emptyReferenceIds不受其控制;id之间的间隔英文',' |
|errorReferenceIds | showError()调用后，errorReferenceIds不受其控制;id之间的间隔英文',' |
|netErrorReferenceIds | showNetError()调用后，netErrorReferenceIds不受其控制;id之间的间隔英文',' |
|otherReferenceIds | showOther()调用后，otherReferenceIds不受其控制;id之间的间隔英文',' |
|loadingReferenceIds | showLoading()调用后，loadingReferenceIds不受其控制;id之间的间隔英文',' |
