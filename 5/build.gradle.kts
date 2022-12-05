plugins {
    kotlin("jvm")
    application
}

application {
    mainClass.set("ua.olehlypskyi.adventofcode.fifth.MainKt")
}
dependencies {
    testImplementation(kotlin("test"))
}