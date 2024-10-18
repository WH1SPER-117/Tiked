plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
}

android {
    namespace = "com.example.ticked"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.ticked"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    // Enable viewBinding and dataBinding
    buildFeatures {
        viewBinding = true
        dataBinding = true
    }
}

dependencies {
    // Core AndroidX libraries
    implementation(libs.androidx.core.ktx.v1101)
    implementation(libs.androidx.appcompat.v161)
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.material.v190)

    // Room (Database)
    implementation(libs.androidx.room.runtime)
    implementation(libs.firebase.crashlytics.buildtools)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.material3.android)
    implementation(libs.androidx.recyclerview)
    kapt(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx.v252)

    // Test dependencies
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(libs.gson)

    // Google Material Design library
    implementation(libs.androidx.recyclerview.v121)

    implementation(libs.material.calendar.view)
    implementation(libs.material.calendar.view.v100)

    implementation(libs.androidx.core.ktx.v160)
    implementation(libs.androidx.appcompat.v130)

    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)
}
