import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    repositories {
        google()
        mavenCentral()
        maven {
            setUrl("https://jitpack.io")
        }
        maven {
            setUrl("https://devrepo.kakao.com/nexus/content/groups/public/")
        }
        maven {
            setUrl("https://plugins.gradle.org/m2/")
            content {
                includeGroup("org.jlleitschuh.gradle")
            }
        }
    }
    dependencies {
        classpath("com.android.tools.build:gradle:${Versions.GRADLE}")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.KOTLIN}")
        classpath("org.jetbrains.kotlin:kotlin-serialization:${Versions.KOTLIN}")
        classpath("com.google.dagger:hilt-android-gradle-plugin:${Versions.DAGGER_HILT}")
        classpath("com.google.gms:google-services:${Versions.GOOGLE_SERVICES}")
        classpath("com.google.firebase:firebase-crashlytics-gradle:${Versions.CRASHLYTICS}")
        classpath("org.jlleitschuh.gradle:ktlint-gradle:${Versions.KTLINT}")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven {
            setUrl("https://jitpack.io")
        }
        maven {
            setUrl("https://devrepo.kakao.com/nexus/content/groups/public/")
        }
        maven {
            setUrl("https://plugins.gradle.org/m2/")
            content {
                includeGroup("org.jlleitschuh.gradle")
            }
        }
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions.jvmTarget = Versions.JAVA
        kotlinOptions.freeCompilerArgs = listOf("-Xallow-result-return-type")
    }
}

task("clean", Delete::class) {
    delete(rootProject.buildDir)
}
