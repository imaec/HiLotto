plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
}

apply(from = "../config_lib.gradle")

android {
    composeOptions {
        kotlinCompilerExtensionVersion = Versions.COMPOSE_COMPILER
    }

    buildFeatures {
        dataBinding = true
        compose = true
    }
    namespace = "com.imaec.hilotto.components"
}

dependencies {
    Android.run {
        implementation(MATERIAL)
    }

    Compose.run {
        api(COMPOSE_UI)
        api(COMPOSE_FOUNDATION)
        api(COMPOSE_LIVEDATA)
        api(COMPOSE_RXJAVA)
        api(COMPOSE_MATERIAL3)
        api(COMPOSE_MATERIAL)
        api(COMPOSE_MATERIAL_CORE)
        api(COMPOSE_MATERIAL_ICON)
        api(COMPOSE_UI_TOOL)
        api(COMPOSE_UI_PREVIEW)
        api(COMPOSE_ANIMATION)
        api(COMPOSE_FOUNDATION_LAYOUT)
        api(COMPOSE_COIL)
        api(COMPOSE_VIEWPAGER)
        api(COMPOSE_VIEWPAGER_INDICATORS)
        api(COMPOSE_VIEW_BINDING)
        api(COMPOSE_UI_UTIL)
        api(COMPOSE_ACTIVITY)
        api(COMPOSE_SWIPE_REFRESH)
        api(COMPOSE_PERMISSIONS)
        api(COMPOSE_LIFECYCLE)
        debugApi(COMPOSE_CUSTOM_VIEW)
    }
}
