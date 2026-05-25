# 老人简易乘车码 App (ElderRideCode)

## 项目简介

这是一个专为老年人设计的简易乘车码应用，界面简洁清晰，字体超大，一键即可打开微信或支付宝的乘车码页面。

## 功能特点

- **超大字体**：标题42sp，按钮文字36sp，副标题22sp
- **超简界面**：只有两个大按钮，无多余元素
- **一键直达**：点击按钮直接跳转到微信/支付宝乘车码
- **智能容错**：未安装对应App时自动提示

## 界面预览

```
+---------------------------+
|                           |
|         乘车码            |  <- 42sp 大字标题
|  点击下方按钮打开乘车码    |  <- 22sp 提示文字
|                           |
|  +---------------------+  |
|  |                     |  |
|  |   微信              |  |
|  |   乘车码            |  |  <- 绿色大按钮 140dp高
|  |                     |  |
|  +---------------------+  |
|                           |
|  +---------------------+  |
|  |                     |  |
|  |   支付宝            |  |
|  |   乘车码            |  |  <- 蓝色大按钮 140dp高
|  |                     |  |
|  +---------------------+  |
|                           |
+---------------------------+
```

## 技术实现

### 微信乘车码跳转
- 方案1：通过 `weixin://dl/business/?t=xxxxx` scheme 直接唤起（需要微信开放平台配置）
- 方案2：备用方案直接启动微信App，并提示用户手动进入乘车码

### 支付宝乘车码跳转
- 通过 `alipays://platformapi/startapp?appId=20000067` scheme 直接唤起支付宝乘车码
- appId=20000067 是支付宝乘车码的固定appId

## 编译要求

- Android Studio Hedgehog (2023.1.1) 或更高版本
- compileSdk: 34
- minSdk: 24 (Android 7.0)
- targetSdk: 34
- Gradle 8.4
- AGP 8.2.0

## 项目结构

```
ElderRideCode/
├── app/
│   ├── build.gradle.kts          # 应用级构建配置
│   └── src/main/
│       ├── AndroidManifest.xml   # 应用清单
│       ├── java/com/elder/ridecode/
│       │   └── MainActivity.java # 主Activity
│       └── res/
│           ├── layout/
│           │   └── activity_main.xml  # 主布局
│           └── values/
│               ├── colors.xml     # 颜色资源
│               ├── strings.xml    # 字符串资源
│               └── themes.xml     # 主题样式
├── build.gradle.kts              # 项目级构建配置
├── settings.gradle.kts           # 项目设置
├── gradle.properties             # Gradle属性
└── gradle/wrapper/               # Gradle Wrapper
```

## 使用说明

### 方法一：使用Android Studio编译
1. 用Android Studio打开此项目目录
2. 同步Gradle (Sync Project with Gradle Files)
3. 连接手机或启动模拟器
4. 点击Run运行

### 方法二：命令行编译
```bash
# 进入项目目录
cd ElderRideCode

# 使用Gradle Wrapper编译Debug APK
./gradlew assembleDebug

# APK输出位置：app/build/outputs/apk/debug/app-debug.apk
```

## 安装到手机

编译完成后将APK传输到手机安装即可。建议：
1. 安装后将其放到手机桌面首屏显眼位置
2. 可设置App为"锁定任务"模式防止误操作退出

## 注意事项

1. 手机需已安装微信和/或支付宝才能使用对应功能
2. 首次使用需在微信/支付宝中开通乘车码服务
3. 部分手机可能需要在设置中允许本App"关联启动"其他应用
