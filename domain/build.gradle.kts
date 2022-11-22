plugins {
    id("com.android.library")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    id("dagger.hilt.android.plugin")
    id("org.jlleitschuh.gradle.ktlint")
    id("kotlin-parcelize")
    kotlin("android")
    kotlin("kapt")
}

android {
    compileSdk = Apps.COMPILE_SDK
    defaultConfig {
        minSdk = Apps.MIN_SDK
        targetSdk = Apps.TARGET_SDK

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        multiDexEnabled = true
    }

    buildTypes {
        getByName("debug") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        getByName("release") {
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
    Android.run {
        implementation(KOTLIN)
    }

    Coroutines.run {
        implementation(COROUTINES_CORE)
    }

    Hilt.run {
        implementation(DAGGER_HILT_ANDROID)
        kapt(DAGGER_HILT_COMPILER)
    }

    implementation(Timber.TIMBER)
}
