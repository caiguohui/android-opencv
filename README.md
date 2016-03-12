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

#准备好开发环境, 正式开始
1. 打开Android Studio, 新建工程My Application, 包名定义成:com.jack.opencv（AS包名是反着来的，所以输入的时候是: opencv.jack.com)
2. 点击下一步，选择Minimum SDK为4.0.3 (基本上支持4.0.3可以兼容市面上97.3%的机器了,剩下的都是一些古董级手机)
3. 继续下一步, 选择一个Empty Activity, 然后下一步, 最后直接Finish. (Finish完之后, 如果是第一次运行AS, 需要等待一段时间, 因为需要下载Gradle依赖的包, Gradle跟Maven类似, 有Maven经验的, 很容易就上手Gradle了)
4. Gradle下载完毕后, 会打开AS主界面了. 点击最左边的Tab选项, 选中Project选项卡, 会出现App项目,  可以看到上面显示Android字样, 表示是Android视图, 点击可以切换成Project视图模式.
5. 选中MyApplication, 右键创建native文件夹(native文件夹和app文件夹是同一层目录)
6. 解压之前下载的OpenCV for Android, 解压后在sdk目录下有一个native, native下有三个文件夹, 3rdparty, jni和libs, 把这三个目录复制到刚才在MyApplication里创建的native目录下
7. 打开gradle.properties,增加下面的属性使用旧版的ndk功能(不添加会使用实验性的ndk构建工具)<pre><code>android.useDeprecatedNdk=true</code></pre>
8. 解压下载的Android NDK到Android SDK根目录下, 并修改文加名为ndk
9. 回到AS, 打开local.properties, 配置ndk目录:<pre><code>ndk.dir=/Users/jack/Library/Android/sdk/ndk</code></pre>
10. 在App项目下的src/main下创建jni目录(右键New时，选择Folder → JNI Folder创建)
11. 在jni根目录下, 创建2个文件. Android.mk和Application.mk
12. 打开Andriod.mk, 新增
<pre><code>
APP_STL := gnustl_static
APP_CPPFLAGS := -frtti -fexceptions
APP_ABI := armeabi armeabi-v7a
APP_PLATFORM := android-8
</code></pre>
13. 打开Android.mk, 新增
<pre><code>
LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)
OpenCV_INSTALL_MODULES := on
OpenCV_CAMERA_MODULES := off
OPENCV_LIB_TYPE :=STATIC
ifeq ("$(wildcard $(OPENCV_MK_PATH))","")
include ..\..\..\..\native\jni\OpenCV.mk
else
include $(OPENCV_MK_PATH)
endif
LOCAL_MODULE := OpenCV
LOCAL_SRC_FILES :=
LOCAL_LDLIBS +=  -lm -llog
include $(BUILD_SHARED_LIBRARY)
</code></pre>
14. 打开build.gradle, 在android节点中增加下面的代码
<pre><code>
//禁止自带的ndk功能
sourceSets.main.jni.srcDirs = []
//重定向so目录为src/main/libs和src/main/jniLibs，原来为src/main/jniLibs
sourceSets.main.jniLibs.srcDirs = ['src/main/libs','src/main/jniLibs']
task ndkBuild(type: Exec, description: 'Compile JNI source with NDK') {
    Properties properties = new Properties()
    properties.load(project.rootProject.file('local.properties').newDataInputStream())
    def ndkDir = properties.getProperty('ndk.dir')
    if (org.apache.tools.ant.taskdefs.condition.Os.isFamily(org.apache.tools.ant.taskdefs.condition.Os.FAMILY_WINDOWS)) {
        commandLine "$ndkDir/ndk-build.cmd", '-C', file('src/main/jni').absolutePath
    } else {
        commandLine "$ndkDir/ndk-build", '-C', file('src/main/jni').absolutePath
    }
}
tasks.withType(JavaCompile) {
    compileTask -> compileTask.dependsOn ndkBuild
}
task ndkClean(type: Exec, description: 'Clean NDK Binaries') {
    Properties properties = new Properties()
    properties.load(project.rootProject.file('local.properties').newDataInputStream())
    def ndkDir = properties.getProperty('ndk.dir')
    if (org.apache.tools.ant.taskdefs.condition.Os.isFamily(org.apache.tools.ant.taskdefs.condition.Os.FAMILY_WINDOWS)) {
        commandLine "$ndkDir/ndk-build.cmd",'clean', '-C', file('src/main/jni').absolutePath
    } else {
        commandLine "$ndkDir/ndk-build",'clean', '-C', file('src/main/jni').absolutePath
    }
}
clean.dependsOn 'ndkClean'
</code></pre>
15. aaa
15. sdfsf
