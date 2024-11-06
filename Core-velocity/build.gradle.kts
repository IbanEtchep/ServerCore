/**
 * CoreVelocity
 */
repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://repo.kryptonmc.org/releases")
    maven("https://jitpack.io")
    maven("https://repo.opencollab.dev/maven-snapshots/")
    maven("https://repo.viaversion.com")
    maven("https://repo.minebench.de/")
    maven("https://repo.william278.net/releases/")
}

dependencies {
    implementation(project(":CoreCommon"))
    implementation("org.ocpsoft.prettytime:prettytime:5.0.3.Final")
    implementation("com.github.Revxrsal.Lamp:common:3.1.7")
    implementation("com.github.Revxrsal.Lamp:velocity:3.1.7")
    implementation("dev.dejvokep:boosted-yaml:1.3.4")
    implementation("de.themoep:minedown-adventure:1.7.1-SNAPSHOT")
    implementation("mysql:mysql-connector-java:8.0.33")
    implementation("org.apache.commons:commons-pool2:2.12.0")

    compileOnly("com.velocitypowered:velocity-api:3.3.0-SNAPSHOT")
    compileOnly("net.luckperms:api:5.4")
    compileOnly("me.neznamy:tab-api:4.0.0")
    compileOnly("org.geysermc.floodgate:api:2.2.0-SNAPSHOT")
    compileOnly("net.william278:papiproxybridge:1.5")
}

tasks.shadowJar {
    relocate("dev.dejvokep.boostedyaml", "fr.iban.velocitycore.libs.boostedyaml")
    relocate("de.themoep.minedown", "fr.iban.velocitycore.libs.minedown")
    relocate("com.mysql.cj", "fr.iban.velocitycore.libs.mysql")
    relocate("org.apache.commons.pool2", "fr.iban.velocitycore.libs.commons.pool2")
}

tasks.compileJava {
    dependsOn(":CoreCommon:shadowJar")
}