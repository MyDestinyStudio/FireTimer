plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.kotlin.parcelize)

    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")


}

android {
    namespace = "my.destinyStudio.firetimer"
    compileSdk = 34

    defaultConfig {
        applicationId = "my.destinyStudio.firetimer"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.7"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
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
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
//    implementation(libs.androidx.material.icons )
    implementation(libs.androidx.material.icons.extended.android)




//
    implementation (libs.androidx.constraintlayout.compose)

    implementation (libs.gson)

    // Dagger+Hilt

    implementation("com.google.dagger:hilt-android:2.48")
    ksp("com.google.dagger:hilt-android-compiler:2.48")

    ksp ("androidx.hilt:hilt-compiler:1.0.0")
    implementation ("androidx.hilt:hilt-navigation-compose:1.1.0-rc01")

    implementation("com.google.dagger:hilt-android-gradle-plugin:2.40")
    //ksp("")\
    //implementation( "org.jetbrains.kotlin:kotlin-gradle-plugin:2.0.0-RC1 ")


    //Room
    implementation ("androidx.room:room-runtime:2.6.0")
    implementation ("androidx.room:room-ktx:2.6.0")
    ksp  ("androidx.room:room-compiler:2.6.0")




    // Coroutines
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.7.3")

    //noinspection GradleDependenc
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")
    //LiveData
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2")
    // implementation ("androidx.lifecycle.lifecycle-viewmodel-ktx:2.6.2")


    //Navigation
    implementation ("androidx.navigation:navigation-compose:2.7.5")
    implementation ("androidx.hilt:hilt-navigation-compose:1.1.0")



    //Navigation
    implementation ("androidx.navigation:navigation-compose:2.7.5")
    implementation ("androidx.hilt:hilt-navigation-compose:1.1.0")

///
    implementation("androidx.room:room-runtime:2.6.1")
    annotationProcessor("androidx.room:room-compiler:2.6.1")
    implementation("com.google.devtools.ksp:symbol-processing-api:1.9.21-1.0.15")

   ksp("androidx.room:room-compiler:2.6.1")


}