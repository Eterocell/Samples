plugins {
    `kotlin-dsl`
}

dependencies {
    // DSL
    compileOnly(embeddedKotlin("gradle-plugin"))
    implementation(libs.gradle.plugin.android)
    implementation(libs.gradle.plugin.ksp)
    implementation(libs.gradle.plugin.room)
    implementation(libs.gradle.plugin.hilt)

    implementation(libs.kotlin.stdlib)
    implementation(libs.gradle.plugin.spotless)
    implementation(libs.gradle.plugin.kotlin)
    implementation(libs.gradle.plugin.compose.compiler)
}
