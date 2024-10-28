apply(from = "${rootDir}/library.gradle")
plugins {
    alias(libs.plugins.hiltAndroid)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlin)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.compose.compiler)
    id("kotlin-parcelize")
}

android {
    namespace = "com.ricknmorty.characters"

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }
    }

    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(libs.hilt.android)

    testImplementation(libs.kotestRunnerJunit5)
    testImplementation(libs.kotestAssertionsCore)
    testImplementation(libs.kotestProperty)
    testImplementation(libs.mockk)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.kotlin.reflect)

    ksp(libs.dagger.hilt.compiler)

    implementation(project(":ui-resources"))
    implementation(project(":helpers:base"))
    implementation(project(":helpers:network"))
    implementation(project(":data"))
}