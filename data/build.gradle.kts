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

    implementation(Android.KOTLIN)

    implementation(Coroutines.COROUTINES_CORE)
    implementation(Coroutines.COROUTINES_ANDROID)

    implementation(Hilt.DAGGER_HILT_ANDROID)
    kapt(Hilt.DAGGER_HILT_COMPILER)
    implementation(Hilt.HILT_COMMON)
    kapt(Hilt.HILT_COMPILER)

    implementation(Retrofit.RETROFIT)
    implementation(Retrofit.RETROFIT_CONVERTER)
    implementation(OkHttp.OKHTTP)
    implementation(OkHttp.OKHTTP_INTERCEPTOR)

    implementation(Room.ROOM_RUNTIME)
    implementation(Room.ROOM_KTX)
    kapt(Room.ROOM_COMPILER)
    implementation(Datastore.DATASTORE)
    implementation(Datastore.DATASTORE_CORE)

    implementation(platform(Firebase.FIREBASE_BOM))
    implementation(Firebase.FIREBASE_DATABASE)
    implementation(Firebase.FIREBASE_ANALYTICS)
    implementation(Firebase.FIREBASE_CRASHLYTICS)

    implementation(Jsoup.JSOUP)
    implementation(Timber.TIMBER)
}
