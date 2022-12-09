plugins {
    kotlin("jvm")
    application
}

application {
    mainClass.set("ua.olehlypskyi.adventofcode.ninth.MainKt")
}
dependencies {
    testImplementation(kotlin("test"))
}