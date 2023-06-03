plugins {
    `maven-publish`
    id("com.android.library")
    kotlin("android")
}

group = "com.github.aroio"

android {
    namespace = "de.aroro.library.nsd.flow"
    compileSdk = 30
    defaultConfig {
        minSdk = 19
        testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"

        // Enabling multidex support.
        multiDexEnabled = true
    }
    buildTypes {
        getByName("debug") {
            enableUnitTestCoverage = true
        }
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                    getDefaultProguardFile("proguard-android.txt"),
                    "proguard-rules.pro"
            )
        }
    }
    packagingOptions {
        resources {
            excludes += setOf("META-INF/AL2.0", "META-INF/LGPL2.1")
        }
    }

    publishing {
        singleVariant("release") {
            withSourcesJar()
            withJavadocJar()
        }
    }
}

dependencies {
    implementation(Dependencies.Kotlin.std)
    implementation(Dependencies.Kotlin.Coroutines.core)
    implementation(Dependencies.Android.annotation)

    testImplementation(Dependencies.JUnit.jupiter)
    testImplementation(Dependencies.Kotlin.Coroutines.test)
    testImplementation(Dependencies.Mockk.core)

    androidTestImplementation(Dependencies.Mockk.core)
    androidTestImplementation(Dependencies.Kotlin.Coroutines.test)
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
}

publishing {
    publications {
        create<MavenPublication>("release") {
            groupId = "com.github.aroio"
            artifactId = "nsd-flow"
            version = "0.1.0"

            afterEvaluate {
                from(components["release"])
            }
        }
    }
}