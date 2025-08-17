plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.dagger.hilt)
    alias(libs.plugins.ktlint)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.wegielek.simpleplanningpoker"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.wegielek.simpleplanningpoker"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlin {
        target {
            compilerOptions {
                jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
            }
        }
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    // Desugar
    coreLibraryDesugaring(libs.desugar.jdk.libs)

    // Serialization
    implementation(libs.kotlinx.serialization.json)

    // Material icons
    implementation(libs.androidx.material.icons.extended)

    // Core OkHttp
    implementation(libs.okhttp)

    // Interceptor
    implementation(libs.logging.interceptor)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)

    // Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    // Hilt navigation for Compose
    implementation(libs.androidx.hilt.navigation.compose)

    // Encrypted Shared Preferences
    implementation(libs.androidx.security.crypto)

    // Data store
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.androidx.security.crypto.v110)
    implementation(libs.tink.android)

    // Viewmodel coroutines
    implementation(libs.androidx.lifecycle.viewmodel.ktx)

    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}

kapt {
    correctErrorTypes = true
}
