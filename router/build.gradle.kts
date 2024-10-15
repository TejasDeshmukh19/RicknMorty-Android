apply(from = "${rootDir}/library.gradle")
plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.serialization")
    id("kotlin-parcelize")
}

android {
    namespace = "com.ricknmorty.router"

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}

dependencies {
    api(libs.navigation.compose)
    api(libs.hilt.navigation.compose)
    implementation(project(":helpers:base"))
    implementation(project(":ui-resources"))
}