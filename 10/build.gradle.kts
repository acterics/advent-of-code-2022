plugins {
    kotlin("jvm")
    application
}

application {
    mainClass.set("ua.olehlypskyi.adventofcode.tenth.MainKt")
}
dependencies {
    testImplementation(kotlin("test"))
}