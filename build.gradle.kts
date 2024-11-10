plugins {
    java
    id("io.github.goooler.shadow") version "8.1.7"
    `maven-publish`
}

allprojects {
    apply(plugin = "java")
    apply(plugin = "maven-publish")

    group = "fr.iban.servercore"
    version = "1.0.0"

    repositories {
        mavenCentral()
        maven("https://repo.papermc.io/repository/maven-public/")
        maven("https://jitpack.io")
    }
}

subprojects {
    tasks.processResources {
        filesMatching("plugin.yml") {
            expand(
                "project_version" to project.version
            )
        }
    }
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}
