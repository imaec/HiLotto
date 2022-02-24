import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    repositories {
        google()
        jcenter()
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
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.10")
        classpath("com.google.dagger:hilt-android-gradle-plugin:${Versions.dagger_hilt}")
        classpath("org.jetbrains.kotlin:kotlin-serialization:${Versions.kotlin}")
        classpath("com.google.gms:google-services:${Versions.google_services}")
        classpath("com.google.firebase:firebase-crashlytics-gradle:${Versions.crashlytics}")
        classpath("org.jlleitschuh.gradle:ktlint-gradle:${Versions.ktlint}")
    }
}

allprojects {
    repositories {
        google()
        jcenter()
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
        kotlinOptions.jvmTarget = Versions.java_version
        kotlinOptions.freeCompilerArgs = listOf("-Xallow-result-return-type")
    }
}

task("clean", Delete::class) {
    delete(rootProject.buildDir)
}
