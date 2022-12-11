plugins {
    kotlin("jvm")
    application
}

application {
    mainClass.set("ua.olehlypskyi.adventofcode.eleventh.MainKt")
}
dependencies {
    testImplementation(kotlin("test"))
}