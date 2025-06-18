import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.compose.compiler) apply false

    alias(libs.plugins.gradle.versions)
    alias(libs.plugins.version.catalog.update)

    id("build-logic.root-project")
}

tasks.named("clean") {
    allprojects.forEach { project ->
        delete(project.layout.buildDirectory)
        delete(files("${project.layout.projectDirectory.asFile.absolutePath}/.cxx"))
    }
}

fun isNonStable(version: String): Boolean {
    val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { version.uppercase().contains(it) }
    val regex = "^[0-9,.v-]+(-r)?$".toRegex()
    val isStable = stableKeyword || regex.matches(version)
    return isStable.not()
}

tasks.withType<DependencyUpdatesTask> {
    val nonStableModuleAllowList = listOf("androidx.security:security-state")
    val nonStableGroupAllowList =
        listOf(
            "com.squareup.okhttp3",
            "androidx.biometric",
            "androidx.camera",
            "androidx.metrics",
        )

    rejectVersionIf {
        if (candidate.moduleIdentifier.toString() == "com.google.guava:guava") {
            return@rejectVersionIf "-jre" in candidate.version
        }
        !nonStableGroupAllowList.contains(candidate.group) &&
            !nonStableModuleAllowList.contains(candidate.module) &&
            isNonStable(candidate.version)
    }
}

versionCatalogUpdate {
    sortByKey = true
    keep {
        keepUnusedVersions = true
    }
}
