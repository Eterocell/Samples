import com.eterocell.gradle.dsl.libs

plugins {
    id("com.google.devtools.ksp")
}

dependencies {
    add("ksp", libs.findLibrary("hilt.compiler").get())
    add("implementation", libs.findLibrary("hilt.core").get())
}

pluginManager.withPlugin("com.android.base") {
    plugins {
        id("dagger.hilt.android.plugin")
    }
    dependencies {
        add("implementation", libs.findLibrary("hilt.android").get())

        add("testImplementation", libs.findLibrary("hilt.android.testing").get())
        add("kspTest", libs.findLibrary("hilt.compiler").get())

        add("androidTestImplementation", libs.findLibrary("hilt.android.testing").get())
        add("kspAndroidTest", libs.findLibrary("hilt.compiler").get())
    }
}
