import org.jetbrains.kotlin.kapt3.base.Kapt.kapt

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    kotlin("kapt")
    id("androidx.navigation.safeargs.kotlin")
//    id("com.google.devtools.ksp") version "1.8.0-1.0.8"
}

android {
    namespace = "com.maskulka.zadanieo2"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.maskulka.zadanieo2"
        minSdk = 28
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        maybeCreate("release").apply {
            isMinifyEnabled = false
            isShrinkResources = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        viewBinding = true
        dataBinding = true
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    testOptions {
        // Used for Unit testing Android dependent elements in /test folder
        unitTests.isIncludeAndroidResources = true
        unitTests.isReturnDefaultValues = true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.activity:activity-ktx:1.6.1")
    implementation("androidx.fragment:fragment-ktx:1.5.5")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.5.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.1")

    implementation("io.insert-koin:koin-android:3.3.3")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")

    implementation("com.jakewharton.timber:timber:5.0.1")

    implementation("com.squareup.retrofit2:converter-moshi:2.9.0")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")

    implementation("com.squareup.okhttp3:logging-interceptor:4.9.3")

    implementation("com.squareup.moshi:moshi:1.14.0")
    implementation("com.squareup.moshi:moshi-kotlin:1.14.0")

    implementation("io.karn:notify:1.4.0")

    implementation("com.github.skydoves:retrofit-adapters-result:1.0.5")

    implementation("androidx.navigation:navigation-fragment-ktx:2.5.3")
    implementation("androidx.navigation:navigation-ui-ktx:2.5.3")

    testImplementation("junit:junit:4.13.2")
    testImplementation("io.cucumber:cucumber-java:7.3.4")
    testImplementation("io.cucumber:cucumber-java8:7.3.4")
    testImplementation("io.cucumber:cucumber-junit:7.3.4")
    testImplementation("io.cucumber:cucumber-android:4.9.0")
    testImplementation("com.github.mkolisnyk:cucumber-runner:1.3.5")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4")
    testImplementation("io.mockk:mockk:1.12.4")
    testImplementation("io.insert-koin:koin-test:3.3.3")
    testImplementation("io.insert-koin:koin-test-junit4:3.3.3")
    testImplementation("com.nhaarman.mockitokotlin2:mockito-kotlin:2.2.0")
    testImplementation("app.cash.turbine:turbine:0.8.0")

}