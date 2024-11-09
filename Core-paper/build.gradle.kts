/**
 * CorePaper
 */
plugins {
    id("io.github.goooler.shadow")
}

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://oss.sonatype.org/content/groups/public/")
    maven("https://ci.ender.zone/plugin/repository/everything/")
    maven("https://jitpack.io")
    maven("https://repo.essentialsx.net/releases/")
}

dependencies {
    implementation(project(":CoreCommon"))
    compileOnly("io.papermc.paper:paper-api:1.21.1-R0.1-SNAPSHOT")
    compileOnly("com.github.MilkBowl:VaultAPI:1.7.1")
    compileOnly("com.arcaniax:HeadDatabase-API:1.3.2")
    compileOnly("net.essentialsx:EssentialsX:2.20.0")
    compileOnly("com.github.plan-player-analytics:Plan:5.5.2150")

    implementation("com.github.Revxrsal.Lamp:common:3.3.0")
    implementation("com.github.Revxrsal.Lamp:bukkit:3.3.0")

    implementation("com.github.technicallycoded:FoliaLib:main-SNAPSHOT")
}

tasks.shadowJar {
    archiveClassifier.set("")

    relocate("com.tcoded.folialib", "fr.iban.servercore.libs.folialib")
    //From common
    relocate("com.zaxxer.hikari", "fr.iban.servercore.libs.hikari")
    relocate("redis.clients.jedis", "fr.iban.servercore.libs.jedis")
}

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
