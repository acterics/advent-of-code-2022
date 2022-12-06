plugins {
    kotlin("jvm")
    application
}

application {
    mainClass.set("ua.olehlypskyi.adventofcode.sixth.MainKt")
}
dependencies {
    testImplementation(kotlin("test"))
}