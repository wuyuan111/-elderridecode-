plugins {
    id("com.android.application")
}

android {
    namespace = "com.elder.ridecode"
    compileSdk = 34

    signingConfigs {
        create("release") {
            storeFile = file("../elderridecode.jks")
            storePassword = "elder123456"
            keyAlias = "elderridecode"
            keyPassword = "elder123456"
        }
    }

    defaultConfig {
        applicationId = "com.elder.ridecode"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        release {
            signingConfig = signingConfigs.getByName("release")
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.tencent.mm.opensdk:wechat-sdk-android:6.8.28")
}
