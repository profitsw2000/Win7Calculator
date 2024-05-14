import com.android.build.api.dsl.ApplicationExtension
import gradleplugins.configureKotlinAndroid
import gradleplugins.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class MainGradlePlugin: Plugin<Project> {
    override fun apply(project: Project) {
        with(project) {
            with(project.pluginManager) {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
                //apply("com.android.library")
                //apply("org.jetbrains.kotlin.kapt")
            }

            extensions.configure<ApplicationExtension> {
                configureKotlinAndroid(this)
                configureDefaultConfig(project)
            }
        }
    }

    private fun ApplicationExtension.configureDefaultConfig(project: Project) {
        with(project) {
            defaultConfig {
                applicationId = "ru.profitsw2000.win7calculator"//libs.findVersion("applicationId").get().toString()
                targetSdk = 34//libs.findVersion("targetSdk").get().toString().toInt()
                versionCode = 1//libs.findVersion("versionCode").get().toString().toInt()
                versionName = "1.0"//libs.findVersion("versionName").get().toString()
            }
        }
    }

}