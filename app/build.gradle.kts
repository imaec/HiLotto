plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    id("dagger.hilt.android.plugin")
    id("org.jlleitschuh.gradle.ktlint")
    id("kotlin-parcelize")
    kotlin("android")
    kotlin("kapt")
}

android {
    signingConfigs {
        getByName("debug") {
            storeFile = file("/Users/imaec/work_android/z_res/HiLotto/hilotto.jks")
            storePassword = "imaec23"
            keyAlias = "hilotto"
            keyPassword = "imaec23"
        }
    }
    compileSdk = Apps.COMPILE_SDK
    buildToolsVersion = Apps.BUILD_TOOLS
    defaultConfig {
        minSdk = Apps.MIN_SDK
        targetSdk = Apps.TARGET_SDK
        versionCode = Apps.VERSION_CODE
        versionName = Apps.VERSION_NAME

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables.useSupportLibrary = true
        multiDexEnabled = true
    }
    buildTypes {
        getByName("debug") {
            isMinifyEnabled = false
            isDebuggable = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            resValue("string", "admob_app_id", "ca-app-pub-3940256099942544~3347511713")
            resValue("string", "ad_id_main_front", "ca-app-pub-3940256099942544/1033173712")
            resValue("string", "ad_id_recommend_front", "ca-app-pub-3940256099942544/1033173712")
            resValue("string", "ad_id_lately_front", "ca-app-pub-3940256099942544/1033173712")
            resValue("string", "ad_id_history_front", "ca-app-pub-3940256099942544/1033173712")
            resValue("string", "ad_id_home_banner", "ca-app-pub-3940256099942544/6300978111")
            resValue("string", "ad_id_my_banner", "ca-app-pub-3940256099942544/6300978111")
            resValue("string", "ad_id_lately_banner", "ca-app-pub-3940256099942544/6300978111")
            resValue("string", "ad_id_store_banner", "ca-app-pub-3940256099942544/6300978111")
        }
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            resValue("string", "admob_app_id", "ca-app-pub-7147836151485354~9585074380")
            resValue("string", "ad_id_main_front", "ca-app-pub-7147836151485354/4133243636")
            resValue("string", "ad_id_recommend_front", "ca-app-pub-7147836151485354/5035261765")
            resValue("string", "ad_id_lately_front", "ca-app-pub-7147836151485354/3774344500")
            resValue("string", "ad_id_history_front", "ca-app-pub-7147836151485354/2078119459")
            resValue("string", "ad_id_home_banner", "ca-app-pub-7147836151485354/6923058507")
            resValue("string", "ad_id_my_banner", "ca-app-pub-7147836151485354/3530608409")
            resValue("string", "ad_id_lately_banner", "ca-app-pub-7147836151485354/8835099498")
            resValue("string", "ad_id_store_banner", "ca-app-pub-7147836151485354/7149007957")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildFeatures {
        dataBinding = true
        viewBinding = true
    }
}

kapt {
    useBuildCache = true
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":data"))

    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar", "*.aar"))))

    testImplementation(TestLibs.JUNIT)
    androidTestImplementation(TestLibs.JUNIT_EXT)
    androidTestImplementation(TestLibs.ESPRESSO)

    Android.run {
        implementation(KOTLIN)
        implementation(APPCOMPAT)
        implementation(CORE_KTX)
        implementation(MATERIAL)
        implementation(CONSTRAINT_LAYOUT)
        implementation(ACTIVITY_KTX)
        implementation(FRAGMENT_KTX)
        implementation(LIFECYCLE_VIEWMODEL_KTX)
        implementation(LIFECYCLE_LIVEDATA_KTX)
        implementation(LIFECYCLE_EXTENSIONS)
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

    implementation(Jsoup.JSOUP)
    implementation(Admob.ADMOB)
    implementation(Kakao.KAKAO_LINK)
    implementation(Timber.TIMBER)
}

ktlint {
    debug.set(true)
    verbose.set(true)
    android.set(true)
    outputToConsole.set(true)
    outputColorName.set("RED")
    reporters {
        reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.CHECKSTYLE)
    }
    filter {
        exclude("**/generated/**")
        include("**/kotlin/**")
    }
}
