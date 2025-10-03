plugins {
    id("org.jetbrains.kotlin.jvm") version "2.1.20" apply false
    id("org.jetbrains.kotlin.plugin.serialization") version "2.1.10" apply false
}

subprojects {
    repositories {
        mavenCentral()
        maven("https://repo.repsy.io/mvn/likespro/maven")
    }

    plugins.withId("org.jetbrains.kotlin.jvm") {
        extensions.configure<org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension> {
            jvmToolchain(17)
        }
    }
}
