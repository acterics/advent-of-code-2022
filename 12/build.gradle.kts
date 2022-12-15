plugins {
    kotlin("jvm")
    application
}

application {
    mainClass.set("ua.olehlypskyi.adventofcode.twelve.MainKt")
}
dependencies {
    testImplementation(kotlin("test"))
}