plugins {
    alias(libs.plugins.win7calc.main.app.gradle.plugin)
}

android {
    namespace = "ru.profitsw2000.win7calculator"
}

dependencies {

    implementation(project(":core"))
    implementation(project(":data"))
    implementation(project(":standard"))
    implementation(project(":scientific"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    //Koin
    implementation(libs.koin)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}