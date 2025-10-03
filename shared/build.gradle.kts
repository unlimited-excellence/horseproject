plugins {
    kotlin("jvm") version "2.1.20"
    id("org.jetbrains.kotlin.plugin.serialization") version "2.1.10"
}

group = "com.axus.id"
version = "0.0.1"

repositories {
    mavenCentral()
    maven("https://repo.repsy.io/mvn/likespro/maven")
    mavenLocal() // TODO Remove this when commons is published
}

dependencies {
    api(project(":core"))

    implementation("io.github.likespro:axusid-client:0.0.1-1")
    implementation("io.github.likespro:atomarix-core:1.0.0-1-disabled")
    implementation("io.github.likespro:commons-core:3.2.0-1")
    implementation("io.github.likespro:commons-reflection:3.2.0-1")
    implementation("io.github.likespro:lpfcp-core:1.2.0-1")
    implementation("io.ktor:ktor-serialization-gson:3.1.3")
    testImplementation(kotlin("test"))

    implementation("org.jetbrains.exposed:exposed-core:0.61.0")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.61.0")
    implementation("org.jetbrains.exposed:exposed-java-time:0.61.0")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}