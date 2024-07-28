plugins {
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id("kotlin-parcelize")
}

android {
    namespace = "com.example.evalutionpractical"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.evalutionpractical"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        dataBinding = true
        viewBinding = true
    }
}

dependencies {
    val navVersion = "2.7.7"
    val roomVersion = "2.6.1"
    val ssp = "1.0.6"
    val sdp = "1.0.6"
    val hiltVersion = "2.48"
    val     retrofitVersion = "2.9.0"
    val okHttpVersion = "4.9.0"
    val coroutinesVersion = "1.4.1"

    implementation ("com.google.code.gson:gson:2.8.9")


    implementation( "org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion")

    implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:$okHttpVersion")

    //Hilt Dependency Injection
    implementation("com.google.dagger:hilt-android:$hiltVersion")
    kapt("com.google.dagger:hilt-android-compiler:$hiltVersion")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")

    //dimen
    implementation("com.intuit.sdp:sdp-android:$sdp")
    implementation("com.intuit.ssp:ssp-android:$ssp")

    kapt("androidx.room:room-compiler:$roomVersion")
    implementation("androidx.room:room-runtime:$roomVersion")
    annotationProcessor("androidx.room:room-compiler:$roomVersion")

    implementation("androidx.navigation:navigation-fragment-ktx:$navVersion")
    implementation("androidx.navigation:navigation-ui-ktx:$navVersion")

    implementation ("com.github.PhilJay:MPAndroidChart:v3.1.0")

    implementation ("com.github.zhpanvip:viewpagerindicator:1.2.3")

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}