import com.android.build.api.dsl.ApplicationExtension
import gradleplugins.configureKotlinAndroid
import gradleplugins.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class MainApplicationGradlePlugin: Plugin<Project> {
    override fun apply(project: Project) {
        with(project) {
            with(project.pluginManager) {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
                apply("org.jetbrains.kotlin.kapt")
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
                applicationId = libs.findVersion("applicationId").get().toString()
                targetSdk = libs.findVersion("targetSdk").get().toString().toInt()
                versionCode = libs.findVersion("versionCode").get().toString().toInt()
                versionName = libs.findVersion("versionName").get().toString()
            }
        }
    }

}