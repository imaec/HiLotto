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

apply(from = "../config.gradle")
android {
    namespace = "com.imaec.hilotto"
}
kapt {
    useBuildCache = true
}

dependencies {
    implementation(project(":components"))
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
        implementation(LIFECYCLE_VIEWMODEL_COMPOSE)
        implementation(LIFECYCLE_LIVEDATA_KTX)
        implementation(LIFECYCLE_EXTENSIONS)
    }

    Compose.run {
        implementation(COMPOSE_NAVIGATION)
        implementation(COMPOSE_MATERIAL3)
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
        implementation(HILT_NAVIGATION_COMPOSE)
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
