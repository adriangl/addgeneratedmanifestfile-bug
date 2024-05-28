plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id("com.ncorti.kotlin.gradle.template.plugin")
}

android {
    namespace = "com.example"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example"
        minSdk = 28
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.toVersion(libs.versions.java.sdk.get())
        targetCompatibility = JavaVersion.toVersion(libs.versions.java.sdk.get())
    }
    kotlinOptions {
        jvmTarget = libs.versions.java.sdk.get()
    }

    buildFeatures {
        buildConfig = true
    }
}

dependencies {

    implementation(libs.appcompat.v7)
    implementation(libs.constraint.layout)
    implementation(libs.livedata)
    implementation(libs.viewmodel)
    testImplementation(libs.junit)
    androidTestImplementation(libs.runner)
    androidTestImplementation(libs.espresso.core)
}

manifestGenerator {
    applicationFqClassName.set("com.example.Application")
    activityFqClassName.set("com.example.MainActivity")
    message.set("Hello World!")
}
