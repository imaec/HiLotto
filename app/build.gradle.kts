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

//kapt {
//    useBuildCache = true
//}

dependencies {
    implementation(project(":domain"))
    implementation(project(":data"))

    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar", "*.aar"))))

    testImplementation(TestLibs.JUNIT)
    androidTestImplementation(TestLibs.JUNIT_EXT)
    androidTestImplementation(TestLibs.ESPRESSO)

    implementation(Android.KOTLIN)
    implementation(Android.APPCOMPAT)
    implementation(Android.CORE_KTX)
    implementation(Android.MATERIAL)
    implementation(Android.CONSTRAINT_LAYOUT)
    implementation(Android.ACTIVITY_KTX)
    implementation(Android.FRAGMENT_KTX)

    implementation(Android.LIFECYCLE_VIEWMODEL_KTX)
    implementation(Android.LIFECYCLE_LIVEDATA_KTX)
    implementation(Android.LIFECYCLE_EXTENSIONS)

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
