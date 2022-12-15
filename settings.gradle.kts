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

include(":fifth-day")
project(":fifth-day").projectDir = File("./5")

include(":sixth-day")
project(":sixth-day").projectDir = File("./6")

include(":seventh-day")
project(":seventh-day").projectDir = File("./7")

include(":eighth-day")
project(":eighth-day").projectDir = File("./8")

include(":ninth-day")
project(":ninth-day").projectDir = File("./9")

include(":tenth-day")
project(":tenth-day").projectDir = File("./10")

include(":eleventh-day")
project(":eleventh-day").projectDir = File("./11")

include(":twelve-day")
project(":twelve-day").projectDir = File("./12")

include(":thirteen-day")
project(":thirteen-day").projectDir = File("./13")