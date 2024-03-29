# CornerLayout

给任意布局添加边角横幅，效果如下图：

![image-20200902154545916](./assets/image-20200902154545916.png)

## 一、引入

本库已上传至`MavenCentral`，在引用前，请确保您项目已添加支持`mavenCentral()`。通常，它是默认就支持的，如无异常，您无需关心。

接下来，依赖本库：

```groovy
implementation 'com.hurryyu.android:cornerlayout:1.2'
```

## 二、使用

只需将您需要添加“边角横幅”的View使用CornerLayout包裹即可，示例代码如下：

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:orientation="vertical"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:gravity="center"
    tools:context=".MainActivity">

    <com.hurryyu.cornerlayout.CornerLayout
        android:id="@+id/cornerLayout"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        app:bannerBackgroundColor="@color/colorPrimary"
        app:bannerDistanceLength="75dp"
        app:bannerText="限时6折"
        app:direction="TOP_RIGHT"
        app:bannerTextColor="#FFFFFF"
        app:bannerTextSize="14sp"
        app:bannerWidth="34dp">

        <TextView
            android:layout_width="180dp"
            android:layout_height="130dp"
            android:background="@drawable/shape_book_card"
            android:gravity="center"
            android:text="安徒生童话"
            android:textColor="#FFFFFF"
            android:textSize="22sp"
            android:textStyle="bold" />
    </com.hurryyu.cornerlayout.CornerLayout>

</LinearLayout>
```

其中您可以自行修改的属性如下：

|        属性名         |                             作用                             |
| :-------------------: | :----------------------------------------------------------: |
| bannerBackgroundColor |                      设置横幅的背景颜色                      |
| bannerDistanceLength  |      设置横幅在View的长度和宽度上的最远距离(见下图解释)      |
|      bannerWidth      |                  设置横幅的宽度(见下图解释)                  |
|    bannerTextColor    |                       横幅上文字的颜色                       |
|    bannerTextSize     |                       横幅上文字的大小                       |
|      bannerText       |                       横幅上的文字内容                       |
|        display        |                         是否显示横幅                         |
|       direction       | 横幅显示的位置(TOP_LEFT , TOP_RIGHT , BOTTOM_LEFT , BOTTOM_RIGHT) |

下面对几个比较难理解的属性做出解释：

- bannerDistanceLength（设置横幅在View的长度和宽度上的最远距离）

  ![](./assets/image-20200904141659634.png)

- bannerWidth（设置横幅的宽度）

  ![](./assets/image-20200904144148587.png)

- display（是否显示横幅）

  默认值为true，您可以在代码中动态修改这个值，达到显示或隐藏横幅的效果：

  ```kotlin
  # 隐藏横幅
  cornerLayout.display = false
  ```

- direction（横幅显示的位置）

  默认情况下，横幅是显示在左上角的，你可以通过修改这个属性的值，来改变横幅显示的位置。当前一共支持4个值：

  1. TOP_LEFT：左上角
  2. TOP_RIGHT：右上角
  3. BOTTOM_LEFT：左下角
  4. BOTTOM_RIGHT：右下角

  ```kotlin
  # 让横幅显示在右下角(使用代码而非xml)
  cornerLayout.bannerPosition(BannerPosition.BOTTOM_RIGHT)
  ```

## 三、实现过程

我精心编写了一篇文章《Android自定义View实战-边角横幅》，它讲解了这个自定义View的实现过程，如果您对此感兴趣，可前往阅读文章了解详情。

博客：[HurryYu的博客](https://www.hurryyu.com/2020/09/04/Android%E8%87%AA%E5%AE%9A%E4%B9%89View%E5%AE%9E%E6%88%98-%E8%BE%B9%E8%A7%92%E6%A8%AA%E5%B9%85/)

CSDN：[HurryYu的CSDN](https://blog.csdn.net/cqbbyzh/article/details/108408490)

简书：[HurryYu的简书](https://www.jianshu.com/p/53989dc3b6cd)

如果对您有帮助，欢迎star，谢谢~