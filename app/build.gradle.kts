import org.jetbrains.kotlin.konan.properties.Properties
import java.io.FileInputStream

plugins {
  alias(libs.plugins.androidApplication)
  alias(libs.plugins.hiltAndroid)
  alias(libs.plugins.kotlin)
  alias(libs.plugins.ksp)
  alias(libs.plugins.kotlinSerialization)
  alias(libs.plugins.compose.compiler)
  id("kotlin-parcelize")
}

android {
  namespace = "com.android.ricknmorty"
  compileSdk = libs.versions.compileSdk.get().toInt()

  defaultConfig {
    applicationId = "com.android.ricknmorty"
    minSdk = libs.versions.minSdk.get().toInt()
    targetSdk = libs.versions.targetSdk.get().toInt()
    versionCode = 1
    versionName = "1.0"

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    vectorDrawables {
      useSupportLibrary = true
    }
  }

  signingConfigs {
    create("release") {
      val keyStoreFile = file("$rootDir/keystore/keystore.properties")
      if (keyStoreFile.exists()) {
        val properties = Properties()
        properties.load(FileInputStream(keyStoreFile))
        storeFile = properties["keyStore"]?.let { file(it) }
        storePassword = properties["storePassword"].toString()
        keyPassword = properties["keyAlias"].toString()
        keyAlias = properties["keyPassword"].toString()
      } else {
        storeFile = file("keystore.jks")
        storePassword = System.getenv("RELEASE_KEYSTORE_PASSWORD")
        keyPassword = System.getenv("RELEASE_KEYSTORE_KEY_ALIAS_PASSWORD")
        keyAlias = System.getenv("RELEASE_KEYSTORE_KEY_ALIAS")
      }
    }
  }

  buildTypes {
    release {
      isMinifyEnabled = true
      proguardFiles(
        getDefaultProguardFile("proguard-android-optimize.txt"),
        "proguard-rules.pro"
      )
      signingConfig = signingConfigs.getByName("release")
    }
    getByName("debug") {
      applicationIdSuffix = ".debug"
    }
  }
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
  }
  kotlinOptions {
    jvmTarget = "1.8"
  }
  buildFeatures {
    compose = true
    buildConfig = true
  }
  packaging {
    resources {
      excludes += "/META-INF/{AL2.0,LGPL2.1}"
    }
  }
  flavorDimensions += listOf("default")
  productFlavors {
    create("alpha") {
      dimension = "default"
      applicationId = "com.android.ricknmorty"
      applicationIdSuffix = ".alpha"
      signingConfig = signingConfigs.getByName("debug")
    }

    create("beta") {
      dimension = "default"
      applicationId = "com.android.ricknmorty"
      applicationIdSuffix = ".beta"
      signingConfig = signingConfigs.getByName("debug")
    }

    create("prod") {
      dimension = "default"
      applicationId = "com.android.ricknmorty"
      signingConfig = signingConfigs.getByName("release")
    }
  }
}

dependencies {
  testImplementation(libs.junit)
  androidTestImplementation(libs.ext.junit)
  androidTestImplementation(libs.espresso.core)
  implementation(libs.hilt.android)
  ksp(libs.dagger.hilt.compiler)

  implementation(project(":helpers:base"))
  implementation(project(":helpers:network"))
  implementation(project(":ui-resources"))
  implementation(project(":features:characters"))
  implementation(project(":features:episodes"))
  implementation(project(":router"))
  implementation(project(":data"))
}