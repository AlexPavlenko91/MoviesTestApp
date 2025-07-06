import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.android.ksp)
    alias(libs.plugins.android.hilt)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.example.moviestest"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.moviestest"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        val localProperties = Properties().apply {
            load(rootProject.file("local.properties").inputStream())
        }
        val tmdbToken = localProperties["TMDB_ACCESS_TOKEN"] ?: "MISSING_TOKEN"

        debug {
            buildConfigField("String", "BASE_URL", "\"https://api.themoviedb.org/3/\"")
            buildConfigField("String", "TMDB_ACCESS_TOKEN", "\"$tmdbToken\"")
        }

        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String", "BASE_URL", "\"https://api.themoviedb.org/3/\"")
            buildConfigField("String", "TMDB_ACCESS_TOKEN", "\"$tmdbToken\"")

        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlin {
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11)
        }
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1,LICENSE.md,LICENSE-notice.md}"
        }
    }
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.paging.compose.android)
    ksp(libs.bundles.kspBundles)

    implementation(libs.bundles.core)
    implementation(libs.bundles.ui)
    implementation(libs.bundles.hilt)
    implementation(libs.bundles.network)
    implementation(libs.bundles.storage)
    implementation(libs.bundles.common)

    androidTestImplementation(platform(libs.androidx.compose.bom))
    implementation(libs.bundles.test)
    testImplementation(kotlin("test"))
}