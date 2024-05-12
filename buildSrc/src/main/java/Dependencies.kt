import org.gradle.api.JavaVersion

object Config {
    const val application_id = "ru.profitsw2000.win7calculator"
    const val compile_sdk = 34
    const val min_sdk = 24
    const val target_sdk = 34
    val java_version = JavaVersion.VERSION_1_8
}

object Releases {
    const val version_code = 1
    const val version_name = "1.0"
}

object Modules {
    const val app = ":app"
}

