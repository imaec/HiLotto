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
    implementation(project(":domain"))

    Android.run {
        implementation(KOTLIN)
    }

    Coroutines.run {
        implementation(COROUTINES_CORE)
        implementation(COROUTINES_ANDROID)
    }

    Hilt.run {
        implementation(DAGGER_HILT_ANDROID)
        kapt(DAGGER_HILT_COMPILER)
        implementation(HILT_COMMON)
        kapt(HILT_COMPILER)
    }

    Retrofit.run {
        implementation(RETROFIT)
        implementation(RETROFIT_CONVERTER)
    }

    OkHttp.run {
        implementation(OKHTTP)
        implementation(OKHTTP_INTERCEPTOR)
    }

    Room.run {
        implementation(ROOM_RUNTIME)
        implementation(ROOM_KTX)
        kapt(ROOM_COMPILER)
    }

    Datastore.run {
        implementation(DATASTORE)
        implementation(DATASTORE_CORE)
    }

    Firebase.run {
        implementation(platform(FIREBASE_BOM))
        implementation(FIREBASE_DATABASE)
        implementation(FIREBASE_ANALYTICS)
        implementation(FIREBASE_CRASHLYTICS)
    }

    // Etc.
    implementation(Jsoup.JSOUP)
    implementation(Timber.TIMBER)
}
