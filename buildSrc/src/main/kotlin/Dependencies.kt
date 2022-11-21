object Apps {
    const val COMPILE_SDK = 31
    const val MIN_SDK = 23
    const val TARGET_SDK = 31
    const val VERSION_CODE = 109
    const val VERSION_NAME = "1.0.9"
    const val BUILD_TOOLS = "31.0.0"
}

object Android {
    const val KOTLIN = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.KOTLIN}"

    // AndroidX
    const val MULTIDEX = "androidx.multidex:multidex:${Versions.MULTIDEX}"
    const val APPCOMPAT = "androidx.appcompat:appcompat:${Versions.APPCOMPAT}"
    const val CORE_KTX = "androidx.core:core-ktx:${Versions.CORE_KTX}"
    const val CONSTRAINT_LAYOUT = "androidx.constraintlayout:constraintlayout:${Versions.CONTRAINT_LAYOUT}"
    const val ACTIVITY_KTX = "androidx.activity:activity-ktx:${Versions.ACTIVITY_KTX}"
    const val FRAGMENT_KTX = "androidx.fragment:fragment-ktx:${Versions.FRAGMENT_KTX}"

    const val MATERIAL = "com.google.android.material:material:${Versions.MATERIAL}"

    // Lifecycle
    const val LIFECYCLE_VIEWMODEL_KTX = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.LIFECYCLE}"
    const val LIFECYCLE_LIVEDATA_KTX = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.LIFECYCLE}"
    const val LIFECYCLE_EXTENSIONS = "androidx.lifecycle:lifecycle-extensions:${Versions.LIFECYCLE_EXTENSION}"
}

object Coroutines {
    const val COROUTINES_CORE = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.COROUTINES}"
    const val COROUTINES_ANDROID = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.COROUTINES}"
    const val COROUTINES_TEST = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.COROUTINES}"
}

object Hilt {
    const val DAGGER_HILT_ANDROID = "com.google.dagger:hilt-android:${Versions.DAGGER_HILT}"
    const val DAGGER_HILT_COMPILER = "com.google.dagger:hilt-android-compiler:${Versions.DAGGER_HILT}"
    const val HILT_COMMON = "androidx.hilt:hilt-common:${Versions.HILT}"
    const val HILT_COMPILER = "androidx.hilt:hilt-compiler:${Versions.HILT}"
}

object Datastore {
    const val DATASTORE = "androidx.datastore:datastore-preferences:${Versions.DATASTORE}"
    const val DATASTORE_CORE = "androidx.datastore:datastore-preferences-core:${Versions.DATASTORE}"
}

object Retrofit {
    const val RETROFIT = "com.squareup.retrofit2:retrofit:${Versions.RETROFIT}"
    const val RETROFIT_CONVERTER = "com.squareup.retrofit2:converter-gson:${Versions.RETROFIT}"
}

object OkHttp {
    const val OKHTTP = "com.squareup.okhttp3:okhttp:${Versions.OKHTTP3}"
    const val OKHTTP_INTERCEPTOR = "com.squareup.okhttp3:logging-interceptor:${Versions.OKHTTP3}"
}

object Room {
    const val ROOM_RUNTIME = "androidx.room:room-runtime:${Versions.ROOM}"
    const val ROOM_KTX = "androidx.room:room-ktx:${Versions.ROOM}"
    const val ROOM_COMPILER = "androidx.room:room-compiler:${Versions.ROOM}"
}

object Firebase {
    const val FIREBASE_BOM = "com.google.firebase:firebase-bom:${Versions.FIREBASE}"
    const val FIREBASE_DATABASE = "com.google.firebase:firebase-database-ktx"
    const val FIREBASE_ANALYTICS = "com.google.firebase:firebase-analytics-ktx"
    const val FIREBASE_CRASHLYTICS = "com.google.firebase:firebase-crashlytics-ktx"
}

object Admob {
    const val ADMOB = "com.google.android.gms:play-services-ads:${Versions.ADMOB}"
}

object Jsoup {
    const val JSOUP = "org.jsoup:jsoup:${Versions.JSOUP}"
}

object Kakao {
    const val KAKAO_LINK = "com.kakao.sdk:v2-link:${Versions.KAKAO_LINK}"
}

object Timber {
    const val TIMBER = "com.jakewharton.timber:timber:${Versions.TIMBER}"
}

object TestLibs {
    const val JUNIT = "junit:junit:${Versions.JUNIT}"
    const val JUNIT_EXT = "androidx.test.ext:junit:${Versions.JUNIT_EXT}"
    const val ESPRESSO = "androidx.test.espresso:espresso-core:${Versions.ESPRESSO}"
}
