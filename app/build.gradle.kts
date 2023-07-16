import tw.idv.louislee.Version

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-parcelize")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "tw.idv.louislee.toolbox"
    compileSdk = Version.SDK

    defaultConfig {
        applicationId = "tw.idv.louislee.toolbox"
        minSdk = Version.MIN_SDK
        targetSdk = Version.SDK
        versionCode = 1
        versionName = "1.0.0"

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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Version.KOTLIN_COMPOSE_COMPILER_EXTENSION
    }
    packaging {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
        }
    }
}

kapt {
    correctErrorTypes = true
}

dependencies {
    implementation(project(mapOf("path" to ":modules:domain")))
    implementation("androidx.core:core-ktx:${Version.ANDROID_CORE}")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:${Version.LIFECYCLE}")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:${Version.LIFECYCLE}")
    implementation("androidx.activity:activity-compose:${Version.ACTIVITY_COMPOSE}")
    implementation(platform("androidx.compose:compose-bom:${Version.COMPOSE}"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("com.maxkeppeler.sheets-compose-dialogs:core:${Version.SHEETS_COMPOSE_DIALOGS}")
    implementation("com.maxkeppeler.sheets-compose-dialogs:calendar:${Version.SHEETS_COMPOSE_DIALOGS}")
    implementation("com.google.dagger:hilt-android:${Version.DAGGER_HILT}")
    implementation("androidx.hilt:hilt-navigation-compose:${Version.DAGGER_HILT_COMPOSE}")
    kapt("com.google.dagger:hilt-android-compiler:${Version.DAGGER_HILT}")
    implementation("androidx.navigation:navigation-compose:${Version.NAV}")
    implementation("org.jsoup:jsoup:${Version.JSOUP}")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:${Version.COMPOSE}"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    testImplementation("org.mockito.kotlin:mockito-kotlin:${Version.MOCKITO}")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:${Version.COROUTINES_TEST}")
}