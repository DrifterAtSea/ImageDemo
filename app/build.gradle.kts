import java.util.Properties

plugins {
  alias(libs.plugins.android.application)
  alias(libs.plugins.kotlin.android)
  alias(libs.plugins.kotlin.compose)
  id("com.google.dagger.hilt.android")
  kotlin("plugin.serialization") version "2.0.21"
  id("kotlin-kapt")
  id("com.google.devtools.ksp") version "2.1.21-2.0.1"
}

val localProperties = Properties().apply {
  val localFile = rootProject.file("local.properties")
  if (localFile.exists()) {
    load(localFile.inputStream())
  }
}

val unsplashAccessKey = localProperties.getProperty("UNSPLASH_ACCESS_KEY") ?: ""

android {
  namespace = "io.johannrosenberg.imagedemo"
  compileSdk = 35

  defaultConfig {
    applicationId = "io.johannrosenberg.imagedemo"
    minSdk = 30
    targetSdk = 35
    versionCode = 1
    versionName = "1.0"

    buildConfigField("String", "UNSPLASH_ACCESS_KEY", "\"$unsplashAccessKey\"")

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }

  buildTypes {
    release {
      isMinifyEnabled = false
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }
  }
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
  }
  kotlinOptions {
    jvmTarget = "11"
  }
  buildFeatures {
    compose = true
    buildConfig = true
  }
}

dependencies {

  implementation(libs.androidx.core.ktx)
  implementation(libs.androidx.lifecycle.runtime.ktx)
  implementation(libs.androidx.activity.compose)
  implementation(platform(libs.androidx.compose.bom))
  implementation(libs.androidx.ui)
  implementation(libs.androidx.ui.graphics)
  implementation(libs.androidx.ui.tooling.preview)
  implementation(libs.androidx.material3)

  implementation(libs.androidx.lifecycle.viewmodel)
  implementation(libs.androidx.lifecycle.viewmodel.compose)

  ksp(libs.room.compiler)

  implementation(libs.androidx.lifecycle.runtime.compose)

  implementation(libs.room.runtime)
  implementation(libs.room.ktx)

  implementation(libs.room.paging)
  implementation(libs.androidx.paging.runtime)
  implementation(libs.androidx.paging.compose)

  implementation(libs.androidx.compose.material)
  implementation(libs.org.jetbrains.kotlinx.serializaiton)
  implementation(libs.com.squareup.retrofit2)
  implementation(libs.com.squareup.retrofit2.adapter.rxjava2)
  implementation(libs.com.squareup.retrofit2.converter.moshi)
  implementation(libs.com.squareup.retrofit2.converter.gson)
  implementation(libs.com.squareup.retrofit2.converter.scalars)

  implementation(libs.coil.compose)
  implementation(libs.io.coil.compose)
  implementation(libs.io.coil.svg)

  implementation(libs.hilt.android)
  ksp(libs.hilt.android.compiler)
  implementation(libs.hilt.navigation.compose)

  implementation(libs.hilt.android)
  ksp(libs.hilt.android.compiler)

  testImplementation(libs.junit)
  androidTestImplementation(libs.androidx.junit)
  androidTestImplementation(libs.androidx.espresso.core)
  androidTestImplementation(platform(libs.androidx.compose.bom))
  androidTestImplementation(libs.androidx.ui.test.junit4)
  debugImplementation(libs.androidx.ui.tooling)
  debugImplementation(libs.androidx.ui.test.manifest)
}