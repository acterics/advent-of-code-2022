plugins {
    kotlin("jvm")
    application
}

application {
    mainClass.set("ua.olehlypskyi.adventofcode.third.MainKt")
}
dependencies {
    testImplementation(kotlin("test"))
}