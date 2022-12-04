plugins {
    kotlin("jvm")
    application
}

application {
    mainClass.set("ua.olehlypskyi.adventofcode.fourth.MainKt")
}
dependencies {
    testImplementation(kotlin("test"))
}