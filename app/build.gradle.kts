plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("kotlin-parcelize")
    id("dagger.hilt.android.plugin")
    id("org.jlleitschuh.gradle.ktlint")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
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
    compileSdk = Apps.compileSdk
    buildToolsVersion = Apps.build_tools
    defaultConfig {
        minSdk = Apps.minSdk
        targetSdk = Apps.targetSdk
        versionCode = Apps.versionCode
        versionName = Apps.versionName

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

    testImplementation(TestLibs.junit)
    androidTestImplementation(TestLibs.junit_ext)
    androidTestImplementation(TestLibs.espresso)

    implementation(Libs.kotlin)
    implementation(Libs.appcompat)
    implementation(Libs.core_ktx)
    implementation(Libs.material)
    implementation(Libs.constraint_layout)
    implementation(Libs.activity_ktx)
    implementation(Libs.fragment_ktx)

    implementation(Libs.lifecycle_viewmodel_ktx)
    implementation(Libs.lifecycle_livedata_ktx)
    implementation(Libs.lifecycle_extensions)

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

    implementation(Libs.room_runtime)
    implementation(Libs.room_ktx)
    kapt(Libs.room_compiler)
    implementation(Libs.datastore)
    implementation(Libs.datastore_core)

    implementation(Libs.jsoup)
    implementation(Libs.admob)
    implementation(platform(Libs.firebase_bom))
    implementation(Libs.firebase_database)
    implementation(Libs.firebase_analytics)
    implementation(Libs.firebase_crashlytics)
    implementation(Libs.kakao_link)
    implementation(Libs.timber)
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
