# folioReader-Tibetan

## FolioReader-Android及Aria的实现电子书阅读器
   其实使用起来很方便，对藏文完美支持，好像知乎也用了。也没有深入去研究写，主要看到两个项目优点多，几行代码就搞定！
## FolioReader Features
 - [x] Custom Fonts
 - [x] Custom Text Size
 - [x] Themes / Day mode / Night mode
 - [x] Text Highlighting
 - [x] List / Edit / Delete Highlights
 - [x] Handle Internal and External Links
 - [x] Portrait / Landscape
 - [x]  Reading Time Left / Pages left
 - [x] In-App Dictionary
 - [x] Media Overlays (Sync text rendering with audio playback)
 - [x] TTS - Text to Speech Support
 - [x] Book Search
 - [x] Add Notes to a Highlight
 - [x] Last Read Position Listener
 - [x] Horizontal reading
 - [x] Distraction Free Reading
## Aria has the following characteristics
 - [x] can be used in Activity, Service, Fragment, Dialog, popupWindow, Notification and other components
 - [x] support the task of automatic scheduling, the user does not need to care about the state of the task switch logic
 - [x] Through the Aria event, it is easy to get the download status of the current download task
 - [x] a code plus can get the current download speed
 - [x] a code can be dynamically set the maximum number of downloads
 - [x] code to achieve speed limit
 - [x] It is easy to modify the number of download threads by modifying the configuration file
 - [x] priority to download a task
## 工具
* Android Studio 安卓开发工具，被墙了下面是国内地址
http://www.android-studio.org/
但感觉还是jetbrains的IDE比较牛逼  http://www.jetbrains.com
* FolioReader项目地址
https://github.com/FolioReader/FolioReader-Android
* Ari项目地址
https://github.com/AriaLyy/Aria/blob/master/ENGLISH_README.md

## 使用
在manifest中配置权限

```xml
<uses-permission android:name="android.permission.MOUNT_UNMOUNT_FILESYSTEMS"
        tools:ignore="ProtectedPermissions" />
<uses-permission android:name="android.permission.INTERNET"/>
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
```
另外加一行配置Android9.0的

```xml
<application android:networkSecurityConfig="@xml/network_security_config">
```
之后在相应的/res/xml/network_security_config.xml配置

```xml
<?xml version="1.0" encoding="utf-8"?>
<network-security-config>
    <domain-config cleartextTrafficPermitted="true">
        <domain includeSubdomains="true">127.0.0.1</domain>
        <domain includeSubdomains="true">localhost</domain>
    </domain-config>
</network-security-config>
```
```
在仓库中添加依赖包

```java

    implementation "com.folioreader:folioreader:0.5.1"
    implementation 'com.arialyy.aria:aria-core:3.5.1'
    annotationProcessor 'com.arialyy.aria:aria-compiler:3.5.1'
```

下载

```java
                Aria.download(this)
                        .load(DOWNLOAD_URL)
                        .setFilePath(filepath,true)
                        .start();
               
```
打开
` folioReader.openBook("sdcard/bookeader/1.epub");`

之后运行测试

