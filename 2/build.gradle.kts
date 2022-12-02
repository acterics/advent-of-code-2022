plugins {
    kotlin("jvm")
    application
}

application {
    mainClass.set("ua.olehlypskyi.adventofcode.second.MainKt")
}
dependencies {
    testImplementation(kotlin("test"))
}