// app/build.gradle.kts
plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
    id("kotlin-kapt")
}

android {
    namespace = "com.siptrack"
    compileSdk = 34
    defaultConfig {
        applicationId = "com.siptrack"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.14"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    // Compose BOM
    implementation(platform(libs.androidx.compose.bom))
    // Compose
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.activity.compose)
    // Hilt
    // Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    // Supabase
    implementation(libs.supabase.kt)
    // Retrofit
    implementation(libs.retrofit.core)
    implementation(libs.okhttp.logging)
    // Coil
    implementation(libs.coil.compose)
    // Maps
    implementation(libs.maps.compose)
    // MPAndroidChart
    implementation(libs.mp.android.chart)
}
