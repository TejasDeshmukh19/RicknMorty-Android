apply(from = "${rootDir}/library.gradle")
plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlin)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.ricknmorty.ui_resources"

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    buildFeatures {
        compose = true
    }
}

dependencies {
    api(libs.core.splashscreen)
    implementation(project(":helpers:base"))
}