import com.eterocell.gradle.dsl.configureAndroidApplication
import com.eterocell.gradle.dsl.configureAppSigningConfigsForRelease

plugins {
    id("com.android.application")
    id("build-logic.android.base")
}

configureAndroidApplication {
    defaultConfig {
        applicationId = extra["samples.project.group"].toString()
        targetSdk = 36

        versionCode = extra["samples.project.version.code"].toString().toInt()
        versionName = extra["samples.project.version.name"].toString()
    }
}

configureAppSigningConfigsForRelease()
