import com.eterocell.gradle.dsl.libs

plugins {
    id("androidx.room")
    id("com.google.devtools.ksp")
}

ksp {
    arg("room.generateKotlin", "true")
}

room {
    schemaDirectory("$projectDir/schemas")
}

dependencies {
    dependencies {
        add("implementation", libs.findLibrary("androidx.room.runtime").get())
        add("implementation", libs.findLibrary("androidx.room.ktx").get())
        add("ksp", libs.findLibrary("androidx.room.compiler").get())
    }
}
