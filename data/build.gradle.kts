plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
    id("kotlin-parcelize")
    id("dagger.hilt.android.plugin")
    id("org.jlleitschuh.gradle.ktlint")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
}

android {
    compileSdk = Apps.compileSdk
    defaultConfig {
        minSdk = Apps.minSdk
        targetSdk = Apps.targetSdk

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
    implementation(project(":domain"))

    implementation(Libs.kotlin)

    implementation(Libs.coroutine_core)
    implementation(Libs.coroutine_android)

    implementation(Libs.dagger_hilt_android)
    kapt(Libs.dagger_hilt_compiler)
    implementation(Libs.hilt_common)
    kapt(Libs.hilt_compiler)

    implementation(Libs.retrofit)
    implementation(Libs.retrofit_converter)
    implementation(Libs.okhttp3)
    implementation(Libs.okhttp3_interceptor)
    implementation(Libs.conscrypt)

    implementation(Libs.room_runtime)
    implementation(Libs.room_ktx)
    kapt(Libs.room_compiler)
    implementation(Libs.datastore)
    implementation(Libs.datastore_core)

    implementation(Libs.jsoup)
    implementation(platform(Libs.firebase_bom))
    implementation(Libs.firebase_database)
    implementation(Libs.firebase_analytics)
    implementation(Libs.firebase_crashlytics)
    implementation(Libs.timber)
}
