import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    java
    id("io.github.goooler.shadow") version "8.1.7"
}

allprojects {
    apply(plugin = "java")
    apply(plugin = "io.github.goooler.shadow")

    group = "fr.iban"
    version = "1.0"

    repositories {
        mavenCentral()
        maven("https://repo.papermc.io/repository/maven-public/")
        maven("https://jitpack.io")
    }
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

subprojects {
    tasks.register<Copy>("copyJar") {
        doFirst {
            mkdir("../libs/")
        }
        from(tasks.named("shadowJar"))
        into("../libs/")
    }

    tasks.build {
        finalizedBy("copyJar")
    }

    tasks.named<ShadowJar>("shadowJar") {
        archiveClassifier.set("")

        relocate("com.tcoded.folialib", "fr.iban.servercore.lib.folialib")
        relocate("com.zaxxer.hikari", "fr.iban.servercore.libs.hikari")
        relocate("redis.clients.jedis", "fr.iban.servercore.libs.jedis")

    }
}
