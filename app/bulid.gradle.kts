plugins {
    id("com.android.application")
}

android {
    namespace = "com.xg89.ai"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.xg89.ai"
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.17.0")
    implementation("androidx.activity:activity-ktx:1.10.1")
    implementation("androidx.appcompat:appcompat:1.8.0")
}
