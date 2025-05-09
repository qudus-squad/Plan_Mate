plugins {
     kotlin("jvm") version "2.0.0"
}

group = "org.qudus.squad"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("com.squareup.okio:okio:3.7.0")

    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.5.0")

    // koin
    implementation("io.insert-koin:koin-core:4.0.3")
    // junit
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.13.0-M1")

    // kotest
    testImplementation("io.kotest:kotest-runner-junit5:6.0.0.M3")
    testImplementation("io.kotest:kotest-assertions-core:6.0.0.M3")

    // parameterized test
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.10.0")

    // mockk
    testImplementation("io.mockk:mockk:1.14.0")

    // coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.0")

    // For Kotlin support of MongoDB
    implementation("org.mongodb:mongodb-driver-kotlin-coroutine:4.10.1")

}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}