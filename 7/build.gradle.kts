plugins {
    kotlin("jvm")
    application
}

application {
    mainClass.set("ua.olehlypskyi.adventofcode.seventh.MainKt")
}
dependencies {
    testImplementation(kotlin("test"))
}