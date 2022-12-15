plugins {
    kotlin("jvm")
    application
}

application {
    mainClass.set("ua.olehlypskyi.adventofcode.thirteen.MainKt")
}
dependencies {
    testImplementation(kotlin("test"))
}