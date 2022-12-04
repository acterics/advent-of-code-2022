pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }

    plugins {
        kotlin("jvm").version(extra["kotlin.version"] as String)
    }
}


enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

dependencyResolutionManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
    versionCatalogs {
        create("deps") {
            from(files("deps.versions.toml"))
        }
    }
}

include(":first-day")
project(":first-day").projectDir = File("./1")

include(":second-day")
project(":second-day").projectDir = File("./2")

include(":third-day")
project(":third-day").projectDir = File("./3")

include(":fourth-day")
project(":fourth-day").projectDir = File("./4")