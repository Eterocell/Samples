import com.eterocell.gradle.dsl.configureAndroidCommon
import com.eterocell.gradle.dsl.libs
import gradle.kotlin.dsl.accessors._8d2461a2f5612be46a1501c2fc5a80cd.coreLibraryDesugaring

plugins {
    id("com.android.base")
    id("build-logic.kotlin.android")
}

configureAndroidCommon {
    namespace = androidNamespace
    compileSdk = 36
    buildToolsVersion = "36.0.0"
    defaultConfig {
        minSdk = 24
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
        isCoreLibraryDesugaringEnabled = true
    }
}

dependencies {
    coreLibraryDesugaring(libs.findLibrary("android-desugar-jdk-libs").get())
}

val Project.androidNamespace
    get() =
        path
            .replace(":", ".")
            .let { if (it == ".app") "" else it.replace("-", ".") }
            .let { extra["samples.project.group"] as String + it }
