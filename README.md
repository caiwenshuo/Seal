# asLockApp

安卓应用锁

### 开发配置
本应用是在Android Studio 3.1上面开发的。

使用的JDK版本是 jdk1.8.0_112。

安卓版本：
最低要求是：minSdkVersion 14
目标版本为：targetSdkVersion 23

关于各个分支的介绍：目前有四个分支，

1、master只实现了最核心的锁定功能。没有设置，只能选择程序锁定。

2、pure分支，完成了相关博客里面的所有功能。

3、framelayout这个版本，加入了一些ReactNative的界面，但是这个工程存在问题，大多数人也没配置相关环境。

4、seal分支，采用material design重构界面，优化了分类逻辑，修复了unlockActivity的逻辑问题，解决了解锁bug。

同时这个版本采用的Broadcast唤醒LockService服务。只需稍作改动就能完成开机自启动等其他重要功能。

### 2016.1.25 更新

加入了更改密码以及绑定邮箱等功能。
同时，对这个项目的机构有了更深入的认识，有了一些重构代码的想法。预计在后面的一个礼拜进行代码的重构。

### 截止2016.9.11 更新
根据《APP研发录》的一些设计重构了部分代码，将一些全局性的变量放到Application类里面。
修复了遇到的所有的bug，算是一个稳定能用的版本。欢迎大家发现bug，一起改进。

### 截止2016.9.18 更新
尝试使用MVP模式对项目进行重构，当前只完成了主界面的重构，算是实现了面向接口编程。
使得项目各模块之间的耦合度降低。方便后期进行模块的更新

### 截止2017.9.5 更新
去掉了ReactNative相关的工能，将博客完整的实现放到了pure分支上。


### 截止2018.9.15 更新
通过material design重构界面，增加了floating action button和顶部toolbar。更改了页面跳转动画。将setting等功能集成到toolbar中。
界面按照已锁定和未锁定分类，逻辑更加清晰。
修改代码逻辑，修正了原来解锁重复出现解锁页面的bug。

### 后续工作

添加指纹解锁模块。


### 参考资料
关于应用锁的实现可以参考原作者的博客：

[安卓开发之应用锁](http://blog.csdn.net/include_u/article/details/49889791)

[应用锁之获取栈顶Activity](http://blog.csdn.net/include_u/article/details/50558130)

seal版本的更新可参考我的博客：

不同程序activity跳转问题 https://blog.csdn.net/weixin_41337483/article/details/82776967

Android任务栈和activity的跳转中需要特别注意的问题 https://blog.csdn.net/weixin_41337483/article/details/82777395

listview的listitem点击没反应问题与android焦点  https://blog.csdn.net/weixin_41337483/article/details/82458392
