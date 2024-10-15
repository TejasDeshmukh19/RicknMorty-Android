apply(from = "${rootDir}/library.gradle")
plugins {
  alias(libs.plugins.androidLibrary)
  alias(libs.plugins.kotlin)
  alias(libs.plugins.ksp)
  alias(libs.plugins.hiltAndroid)
}

android {
  namespace = "com.ricknmorty.data"
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
  implementation(libs.hilt.android)
  ksp(libs.dagger.hilt.compiler)

  implementation(project(":ui-resources"))
  implementation(project(":helpers:network"))
}