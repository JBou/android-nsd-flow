plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    namespace = "de.aroio.application.nsd.flow"
    compileSdk = 33
    defaultConfig {
        applicationId = "de.aroro.application.nsd.flow"
        minSdk = 19
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        getByName("debug") {
            enableUnitTestCoverage = true
        }
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }
}

dependencies {

    implementation(project(path = ":android-nsd-flow"))
    implementation(Dependencies.Kotlin.std)
    implementation(Dependencies.Kotlin.Coroutines.core)
    implementation(Dependencies.Kotlin.Coroutines.android)
    implementation(Dependencies.Android.annotation)

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.recyclerview:recyclerview:1.3.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    testImplementation(Dependencies.JUnit.jupiter)

    androidTestImplementation("androidx.test.ext:junit:1.1.5")
}