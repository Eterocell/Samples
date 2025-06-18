import com.eterocell.gradle.dsl.kotlinCompile
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    kotlin("android")
}

kotlinCompile {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_1_8

        val warningsAsErrors: String? by project
        allWarningsAsErrors = warningsAsErrors.toBoolean()
        freeCompilerArgs =
            listOf(
                "-opt-in=kotlin.RequiresOptIn",
                "-opt-in=kotlinx.coroutines.FlowPreview",
            )
    }
}
