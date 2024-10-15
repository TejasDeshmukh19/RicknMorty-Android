apply(from = "${rootDir}/library.gradle")
plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlin)
    alias(libs.plugins.hiltAndroid)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.helpers.network"

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
    api(libs.retrofit)
    api(libs.retrofit2.converter.gson)
    implementation(libs.hilt.android)
    ksp(libs.dagger.hilt.compiler)
}