plugins {
    `kotlin-dsl`
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("mainGradlePlugin") {
            id = "win7calc.main.gradle.plugin"
            implementationClass = "MainGradlePlugin"
        }
    }
}