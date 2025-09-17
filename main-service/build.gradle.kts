plugins {
    kotlin("jvm") version "2.1.20"
    id("io.ktor.plugin") version "3.1.3"
    id("org.jetbrains.kotlin.plugin.serialization") version "2.1.10"

}

group = "com.axus.id"
version = "0.0.1"

application {
    mainClass = "io.ktor.server.netty.EngineMain"
}

repositories {
    mavenCentral()
    maven("https://repo.repsy.io/mvn/likespro/maven")
    mavenLocal() // TODO Remove this when commons is published
}

dependencies {
    implementation(project(":shared"))

    implementation("io.github.likespro:axusid-client:0.0.1")

    implementation("io.github.likespro:atomarix-core:1.0.0-1-disabled")
    implementation("io.github.likespro:atomarix-exposed:1.0.0-1-disabled")
    implementation("io.github.likespro:commons-core:3.2.0-1")
    implementation("io.github.likespro:commons-reflection:3.2.0-1")
    implementation("io.github.likespro:commons-network:3.2.0-1")
    implementation("io.github.likespro:lpfcp-core:1.2.0-1")
    implementation("io.github.likespro:lpfcp-ktor:1.2.0-1")
    implementation("io.github.oshai:kotlin-logging-jvm:5.1.0")

    implementation("io.insert-koin:koin-ktor:3.5.0")
    implementation("io.ktor:ktor-server-core")
    implementation("io.ktor:ktor-server-netty")
    implementation("io.ktor:ktor-server-content-negotiation")
    implementation("io.ktor:ktor-serialization-kotlinx-json")
    implementation("org.jetbrains.exposed:exposed-core:0.61.0")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.61.0")
    implementation("org.jetbrains.exposed:exposed-java-time:0.61.0")
    implementation("io.ktor:ktor-server-config-yaml")
    implementation("ch.qos.logback:logback-classic:1.5.13")
    implementation ("org.postgresql:postgresql:42.7.2")
    implementation("io.ktor:ktor-serialization-gson:3.1.3")

    testImplementation("io.ktor:ktor-server-test-host")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}