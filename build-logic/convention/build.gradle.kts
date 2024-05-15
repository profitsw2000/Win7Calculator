plugins {
    `kotlin-dsl`
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("mainApplicationGradlePlugin") {
            id = "win7calc.main.app.gradle.plugin"
            implementationClass = "MainApplicationGradlePlugin"
        }
        register("mainLibraryGradlePlugin") {
            id = "win7calc.main.lib.gradle.plugin"
            implementationClass = "MainLibraryGradlePlugin"
        }
    }
}