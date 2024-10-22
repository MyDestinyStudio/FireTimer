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
    implementation(libs.core.ktx)
    implementation(libs.androidx.adaptive.android)
    implementation(libs.androidx.datastore.core.android)
    implementation(libs.rendering)


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

    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)

    ksp (libs.androidx.hilt.compiler)
    implementation (libs.hilt.navigation.compose)

    implementation(libs.hilt.android.gradle.plugin)

    //ksp("")\
       implementation("androidx.compose.material3:material3-window-size-class:1.2.1")

    //implementation( "io.coil-kt:coil-compose:2.2.2")

//coil
    implementation( libs.coil.compose)

    //Room
    implementation (libs.androidx.room.runtime)
    implementation (libs.androidx.room.ktx)
    ksp  (libs.androidx.room.compiler)


    implementation(libs.androidx.datastore.preferences)
    implementation(libs.symbol.processing.api)


    // Coroutines
    implementation (libs.kotlinx.coroutines.core)
    implementation (libs.kotlinx.coroutines.android)
    implementation (libs.kotlinx.coroutines.play.services)

    //noinspection GradleDependenc
    implementation (libs.androidx.lifecycle.runtime.ktx.v262)
    implementation (libs.androidx.lifecycle.viewmodel.compose.v262)
    //LiveData
    implementation (libs.androidx.lifecycle.livedata.ktx)

    implementation(libs.androidx.datastore.preferences)

    //Navigation
    implementation (libs.androidx.navigation.compose.v275)
    implementation (libs.hilt.navigation.compose)



}