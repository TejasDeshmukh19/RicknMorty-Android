plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlin)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.compose.compiler)
    id("kotlin-parcelize")
}

android {
    namespace = "com.helpers.base"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    android.testOptions {
        unitTests.all {
            it.useJUnitPlatform()
        }
    }
}

dependencies {
    api(libs.hilt.android)
    ksp(libs.dagger.hilt.compiler)

    api(libs.retrofit)
    api(libs.retrofit2.converter.gson)

    api(libs.okhttp)
    debugApi(libs.chucker)
    releaseApi(libs.chucker.library.no.op)

    api(libs.core.ktx)
    androidTestApi(libs.ui.test.junit4)
    debugApi(libs.ui.tooling)
    debugApi(libs.ui.test.manifest)
    api(libs.activity.compose)
    api(libs.ui)
    api(libs.ui.graphics)
    api(libs.ui.tooling.preview)
    api(libs.material3)
    api(libs.coil.compose)
    api(libs.material)
    api(libs.material.icons.core)
    api(libs.material.icons.extended)
    api(libs.material3.window.size)
    api(libs.navigation.compose)
    api(libs.hilt.navigation.compose)
    api(libs.lifecycle.runtime.ktx)
    api(libs.lifecycle.viewmodel.compose)
    api(libs.kotlinx.serialization.json)
    api(libs.android.image.cropper)
}