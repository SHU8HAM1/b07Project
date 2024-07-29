plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.google.gms.google.services)
}

android {
    buildToolsVersion = "29.0.3"
    namespace = "com.example.b07project"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.b07project"
        minSdk = 26
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
}


dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.firebase.database)
    implementation(libs.firebase.storage)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation("com.github.bumptech.glide:glide:4.11.0")
    implementation("com.firebaseui:firebase-ui-storage:7.2.0")
    annotationProcessor(libs.compiler)
    try {
        annotationProcessor ("com.github.bumptech.glide:compiler:4.11.0")
    } catch (e: Exception) {
        TODO("Not yet implemented")
    }
    implementation ("com.squareup.picasso:picasso:2.8")
    implementation ("androidx.activity:activity-ktx:1.8.0") // Check for the latest version
    implementation ("androidx.fragment:fragment-ktx:1.6.0")
    implementation("com.itextpdf:itext7-core:7.1.15")
    implementation ("androidx.appcompat:appcompat:1.3.0-alpha01")
}