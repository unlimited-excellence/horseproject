val GROUP = "io.github.likespro" // Until normal group ID - com.axus.id
val VERSION = "0.0.1"
val NAME = "AXUS ID Client"
val DESCRIPTION = "Client library for AXUS ID identity system."
val URL = "https://axusid.unlimitedexcellence.org"

val LICENSES: MavenPomLicenseSpec.(project: Project) -> Unit = { project ->
    license {
        name.set("Mozilla Public License 2.0")
        url.set("https://www.mozilla.org/en-US/MPL/2.0/")
    }
}
val DEVELOPERS: MavenPomDeveloperSpec.() -> Unit = {
    developer {
        id.set("likespro")
        email.set("likespro.eth@gmail.com")
    }
}
val SCM: MavenPomScm.() -> Unit = { }

val PUBLISHED_ARTIFACTS_PREFIX = "axusid-" // Until normal group ID - eth.likespro.commons

plugins {
    kotlin("jvm") version "2.1.20"
    id("org.jetbrains.kotlin.plugin.serialization") version "2.1.10"
    id("jacoco")
    id("org.jetbrains.dokka") version "1.9.0"
    id("maven-publish")
    id("signing")
    id("com.gradleup.nmcp").version("0.1.4")
}

group = GROUP
version = VERSION

repositories {
    mavenCentral()
    maven("https://repo.repsy.io/mvn/likespro/maven")
}

kotlin {
    jvmToolchain(17)
}

dependencies {
    api("io.github.likespro:axusid-core:0.0.1-1")
    implementation("io.github.likespro:commons-core:3.2.0-1")
    implementation("io.github.likespro:lpfcp-core:1.2.0-1")
    implementation("io.ktor:ktor-serialization-gson:3.1.3")
    testImplementation(kotlin("test"))
}

java {
    withJavadocJar()
    withSourcesJar()
}

jacoco {
    toolVersion = "0.8.10"
}

tasks.test {
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport)
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
            artifactId = PUBLISHED_ARTIFACTS_PREFIX + project.name
            pom {
                name.set(NAME)
                description.set(DESCRIPTION)
                url.set(URL)
                licenses { LICENSES(project) }
                developers { DEVELOPERS() }
                scm { SCM() }
            }
        }
    }
}

if (System.getenv("PGP_PRIVATE_KEY") != null) signing {
    sign(publishing.publications)
    useInMemoryPgpKeys(
        System.getenv("PGP_PRIVATE_KEY").replace("\\n", "\n")?.trimIndent(),
        System.getenv("PGP_PRIVATE_KEY_PASSWORD") ?: ""
    )
}

nmcp {
    centralPortal {
        username = System.getenv("SONATYPE_USERNAME")
        password = System.getenv("SONATYPE_PASSWORD")
        publishingType = "USER_MANAGED"
    }
}
