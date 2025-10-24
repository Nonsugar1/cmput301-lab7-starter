plugins {
    id("com.android.application")
}

android {
    namespace = "com.example.androiduitesting"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.androiduitesting"
        minSdk = 24
        targetSdk = 33
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
    // app code
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    // unit test
    testImplementation("junit:junit:4.13.2")

    // android instrumented tests
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")

    // --- add the following for this lab ---
    // Intents assertions (to verify activity switching)
    androidTestImplementation("androidx.test.espresso:espresso-intents:3.6.1")
    // Rules & core testing utilities
    androidTestImplementation("androidx.test:rules:1.6.1")
    androidTestImplementation("androidx.test:core:1.6.1")
    // Hamcrest matchers (is(), instanceOf(), anything(), etc.)
    androidTestImplementation("org.hamcrest:hamcrest-library:2.2")
}