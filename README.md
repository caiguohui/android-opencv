# 感谢"Android弟"的blog成功在Windows下集成opencv
Windows集成opencv参考：http://blog.csdn.net/sbsujjbcy/article/details/49520791

以下是Mac下集成opencv的步骤

#安装软件, 依次按照顺序下载安装
1. 下载安装JDK1.8, 下载地址: http://download.oracle.com/otn-pub/java/jdk/8u73-b02/jdk-8u73-macosx-x64.dmg
2. 下载Android SDK, 下载地址: http://dl.google.com/android/android-sdk_r24.4.1-macosx.zip
3. 下载Android NDK, 下载地址: http://dl.google.com/android/repository/android-ndk-r11-darwin-x86_64.zip
4. 下载安装Android Studio 1.5.1, 下载地址: https://dl.google.com/dl/android/studio/install/1.5.1.0/android-studio-ide-141.2456560-mac.dmg
5. 下载OpenCV for Android, 下载地址: http://120.52.72.56/jaist.dl.sourceforge.net/c3pr90ntcsf0/project/opencvlibrary/opencv-android/3.1.0/OpenCV-3.1.0-android-sdk.zip

ps: 在当前用户目录下创建Library/Android, 然后把Android SDK解压到创建的目录下,修改目录名称为sdk, 然后把Android NDK解压到sdk根目录下. 如果当前用户目录的路径有特殊字符、空格或者数字，请切换到其他目录下，保证目录路径没有特殊字符、空格或者数字.</br>
  如无法直接下载上面的连接, 可使用迅雷. 若还无法下载, 你懂得, 那只能翻墙了.</br>
  安装好Android Studio之后, 配置SDK路径. 目前用的SDK是6.0.</br>

#准备好开发环境, 正式
1. 打开Android Studio, 新建工程
