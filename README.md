# MultiStatusConstraintLayout、MultiStatusLayout
可自由的加载多种状态的视图，并且只有要显示的时候才会去加载相应的布局文件。这样无需加载使用不到的布局文件，提升了页面加载的性能。
分别继承自`ConstraintLayout`、`Relativelayout`可以作为最外层的布局，支持在布局文件中指定不同状态下的布局。如果感兴趣，可以下载Demo具体看用法

### `MultiStatusConstraintLayout`在布局文件中的每一个子View都要添加id且必须在loadingLayout、emptyLayout、otherLayout、netErrorLayout、errorLayout的布局文件中的根`ViewGroup`下添加id，不然会抛异常`throw new RuntimeException("All children of ConstraintLayout must have ids to use ConstraintSet");`

### 新增xml属性如下
- `targetViewId`：表示在任何情况下都不隐藏的View的id
- `netErrorReloadViewId`：表示网络错误布局显示，重试View的id，如果不设置，重试事件的监听直接设置在netErrorLayout上
- `errorReloadViewId`：表示数据错误，重试View的id，如果不设置，重试事件的监听直接设置在errorLayout上

### Gradle集成
```
allprojects {
        repositories {
            ...
            maven { url 'https://jitpack.io' }
        }
    }
    
    
dependencies {
        implementation 'com.github.Walll-E.MultiStatusLayout:library:1.0.4'
        annotationProcessor 'com.github.Walll-E.MultiStatusLayout:compiler:1.0.4'
    }
```

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

### 提供方法
| 方法   |  作用  |
|:-----:|:-------:|
|showLoading()| 显示加载中布局|
|showNetError()| 显示网络错误的布局|
|showEmpty()| 显示数据为空时布局|
|showError()| 显示数据错误时布局|
|showContent()|显示成功时的内容|
|showOther()|显示扩充布局的内容|
|getLoadingView()|获取加载布局|
|getNetErrorView()|获取网络错误布局|
|getEmptyView()|获取数据为空布局|
|getErrorView()|获取数据错误布局|
|getOtherView()|获取扩充布局|

### 布局文件使用
```
<com.walle.multistatuslayout.MultiStatusLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:id="@+id/status_layout"
        android:layout_marginBottom="50dp"
        app:loadingLayout="@layout/loading_layout"
        app:emptyLayout="@layout/empty_layout"
        app:errorLayout="@layout/error_layout"
        app:netErrorLayout="@layout/net_error_layout"
        app:targetViewId="@+id/rl_title"
        app:netErrorReloadViewId="@id/tv_netError"
        app:errorReloadViewId="@id/tv_error"
        tools:context="com.example.wall_e.multistatuslayout.MainActivity">

        <TextView
            android:text="CONTENT"
            android:textSize="30dp"
            android:layout_marginTop="30dp"
            android:textColor="#FF5399F4"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content" />
       
    </com.walle.multistatuslayout.MultiStatusLayout>

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

## 关于我
个人邮箱：walle0228@163.com

[我的简书](http://www.jianshu.com/u/f914004db506)

###如果觉得文章帮到你，不求打赏，喜欢我的文章可以关注我和朋友一起运营的微信公众号，将会定期推送优质技术文章，求关注~~~###

![欢迎关注“大话安卓”微信公众号](http://upload-images.jianshu.io/upload_images/1956769-2f49dcb0dc5195b6.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![欢迎加入“大话安卓”技术交流群，互相学习提升](http://upload-images.jianshu.io/upload_images/1956769-326c166b86ed8e94.JPG?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
