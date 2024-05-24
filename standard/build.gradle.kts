plugins {
    alias(libs.plugins.win7calc.main.lib.gradle.plugin)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "ru.profitsw2000.standard"
}

dependencies {
    implementation(project(":core"))
    implementation(project(":data"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
