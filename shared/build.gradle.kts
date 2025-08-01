plugins {
    kotlin("jvm") version "2.1.20"
    id("org.jetbrains.kotlin.plugin.serialization") version "2.1.10"
}

group = "com.axus.id"
version = "0.0.1"

repositories {
    mavenCentral()
    mavenLocal() // TODO Remove this when commons is published
}

dependencies {
    api(project(":core"))

    implementation("io.github.likespro:axusid-client:0.0.1")
    implementation("io.github.likespro:atomarix-core:1.0.0")
    implementation("io.github.likespro:commons-core:3.1.0")
    implementation("io.github.likespro:commons-reflection:3.1.0")
    implementation("io.github.likespro:lpfcp-core:1.1.0")
    implementation("io.ktor:ktor-serialization-gson:3.1.3")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}