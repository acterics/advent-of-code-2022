plugins {
    kotlin("jvm")
    application
}

application {
    mainClass.set("ua.olehlypskyi.adventofcode.eighth.MainKt")
}
dependencies {
    testImplementation(kotlin("test"))
}