import com.android.build.gradle.BaseExtension
import com.android.build.gradle.api.AndroidBasePlugin

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    kotlin("kapt") version "1.9.24" apply false
}

buildscript {
    repositories {
        google()
        mavenCentral()
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

subprojects {

    plugins.withType<AndroidBasePlugin> {

        configure<BaseExtension> {
            compileSdkVersion(Config.compile_sdk)

            buildFeatures.viewBinding = true

            defaultConfig {
                minSdkVersion(Config.min_sdk)
                targetSdkVersion(Config.target_sdk)
                versionCode(Releases.version_code)
                versionName(Releases.version_name)
                testInstrumentationRunner("androidx.test.runner.AndroidJUnitRunner")
            }

            compileOptions {
                sourceCompatibility(Config.java_version)
                targetCompatibility(Config.java_version)
            }
        }
    }
}

task<Delete>("clean") {
    delete(rootProject.buildDir)
}