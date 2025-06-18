import com.eterocell.gradle.dsl.configureAndroidCommon

plugins {
    id("org.jetbrains.kotlin.plugin.compose")
}

configureAndroidCommon {
    defaultConfig.vectorDrawables { useSupportLibrary = true }
    buildFeatures {
        compose = true
    }
}
